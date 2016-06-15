package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.Util;
import org.junit.Ignore;
import org.junit.Test;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;

/**
 * Created by erkinkarincaoglu on 14/06/2016.
 */
public class CreateClassificationTestDataFromSon7RegressionData {

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
