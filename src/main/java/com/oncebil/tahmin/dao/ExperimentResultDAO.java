package com.oncebil.tahmin.dao;


import com.oncebil.tahmin.entity.BilinenKosu;
import com.oncebil.tahmin.entity.ExperimentKosu;
import com.oncebil.tahmin.entity.ExperimentResult;
import com.oncebil.tahmin.entity.Kazanc;
import de.laliluna.transactions.Transactional;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 15/06/2016.
 */
public class ExperimentResultDAO {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ExperimentResultDAO.class);
    @Inject
    private EntityManager manager;

    @Transactional
    public void removeAndInsert(ExperimentResult experimentResult) {
        ExperimentResult previous = manager.find(ExperimentResult.class, experimentResult.getExperiment());
        if (previous != null) {
            manager.remove(previous);
        }
        for (Kazanc kazanc : experimentResult.getKazanclar()) {
            kazanc.setExperimentResult(experimentResult);
            for (BilinenKosu bilinenKosu : kazanc.getBilinenKosular()) {
                bilinenKosu.setKazanc(kazanc);
            }
        }
        for (ExperimentKosu experimentKosu : experimentResult.getExperimentKosular()) {
            experimentKosu.setExperimentResult(experimentResult);
        }
        manager.merge(experimentResult);
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

    @Transactional
    public List<Kazanc> findKazancGanyansOnThresholds(String experiment) {
        Query q = manager.createQuery("select  k  from   Kazanc k\n" +
                "where  k.experimentResult.experiment = :experiment \n" +
                "and gametype = 'KazancGanyan'\n" +
                "and index in (2,3,4,5,6)\n" +
                "order by  yuzdekacindaoynardik desc");
        q.setParameter("experiment",experiment);
        return (List<Kazanc>)q.getResultList();
    }
}
