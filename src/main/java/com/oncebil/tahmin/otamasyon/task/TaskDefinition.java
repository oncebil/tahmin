/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.oncebil.tahmin.otamasyon.task;



import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.TahminException;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author ekarincaoglu
 */
@Root(name = "task")
public class TaskDefinition implements Comparable<TaskDefinition> {

    private final static Logger logger = Logger.getLogger(TaskDefinition.class.getName());
    @Attribute(required = false)
    private String className;
    @Attribute(required = false)
    private String parent;
    @ElementMap(entry = "property", key = "key", attribute = true, inline = true, required = false)
    private Map<String, String> map;
    @Attribute
    private String name;
    @Attribute(required = false)
    private String overriding;

    public String getProperty(String key) {
        return map.get(key);

    }

    public String getClassName() {
        return className;

    }

    public String getParent() {
        return parent;

    }

    public String getName() {
        return name;

    }

    public Map<String, String> getMap() {
        return map;

    }

    protected void setName(String name) {
        this.name = name;

    }

    protected String getOverriding() {
        return overriding;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof TaskDefinition)) {
            return false;
        }


        TaskDefinition taskDefinition = (TaskDefinition) o;


        return taskDefinition.name.equals(name);


    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    protected void mergeWithTask(TaskDefinition parentTaskDefinition) {
        if (map == null) {
            map = new HashMap<String, String>();
        }

        for (String key : parentTaskDefinition.map.keySet()) {
            if (!map.containsKey(key)) {

                map.put(key, parentTaskDefinition.map.get(key));
            }
        }
        if (parentTaskDefinition.className != null && this.className == null) {
            this.className = parentTaskDefinition.className;
        }

        if (parentTaskDefinition.parent != null && this.parent == null) {
            this.parent = parentTaskDefinition.parent;
        }


    }

    protected void overrideTask(TaskDefinition overrideWith) {
        this.name = overrideWith.name;
        if (map == null) {
            map = new HashMap<String, String>();
        }

        for (String key : overrideWith.map.keySet()) {
            map.put(key, overrideWith.map.get(key));

        }
        if (overrideWith.className != null)
            this.className = overrideWith.className;
        if (overrideWith.parent != null)
            this.parent = overrideWith.parent;
    }

    protected void processParent() {
        if (parent != null) {

            Serializer serializer = new Persister();
            String file = ApplicationConstants.repositoryPath + "/" + parent + ".xml";
            File source = new File(file);
            if (!source.exists()) {
                throw new TahminException(file + " can't be found");
            }

            TaskDefinition parentTaskDefinition;
            try {
                parentTaskDefinition = serializer.read(TaskDefinition.class, source);
            } catch (Exception ex) {
                throw new TahminException("Can't create task from xml resource resource=" + file, ex);
            }
            mergeWithTask(parentTaskDefinition);


        }
    }

    protected AbstractTask createExecutableTask(String projectName) {
        processParent();


        if (className == null) {
            throw new TahminException("className for the task is null task=" + this);
        }
        String concreateClassName = ApplicationConstants.taskDefinitons.get(className);
        if (concreateClassName == null) {
            throw new TahminException("Concreate class name can't be found for task task=" + this);
        }

        TaskBuilder taskBuilder = null;

        try {
            //Object object = Class.forName(concreateClassName).newInstance(projectName, name ,this);
            Constructor constructor = Class.forName(concreateClassName).getConstructor(String.class, String.class, TaskDefinition.class);
            Object object = constructor.newInstance(projectName, name, this);

            if (!(object instanceof TaskBuilder)) {
                throw new TahminException(concreateClassName + " does not implement TaskBuilder");
            }
            taskBuilder = (TaskBuilder) object;


            Map<String, Field> annotations = new HashMap<String, Field>();


            for (Field f : Class.forName(concreateClassName).getDeclaredFields()) {
                if (f.isAnnotationPresent(Parameter.class)) {
                    annotations.put(f.getAnnotation(Parameter.class).value(), f);
                }

            }
            if (map == null) {
                //throw new TaskCreationException("xml task="+getName()+" contains no parameters at all ");
                logger.warning("xml task=" + getName() + " contains no parameters at all ");
                map = new HashMap<String, String>();

            }
            Set<String> keys = map.keySet();
            for (String key : keys) {
                Field f = annotations.get(key);
                if (f == null) {
                    throw new TahminException("Field f=" + key + " doesn't exist but xml task=" + getName() + " contains a paramater named=" + key);

                }
                f.setAccessible(true);
                String value = getProperty(f.getAnnotation(Parameter.class).value());
                if (value != null) {
                    f.set(object, value);
                }


            }


        } catch (InstantiationException |IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
            throw new TahminException("can't create class task className=" + concreateClassName, ex);
        }


        return taskBuilder.build();

    }

    @Override
    public String toString() {
        return "TaskDefinition:name=" + name + " classname=" + getClass().getName() + " parent=" + parent + " className=" + className + " overriding=" + overriding + " properties=" + map;

    }

    @Override
    public int compareTo(TaskDefinition o) {
        return name.compareTo(o.name);

    }
}
