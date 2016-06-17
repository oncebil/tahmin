package com.oncebil.tahmin.dao;


import com.oncebil.tahmin.entity.BilinenKosu;
import com.oncebil.tahmin.entity.ExperimentResult;
import com.oncebil.tahmin.entity.Kazanc;
import com.oncebil.tahmin.entity.Kosu;
import de.laliluna.transactions.Transactional;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 15/06/2016.
 */
public class ExperimentResultDAO {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ExperimentResultDAO.class);
    @Inject
    private EntityManager manager;

    @Transactional
    public void persist(ExperimentResult experimentResult) {
        for (Kazanc kazanc : experimentResult.getKazanclar()) {
            kazanc.setExperimentResult(experimentResult);
            for (BilinenKosu bilinenKosu : kazanc.getBilinenKosular()) {
                bilinenKosu.setKazanc(kazanc);
            }
        }
        manager.persist(experimentResult);
    }

    @Transactional
    public List<ExperimentResult> getExperimentResultByName(String experiment) {
        Query q = manager.createQuery("select distinct a from ExperimentResult a " +
                "left join fetch a.kazanclar b  " +
                "left join fetch b.bilinenKosular c " +
                "where a.experiment=:experiment " +
                "");

        q.setParameter("experiment",experiment);
        return (List<ExperimentResult>)q.getResultList();
    }
}
