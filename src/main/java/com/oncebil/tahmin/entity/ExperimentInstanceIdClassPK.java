package com.oncebil.tahmin.entity;

import java.io.Serializable;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class ExperimentInstanceIdClassPK implements Serializable {

    private String experiment;
    private String instanceId;
    private String classificationClass;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperimentInstanceIdClassPK that = (ExperimentInstanceIdClassPK) o;

        if (!experiment.equals(that.experiment)) return false;
        if (!instanceId.equals(that.instanceId)) return false;
        return classificationClass.equals(that.classificationClass);

    }

    @Override
    public int hashCode() {
        int result = experiment.hashCode();
        result = 31 * result + instanceId.hashCode();
        result = 31 * result + classificationClass.hashCode();
        return result;
    }
}
