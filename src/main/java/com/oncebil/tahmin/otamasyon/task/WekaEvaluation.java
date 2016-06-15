/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin.otamasyon.task;



import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.TahminException;
import com.oncebil.tahmin.Util;
import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.dao.KosuDAO;
import com.oncebil.tahmin.entity.ClassificationPrediction;
import com.oncebil.tahmin.entity.Kosu;
import com.oncebil.tahmin.entity.RegressionPrediction;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import weka.classifiers.Classifier;
import weka.classifiers.CostMatrix;
import weka.classifiers.Evaluation;

import weka.core.Instances;
import weka.core.Range;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ekarincaoglu
 */
public class WekaEvaluation extends AbstractTask {

    private final static Logger logger = Logger.getLogger(WekaEvaluation.class.getName());
    private final String classifier;
    private final String options;
    private final int random;
    private final String attributesToOutput;
    private final String evaluationCostMatrix;
    private Instances inputInstances;
    private List<Instances> inputInstancesList;
    private Object output;
    private final boolean saveExperimentResults;
    private final boolean overrideResults;
    private final boolean writePredictions;

    public WekaEvaluation(String projectName, String name, String classifier, String options, int random, String attributesToOutput, String evaluationCostMatrix, boolean saveExperimentResults, boolean overrideResults, boolean writePredictions) {
        super(projectName, name);
        this.classifier = classifier;
        this.options = options;
        this.random = random;
        this.attributesToOutput = attributesToOutput;
        this.evaluationCostMatrix = evaluationCostMatrix;
        this.saveExperimentResults = saveExperimentResults;
        this.overrideResults = overrideResults;
        this.writePredictions = writePredictions;
    }

    public final static  String RESULTS_XML_NAME = "-EvaluationResults.xml";
    public final static  String MODEL_FILE_NAME = "-Model.model";
    public final static  String PREDICTIONS_FILE_NAME = "-Predictions.txt";
    @Override
    public void run() {
        logger.log(Level.INFO, "WekaEvaluation projectname={0} name={1} classifier={2} options={3} random={4} attrattributesToOutput={5} evaluationCostMatrix={6}", new Object[]{getProjectName(), getName(), classifier, options, random, attributesToOutput,evaluationCostMatrix});
        if (inputInstances == null && inputInstancesList == null) {
            throw new IllegalArgumentException("instances can't be null. task does not have an input ? taskname=" + getName() + " projectName=" + getProjectName());
        }
        int iterationCount =1;
        if (inputInstances != null ) {
            output = new Instances(inputInstances);
        }
        else if(inputInstancesList!=null) {
            List<Instances> outputInstancesList = new ArrayList<>(inputInstancesList.size());
            for (Instances in:inputInstancesList) {
                outputInstancesList.add(  new Instances(in));
            }
            output = outputInstancesList;
            iterationCount = inputInstancesList.size();
        }
        

        try {

            for (int i=0;i<iterationCount;i++) {
                String folderName = ApplicationConstants.repositoryOut + getProjectName()+ File.separatorChar ;
                String filename = (inputInstancesList == null) ? folderName + getName() + RESULTS_XML_NAME :
                        folderName + getName() + "-" + i+ RESULTS_XML_NAME;
                if (!overrideResults && new File(filename).exists()) {
                    continue;
                }
                long start = System.nanoTime();
                Instances instances= (inputInstancesList == null) ?inputInstances:inputInstancesList.get(i);
                logger.log(Level.INFO, "Iteration=" + i +" instance count=" + instances.numInstances());
                // minumum 10 fold * 10 instances requires
//                if (instances.numInstances() < 10 * 10) {
//                    continue;
//                }
                CostMatrix costMatrix = null;
                if (evaluationCostMatrix != null) {
                    StringReader stringReader = new StringReader(evaluationCostMatrix);
                    costMatrix = new CostMatrix(stringReader);
                }
                File f = new File(folderName);
                if (!f.exists()) {
                    f.mkdirs();
                }
                Classifier classifierObj = Classifier.forName(classifier, weka.core.Utils.splitOptions(options));
                StringBuffer predsBuff = new StringBuffer();
                Evaluation evaluation = (costMatrix == null)  ? new Evaluation(instances): new Evaluation(instances, costMatrix);
                boolean classification = (instances.classAttribute().isNominal()) ? true : false;
                evaluation.crossValidateModel(classifierObj, instances, 10, new Random(1), predsBuff, new Range(attributesToOutput), true);
                if (writePredictions) {
                    String predictionsFile = (inputInstancesList == null) ? folderName + getName() + PREDICTIONS_FILE_NAME :
                            folderName + getName() + "-" + i + PREDICTIONS_FILE_NAME;
                    Util.writeToFile(predictionsFile , predsBuff.toString(),"UTF-8" );
                    logger.log(Level.INFO, "WekaEvaluation predictionsFile={0} ", new Object[]{ predictionsFile });
                }
                if (classification) {
                    ClassificationPredictions.createFromPredictionsOutput( getName(),
                            predsBuff.toString(),Util.getInstancesClassValues(instances) ).save();
                } else {
                    RegressionPredictions.createFromPredictionsOutput
                            ( getName() , predsBuff.toString() ).save();

                    KosuDAO kosuDAO = WeldGlobal.get(KosuDAO.class);
                    List<Kosu> kosular = kosuDAO.findbyExperimentWithRegressionPredictions(getName());
                    ExperimentAnalyze analyze = new ExperimentAnalyze();
                    ExperimentAnalyzeResults analyzeResults = analyze.analyze2(kosular);

                }


                //System.out.println("al=" + evaluation.predictions());
//                Path path = FileSystems.getDefault().getPath("/Users/erkinkarincaoglu/Documents/code/repository/predictions.txt");
//                String line;
//                try (
//                    BufferedWriter writer = Files.newBufferedWriter(path,
//                            StandardCharsets.UTF_8);
//                ) {
//                    while ((line = input.readLine()) != null) {
//                        writer.write(line);
//                        // must do this: .readLine() will have stripped line endings
//                        writer.newLine();
//                    }
//                }

//                Predictions preds = Predictions.createByWekaEvaluationOutput(getProjectName(),
//                        input);
//
//                ExperimentKazanciAnalizBuilder builder =
//                        new ExperimentKazanciAnalizBuilder(getProjectName()+"-"+getName() + "-" + i, preds).
//                        deleteRowsInKosuNominalTableAfterAnalyse( !saveExperimentResults);
//                ExperimentKazanciAnaliz analiz = builder.build();
//                long end = System.nanoTime();
//                NominalEvaluationResults evaluationResults = new NominalEvaluationResults(getProjectName(),
//                        getName() + "-" + i, instances.numInstances() , evaluation,analiz,(double)(end -start) / 1000000000.0 / 60.0,classification);
//                // save evaluation results


//                Serializer serializer = new Persister();
//                File source = new File(filename);
//                serializer.write(evaluationResults, source);

                // save model

                Classifier classifierObj2 = Classifier.forName(classifier, weka.core.Utils.splitOptions(options));
                classifierObj2.buildClassifier(instances);
                String modelfile = (inputInstancesList == null) ?folderName + getName() + MODEL_FILE_NAME:
                        folderName + getName() + "-" + i + MODEL_FILE_NAME;
                weka.core.SerializationHelper.write(modelfile, classifierObj2);
                logger.log(Level.INFO, "WekaEvaluation modelfile={0} ", new Object[]{ modelfile });

            }
        } catch (Exception ex) {
            throw new TahminException("failed to run task", ex);
        }

    }

    void createModelFileDir() {

    }

    @Override
    public Object getOutput() {
        return output;
    }

    @Override
    public void setInput(Object object) {
        
        if (object instanceof Instances) {
            this.inputInstances = (Instances) object;
            // need to look how it works i am stupid
        } else if (object instanceof List) {

            this.inputInstancesList = (List<Instances>)object;
        }
    }

    public static class Builder extends TaskBuilder {

        @Parameter("classifier")
        private String classifier;
        @Parameter("options")
        private String options;
        @Parameter("random")
        private String random;
        @Parameter("attributesToOutput")
        private String attributesToOutput;
        @Parameter("evaluationCostMatrix")
        private String evaluationCostMatrix;
        @Parameter("saveExperimentResults")
        private String saveExperimentResults;
        @Parameter("overrideResults")
        private String overrideResults;
        @Parameter("writePredictions")
        private String writePredictions;
        
        public Builder(String projectName,String name,TaskDefinition taskDefinition ) {
            super(projectName, name, taskDefinition);

        }

        @Override
        public WekaEvaluation build() {
            if (classifier == null) {
                throw new IllegalArgumentException("classifier can't be null");
            }
            if (options == null) {
                throw new IllegalArgumentException("options can't be null");
            }
            int intrandom = 1;
            if (random == null) {
                random = "1";
            } else {
                intrandom = Integer.parseInt(random);
            }
            if (attributesToOutput == null) {
                // First attribute is the instance id
                attributesToOutput = "1";
            }

            
            if (saveExperimentResults!= null &&
                    !(saveExperimentResults.equals( ApplicationConstants.TRUE) || saveExperimentResults.equals( ApplicationConstants.FALSE))  )  {
    
                throw new IllegalArgumentException("saveExperimentResults can be true or false");

            }
            if (overrideResults!= null &&
                    !(overrideResults.equals( ApplicationConstants.TRUE) || overrideResults.equals( ApplicationConstants.FALSE))  )  {

                throw new IllegalArgumentException("overrideResults can be true or false");

            }
            return new WekaEvaluation(getProjectName(), getName(), classifier, options, intrandom,
                    attributesToOutput, evaluationCostMatrix,
                    (saveExperimentResults != null && Boolean.parseBoolean(overrideResults)) ? true : false,
                    (overrideResults != null && Boolean.parseBoolean(overrideResults)) ? true : false,
                    (writePredictions != null && Boolean.parseBoolean(writePredictions)) ? true : false);

        }
    }
}
