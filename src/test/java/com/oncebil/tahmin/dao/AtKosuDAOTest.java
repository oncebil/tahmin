/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin.dao;


import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.entity.AtKosu;
import com.oncebil.tahmin.entity.Kazanc;
import de.laliluna.transactions.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.Date;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @author ekarincaoglu
 */
public class AtKosuDAOTest extends Base {

    private AtKosuDAO atKosuDAO;

    @Before
    public void before() throws Exception {
        atKosuDAO = WeldGlobal.get(AtKosuDAO.class);
        //Base.insertTestData();
    }

    @Test

    public void testSelectByKosuKodu() {

        Assert.assertNotNull(atKosuDAO.getAtKosu(80048955L, 80024494L));
    }


}
