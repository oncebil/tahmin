package com.oncebil.tahmin.dao;

import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.entity.BilinenKosu;
import com.oncebil.tahmin.entity.ExperimentResult;
import com.oncebil.tahmin.entity.Kazanc;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * Created by erkinkarincaoglu on 15/06/2016.
 */
public class ExperimentResultDAOTest {
    private ExperimentResultDAO experimentResultDAO;
    @Before
    public void before() throws Exception {
        experimentResultDAO = WeldGlobal.get(ExperimentResultDAO.class);
    }

    @Test
    public void testSave() {
        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.
                aNewEnhancedRandomBuilder().maxCollectionSize(3).maxStringLength(5).build();
        ExperimentResult experimentResult = enhancedRandom.nextObject(ExperimentResult.class);
        for (Kazanc kazanc : experimentResult.getKazanclar()) {
            kazanc.setGameType("KazancGanyan");
            kazanc.setType("oynamayuzdesi-threshold");
            kazanc.setId(null);
            for (BilinenKosu bilinenKosu : kazanc.getBilinenKosular()) {
                bilinenKosu.setId(null);
            }
        }
        experimentResultDAO.persist(experimentResult);
        List<ExperimentResult> results = experimentResultDAO.getExperimentResultByName( experimentResult.getExperiment());
        Assert.assertTrue( results.size() > 0 );
        Assert.assertTrue( results.get(0).getKazanclar().size() == experimentResult.getKazanclar().size());
        Assert.assertTrue( results.get(0).getKazanclar().iterator().next().getBilinenKosular().size() > 0);
    }
}
