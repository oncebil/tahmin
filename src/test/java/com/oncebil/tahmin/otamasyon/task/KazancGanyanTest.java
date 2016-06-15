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
public class KazancGanyanTest {

    KosuDAO kosuDAO;

    @Before
    public void before() throws Exception {
        kosuDAO = WeldGlobal.get(KosuDAO.class);
        Base.insertTestData("test_son7kosu_kstar_predictions_data.xml");
    }

    @Test
    public void testGanyanKazancRegression() {
        List<Kosu> kosular = kosuDAO.findbyExperimentWithRegressionPredictions("test-son7kosu-kstar-experiment");
        KazancGanyan kazancGanyan = new KazancGanyan(kosular);
        kazancGanyan.analyze(new BigDecimal("2.1"));
        Assert.assertEquals(new BigDecimal("23.10"), kazancGanyan.kacliraKazanirdik);
        Assert.assertEquals(new BigDecimal("21.00"), kazancGanyan.neKadarVerirdik);


    }

    @Test
    public void testGanyanKazancNominal() {
        List<Kosu> kosular = kosuDAO.findbyExperimentWithRegressionPredictions("test-son7kosu-nominal-kstar-experiment");
        KazancGanyan kazancGanyan = new KazancGanyan(kosular);
        // todo


    }
}
