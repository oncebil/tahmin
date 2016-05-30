/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.TahminException;
import com.oncebil.tahmin.Util;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.filters.Filter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ekarincaoglu
 */
public class WekaFilterTask extends AbstractTask {

    private final static Logger logger = Logger.getLogger(WekaFilterTask.class.getName());
    private final String wekafilter;
    private final String options;
    private Instances inputInstances;
    private List<Instances> inputInstancesList;
    private List<Instances> outputInstances;

    public WekaFilterTask(String projectName, String taskName, String wekafilter, String options) {
        super(projectName, taskName);
        this.wekafilter = wekafilter;
        this.options = options;
        outputInstances = new ArrayList<Instances>();
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "WekaFilterTask projectname={0} name={1} wekafilter={2} options={3}",
                new Object[]{getName(), getName(), wekafilter, options});
        String newOptions = options;
        Pattern pattern = Pattern.compile("\\$\\{attribute-[a-zA-Z0-9]+\\}");
        Matcher matcher = pattern.matcher(newOptions);
        int iterationCount = 1;
        if (inputInstancesList != null) {
            iterationCount = inputInstancesList.size();
        }
        for (int i = 0; i < iterationCount; i++) {
            try {

                Instances instances = null;
                if (inputInstancesList == null) {
                    instances = inputInstances;
                } else {
                    instances = inputInstancesList.get(i);
                }

                while (matcher.find()) {
                    /*logger.log(Level.INFO, "I found the text \"{0}\" starting at " +
                    "index {1} and ending at index {2}",
                    new Object[]{matcher.group(), matcher.start(), matcher.end()});*/
                    String attributeName = matcher.group().substring(matcher.group().indexOf("-") + 1, matcher.group().length() - 1);
                    //logger.info(attributeName);
                    //logger.info(inputInstances.attribute(attributeName).index() + "");

                    if (instances.attribute(attributeName) == null) {
                        throw new TahminException("Task " + getName() + " attribute=" + attributeName + " doesn't exist");
                    }

                    newOptions = newOptions.replace(matcher.group(), (instances.attribute(attributeName).index() + 1) + "");

                }
                logger.info(newOptions);

                Filter filter = (Filter) Class.forName(wekafilter).newInstance();
                OptionHandler optionshandler = (OptionHandler) filter;
                String optionsBeforeProcess = newOptions;
                String[] optionsArray = weka.core.Utils.splitOptions(newOptions);
                logger.info("Filter class name=" + filter.getClass().getName() + " options=[" + optionsBeforeProcess + "] optionsarray=" + Arrays.toString(optionsArray));

                optionshandler.setOptions(optionsArray);
                filter.setInputFormat(instances);
                useFilterAndSaveOutput(instances, filter, i, getProjectName(), getName());
                weka.core.SerializationHelper.write(getSerilizationFilename(i), filter);

            } catch (ClassNotFoundException ex) {
                throw new TahminException("Task " + getName() + " can't create filter=" + wekafilter, ex);
            } catch (InstantiationException ex) {
                throw new TahminException("Task " + getName() + " can't create filter=" + wekafilter, ex);
            } catch (IllegalAccessException ex) {
                throw new TahminException("Task " + getName() + " can't create filter=" + wekafilter, ex);
            } catch (Exception ex) {
                throw new TahminException("Task " + getName() + " can't create filter=" + wekafilter, ex);
            }
        }


        logger.info("DONE");
    }

    public void useFilterAndSaveOutput(Instances instances, Filter filter, int iteration, String projectName, String taskName) throws Exception {

        Instances newData = Filter.useFilter(instances, filter);
        String folderName = ApplicationConstants.repositoryOut + projectName + File.separator + "filtered-data" + File.separator ;
        File f = new File(folderName);
        if (!f.exists()) {
            f.mkdirs();
        }
        logger.info("NEDIR=" + folderName);
        logger.info("NEDIR2=" + folderName + taskName + "-" + iteration + ".arff");
        outputInstances.add(newData);
        if (inputInstancesList == null) {
            Util.writeInstances(newData, folderName + taskName + ".arff");
        } else {
            Util.writeInstances(newData, folderName + taskName + "-" + iteration + ".arff");
        }

    }

    public void runForPrediction(String projectName, String taskName) {


        int iterationCount = 1;
        if (inputInstancesList != null) {
            iterationCount = inputInstancesList.size();
        }
        for (int i = 0; i < iterationCount; i++) {


            try {
                Instances instances = null;
                if (inputInstancesList == null) {
                    instances = inputInstances;
                } else {
                    instances = inputInstancesList.get(i);
                }


                Filter filter = (Filter) weka.core.SerializationHelper.read(getSerilizationFilename(i));


                useFilterAndSaveOutput(instances, filter, i, projectName, taskName);

            } catch (Exception ex) {
                throw new TahminException("Task " + getName() + " can't use filter=" + wekafilter, ex);
            }

        }
    }

    private String getSerilizationFilename(int instanceCount) {
        return ApplicationConstants.repositoryOut + getProjectName() + File.separator + getName() + "-instance-" + instanceCount + ".obj";
    }

    @Override
    public Object getOutput() {
        if (outputInstances.size() == 1)
            return outputInstances.get(0);
        return outputInstances;
    }

    @Override
    public void setInput(Object object) {
        if (object instanceof weka.core.Instances) {
            this.inputInstances = (Instances) object;
            // need to look how it works i am stupid
        } else if (object instanceof List) {

            this.inputInstancesList = (List<Instances>) object;
        }
    }

    public static class Builder extends TaskBuilder {

        @Parameter("wekafilter")
        private String wekafilter;
        @Parameter("options")
        private String options;

        public Builder(String projectName, String name, TaskDefinition taskDefinition) {
            super(projectName, name, taskDefinition);


        }

        @Override
        public WekaFilterTask build() {
            if (wekafilter == null) {
                throw new IllegalArgumentException("wekafilter can't be null");
            }
            if (options == null) {
                throw new IllegalArgumentException("options can't be null");
            }
            return new WekaFilterTask(getProjectName(), getName(), wekafilter, options);

        }
    }
}
