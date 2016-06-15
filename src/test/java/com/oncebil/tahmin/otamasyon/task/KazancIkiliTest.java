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
public class KazancIkiliTest {
    KosuDAO kosuDAO;

    @Before
    public void before() throws Exception {
        kosuDAO = WeldGlobal.get(KosuDAO.class);
        Base.insertTestData("test_son7kosu_kstar_predictions_data.xml");
    }

    @Test
    public void testIkiliKazanc() {
        List<Kosu> kosular = kosuDAO.findbyExperimentWithRegressionPredictions("test-son7kosu-kstar-experiment");
        KazancIkili kazancIkili = new KazancIkili(kosular);
        kazancIkili.analyze(new BigDecimal("3.0"));
        Assert.assertEquals(new BigDecimal("71.20"), kazancIkili.kacliraKazanirdik);
        Assert.assertEquals(new BigDecimal("35.00"), kazancIkili.neKadarVerirdik);


    }


    @Test
    public void testIkiliKazancClassification() {
        List<Kosu> kosular = kosuDAO.
                findbyExperimentWithClassificationPredictions("test-son7kosu-nominal-kstar-experiment");
        KazancIkili kazancIkili = new KazancIkili(kosular);
        kazancIkili.analyze(new BigDecimal("0.91600001"));
        Assert.assertEquals(new BigDecimal("491.20"), kazancIkili.kacliraKazanirdik);
        Assert.assertEquals(new BigDecimal("837.00"), kazancIkili.neKadarVerirdik);


    }
}
