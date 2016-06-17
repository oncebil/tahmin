package com.oncebil.tahmin.dao;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.RegressionClassificationDataTestBase;
import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.entity.AtKosu;
import com.oncebil.tahmin.entity.Kosu;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
public class KosuDAOTest extends RegressionClassificationDataTestBase {
    @Test
    public void findKosularbyExperimentRegression() {
        Assert.assertEquals( 153, regressionKosular.size());
        Kosu ilkkosu = regressionKosular.get(0);
        Assert.assertNotNull(ilkkosu);
        Assert.assertTrue(ilkkosu.getAtlar().size() > 0);
        Assert.assertTrue(ilkkosu.getBahisler().size() > 0);
        AtKosu ilkat = ilkkosu.getAtlar().iterator().next();
        Assert.assertNotNull(ilkat);
        Assert.assertNotNull(ilkat.getRegressionPredictions());
        Assert.assertTrue(ilkat.getRegressionPredictions().size() > 0);
        Assert.assertNotNull(ilkat.getRegressionPredictions().iterator().next().getAtKosu());
    }

    @Test
    public void findKosularbyExperimentClassification() {

        Assert.assertEquals( 153, classificationKosular.size());
        Kosu ilkkosu = classificationKosular.get(0);
        Assert.assertNotNull(ilkkosu);
        Assert.assertTrue(ilkkosu.getAtlar().size() > 0);
        Assert.assertTrue(ilkkosu.getBahisler().size() > 0);
        AtKosu ilkat = ilkkosu.getAtlar().iterator().next();
        Assert.assertNotNull(ilkat);
        Assert.assertNotNull(ilkat.getClassificationPredictions());
        Assert.assertTrue(ilkat.getClassificationPredictions().size() > 0);
        Assert.assertNotNull(ilkat.getClassificationPredictions().iterator().next().getAtKosu());


    }
}
