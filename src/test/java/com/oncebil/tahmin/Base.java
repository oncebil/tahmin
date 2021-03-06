package com.oncebil.tahmin;

import com.oncebil.tahmin.ApplicationConstants;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by erkinkarincaoglu on 28/05/2016.
 */
public class Base {
    public  static AtomicBoolean testDataSetup = new AtomicBoolean(false);
    public Base() {
        ApplicationConstants.repositoryPath = getTestFilesPath() + File.separator;
        ApplicationConstants.repositoryOut = ApplicationConstants.repositoryPath + File.separator;
    }

    public static File getTestFile(String file) {
        return new File(Base.class.getClassLoader().getResource(file).getFile());
    }

    public static String readTestFile(String file) throws FileNotFoundException {
        return new Scanner(getTestFile(file)).useDelimiter("\\Z").next();
    }

    public static String getTestFilesPath() {
        return new File(Base.class.getClassLoader().getResource(".").getFile()).getAbsolutePath();
    }

    public static void insertTestData()  {
        try {
            if (testDataSetup.compareAndSet(false, true)) {
                IDataSet dataset = new FlatXmlDataSetBuilder().setColumnSensing(true).build(getTestFile("test_son7kosu_kstar_predictions_data.xml"));
                IDatabaseTester databaseTester = new JdbcDatabaseTester(ApplicationConstants.driverClass,
                        ApplicationConstants.jdbcUrl, ApplicationConstants.username, ApplicationConstants.password);
                databaseTester.setSetUpOperation(DatabaseOperation.UPDATE);
                databaseTester.setDataSet(dataset);
                databaseTester.onSetup();
                IDataSet dataset2 = new FlatXmlDataSetBuilder().setColumnSensing(true).build(getTestFile("experiment_result.xml"));
                databaseTester.setDataSet(dataset2);
                databaseTester.onSetup();
            }
        } catch(Exception e) {
            throw  new RuntimeException(e);
        }

    }
}
