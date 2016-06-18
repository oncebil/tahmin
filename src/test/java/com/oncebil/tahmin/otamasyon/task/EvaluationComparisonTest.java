package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.WeldGlobal;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by erkinkarincaoglu on 18/06/2016.
 */
public class EvaluationComparisonTest {

    @Test
    public void compareExperiments() {
        Base.insertTestData();
        EvaluationComparison comparison = WeldGlobal.get( EvaluationComparison.class );
        EvaluationComparisonResult  comparisionResult  =
                comparison.compare (Arrays.asList( new String[] { "test-son7kosu-kstar-experiment", "test-son7kosu-nominal-kstar-experiment"} ));
        System.out.println(comparisionResult.ganyanComparisons);
        Assert.assertEquals( new BigDecimal("3.75"), comparisionResult.ganyanComparisons.get(0).totalKazancOnAllThresholds);
        Assert.assertEquals( "test-son7kosu-nominal-kstar-experiment", comparisionResult.ganyanComparisons.get(0).experiment);
        Assert.assertEquals( new BigDecimal("3.64"), comparisionResult.ganyanComparisons.get(1).totalKazancOnAllThresholds);
        Assert.assertEquals( "test-son7kosu-kstar-experiment", comparisionResult.ganyanComparisons.get(1).experiment);
    }

    @Test
    @Ignore("Only used once to create test data")
    public void createTestData() throws DatabaseUnitException, SQLException, IOException {
        // after running ExperimentAnalizTest.xml this has been created

        Connection jdbcConnection = DriverManager.getConnection(
                ApplicationConstants.jdbcUrl, ApplicationConstants.username, ApplicationConstants.password);
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("ExperimentResult",
                " select  *  from  ExperimentResult \n" +
                        "where experiment in ( 'test-son7kosu-kstar-experiment', 'test-son7kosu-nominal-kstar-experiment')");

        partialDataSet.addTable("Kazanc",
                "select  *  from  Kazanc \n" +
                        "where experiment in ( 'test-son7kosu-kstar-experiment', 'test-son7kosu-nominal-kstar-experiment') \n" +
                        "order by gametype, yuzdekacindaoynardik desc");


        partialDataSet.addTable("BilinenKosu" ,
                "select  a.*  from  BilinenKosu a, Kazanc b \n" +
                        "where experiment in ( 'test-son7kosu-kstar-experiment', 'test-son7kosu-nominal-kstar-experiment') \n" +
                        "and a.kazanc_id = b.id \n" +
                        "order by gametype, yuzdekacindaoynardik desc , kosukodu desc");

        String filename = Base.getTestFilesPath() + "/experiment_result.xml";
        FlatXmlDataSet.write(partialDataSet,
                new FileOutputStream( filename) );

    }
}
