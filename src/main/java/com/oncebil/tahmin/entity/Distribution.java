package com.oncebil.tahmin.entity;

import java.math.BigDecimal;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class Distribution {


    private String instanceId;
    private String experiment;
    private String classificationClass;
    private BigDecimal distribution;

    public Distribution() {
    }

    public Distribution(String instanceId, String experiment, String classificationClass, BigDecimal distribution) {
        this.instanceId = instanceId;
        this.experiment = experiment;
        this.classificationClass = classificationClass;
        this.distribution = distribution;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }

    public String getClassificationClass() {
        return classificationClass;
    }

    public void setClassificationClass(String classificationClass) {
        this.classificationClass = classificationClass;
    }

    public BigDecimal getDistribution() {
        return distribution;
    }

    public void setDistribution(BigDecimal distribution) {
        this.distribution = distribution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Distribution that = (Distribution) o;

        if (!instanceId.equals(that.instanceId)) return false;
        if (!experiment.equals(that.experiment)) return false;
        if (!classificationClass.equals(that.classificationClass)) return false;
        return distribution.equals(that.distribution);

    }

    @Override
    public int hashCode() {
        int result = instanceId.hashCode();
        result = 31 * result + experiment.hashCode();
        result = 31 * result + classificationClass.hashCode();
        result = 31 * result + distribution.hashCode();
        return result;
    }
}
