package com.oncebil.tahmin;

import com.oncebil.tahmin.ApplicationConstants;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.io.*;
import java.util.Scanner;

/**
 * Created by erkinkarincaoglu on 28/05/2016.
 */
public class Base {
    public Base() {
        File file = new File(getClass().getClassLoader().getResource("TestCahitArfBase.xml").getFile());
        ApplicationConstants.repositoryPath = file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf( File.separator) );
        ApplicationConstants.repositoryOut = ApplicationConstants.repositoryPath + File.separator;
    }


    public static File getTestFile(String file) throws UnsupportedEncodingException {
        return new File(Base.class.getClassLoader().getResource(file).getFile());
    }
    public static String readTestFile(String file) throws UnsupportedEncodingException, FileNotFoundException {
        return new Scanner(getTestFile(file)).useDelimiter("\\Z").next();
    }

    public static void insertTestData(String file) throws Exception{
        IDataSet dataset = new FlatXmlDataSetBuilder().setColumnSensing(true).build( getTestFile(file));
        IDatabaseTester databaseTester = new JdbcDatabaseTester(ApplicationConstants.driverClass,
                ApplicationConstants.jdbcUrl, ApplicationConstants.username, ApplicationConstants.password);
        databaseTester.setSetUpOperation(DatabaseOperation.REFRESH);
        databaseTester.setDataSet(dataset);
        databaseTester.onSetup();
    }
}
