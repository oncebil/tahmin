package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.entity.RegressionPrediction;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;


/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class RegressionPredictionsTest {
    @Test
    public void testCreateFromWekaEvaluationOutput() throws IOException {

        RegressionPredictions predictions = RegressionPredictions.
                createFromPredictionsOutput("test-regression-predictions",
                        Base.readTestFile("Cpu-LinearRegression-Predictions.txt"));
        Assert.assertNotNull( predictions);
        Assert.assertFalse( predictions.regressionPredictions.isEmpty());
        RegressionPrediction prediction = predictions.regressionPredictions.get(0);
        // this is the first attribute. this will be kosuId+atId usually
        //  first attribute  has been selected as instance id for testing purposes
        Assert.assertEquals( prediction.getInstanceId(), "116" );
        Assert.assertEquals( prediction.getExperiment(), "test-regression-predictions" );
        Assert.assertEquals( prediction.getActual(), new BigDecimal("70") );
        Assert.assertEquals( prediction.getPredicted(), new BigDecimal("87.282") );
        Assert.assertEquals( prediction.getError(), new BigDecimal("17.282") );
    }
}
