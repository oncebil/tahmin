/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oncebil.tahmin.otamasyon;


/**
 *
 * @author ekarincaoglu
 */

public abstract class AbstractTask {

    protected AbstractTask(String projectName, String name) {
        this.name = name;
        this.projectName = projectName;
    }
    private String name;
    private String projectName;

    public abstract void run();

    public abstract Object getOutput();

    public abstract void setInput(Object object);
    
    public String getName() {
        return name;
        
    }

    protected String getProjectName() {
        return projectName;

    }
    
    protected void setProjectName(String projectName) {
        this.projectName = projectName;

    }   
    
    protected void setTaskName(String taskName) {
        this.name = taskName;

    }        
}
