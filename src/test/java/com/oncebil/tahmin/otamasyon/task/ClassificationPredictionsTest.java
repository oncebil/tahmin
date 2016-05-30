package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.entity.Classification;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class ClassificationPredictionsTest {


    @Test
    public void testCreateFromWekaEvaluationOutput() throws IOException {
        ClassificationPredictions predictions = ClassificationPredictions.
                createFromPredictionsOutput("testexperiment",
                        Base.readTestFile("/SoyBean-Classification-Predictions.txt"));
        Assert.assertNotNull( predictions);
        Assert.assertFalse( predictions.classifications.isEmpty());
        Classification prediction = predictions.classifications.get(0);
        Assert.assertEquals( prediction.getActualIndex(), 2 );
        Assert.assertEquals( prediction.getActual(), "charcoal" );
        Assert.assertEquals( prediction.getPredictedIndex(), 2 );
        Assert.assertEquals( prediction.getPredicted(), "charcoal" );
        Assert.assertEquals( prediction.isError(), false );
        // this is the first attribute. this will be kosuId+atId usually
        // test purposes first one has been selected
        Assert.assertEquals( prediction.getInstanceId(), "october" );
        Assert.assertEquals( prediction.getExperiment(), "testexperiment" );


    }
}
