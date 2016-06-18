package com.oncebil.tahmin.dao;

import com.oncebil.tahmin.entity.AtKosu;
import com.oncebil.tahmin.entity.ClassificationPrediction;
import com.oncebil.tahmin.entity.RegressionPrediction;
import de.laliluna.transactions.Transactional;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class ClassificationPredictionDAO {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ClassificationPredictionDAO.class);
    @Inject
    private EntityManager manager;

    @Transactional
    public void merge(List<ClassificationPrediction> classificationPredictions) {
        for (ClassificationPrediction classificationPrediction : classificationPredictions) {
            manager.merge(classificationPrediction);
        }

    }
    @Transactional
    public List<ClassificationPrediction> findByExperimentName(String experiment) {
        Query q = manager.createQuery("select a from ClassificationPrediction a where experiment=:experiment");
        q.setParameter("experiment",experiment);
        return (List<ClassificationPrediction>)q.getResultList();
    }
}
