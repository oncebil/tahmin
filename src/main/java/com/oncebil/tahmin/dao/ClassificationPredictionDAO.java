package com.oncebil.tahmin.dao;

import com.oncebil.tahmin.entity.AtKosu;
import com.oncebil.tahmin.entity.ClassificationPrediction;
import de.laliluna.transactions.Transactional;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class ClassificationPredictionDAO {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ClassificationPredictionDAO.class);
    @Inject
    private EntityManager manager;

    @Transactional
    public void merge(ClassificationPrediction classificationPrediction) {
        manager.merge(classificationPrediction);
    }
}
