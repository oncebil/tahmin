package com.oncebil.tahmin.entity;

import java.math.BigDecimal;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class RegressionPrediction {

    private String experiment;
    private String instanceId;
    private BigDecimal actual;
    private BigDecimal predicted;
    private BigDecimal error;

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public BigDecimal getActual() {
        return actual;
    }

    public void setActual(BigDecimal actual) {
        this.actual = actual;
    }

    public BigDecimal getPredicted() {
        return predicted;
    }

    public void setPredicted(BigDecimal predicted) {
        this.predicted = predicted;
    }

    public BigDecimal getError() {
        return error;
    }

    public void setError(BigDecimal error) {
        this.error = error;
    }
}
