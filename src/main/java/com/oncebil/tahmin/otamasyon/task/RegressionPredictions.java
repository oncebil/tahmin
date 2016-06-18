package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.dao.RegressionPredictionDAO;
import com.oncebil.tahmin.entity.RegressionPrediction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class RegressionPredictions {
    private final static Logger logger = Logger.getLogger(RegressionPredictions.class.getName());
    private RegressionPredictionDAO dao = WeldGlobal.get(RegressionPredictionDAO.class);
    private List<RegressionPrediction> regressionPredictions = new ArrayList<>();
    private String experimentName;

    public static RegressionPredictions createFromPredictionsOutput(String experimentName,
                                                                    String predictionsOutput) {


        int i = 0;
        RegressionPredictions predictions = new RegressionPredictions();
        predictions.experimentName = experimentName;
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
            predictions.getRegressionPredictions().add(rp);

        }
        return predictions;
    }

    public void save() {
        logger.info("Saving predictions experiment=" + experimentName + " size=" + regressionPredictions.size());
        dao.merge(getRegressionPredictions());
    }

    public static RegressionPredictions loadWithExperiment(String experiment) {
        RegressionPredictions predictions = new RegressionPredictions();
        predictions.setRegressionPredictions(predictions.dao.findbyExperimentName(experiment));
        return predictions;

    }

    public List<RegressionPrediction> getRegressionPredictions() {
        return regressionPredictions;
    }

    public void setRegressionPredictions(List<RegressionPrediction> regressionPredictions) {
        this.regressionPredictions = regressionPredictions;
    }
}
