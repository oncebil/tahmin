/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oncebil.tahmin.otamasyon.task;


/**
 *
 * @author ekarincaoglu
 */
public abstract class TaskBuilder {
    
    private String name;
    private String projectName;
    private TaskDefinition taskDefinition;

    public TaskBuilder(String projectName, String name, TaskDefinition taskDefinition ) {
        this.name = name;
        this.projectName = projectName;
        this.taskDefinition = taskDefinition;

    }

    public abstract AbstractTask build();


    
    public String getName() {
        return name;

    }
    public void setName(String name) {
        this.name = name;

    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the taskDefinition
     */
    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }




  
}
