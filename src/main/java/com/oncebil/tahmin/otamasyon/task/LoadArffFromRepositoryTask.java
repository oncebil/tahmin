package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.TahminException;
import org.slf4j.LoggerFactory;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;

/**
 * Created by erkinkarincaoglu on 29/05/2016.
 */
public class LoadArffFromRepositoryTask extends AbstractTask {
    private final String dataName;
    private final String arffFilename;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(LoadArffFromRepositoryTask.class);
    public LoadArffFromRepositoryTask(String projectName, String name, String dataName, String arffFilename) {
        super(projectName,name);
        this.dataName = dataName;
        this.arffFilename = arffFilename;
    }


    @Override
    public void run() {
        // gets instances when its output is requested

    }

    @Override
    public Object getOutput() {
        String absoluteOutputArfFile = ApplicationConstants.repositoryOut + "data" + File.separatorChar  + dataName + File.separatorChar  + arffFilename;


        try {
            ArffLoader arffLoader = new ArffLoader();
            arffLoader.setFile(new File(absoluteOutputArfFile));
            Instances instances =arffLoader.getDataSet();
            instances.setClassIndex( instances.numAttributes()-1 );
            logger.info("loaded arff=" + absoluteOutputArfFile + " instance count=" + instances.numInstances() + " attribute count=" + instances.numAttributes());
            return instances;
        } catch (Exception ex) {
            throw new TahminException("can't load arrff=" + absoluteOutputArfFile, ex);
        }
    }

    @Override
    public void setInput(Object object) {

    }

    public static class Builder extends TaskBuilder {
        @Parameter("dataName")
        private String dataName;
        @Parameter("arffFilename")
        private String arffFilename;
        public Builder(String projectName,String name,TaskDefinition taskDefinition ) {
            super(projectName, name, taskDefinition);

        }
        @Override
        public AbstractTask build() {
            if (dataName == null) {
                throw new IllegalArgumentException("dataName can't be bull");
            }
            return new LoadArffFromRepositoryTask(getProjectName(), getName(), dataName, arffFilename);

        }
    }

}
