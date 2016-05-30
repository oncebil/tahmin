package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.entity.ClassificationPrediction;
import com.oncebil.tahmin.entity.Distribution;
import com.oncebil.tahmin.entity.RegressionPrediction;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class RegressionPredictions {
    List<RegressionPrediction> regressionPredictions = new ArrayList<>();

    public static RegressionPredictions createFromPredictionsOutput(String experimentName,
                                                                    String predictionsOutput) {

        int i = 0;
        RegressionPredictions predictions = new RegressionPredictions();
        String[] lines = predictionsOutput.split(System.getProperty("line.separator"));
        for (String line : lines) {
            i++;
            if (i == 1) {
                continue;
            }
            String[] values = line.split("\\s+");
            if (values.length < 6) {
                throw new IllegalArgumentException("Unexpected predictions input");
            }
            RegressionPrediction rp = new RegressionPrediction();
            rp.setExperiment(experimentName);
            rp.setInstanceId(values[5].substring(1, values[5].length() - 1));
            rp.setActual(new BigDecimal(values[2]));
            rp.setPredicted(new BigDecimal(values[3]));
            rp.setError(new BigDecimal(values[4]));
            predictions.regressionPredictions.add(rp);

        }
        return predictions;
    }
}