/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oncebil.tahmin.otamasyon.task;


import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.Base;
import org.junit.Assert;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import weka.classifiers.Classifier;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ekarincaoglu
 */
public class WekaEvaluationTaskTest extends Base {

    @Test
    public void runSon7Kosular() throws IOException, Exception {
        Project project = Project.loadProject("Son7Kosu");
        Assert.assertNotNull(project);
        project.run();
        Assert.assertTrue( new File(ApplicationConstants.repositoryOut+ "Son7Kosu" + File.separator + "KStar-Model.model").exists());
    }

    @Test
    public void testProject10TestRegression() throws IOException, Exception {
        Project project = Project.loadProject("TestProject10Regression");
        Assert.assertNotNull(project);
        project.run();
        Assert.assertTrue( new File(ApplicationConstants.repositoryOut+ "TestProject10Regression" + File.separator + "LinearRegression-Model.model").exists());

    }
    @Test
    public void testProject9TestClassification() throws IOException, Exception {
        Project project = Project.loadProject("TestProject9TestEvaluation");
        Assert.assertNotNull(project);
        project.run();
        Assert.assertTrue( new File(ApplicationConstants.repositoryOut+ "TestProject9TestEvaluation" + File.separator + "SimpleNaiveBayesClassifier-Model.model").exists());

//        File f = new File(ApplicationConstants.repositoryOut + "data\\" + "TestCahitArfNaiveBayes1Data" + "\\" + "output-arff-file.arff");
//        Assert.assertTrue(f.exists());
//        String  evaluationResultsFileName = ApplicationConstants.repositoryOut+ "TestProject9TestEvaluation" + "\\" + "SimpleNaiveBayesClassifier-EvaluationResults.xml";
//        f = new File( evaluationResultsFileName);
//        Assert.assertTrue( f.exists()   );


        Serializer serializer = new Persister();
//        NominalEvaluationResults result = serializer.read( NominalEvaluationResults.class, new File(evaluationResultsFileName) );
//        double[][] confusionMatrix = new double[4][1];


       /* confusionMatrix[0][0] = 593;
        confusionMatrix[1][0] = 209;
        confusionMatrix[2][0] = 56;
        confusionMatrix[3][0] = 34;
        Assert.assertArrayEquals( confusionMatrix, result.getConfusionMatrix());
        Assert.assertEquals(  70.2914798206278, result.getPctCorrect(), 0.001);
        Assert.assertEquals(  29.708520179372197, result.getPctIncorrect(), 0.001);
        Assert.assertEquals(  627.0 , result.getCorrect(), 0.001);
        Assert.assertEquals(  265.0, result.getIncorrect(), 0.001);
        Assert.assertEquals(  892, result.getInstanceCount());
        Assert.assertEquals(  0.4326666666666667, result.getGanyanOyunuKazanci().kazancOraninNeOlurdu(), 0.001);*/




        // Check if model file exists and valid
//        String modelFileName = ApplicationConstants.repositoryOut+ "TestProject9TestEvaluation" + "\\" + "SimpleNaiveBayesClassifier-Model.model";
//        f = new File( modelFileName);
//        Assert.assertTrue( f.exists()   );
//        Classifier cls = (Classifier) weka.core.SerializationHelper.read(modelFileName);
//        Assert.assertNotNull(cls);

    }
    
  /*@Test
    public void runTestProject8TestClassifier() throws IOException, Exception  {

        
        Project project = Project.loadProject("TestProject8TestEvaluation");
        Assert.assertNotNull(project);
        
        project.run();
        File f = new File(ApplicationConstants.repositoryOut+ "data\\"+ "TestClassifierCahitArf" + "\\" +  "output-arff-file.arff" );
        Assert.assertTrue( f.exists()   );
        String  evaluationResultsFileName = ApplicationConstants.repositoryOut+ "TestProject8TestEvaluation" + "\\" + "CostSensitiveClassifier-EvaluationResults.xml";
        f = new File( evaluationResultsFileName);
        Assert.assertTrue( f.exists()   );

        Serializer serializer = new Persister();
        NominalEvaluationResults result = serializer.read( NominalEvaluationResults.class, new File(evaluationResultsFileName) );
        double[][] confusionMatrix = new double[4][1];

        
        confusionMatrix[0][0] = 488;
        confusionMatrix[1][0] = 169;
        confusionMatrix[2][0] = 28;
        confusionMatrix[3][0] = 49;
        Assert.assertArrayEquals( confusionMatrix, result.getConfusionMatrix());
        Assert.assertEquals(  73.16076294277929, result.getPctCorrect(), 0.001);
        Assert.assertEquals(  26.839237057220707, result.getPctIncorrect(), 0.001);
        Assert.assertEquals(  537.0 , result.getCorrect(), 0.001);
        Assert.assertEquals(  197.0, result.getIncorrect(), 0.001);
        Assert.assertEquals(  734, result.getInstanceCount());
        Assert.assertEquals(  0.8401639344262295, result.getGanyanOyunuKazanci().kazancOraninNeOlurdu(), 0.001);

        // Check if model file exists and valid
        String modelFileName = ApplicationConstants.repositoryOut+ "TestProject8TestEvaluation" + "\\" + "CostSensitiveClassifier-Model.model";
        f = new File( modelFileName);
        Assert.assertTrue( f.exists()   );
        Classifier cls = (Classifier) weka.core.SerializationHelper.read(modelFileName);
        Assert.assertNotNull(cls);

   }*/





}
