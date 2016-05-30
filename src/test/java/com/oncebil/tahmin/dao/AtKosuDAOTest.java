/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin.dao;



import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.entity.AtKosu;
import de.laliluna.transactions.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author ekarincaoglu
 */
public class AtKosuDAOTest extends Base {

    private AtKosuDAO atKosuDAO;
    @Before
    public void before() throws Exception {
        atKosuDAO = WeldGlobal.get(AtKosuDAO.class);
        Base.insertTestData("test_son7kosu_kstar_predictions_data.xml");
    }

    @Test
    @Transactional
    public void testSelectByKosuKodu() {
        Assert.assertNotNull( atKosuDAO.getAtKosu(80048955L,80024494L) );
    }
    
}
