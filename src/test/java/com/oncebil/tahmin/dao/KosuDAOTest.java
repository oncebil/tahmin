package com.oncebil.tahmin.dao;

import com.oncebil.tahmin.Base;
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
public class KosuDAOTest {
    private KosuDAO kosuDAO;
    @Before
    public void before() throws Exception {
        kosuDAO = WeldGlobal.get(KosuDAO.class);
        Base.insertTestData("test_son7kosu_kstar_predictions_data.xml");
    }
    @Test
    public void findKosularbyExperimentName() {
        List<Kosu> kosular = kosuDAO.findbyExperimentName("test-son7kosu-kstar-experiment");
        Assert.assertEquals( 153, kosular.size());
        Kosu ilkkosu = kosular.get(0);
        Assert.assertNotNull(ilkkosu);
        Assert.assertTrue(ilkkosu.getAtlar().size() > 0);
        Assert.assertTrue(ilkkosu.getBahisler().size() > 0);
        AtKosu ilkat = ilkkosu.getAtlar().iterator().next();
        Assert.assertNotNull(ilkat);
        Assert.assertTrue(ilkat.getRegressionPredictions().size() > 0);



    }
}
