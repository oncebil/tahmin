package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.dao.KosuDAO;
import com.oncebil.tahmin.entity.Kosu;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 08/06/2016.
 */
public class KazancSiraliIkiliTest {

    KosuDAO kosuDAO;

    @Before
    public void before() throws Exception {
        kosuDAO = WeldGlobal.get(KosuDAO.class);
        Base.insertTestData("test_son7kosu_kstar_predictions_data.xml");
    }

    @Test
    public void testSiraliIkiliKazanc() {
        List<Kosu> kosular = kosuDAO.findbyExperimentWithRegressionPredictions("test-son7kosu-kstar-experiment");
        KazancSiraliIkili kazancSiraliIkili = new KazancSiraliIkili(kosular);
        kazancSiraliIkili.analyze(new BigDecimal("3.0"));
        Assert.assertEquals(new BigDecimal("78.10"), kazancSiraliIkili.kacliraKazanirdik);
        Assert.assertEquals(new BigDecimal("18.00"), kazancSiraliIkili.neKadarVerirdik);


    }
}
