package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.dao.ClassificationPredictionDAO;
import com.oncebil.tahmin.entity.ClassificationPrediction;
import com.oncebil.tahmin.entity.Distribution;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class ClassificationPredictions {
    private final static Logger logger = Logger.getLogger(ClassificationPredictions.class.getName());
    List<ClassificationPrediction> classificationPredictions = new ArrayList<>();
    ClassificationPredictionDAO dao = WeldGlobal.get(ClassificationPredictionDAO.class);
    private String experimentName;

    public static ClassificationPredictions createFromPredictionsOutput(String experimentName,
                                                                        String predictionsOutput,
                                                                        List<String> classes) throws IOException {
        int i = 0;
        ClassificationPredictions predictions = new ClassificationPredictions();
        predictions.experimentName = experimentName;
        String[] lines = predictionsOutput.split(System.getProperty("line.separator"));
        for (String line : lines) {
            i++;
            String[] values = line.split("\\s+");
            if (values.length < 6) {
                throw new IllegalArgumentException("Unexpected predictions input");
            }
            if (i == 1) {
                continue;
            }
            // if error there are 5 columns
            ClassificationPrediction p = new ClassificationPrediction();
            p.setExperiment(experimentName);
            p.setActualIndex(Integer.parseInt(values[2].split(":")[0]));
            p.setActual(values[2].split(":")[1]);
            p.setPredictedIndex(Integer.parseInt(values[3].split(":")[0]));
            p.setPredicted(values[3].split(":")[1]);
            p.setError((values[4].equals("+")) ? true : false);
            int distcolumnnStart = (values[4].equals("+")) ? 5 : 4;
            String[] distributions = values[distcolumnnStart].split(",");
            List<Distribution> dists = new ArrayList<Distribution>();
            int j = 0;
            for (String distributionText : distributions) {
                if (distributionText.startsWith("*")) {
                    distributionText = distributionText.substring(1);
                }
                Distribution distribution = new Distribution();
                distribution.setExperiment(experimentName);
                distribution.setDistribution(new BigDecimal(Double.parseDouble(distributionText)));
                distribution.setInstanceId(values[distcolumnnStart + 1].substring(1, values[distcolumnnStart + 1].length() - 1));
                distribution.setClassificationClass(classes.get(j));
                p.getDistributions().add(distribution);
                j++;
            }
            p.setInstanceId(values[distcolumnnStart + 1].substring(1, values[distcolumnnStart + 1].length() - 1));
            predictions.classificationPredictions.add(p);
        }
       return predictions;
    }


    public void deleteAndInsert() {
        logger.info("Saving predictions experiment=" + experimentName + " size=" + classificationPredictions.size());
        dao.deleteAndInsert(classificationPredictions);

    }
    public static ClassificationPredictions loadWithExperiment(String experiment) {
        ClassificationPredictions predictions = new ClassificationPredictions();
        predictions.classificationPredictions =predictions.dao.findByExperimentName(experiment);
        return predictions;

    }

}
