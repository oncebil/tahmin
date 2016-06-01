package com.oncebil.tahmin.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
@Entity
@IdClass(ExperimentInstanceIdPK.class)
@Table(name = "RegressionPrediction")
public class RegressionPrediction implements Serializable {

    @Id
    private String experiment;
    @Id
    private String instanceId;
    @Column(name = "actual")
    private BigDecimal actual;
    @Column(name = "predicted")
    private BigDecimal predicted;
    @Column(name = "error")
    private BigDecimal error;
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "instanceId",insertable = false,updatable = false)
    })
    @NotFound(action= NotFoundAction.IGNORE)
    private AtKosu atKosu;

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

    public AtKosu getAtKosu() {
        return atKosu;
    }

    public void setAtKosu(AtKosu atKosu) {
        this.atKosu = atKosu;
    }
}
