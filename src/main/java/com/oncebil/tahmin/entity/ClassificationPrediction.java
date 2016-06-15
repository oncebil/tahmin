package com.oncebil.tahmin.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
@Entity
@IdClass(ExperimentInstanceIdPK.class)
@Table(name = "ClassificationPrediction")
public class ClassificationPrediction {
    @Id
    private String experiment;
    @Id
    private String instanceId;
    @Column(name = "actual")
    private String actual;
    @Column(name = "actualIndex")
    private int actualIndex;
    @Column(name = "predicted")
    private String predicted;
    @Column(name = "predictedIndex")
    private int predictedIndex;
    @Column(name = "error")
    private boolean error;

    @OneToMany( fetch=FetchType.EAGER ,cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "experiment", referencedColumnName = "experiment"),
            @JoinColumn(name = "instanceId", referencedColumnName = "instanceId")
    })
    private Set<Distribution> distributions = new HashSet<>();

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "instanceId",insertable = false,updatable = false)
    })
    @NotFound(action= NotFoundAction.IGNORE)
    private AtKosu atKosu;

    public Set<Distribution> getDistributions() {
        return distributions;
    }

    public void setDistributions(Set<Distribution> distributions) {
        this.distributions = distributions;
    }

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

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getPredicted() {
        return predicted;
    }

    public void setPredicted(String predicted) {
        this.predicted = predicted;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getActualIndex() {
        return actualIndex;
    }

    public void setActualIndex(int actualIndex) {
        this.actualIndex = actualIndex;
    }

    public int getPredictedIndex() {
        return predictedIndex;
    }

    public void setPredictedIndex(int predictedIndex) {
        this.predictedIndex = predictedIndex;
    }

    public AtKosu getAtKosu() {
        return atKosu;
    }

    public void setAtKosu(AtKosu atKosu) {
        this.atKosu = atKosu;
    }
}
