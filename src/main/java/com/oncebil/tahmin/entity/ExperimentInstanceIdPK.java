package com.oncebil.tahmin.entity;

import java.io.Serializable;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class ExperimentInstanceIdPK implements Serializable {

    private String experiment;

    private String instanceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperimentInstanceIdPK that = (ExperimentInstanceIdPK) o;

        if (!experiment.equals(that.experiment)) return false;
        return instanceId.equals(that.instanceId);

    }

    @Override
    public int hashCode() {
        int result = experiment.hashCode();
        result = 31 * result + instanceId.hashCode();
        return result;
    }
}
