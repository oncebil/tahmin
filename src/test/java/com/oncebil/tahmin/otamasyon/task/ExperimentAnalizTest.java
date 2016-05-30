package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.dao.AtKosuDAO;
import com.oncebil.tahmin.entity.RegressionPrediction;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class ExperimentAnalizTest {
    @Before
    public void before() throws Exception {
        Base.insertTestData("test_son7kosu_kstar_predictions_data.xml");
    }

    @Test
    public void testExperimentAnaliz() throws Exception {
        RegressionPredictions predictions = RegressionPredictions.loadWithExperiment("test-son7kosu-kstar-experiment");
        Assert.assertTrue( predictions.regressionPredictions.size() > 0 );
    }

    @Test
    @Ignore("Only used once to create test data")
    public void createTestData() throws DatabaseUnitException, SQLException, IOException {
        // after running Son7kosu.xml experience Regression Predictions were saved to db
        // out of these we select from RegressionPrediction and AtKosu and appended 800 to kosu and at ids
        // and changed kosutarihi to 2001-03-28
        // And we created RegressionPrediction and AtKosu test rows.
        // we will save them to test resources
        Connection jdbcConnection = DriverManager.getConnection(
                ApplicationConstants.jdbcUrl, ApplicationConstants.username, ApplicationConstants.password);
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("RegressionPrediction",
                " select 'test-son7kosu-kstar-experiment' experiment , " +
                        " '800'|| b.KosuKodu || '_' || '800' || b.AtKodu instanceId, " +
                        " actual,predicted,error from RegressionPrediction a, AtKosu b  \n" +
                        "  where experiment = 'KStar'  \n" +
                        "  and a.instanceId = b.KosuKoduAtKodu \n" +
                        "  order by b.KosuKodu desc, predicted asc ");

        partialDataSet.addTable("AtKosu",
                "   select '800' || id id ,\n" +
                        "  '2001-03-28' kosutarihi,\n" +
                        "  hipodromkodu, hipodromyeri, cim, kum, \n" +
                        "  '800' || kosukodu kosukodu, \n" +
                        "  kosuno, onemlikosuadi, kosucinsdetayadi, grupadi, ikramiye1, ikramiye2, ikramiye3, ikramiye4, mesafe, pistuzunadi, \n" +
                        "  '800' || atkodu atkodu, \n" +
                        "  atno, atadi, sonkilo, jokeykisaadi, sahipkisaadi, startno, puan, puankum, annebabakodu, annebabakodu1, antrenorkodu, sahipkodu, jokeykodu, sonucno, derece, ganyan, atikramiye, birincilikyuzdesi, ikincilikyuzdesi, ucunculukyuzdesi, ikinciyegirisyuzdesi, ucuncuyegirisyuzdesi, bitirisortalamasi, son3derecefarkiortalamasi, son7derecefarkiortalamasi, birinciilederecefarki, son7birincilikyuzdesi, son7ikincilikyuzdesi, son7ucunculukyuzdesi, son7bitirisortalamasi, \n" +
                        "  '800'|| b.KosuKodu || '_' || '800' || b.AtKodu kosukoduatkodu \n" +
                        "   from RegressionPrediction a, AtKosu b  \n" +
                        "  where experiment = 'KStar'  \n" +
                        "  and a.instanceId = b.KosuKoduAtKodu \n" +
                        "  order by b.KosuKodu desc, predicted asc ");

        String filename = Base.getTestFilesPath() + "/test_son7kosu_kstar_predictions_data.xml";
        System.out.println(filename);
        FlatXmlDataSet.write(partialDataSet,
                new FileOutputStream( filename) );

    }
}
