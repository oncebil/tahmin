/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin.otamasyon.task;



import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.TahminException;
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
    

    public WekaEvaluation(String projectName, String name, String classifier, String options, int random, String attributesToOutput, String evaluationCostMatrix, boolean saveExperimentResults, boolean overrideResults) {
        super(projectName, name);
        this.classifier = classifier;
        this.options = options;
        this.random = random;
        this.attributesToOutput = attributesToOutput;
        this.evaluationCostMatrix = evaluationCostMatrix;
        this.saveExperimentResults = saveExperimentResults;
        this.overrideResults = overrideResults;
    }

    public final static  String RESULTS_XML_NAME = "-EvaluationResults.xml";
    public final static  String MODEL_FILE_NAME = "-Model.model";
    @Override
    public void run() {
        logger.log(Level.INFO, "WekaEvaluation projectname={0} name={1} classifier={2} options={3} random={4} attrattributesToOutput={5} evaluationCostMatrix={6}", new Object[]{getProjectName(), getName(), classifier, options, random, attributesToOutput,evaluationCostMatrix});
        if (inputInstances == null && inputInstancesList == null) {
            throw new IllegalArgumentException("instances can't be null. task does not have an input ? taskname=" + getName() + " projectName=" + getProjectName());
        }
        
        if (inputInstances != null ) {
            output = new Instances(inputInstances);
        }
        else if(inputInstancesList!=null) {
            List<Instances> outputInstancesList = new ArrayList<Instances>(inputInstancesList.size());
            for (Instances in:inputInstancesList) {
                outputInstancesList.add(  new Instances(in));
            }
            output = outputInstancesList;
        }

        int iterationCount =1;
        if (inputInstancesList != null) {
            iterationCount = inputInstancesList.size();
        }
        
        String[] optionsArray = null;
        try {

            for (int i=0;i<iterationCount;i++) {
                String folderName = ApplicationConstants.repositoryOut + getProjectName()+ File.separatorChar ;
                String filename;
                if (inputInstancesList == null)
                    filename = folderName + getName() + RESULTS_XML_NAME;
                else
                    filename = folderName + getName() + "-" + i+ RESULTS_XML_NAME;

               
                if (!overrideResults && new File(filename).exists()) {
                    
                    continue;
                }
                
                long start = System.nanoTime();
                Instances instances= null;
                if (inputInstancesList==null) {
                    instances = inputInstances;
                }else {
                    instances = inputInstancesList.get(i);
                }

                
                logger.log(Level.INFO, "Iteration=" + i +" instance count=" + instances.numInstances());

                // minumum 10 fold * 10 instances requires
//                if (instances.numInstances() < 10 * 10) {
//                    continue;
//                }
                CostMatrix costMatrix = null;
                Evaluation evaluation = null;
                //EvaluationEx2 evaluation = null;
                if (evaluationCostMatrix != null) {
                    StringReader stringReader = new StringReader(evaluationCostMatrix);
                    costMatrix = new CostMatrix(stringReader);
                }
                optionsArray = weka.core.Utils.splitOptions(options);
                Classifier classifierObj = Classifier.forName(classifier, optionsArray);
                StringBuffer predsBuff = new StringBuffer();

                if (costMatrix == null) {
                    evaluation = new Evaluation(instances);
                } else {
                    evaluation = new Evaluation(instances, costMatrix);
                }


                boolean classification = (instances.classAttribute().isNominal()) ? true : false;
                evaluation.crossValidateModel(classifierObj, instances, 10, new Random(1), predsBuff, new Range(attributesToOutput), true);
                BufferedReader input = new BufferedReader(new StringReader(predsBuff.toString()));

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

//                Predictions preds = Predictions.createPredictionsFromWekaEvaluationOutput(getProjectName(),
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
//                File f = new File(folderName);
//                if (!f.exists()) {
//                    f.mkdirs();
//                }
//
//                Serializer serializer = new Persister();
//                File source = new File(filename);
//                serializer.write(evaluationResults, source);

                // save model
                optionsArray = weka.core.Utils.splitOptions(options);
                Classifier classifierObj2 = Classifier.forName(classifier, optionsArray);
                classifierObj2.buildClassifier(instances);

                String modelfile;
                if (inputInstancesList == null) {
                    modelfile = folderName + getName() + MODEL_FILE_NAME;
                    File file = new File(modelfile);
                    if ( ! file.getParentFile().exists() ) {
                        file.getParentFile().mkdirs();
                    }

                    logger.log(Level.INFO, "WekaEvaluation modelfile={0} ", new Object[]{ modelfile });
                    weka.core.SerializationHelper.write(modelfile, classifierObj2);
                }
                else {
                    modelfile = folderName + getName() + "-" + i + MODEL_FILE_NAME;
                    File file = new File(modelfile);
                    if ( ! file.getParentFile().exists() ) {
                        file.getParentFile().mkdirs();
                    }

                    logger.log(Level.INFO, "WekaEvaluation modelfile={0} ", new Object[]{ modelfile });
                    weka.core.SerializationHelper.write(folderName + getName() + "-" + i + MODEL_FILE_NAME, classifierObj2);
                }



                
            }
        } catch (Exception ex) {
            throw new TahminException("failed to run task", ex);
        }

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
                // First 2 attributes are usually kosu id and at id
                attributesToOutput = "1,2";



            }

            
            if (saveExperimentResults!= null &&
                    !(saveExperimentResults.equals( ApplicationConstants.TRUE) || saveExperimentResults.equals( ApplicationConstants.FALSE))  )  {
    
                throw new IllegalArgumentException("saveExperimentResults can be true or false");

            }
            if (overrideResults!= null &&
                    !(overrideResults.equals( ApplicationConstants.TRUE) || overrideResults.equals( ApplicationConstants.FALSE))  )  {

                throw new IllegalArgumentException("overrideResults can be true or false");

            }
            

            return new WekaEvaluation(getProjectName(), getName(), classifier, options, intrandom, attributesToOutput, evaluationCostMatrix, (saveExperimentResults != null && Boolean.parseBoolean(overrideResults)) ? true : false, (overrideResults != null && Boolean.parseBoolean(overrideResults)) ? true : false );

        }
    }
}
