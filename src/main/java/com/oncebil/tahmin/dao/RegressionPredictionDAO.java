package com.oncebil.tahmin.dao;

import com.oncebil.tahmin.entity.AtKosu;
import com.oncebil.tahmin.entity.ClassificationPrediction;
import com.oncebil.tahmin.entity.RegressionPrediction;

import de.laliluna.transactions.Transactional;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class RegressionPredictionDAO {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(RegressionPredictionDAO.class);
    @Inject
    private EntityManager manager;

    @Transactional
    public void merge(RegressionPrediction regressionPrediction) {
        manager.merge(regressionPrediction);
    }

    @Transactional
    public List<RegressionPrediction> findbyExperimentName(String experiment) {
        Query q = manager.createQuery("select  a from RegressionPrediction a " +
                "where experiment=:experiment ");
        q.setParameter("experiment",experiment);
        return (List<RegressionPrediction>)q.getResultList();
    }

}
