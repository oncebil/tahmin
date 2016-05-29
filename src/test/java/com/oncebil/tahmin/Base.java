package com.oncebil.tahmin;

import com.oncebil.tahmin.ApplicationConstants;

import java.io.File;

/**
 * Created by erkinkarincaoglu on 28/05/2016.
 */
public class Base {
    public Base() {

        File file = new File(getClass().getClassLoader().getResource("TestCahitArfBase.xml").getFile());
        ApplicationConstants.repositoryPath = file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf( File.separator) );
        ApplicationConstants.repositoryOut = ApplicationConstants.repositoryPath + File.separator;

        System.out.println(ApplicationConstants.repositoryPath);
    }
}
