package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.Util;
import com.oncebil.tahmin.entity.ClassificationPrediction;
import com.oncebil.tahmin.entity.Distribution;
import org.junit.Assert;
import org.junit.Test;
import weka.core.Instances;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class ClassificationPredictionsTest {


    @Test
    public void testCreateFromWekaEvaluationOutput() throws IOException {
        Instances instances = Util.loadInstances( Base.getTestFile("data/testdataname/soybean.arff") );
        instances.setClassIndex( instances.numAttributes() -1);
        List<String> classes = Util.getInstancesClassValues(instances);
        ClassificationPredictions predictions = ClassificationPredictions.
                createFromPredictionsOutput("testexperiment",
                        Base.readTestFile("SoyBean-Classification-Predictions.txt"),
                        classes);
        Assert.assertNotNull( predictions);
        Assert.assertFalse( predictions.classificationPredictions.isEmpty());
        ClassificationPrediction prediction = predictions.classificationPredictions.get(0);
        Assert.assertEquals( prediction.getActualIndex(), 2 );
        Assert.assertEquals( prediction.getActual(), "charcoal" );
        Assert.assertEquals( prediction.getPredictedIndex(), 2 );
        Assert.assertEquals( prediction.getPredicted(), "charcoal" );
        Assert.assertEquals( prediction.isError(), false );
        // this is the first attribute. this will be kosuId+atId usually
        // test purposes first attribute  has been selected as instance id
        Assert.assertEquals( prediction.getInstanceId(), "october" );
        Assert.assertEquals( prediction.getExperiment(), "testexperiment" );
        Assert.assertEquals(new Distribution("october","testexperiment","diaporthe-stem-canker",new BigDecimal(0.0)),prediction.getDistributions().get(0));
        Assert.assertEquals(new Distribution("october","testexperiment","charcoal-rot",new BigDecimal(1.0)),prediction.getDistributions().get(1));
        Assert.assertTrue( predictions.classificationPredictions.get(14).isError() );
    }
}
