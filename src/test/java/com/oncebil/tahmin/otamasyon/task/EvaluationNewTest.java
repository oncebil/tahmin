package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.Util;
import org.junit.Assert;
import org.junit.Test;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.io.IOException;

/**
 * Created by erkinkarincaoglu on 18/06/2016.
 */
public class EvaluationNewTest {

    @Test
    public void testGetTrainTestInstances() throws Exception {
        Instances instances = Util.loadInstances(Base.getTestFilesPath() + "/data/arffs/son7kosular.arff");
        instances.setClassIndex( instances.numAttributes() - 1);
        EvaluationNew evaluationNew = new EvaluationNew(instances);
        evaluationNew.prepareFolds( instances, 10);
        EvaluationNew.TrainTestInstances trainTestInstances = evaluationNew.getTrainTestInstances(instances,10,0);
        Assert.assertTrue(trainTestInstances.train.numInstances() > 0 );
        Assert.assertTrue(trainTestInstances.test.numInstances() > 0 );
        Assert.assertEquals( trainTestInstances.train.numInstances() +trainTestInstances.test.numInstances() ,
                instances.numInstances() );
        Assert.assertEquals("47947_21528",
                trainTestInstances.test.instance(0).stringValue(trainTestInstances.test.attribute(Util.KOSUID_ATID_ATTRIBUTE))  );
        trainTestInstances = evaluationNew.getTrainTestInstances(instances,10,9);
        Assert.assertEquals("49189_21316",
                trainTestInstances.test.instance( trainTestInstances.test.numInstances() - 1 ).stringValue(trainTestInstances.test.attribute(Util.KOSUID_ATID_ATTRIBUTE))  );
    }
}
