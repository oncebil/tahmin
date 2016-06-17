package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.Util;
import com.oncebil.tahmin.entity.ClassificationPrediction;
import com.oncebil.tahmin.entity.Distribution;
import org.junit.*;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
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
        Assert.assertTrue  ( prediction.getDistributions().contains(   new Distribution("326","test-classification-predictions","diaporthe-stem-canker",new BigDecimal(0.0)) ));
        Assert.assertTrue  ( prediction.getDistributions().contains(   new Distribution("326","test-classification-predictions","charcoal-rot",new BigDecimal(1.0) )));
        Assert.assertTrue( prediction.getDistributions().size() == classes.size());

    }

    @Test
    public void testSavePredictions() {
        predictions.save();
        ClassificationPredictions loadedPredicitons = ClassificationPredictions.loadWithExperiment("test-classification-predictions");
        Assert.assertTrue( loadedPredicitons.classificationPredictions.size()>0);
        Assert.assertTrue( loadedPredicitons.classificationPredictions.get(0).getDistributions().size() > 0);
    }


    @Test
    @Ignore("Have run once to create nominal test data")
    public void createNewArff() throws IOException {
        String testFilesPath = Base.getTestFilesPath();
        Instances instances = Util.loadInstances(testFilesPath + "/data/arffs/son7kosular.arff");
        System.out.println(instances.numInstances());
        FastVector atts = new FastVector();
        atts.addElement(new Attribute("KosuId_AtId", (FastVector) null));
        atts.addElement(new Attribute("Son73egirisyuzdesi") );
        atts.addElement(new Attribute("Son7BitirisOrtalamasi") );
        atts.addElement(new Attribute("Son7DereceFarkiOrtalamasi") );
        atts.addElement(new Attribute("Son3DereceFarkiOrtalamasi") );
        FastVector      attVals = new FastVector();
        attVals.addElement("Y");
        attVals.addElement("N");
        atts.addElement(new Attribute("BirinciMi",attVals ));
        Instances classificatonData = new Instances("MyRelation", atts, 0);
        double[]        vals;
        for (int i = 0 ;i<instances.numInstances();i++) {
            vals = new double[classificatonData.numAttributes()];
            vals[0] =classificatonData.attribute(0).addStringValue(instances.instance(i).stringValue(0));
            vals[1] =instances.instance(i).value(1);
            vals[2] =instances.instance(i).value(2);
            vals[3] =instances.instance(i).value(3);
            vals[4] =instances.instance(i).value(4);
            if (instances.instance(i).value(5) <=1) {
                vals[5] = 0;
            } else {
                vals[5] = 1;
            }
            classificatonData.add(new Instance(1.0, vals));
        }

        for (int i = 0 ;i<classificatonData.numInstances();i++) {
            System.out.println(classificatonData.instance(i));
        }
        Util.saveInstances( classificatonData, new File(testFilesPath + "/data/arffs/son7kosular_nominal.arff"));
    }
}
