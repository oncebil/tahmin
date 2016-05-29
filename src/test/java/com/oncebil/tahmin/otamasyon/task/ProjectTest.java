/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oncebil.tahmin.otamasyon.task;


import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.TahminException;
import com.oncebil.tahmin.Base;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author ekarincaoglu
 */
public class ProjectTest extends Base {



    @Test
    public void createProjectThreeParentProject()  {

        Project project = Project.loadProject("TestProject6ThreeParentProject");
        
        Assert.assertNotNull(project);
        Assert.assertEquals(4, project.getTasks().size());

        TaskDefinition taskDefinition0 = (TaskDefinition)project.getTasks().get(0);
        Assert.assertEquals("CahitArf0" ,taskDefinition0.getName());
        Assert.assertEquals(null ,taskDefinition0.getProperty("jdbcUser"));
        Assert.assertEquals("overridden" ,taskDefinition0.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition0.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition0.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition0.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition0.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition0.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition0.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition0.getProperty("attributes"));


        TaskDefinition taskDefinition1 = (TaskDefinition)project.getTasks().get(1);
        Assert.assertEquals("CahitArfExtended3" ,taskDefinition1.getName());
        Assert.assertEquals(null ,taskDefinition1.getProperty("jdbcUser"));
        Assert.assertEquals("overridden" ,taskDefinition1.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition1.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition1.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition1.getProperty("selectClause"));
        Assert.assertEquals("overridden twice" ,taskDefinition1.getProperty("fromClause"));
        Assert.assertEquals("overridden" ,taskDefinition1.getProperty("whereClause"));
        Assert.assertEquals("overridden third" ,taskDefinition1.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition1.getProperty("attributes"));

        TaskDefinition taskDefinition2 = (TaskDefinition)project.getTasks().get(2);
        Assert.assertEquals("CahitArf2" ,taskDefinition2.getName());
        Assert.assertEquals(null ,taskDefinition2.getProperty("jdbcUser"));
        Assert.assertEquals("overridden2" ,taskDefinition2.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition2.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition2.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition2.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition2.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition2.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition2.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition2.getProperty("attributes"));


        TaskDefinition taskDefinition3 = (TaskDefinition)project.getTasks().get(3);
        Assert.assertEquals("CahitArf3" ,taskDefinition3.getName());
        Assert.assertEquals(null ,taskDefinition3.getProperty("jdbcUser"));
        Assert.assertEquals("overridden" ,taskDefinition3.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition3.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition3.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition3.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition3.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition3.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition3.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition3.getProperty("attributes"));


        Assert.assertEquals(4 ,project.getExceutableTasks().size());
        Assert.assertTrue( project.getExceutableTasks().get(0) instanceof CahitArfTask);


        CahitArfTask task =  (CahitArfTask)project.getExceutableTasks().get(0);
        Assert.assertEquals(null ,task.getJdbcUser());
        Assert.assertEquals("overridden" ,task.getJdbcDriver());
        Assert.assertEquals("cahitarf-properties-file" ,task.getCahitArfPropertiesFile());
        Assert.assertEquals("output-arff-file" ,task.getOutputArfFile());
        Assert.assertEquals("select AtId from At" ,task.getSelectClause());
        Assert.assertEquals("from At" ,task.getFromClause());
        Assert.assertEquals(null ,task.getWhereClause());
        Assert.assertEquals("limit 10" ,task.getOtherClauses());
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,task.getAttributes());


    }
    

    @Test
    public void createProjectTwoParentProject()  {
        Project project = Project.loadProject("TestProject5TwoParentProject");
        Assert.assertNotNull(project);
        Assert.assertEquals(4, project.getTasks().size());

        TaskDefinition taskDefinition0 = (TaskDefinition)project.getTasks().get(0);
        Assert.assertEquals("CahitArf0" ,taskDefinition0.getName());
        Assert.assertEquals(null ,taskDefinition0.getProperty("jdbcUser"));
        Assert.assertEquals("overridden" ,taskDefinition0.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition0.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition0.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition0.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition0.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition0.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition0.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition0.getProperty("attributes"));


        TaskDefinition taskDefinition1 = (TaskDefinition)project.getTasks().get(1);
        Assert.assertEquals("CahitArfExtended2" ,taskDefinition1.getName());
        Assert.assertEquals(null ,taskDefinition1.getProperty("jdbcUser"));
        Assert.assertEquals("overridden" ,taskDefinition1.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition1.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition1.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition1.getProperty("selectClause"));
        Assert.assertEquals("overridden" ,taskDefinition1.getProperty("fromClause"));
        Assert.assertEquals("whereClause" ,taskDefinition1.getProperty("whereClause"));
        Assert.assertEquals("overridden twice" ,taskDefinition1.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition1.getProperty("attributes"));

        TaskDefinition taskDefinition2 = (TaskDefinition)project.getTasks().get(2);
        Assert.assertEquals("CahitArf2" ,taskDefinition2.getName());
        Assert.assertEquals(null ,taskDefinition2.getProperty("jdbcUser"));
        Assert.assertEquals("overridden2" ,taskDefinition2.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition2.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition2.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition2.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition2.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition2.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition2.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition2.getProperty("attributes"));


        TaskDefinition taskDefinition3 = (TaskDefinition)project.getTasks().get(3);
        Assert.assertEquals("CahitArf3" ,taskDefinition3.getName());
        Assert.assertEquals(null ,taskDefinition3.getProperty("jdbcUser"));
        Assert.assertEquals("overridden" ,taskDefinition3.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition3.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition3.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition3.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition3.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition3.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition3.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition3.getProperty("attributes"));


        Assert.assertEquals(4 ,project.getExceutableTasks().size());
        Assert.assertTrue( project.getExceutableTasks().get(0) instanceof CahitArfTask);


        CahitArfTask task =  (CahitArfTask)project.getExceutableTasks().get(0);
        Assert.assertEquals(null ,task.getJdbcUser());
        Assert.assertEquals("overridden" ,task.getJdbcDriver());
        Assert.assertEquals("cahitarf-properties-file" ,task.getCahitArfPropertiesFile());
        Assert.assertEquals("output-arff-file" ,task.getOutputArfFile());
        Assert.assertEquals("select AtId from At" ,task.getSelectClause());
        Assert.assertEquals("from At" ,task.getFromClause());
        Assert.assertEquals(null ,task.getWhereClause());
        Assert.assertEquals("limit 10" ,task.getOtherClauses());
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,task.getAttributes());
    }
    

   
    @Test(expected=TahminException.class)
    public void createProjectHasDuplicateTask()  {
        Project project = Project.loadProject("TestProject17ProjectHasDuplicateTaskName");
        Assert.assertNotNull(project);
        Assert.assertEquals(2, project.getTasks().size());

    }   
    
    @Test
    public void createProjectParentProject()  {
        Project project = Project.loadProject("TestProject4ParentProject");
        Assert.assertNotNull(project);
        Assert.assertEquals(4, project.getTasks().size());

        TaskDefinition taskDefinition0 = (TaskDefinition)project.getTasks().get(0);
        Assert.assertEquals("CahitArf0" ,taskDefinition0.getName());
        Assert.assertEquals(null ,taskDefinition0.getProperty("jdbcUser"));
        Assert.assertEquals("overridden" ,taskDefinition0.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition0.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition0.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition0.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition0.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition0.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition0.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition0.getProperty("attributes"));
        

        TaskDefinition taskDefinition1 = (TaskDefinition)project.getTasks().get(1);
        Assert.assertEquals("CahitArfExtended" ,taskDefinition1.getName());
        Assert.assertEquals(null ,taskDefinition1.getProperty("jdbcUser"));
        Assert.assertEquals("overridden" ,taskDefinition1.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition1.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition1.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition1.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition1.getProperty("fromClause"));
        Assert.assertEquals("whereClause" ,taskDefinition1.getProperty("whereClause"));
        Assert.assertEquals("overridden" ,taskDefinition1.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition1.getProperty("attributes"));

        TaskDefinition taskDefinition2 = (TaskDefinition)project.getTasks().get(2);
        Assert.assertEquals("CahitArf2" ,taskDefinition2.getName());
        Assert.assertEquals(null ,taskDefinition2.getProperty("jdbcUser"));
        Assert.assertEquals("overridden2" ,taskDefinition2.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition2.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition2.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition2.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition2.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition2.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition2.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition2.getProperty("attributes"));


        TaskDefinition taskDefinition3 = (TaskDefinition)project.getTasks().get(3);
        Assert.assertEquals("CahitArf3" ,taskDefinition3.getName());
        Assert.assertEquals(null ,taskDefinition3.getProperty("jdbcUser"));
        Assert.assertEquals("overridden" ,taskDefinition3.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition3.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition3.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition3.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition3.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition3.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition3.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition3.getProperty("attributes"));
        

        Assert.assertEquals(4 ,project.getExceutableTasks().size());
        Assert.assertTrue( project.getExceutableTasks().get(0) instanceof CahitArfTask);


        CahitArfTask task =  (CahitArfTask)project.getExceutableTasks().get(0);
        Assert.assertEquals(null ,task.getJdbcUser());
        Assert.assertEquals("overridden" ,task.getJdbcDriver());
        Assert.assertEquals("cahitarf-properties-file" ,task.getCahitArfPropertiesFile());
        Assert.assertEquals("output-arff-file" ,task.getOutputArfFile());
        Assert.assertEquals("select AtId from At" ,task.getSelectClause());
        Assert.assertEquals("from At" ,task.getFromClause());
        Assert.assertEquals(null ,task.getWhereClause());
        Assert.assertEquals("limit 10" ,task.getOtherClauses());
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,task.getAttributes());
        
    }
    
    @Test(expected=TahminException.class)
    public void createProjectTaskHasNoClassName()  {
        Project.loadProject("TestProject2TaskHasNoClassName");
    }


    
    @Test
    public void createProjectSimple()  {
        Project project = Project.loadProject("TestProject1Simple");
        Assert.assertNotNull(project);
        Assert.assertEquals(1, project.getTasks().size());

        TaskDefinition taskDefinition = (TaskDefinition)project.getTasks().get(0);

        

        
        Assert.assertEquals(null ,taskDefinition.getProperty("jdbcUser"));
        Assert.assertEquals("org.sqlite.JDBC" ,taskDefinition.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition.getProperty("output-arff-file"));
        Assert.assertEquals("select AtId from At" ,taskDefinition.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition.getProperty("attributes"));

        Assert.assertEquals(1 ,project.getExceutableTasks().size());
        Assert.assertTrue( project.getExceutableTasks().get(0) instanceof CahitArfTask);

        CahitArfTask task =  (CahitArfTask)project.getExceutableTasks().get(0);
        Assert.assertEquals(null ,task.getJdbcUser());
        Assert.assertEquals("org.sqlite.JDBC" ,task.getJdbcDriver());
        Assert.assertEquals("cahitarf-properties-file" ,task.getCahitArfPropertiesFile());
        Assert.assertEquals("output-arff-file" ,task.getOutputArfFile());
        Assert.assertEquals("select AtId from At" ,task.getSelectClause());
        Assert.assertEquals("from At" ,task.getFromClause());
        Assert.assertEquals(null ,task.getWhereClause());
        Assert.assertEquals("limit 10" ,task.getOtherClauses());
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,task.getAttributes());

    }

    @Test
    public void createProjectTaskHasParentTask()  {
        
        

        Project project = Project.loadProject("TestProject3ParentTask");
        Assert.assertNotNull(project);
        Assert.assertEquals(3, project.getTasks().size());
        
        TaskDefinition taskDefinition = (TaskDefinition)project.getTasks().get(0);
        
        Assert.assertEquals("TestCahitArfBase" ,taskDefinition.getParent());


        Assert.assertEquals(null ,taskDefinition.getProperty("jdbcUser"));
        Assert.assertEquals("overridden" ,taskDefinition.getProperty("jdbcDriver"));
        Assert.assertEquals("cahitarf-properties-file" ,taskDefinition.getProperty("cahitarf-properties-file"));
        Assert.assertEquals("output-arff-file" ,taskDefinition.getProperty("output-arff-file"));       
        Assert.assertEquals("select AtId from At" ,taskDefinition.getProperty("selectClause"));
        Assert.assertEquals("from At" ,taskDefinition.getProperty("fromClause"));
        Assert.assertEquals(null ,taskDefinition.getProperty("whereClause"));
        Assert.assertEquals("limit 10" ,taskDefinition.getProperty("otherClauses"));
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,taskDefinition.getProperty("attributes"));
        
        Assert.assertEquals(3 ,project.getExceutableTasks().size());
        Assert.assertTrue( project.getExceutableTasks().get(0) instanceof CahitArfTask);

        CahitArfTask task =  (CahitArfTask)project.getExceutableTasks().get(0);
        Assert.assertEquals(null ,task.getJdbcUser());
        Assert.assertEquals("overridden" ,task.getJdbcDriver());
        Assert.assertEquals("cahitarf-properties-file" ,task.getCahitArfPropertiesFile());
        Assert.assertEquals("output-arff-file" ,task.getOutputArfFile());
        Assert.assertEquals("select AtId from At" ,task.getSelectClause());
        Assert.assertEquals("from At" ,task.getFromClause());
        Assert.assertEquals(null ,task.getWhereClause());
        Assert.assertEquals("limit 10" ,task.getOtherClauses());
        Assert.assertEquals("attr.AtId=gm_AtId:numeric" ,task.getAttributes());
        
    }

    @Test(expected=TahminException.class)
    public void createProjectNotFound() throws TahminException {
        Project project = Project.loadProject(ApplicationConstants.repositoryPath + "\\UnknownProject.xml");
        Assert.assertNotNull(project);
    }

    @Test(expected=TahminException.class)
    public void createTaskHasUnkownProperty()  {
        Project.loadProject("TestProject11TaskHasUnkownProperty");
    }
}
