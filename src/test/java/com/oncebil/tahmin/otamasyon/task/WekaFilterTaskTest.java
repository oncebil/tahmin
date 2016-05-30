/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oncebil.tahmin.otamasyon.task;


import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.Util;
import org.junit.Assert;
import org.junit.Test;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ekarincaoglu
 */
public class WekaFilterTaskTest extends Base {
    @Test
    public void runWekaFilter() throws IOException  {
        Project project = Project.loadProject("TestProject16WekaFilterTask");
        Assert.assertNotNull(project);
        project.run();
        Assert.assertTrue( new File(ApplicationConstants.repositoryOut+ "TestProject16WekaFilterTask" + File.separator + "WekaFilterAddID-instance-0.obj").exists());
        Assert.assertTrue( new File(ApplicationConstants.repositoryOut+ "TestProject16WekaFilterTask" + File.separator + "filtered-data" + File.separator + "WekaFilterAddID.arff").exists());

    }

}
