package com.oncebil.tahmin.dao;

import com.oncebil.tahmin.entity.Kosu;
import com.oncebil.tahmin.entity.RegressionPrediction;
import de.laliluna.transactions.Transactional;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
public class KosuDAO {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(KosuDAO.class);
    @Inject
    private EntityManager manager;


    @Transactional
    public List<Kosu> findbyExperimentName(String experiment) {
        Query q = manager.createQuery("select distinct a from Kosu a " +
                "inner join  fetch a.atlar b  " +
                "inner join fetch b.regressionPredictions c " +
                "inner join fetch a.bahisler d " +
                "where a.KOSUKODU = b.KOSUKODU " +
                "and b.kosuKoduAtKodu = c.instanceId " +
                "and c.experiment=:experiment ");

        q.setParameter("experiment",experiment);
        return (List<Kosu>)q.getResultList();
    }
}
