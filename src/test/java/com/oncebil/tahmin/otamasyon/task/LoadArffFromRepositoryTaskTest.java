/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oncebil.tahmin.otamasyon.task;


import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.Base;
import org.junit.Assert;
import org.junit.Test;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;

/**
 * @author ekarincaoglu
 */
public class LoadArffFromRepositoryTaskTest extends Base {

    @Test
    public void loadArffFromRepository() throws IOException, Exception {
        Project project = Project.loadProject("TestProject12LoadArffFromRepository");
        Assert.assertNotNull(project);
        project.run();
        Assert.assertNotNull(project.getLastOutput());
        Assert.assertTrue(project.getLastOutput() instanceof Instances);
        Instances instances = (Instances) project.getLastOutput();
        Assert.assertTrue(instances.attribute(0).name().equals("MYCT"));
    }
}
