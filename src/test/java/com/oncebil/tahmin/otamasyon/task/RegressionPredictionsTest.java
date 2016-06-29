package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.entity.RegressionPrediction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;


/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class RegressionPredictionsTest {

    RegressionPredictions predictions;
    @Before
    public void setup() throws UnsupportedEncodingException, FileNotFoundException {
        predictions = RegressionPredictions.
                createFromPredictionsOutput("test-regression-predictions",
                        Base.readTestFile("Cpu-LinearRegression-Predictions.txt"));
    }
    @Test
    public void testCreateFromWekaEvaluationOutput() throws IOException {
        Assert.assertNotNull( predictions);
        Assert.assertFalse( predictions.getRegressionPredictions().isEmpty());
        RegressionPrediction prediction = predictions.getRegressionPredictions().get(0);
        Assert.assertEquals( prediction.getInstanceId(), "194" );
        Assert.assertEquals( prediction.getExperiment(), "test-regression-predictions" );
        Assert.assertEquals( prediction.getActual(), new BigDecimal("70") );
        Assert.assertEquals( prediction.getPredicted(), new BigDecimal("87.282") );
        Assert.assertEquals( prediction.getError(), new BigDecimal("17.282") );
    }

    @Test
    public void testSave () {
        predictions.deleteAndInsert();
        RegressionPredictions loadedPredicitons = RegressionPredictions.loadWithExperiment("test-regression-predictions");
        Assert.assertTrue( loadedPredicitons.getRegressionPredictions().size() > 0);
    }
}
