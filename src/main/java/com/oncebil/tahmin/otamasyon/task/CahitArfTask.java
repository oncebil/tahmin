/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin.otamasyon.task;



import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.TahminException;
import com.oncebil.tahmin.Util;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


/**
 *
 * @author ekarincaoglu
 */
public class CahitArfTask extends AbstractTask {


    private final static Logger logger = Logger.getLogger(CahitArfTask.class.getName());

    private final String cahitArfPropertiesFile;
    private final String outputArfFile;
    private final String relation;
    private final String jdbcDriver;
    private final String jdbcUser;
    private String selectClause;
    private final String fromClause;
    private String whereClause;
    private final String otherClauses;
    private final String attributes;
    private final String createIfExists;
    private CahitArfSplit cahitArfSplit;

    public CahitArfTask(String projectName, String name, String cahitArfPropertiesFile, String outputArfFile, String relation, String jdbcDriver, String jdbcUser, String selectClause, String fromClause, String whereClause, String otherClauses, String attributes, String createIfExists, CahitArfSplit cahitArfSplit) {
        super(projectName,name);
        this.cahitArfPropertiesFile = cahitArfPropertiesFile;
        this.outputArfFile = outputArfFile;
        this.relation = relation;
        this.jdbcDriver = jdbcDriver;
        this.jdbcUser = jdbcUser;
        this.selectClause = selectClause;
        this.fromClause = fromClause;
        this.whereClause = whereClause;
        this.otherClauses = otherClauses;
        this.attributes = attributes;
        this.createIfExists = createIfExists;
        this.cahitArfSplit = cahitArfSplit;
        if (cahitArfSplit != null) {
            logger.info("We have cahit arf split=" + cahitArfSplit + " task name=" + name);
        }
    }
    
    public CahitArfTask newCopyForPrediction(String name) {
        return new CahitArfTask(this.getProjectName(),
                name,
                cahitArfPropertiesFile, outputArfFile, relation, jdbcDriver, jdbcUser, selectClause, fromClause, whereClause, otherClauses, attributes, createIfExists, cahitArfSplit);
    }
    
    public void modifyForPrecitionNewKosu(String kosuId) {
        String  where = whereClause;
        
        // assuming sql uses b as alias to gm_kosu
        where += " and b.gm_KosuId = " + kosuId;
        
        
        // assuming sql uses d as alias to gm_KosuyaKatilanAtKazanclar
        int index = selectClause.indexOf("CASE WHEN (d.gm_Kacinci = 1 ) THEN 'Y' ELSE 'N' END AS BirinciMi");
        if (index < 0) {
            throw new TahminException("Cahit arf doesn't have any birinci selection" + selectClause);
        }        
        String select = selectClause.replaceAll("CASE WHEN \\(d.gm_Kacinci = 1 \\) THEN 'Y' ELSE 'N' END AS BirinciMi", "'?' As BirinciMi");
        this.whereClause = where;
        this.selectClause = select;
        
        
    }

    @Override
    public void run() {

        
        String folderName = ApplicationConstants.repositoryOut + "data\\" + getName() + "\\";
        //logger.info("FILE CREATE=" + folderName);
        File f = new File(folderName);
        if (!f.exists()) {
            f.mkdirs();
        }
        else {

            //logger.info("createIfExists=" + createIfExists);
            if (createIfExists != null && createIfExists.equals(ApplicationConstants.TRUE)) {
                logger.info("recreating");
            } else {
                logger.info("wont recreate");
                return;
            }
        }
        String absoluteCahitArfPropertiesFile = ApplicationConstants.repositoryOut + "data" + File.separatorChar + getName() + File.separatorChar  + cahitArfPropertiesFile;
        String absoluteOutputArfFile = ApplicationConstants.repositoryOut + "data" + File.separatorChar  + getName() + File.separatorChar  + outputArfFile;

        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append(selectClause).append(" ").append(fromClause).append(" ");
        if (whereClause != null) {
            queryBuffer.append(whereClause).append(" ");
        }
        if (otherClauses != null) {
            queryBuffer.append(otherClauses);
        }
        //logger.info(" relation=" + relation + " cahitArfPropertiesFile=" + absoluteCahitArfPropertiesFile + "\n queryBufer=" + queryBuffer.toString() + "\n attr=" + attributes);
        queryBuffer = new StringBuffer(queryBuffer.toString().replaceAll(" \\\\", " ").replaceAll(System.getProperty("line.separator"), " ").replaceAll("\\n", " "));


        logger.info("Modified query bufer=" + queryBuffer.toString());

        Util.createCahitArffPropertiesFile(relation,
                absoluteCahitArfPropertiesFile,
                queryBuffer.toString(), attributes);

        String[] args = new String[]{absoluteCahitArfPropertiesFile, absoluteOutputArfFile};


        try {
            //Db2ArffEx.main(args);
        } catch (Throwable ex) {
            throw new TahminException("Failed to execute cahit arf task name=" + getName(), ex);
        }
        String arffout = Util.readFromFile(absoluteOutputArfFile);
        arffout = arffout.replaceAll("null", "?");
        logger.info("Writing cahit arf out to = " + absoluteOutputArfFile);
        Util.writeToFile(absoluteOutputArfFile, arffout, null);
    }

    /**
     * @return the cahitArfPropertiesFile
     */
    public String getCahitArfPropertiesFile() {
        return cahitArfPropertiesFile;
    }

    /**
     * @return the outputArfFile
     */
    public String getOutputArfFile() {
        return outputArfFile;
    }

    /**
     * @return the relation
     */
    public String getRelation() {
        return relation;
    }

    /**
     * @return the jdbcDriver
     */
    public String getJdbcDriver() {
        return jdbcDriver;
    }

    /**
     * @return the jdbcUser
     */
    public String getJdbcUser() {
        return jdbcUser;
    }

    /**
     * @return the selectClause
     */
    public String getSelectClause() {
        return selectClause;
    }

    /**
     * @return the fromClause
     */
    public String getFromClause() {
        return fromClause;
    }

    /**
     * @return the whereClause
     */
    public String getWhereClause() {
        return whereClause;
    }

    /**
     * @return the otherClauses
     */
    public String getOtherClauses() {
        return otherClauses;
    }

    /**
     * @return the attributes
     */
    public String getAttributes() {
        return attributes;
    }

    @Override
    public Object getOutput() {
       String absoluteOutputArfFile = ApplicationConstants.repositoryOut + "data" + File.separatorChar  + getName() + File.separatorChar  + outputArfFile;

        
        try {
            ArffLoader arffLoader = new ArffLoader();
            arffLoader.setFile(new File(absoluteOutputArfFile));
            Instances instances =arffLoader.getDataSet();
            instances.setClassIndex( instances.numAttributes()-1 );
            return instances;
        } catch (IOException ex) {
            throw new TahminException("can't load arrff=" + absoluteOutputArfFile, ex);
        }


    }

    @Override
    public void setInput(Object object) {
        
    }

    public static class Builder extends TaskBuilder {


        @Parameter("cahitarf-properties-file")
        private String cahitArfPropertiesFile;
        @Parameter("output-arff-file")
        private String outputArfFile;
        @Parameter("relation")
        private String relation;
        @Parameter("jdbcDriver")
        private String jdbcDriver;
        @Parameter("jdbcUser")
        private String jdbcUser;
        @Parameter("selectClause")
        private String selectClause;
        @Parameter("fromClause")
        private String fromClause;
        @Parameter("whereClause")
        private String whereClause;
        @Parameter("otherClauses")
        private String otherClauses;
        @Parameter("attributes")
        private String attributes;
        @Parameter("createIfExists")
        private String createIfExists;

        public Builder(String projectName,String name,TaskDefinition taskDefinition ) {
            super(projectName, name, taskDefinition);
            

        }


        @Override
        public CahitArfTask build() {
            if (cahitArfPropertiesFile == null) {
                throw new IllegalArgumentException("cahitArfPropertiesFile can't be null");
            }
            if (outputArfFile == null) {
                throw new IllegalArgumentException("outputArfFile can't be null");
            }
            if (jdbcDriver == null) {
                throw new IllegalArgumentException("jdbcDriver can't be null");
            }
            /*if (jdbcUser == null) {
            throw new IllegalArgumentException("jdbcUser can't be null");
            }*/
            if (selectClause == null) {
                throw new IllegalArgumentException("selectClause can't be null");
            }
            if (fromClause == null) {
                throw new IllegalArgumentException("fromClause can't be null");
            }
            /*if (whereClause == null) {
            throw new IllegalArgumentException("whereClause can't be null");
            }
            if (otherClauses == null) {
            throw new IllegalArgumentException("otherClauses can't be null");
            }*/
            if (attributes == null) {
                throw new IllegalArgumentException("attributes can't be null in task=" + getName());
            }
            CahitArfSplit cahitArfSplit = null;
            if (getTaskDefinition() instanceof CahitArfTaskDefinition) {
                cahitArfSplit = ((CahitArfTaskDefinition)getTaskDefinition()).getCahitArfSplit();
            }
            return new CahitArfTask(getProjectName(), getName(), cahitArfPropertiesFile, outputArfFile, relation, jdbcDriver, jdbcUser, selectClause, fromClause, whereClause, otherClauses, attributes,createIfExists, cahitArfSplit);

        }
    }
}

