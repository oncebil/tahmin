package com.oncebil.tahmin.entity;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * Created by erkinkarincaoglu on 14/06/2016.
 */
public class Prediction {
    private RegressionPrediction regressionPrediction;
    private ClassificationPrediction classificationPrediction;

    private final static String BIRINCI_DEGIL_CLASS = "N";
    public static Prediction create(RegressionPrediction regressionPrediction) {
        Prediction p = new Prediction();
        p.regressionPrediction = regressionPrediction;
        return p;

    }

    public static Prediction create(ClassificationPrediction classificationPrediction) {
        Prediction p = new Prediction();
        p.classificationPrediction = classificationPrediction;
        return p;
    }

    public AtKosu getAtKosu() {
        if (regressionPrediction == null) {
            return classificationPrediction.getAtKosu();
        }
        else {
            return regressionPrediction.getAtKosu();
        }
    }
    public BigDecimal getPredicted() {
        if (regressionPrediction == null) {
            return classificationPrediction.getDistributions().stream().
                    filter( d -> d.getClassificationClass().equals(BIRINCI_DEGIL_CLASS)).
                    collect(Collectors.toList()).get(0).getDistribution();
        }
        else {
            return regressionPrediction.getPredicted();
        }
    }
}
