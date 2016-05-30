package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.Util;
import com.oncebil.tahmin.entity.ClassificationPrediction;
import com.oncebil.tahmin.entity.Distribution;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import weka.core.Instances;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class ClassificationPredictionsTest {

    ClassificationPredictions predictions;
    List<String> classes;
    @Before
    public void setup() throws IOException {
        Instances instances = Util.loadInstances( Base.getTestFile("data/arffs/soybean-withid.arff") );
        instances.setClassIndex( instances.numAttributes() -1);
        classes = Util.getInstancesClassValues(instances);
        predictions = ClassificationPredictions.
                createFromPredictionsOutput("test-classification-predictions",
                        Base.readTestFile("SoyBean-Classification-Predictions.txt"),
                        classes);
    }
    @Test
    public void testCreateFromWekaEvaluationOutput() throws IOException {
        Assert.assertNotNull( predictions);
        Assert.assertFalse( predictions.classificationPredictions.isEmpty());
        ClassificationPrediction prediction = predictions.classificationPredictions.get(0);
        Assert.assertEquals( prediction.getInstanceId(), "326" );
        Assert.assertEquals( prediction.getExperiment(), "test-classification-predictions" );
        Assert.assertEquals( prediction.getActualIndex(), 2 );
        Assert.assertEquals( prediction.getActual(), "charcoal" );
        Assert.assertEquals( prediction.getPredictedIndex(), 2 );
        Assert.assertEquals( prediction.getPredicted(), "charcoal" );
        Assert.assertEquals( prediction.isError(), false );
        Assert.assertEquals(new Distribution("326","test-classification-predictions","diaporthe-stem-canker",new BigDecimal(0.0)),prediction.getDistributions().get(0));
        Assert.assertEquals(new Distribution("326","test-classification-predictions","charcoal-rot",new BigDecimal(1.0)),prediction.getDistributions().get(1));
        Assert.assertTrue( prediction.getDistributions().size() == classes.size());

    }

    @Test
    public void testSavePredictions() {
        predictions.save();
        ClassificationPredictions loadedPredicitons = ClassificationPredictions.loadWithExperiment("test-classification-predictions");
        Assert.assertTrue( loadedPredicitons.classificationPredictions.size()>0);
        Assert.assertTrue( loadedPredicitons.classificationPredictions.get(0).getDistributions().size() > 0);


    }
}
