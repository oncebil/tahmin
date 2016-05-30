/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ekarincaoglu
 */
public class ApplicationConstants {


    public static String driverClass = "org.postgresql.Driver";
    public static String jdbcUrl = "jdbc:postgresql://localhost/KOSUTAHMIN";
    public static String username = "postgres";
    public static String password = "";

    public static String repositoryFolder = "/Users/erkinkarincaoglu/Documents/code/repository/";
    public static String repositoryOut = "/Users/erkinkarincaoglu/Documents/code/repository/";
  
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    
    public static final String KosuIdAttribute = "KosuId";
    public static final String AtIdAttribute = "AtId";
    public static final String BirinciMiAttribute = "BirinciMi";
    
    public static final String BirinciYes = "Y";
    public static final String BirinciNo = "N";    
    
    public static final List<String> idAttributes = Arrays.asList( new String[]  { KosuIdAttribute, AtIdAttribute}  );
    
    public static  String repositoryPath = System.getProperty("user.dir") + File.separatorChar  +  "tahminmodel";
    

    public static final ConcurrentHashMap<String,String> taskDefinitons = new ConcurrentHashMap<String,String>();
    static {
        taskDefinitons.put("CahitArf", "com.oncebil.tahmin.otamasyon.task.CahitArfTask$Builder");
        taskDefinitons.put("WekaEvaluation", "com.oncebil.tahmin.otamasyon.task.WekaEvaluation$Builder");
        taskDefinitons.put("EvaluationComparasion", "com.oncebil.tahmin.otamasyon.task.EvaluationComparasionTask$Builder");
        taskDefinitons.put("LoadArffFromRepository", "com.oncebil.tahmin.otamasyon.task.LoadArffFromRepositoryTask$Builder");
        taskDefinitons.put("NumericAttributeSplitTask", "com.oncebil.tahmin.otamasyon.task.NumericAttributeSplitTask$Builder");
        taskDefinitons.put("ReplaceOutliersTask", "com.oncebil.tahmin.otamasyon.task.ReplaceOutliersTask$Builder");
        taskDefinitons.put("WekaFilter", "com.oncebil.tahmin.otamasyon.task.WekaFilterTask$Builder");
        taskDefinitons.put("ResampleByKosuTask", "com.oncebil.tahmin.otamasyon.task.ResampleByKosuTask$Builder");
        taskDefinitons.put("SaveArffToRepositoryTask", "com.oncebil.tahmin.otamasyon.task.SaveArffToRepositoryTask$Builder");
        taskDefinitons.put("WekaAttributeSelection", "com.oncebil.tahmin.otamasyon.task.WekaAttributeSelectionTask$Builder");
        taskDefinitons.put("ReplaceMissingByOtherAttributeMeanTask", "com.oncebil.tahmin.otamasyon.task.ReplaceMissingByOtherAttributeMeanTask$Builder");
        taskDefinitons.put("NumericToNumericGroupsTask", "com.oncebil.tahmin.otamasyon.task.NumericToNumericGroupsTask$Builder");
        taskDefinitons.put("MakePredictionTask", "com.oncebil.tahmin.otamasyon.task.MakePredictionTask$Builder");
        taskDefinitons.put("MakePredictionUsingProjectTask", "com.oncebil.tahmin.otamasyon.task.MakePredictionUsingProjectTask$Builder");
        taskDefinitons.put("GanyanBasedInstanceWeightAssigner", "com.oncebil.tahmin.otamasyon.task.GanyanBasedInstanceWeightAssigner$Builder");
        
    }
        

    
}