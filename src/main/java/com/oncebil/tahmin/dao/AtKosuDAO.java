/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin.dao;


import com.oncebil.tahmin.entity.AtKosu;
import de.laliluna.transactions.Transactional;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ekarincaoglu
 */
public class AtKosuDAO {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AtKosuDAO.class);
    @Inject
    private EntityManager manager;

    public void merge(AtKosu atKosu) {
        manager.merge(atKosu);
    }

    @Transactional
    public AtKosu getAtKosu(Long KOSUKODU, Long ATKODU) {
        String sql = "select a  "
                + "from AtKosu a "
                + "where KOSUKODU=:KOSUKODU "
                + "and ATKODU=:ATKODU ";

        Query query = manager.createQuery(sql);
        query.setParameter("KOSUKODU", KOSUKODU);
        query.setParameter("ATKODU", ATKODU);
        List<AtKosu> list = (List<AtKosu>) query.getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);


    }
}
