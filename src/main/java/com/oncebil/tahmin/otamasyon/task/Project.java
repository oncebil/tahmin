/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin.otamasyon.task;


import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.TahminException;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author ekarincaoglu
 */
@Root
public class Project {

    private final static Logger logger = Logger.getLogger(Project.class.getName());
    
    @Attribute(required = false)
    private String parent;
    @ElementList(inline = true)
    private List<TaskDefinition> taskDefinitions = new ArrayList<TaskDefinition>();

    private List<TaskDefinition> finalTaskDefinitions = new ArrayList<TaskDefinition>();
    
    private List<AbstractTask> exceutableTasks = new ArrayList<AbstractTask>();
    
    private String name;

    private Project() {
    }

    protected List<TaskDefinition> getTasks() {
        return finalTaskDefinitions;
    }

    private Object lastoutput;
    public Object getLastOutput() {
        return lastoutput;
    }
    public void run() {
        int i = 0;
        Object output = null;
        Iterator<AbstractTask> it = exceutableTasks.listIterator();
        while (it.hasNext()) {
            AbstractTask abstractTask = it.next();
            abstractTask.setInput(output);
            abstractTask.run();
            output = abstractTask.getOutput();
            lastoutput = output;
            abstractTask = null;
            it.remove();
        }
    }

    private static Project loadProjectFromXML(String path) {
        Serializer serializer = new Persister();
        String file = ApplicationConstants.repositoryPath + File.separatorChar + path + ".xml";
        File source = new File(file);
        if (!source.exists()) {
            throw new TahminException("Project can't be found=" + file);
        }
        Project project = null;
        try {
            project = serializer.read(Project.class, source);
            project.name = path;
        } catch (Exception ex) {
            throw new TahminException(ex);
        }
        return project;
    }

    public static Project loadProject(String path) {
        Project project = loadProjectFromXML(path);
        project.loadProjectRecursively(project);
        project.processTaskDefinitions();
        return project;

    }

    private Project loadProjectRecursively(Project initialProject) {
        // if this project's parent is not null load it
        if (parent != null) {
            loadProjectFromXML(parent).loadProjectRecursively(initialProject);
        }       
        for (TaskDefinition taskDefinition : taskDefinitions) {
            initialProject.finalTaskDefinitions.add( taskDefinition);
        }
        return this;
    }

    public void setExceutableTasks(List<AbstractTask> exceutableTasks) {
        this.exceutableTasks = exceutableTasks;
    }


    private static class TaskDefinitionNameComparator implements Comparator<TaskDefinition> {

        private static TaskDefinitionNameComparator instance = new TaskDefinitionNameComparator();

        private TaskDefinitionNameComparator() {
        }

        public int compare(TaskDefinition o1, TaskDefinition o2) {
            if (o1.getName() == null && o2.getName() == null) {
                return 0;
            } else if (o1.getName() != null && o2.getName() == null) {
                return 1;
            } else if (o1.getName() == null && o2.getName() != null) {
                return -1;
            } else {
                return o1.getName().compareTo(o2.getName());
            }

        }

        private static TaskDefinitionNameComparator getInstance() {
            return instance;
        }
    }
    

    private void processTaskDefinitions()  {

        

        //check if duplicate tasks
        Set<TaskDefinition> uniqueTaskDefinitions = new HashSet<TaskDefinition>(finalTaskDefinitions.size());
        for (TaskDefinition td : finalTaskDefinitions) {
            if (!uniqueTaskDefinitions.add(td)) {
                throw new TahminException("There are duplicate tasks with same name=" + td.getName());

            }
        }

        // process overriden tasks. removeAndInsert them with overriden task and keep the position of the overidden task
        Iterator<TaskDefinition> it = finalTaskDefinitions.iterator();        
        while (it.hasNext()) {
            TaskDefinition td = it.next();
            if (td.getOverriding() != null) {
                List<TaskDefinition> sortedList = new ArrayList<TaskDefinition>(finalTaskDefinitions);
                Collections.sort(sortedList);
                TaskDefinition tdtemp = new TaskDefinition();
                tdtemp.setName(td.getOverriding());
                int index = Collections.binarySearch(sortedList, tdtemp);
                if (index == -1) {
                    throw new TahminException("task " + td.getName() + " is overriding=" + td.getOverriding() + " but there is no task with overridding name");
                }
                TaskDefinition willBeOverridden = sortedList.get(index);
                willBeOverridden.overrideTask(td);
                it.remove();


            }

        }
        for (TaskDefinition taskDefiniton : finalTaskDefinitions) {
            getExceutableTasks().add(taskDefiniton.createExecutableTask(name));

        }
    }

    /**
     * @return the exceutableTasks
     */
    public List<AbstractTask> getExceutableTasks() {
        return exceutableTasks;
    }
}
