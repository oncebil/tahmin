package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.dao.KosuDAO;
import com.oncebil.tahmin.entity.Kosu;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class ExperimentAnalizTest {
    KosuDAO kosuDAO;
    @Before
    public void before() throws Exception {
        kosuDAO = WeldGlobal.get(KosuDAO.class);
        Base.insertTestData("test_son7kosu_kstar_predictions_data.xml");
    }

    @Test
    public void testExperimentAnalizClassification() throws Exception {
        List<Kosu> kosular = kosuDAO.findbyExperimentWithClassificationPredictions("test-son7kosu-nominal-kstar-experiment");
        ExperimentAnalyze analyze = new ExperimentAnalyze();
        ExperimentAnalyzeResults analyzeResults = analyze.analyze2(kosular);
        Assert.assertTrue( analyzeResults.ganyanKazanclari.size() > 0);
        Assert.assertTrue( analyzeResults.ikiliKazanclari.size() > 0);
        Assert.assertTrue( analyzeResults.siraliIkiliKazanclari.size() > 0);


    }

    @Test
    public void testExperimentAnalizRegression() throws Exception {
        List<Kosu> kosular = kosuDAO.findbyExperimentWithRegressionPredictions("test-son7kosu-kstar-experiment");
        ExperimentAnalyze analyze = new ExperimentAnalyze();
        ExperimentAnalyzeResults analyzeResults = analyze.analyze2(kosular);
        Assert.assertTrue( analyzeResults.ganyanKazanclari.size() > 0);
        Assert.assertTrue( analyzeResults.ikiliKazanclari.size() > 0);
        Assert.assertTrue( analyzeResults.siraliIkiliKazanclari.size() > 0);


    }

    @Test
    public void testExperimentAnaliz() throws Exception {
        List<Kosu> kosular = kosuDAO.findbyExperimentWithRegressionPredictions("test-son7kosu-kstar-experiment");
        ExperimentAnalyze analyze = new ExperimentAnalyze();
        ExperimentAnalyzeResults analyzeResults = analyze.analyze(kosular);
        Assert.assertTrue( analyzeResults.ganyanKazanclari.size() > 0);
        Assert.assertTrue( analyzeResults.ikiliKazanclari.size() > 0);
        Assert.assertTrue( analyzeResults.siraliIkiliKazanclari.size() > 0);



    }

    @Test
    @Ignore("Only used once to create test data")
    public void createTestData() throws DatabaseUnitException, SQLException, IOException {
        // after running Son7kosu.xml experience Regression Predictions were saved to db
        // out of these we select from RegressionPrediction and AtKosu and appended 800 to kosu and at ids
        // and changed kosutarihi to 2001-03-28
        // And we created RegressionPrediction and AtKosu test rows.
        // we will save them to test resources
        // experiment in kullanildigi kosu araligi AND KOSUTARIHI<='2011-08-01' AND KOSUTARIHI>='2011-05-01'

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
                "   select " +
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

        // Bahisleri sonradan ekledik
        partialDataSet.addTable("Bahisler" ,
                "select '800' || id id,\n" +
                        "'800' || kosukodu kosukodu,\n" +
                        "bahistipkodu ,bahistipadi ,tutar ,sonuc ,aciklama ,cikan1atlatutar ,cikan2atlatutar ,cikan3atlatutar ,farklar ,\n" +
                        "'2001-03-28' kosutarihi,\n" +
                        "hipodromkodu ,hipodromyeri\n" +
                        "from Bahisler where kosukodu::varchar(255) in (\n" +
                        "select substring(kosukodu::varchar(255) from 4 for length(kosukodu::varchar(255)) -3 )    from (\n" +
                        "select kosukodu from \n" +
                        "(\n" +
                        "select distinct b.kosukodu from  AtKosu b, RegressionPrediction c \n" +
                        "where b.kosukoduatkodu = c.instanceid\n" +
                        "and c.experiment= 'test-son7kosu-kstar-experiment'\n" +
                        ") e \n" +
                        " )  f ) \n" +
                        "order by kosukodu desc, bahistipkodu");


        partialDataSet.addTable("ClassificationPrediction",
                " select 'test-son7kosu-nominal-kstar-experiment' experiment , " +
                        " '800'|| b.KosuKodu || '_' || '800' || b.AtKodu instanceId, " +
                        " actual,actualindex,predicted,predictedindex,error from ClassificationPrediction a, AtKosu b  \n" +
                        "  where experiment = 'KStarNominal'  \n" +
                        "  and a.instanceId = b.KosuKoduAtKodu \n" +
                        "  order by b.KosuKodu desc, predicted asc ");

        partialDataSet.addTable("Distribution",
                " select 'test-son7kosu-nominal-kstar-experiment' experiment , " +
                        " '800'|| b.KosuKodu || '_' || '800' || b.AtKodu instanceId, " +
                        " classificationclass,distribution from Distribution a, AtKosu b  \n" +
                        "  where experiment = 'KStarNominal'  \n" +
                        "  and a.instanceId = b.KosuKoduAtKodu \n" +
                        "  order by b.KosuKodu desc, classificationclass asc ");



        String filename = Base.getTestFilesPath() + "/test_son7kosu_kstar_predictions_data.xml";
        FlatXmlDataSet.write(partialDataSet,
                new FileOutputStream( filename) );

    }
}
