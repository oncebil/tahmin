package com.oncebil.tahmin.entity;

import com.oncebil.tahmin.TahminException;
import org.slf4j.LoggerFactory;
import weka.classifiers.Evaluation;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by erkinkarincaoglu on 31/05/2016.
 */
@Entity
@Table(name = "ExperimentResult")

public class ExperimentResult {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExperimentResult.class);
    @Id
    private String experiment;

    @OneToMany( mappedBy = "experimentResult",cascade = CascadeType.ALL)
    private Set<Kazanc> kazanclar = new HashSet<>();
    private Double correct;
    private Double pctCorrect;
    private Double incorrect;
    private Double pctIncorrect;
    private Double kappa;
    private Double totalCost;
    private Double avgCost;
    private Double KBRelativeInformation;
    private Double KBInformation;
    private Double KBMeanInformation;
    private Double SFPriorEntropy;
    private Double SFMeanPriorEntropy;
    private Double SFSchemeEntropy;
    private Double SFMeanSchemeEntropy;
    private Double SFEntropyGain;
    private Double SFMeanEntropyGain;
    private Double meanAbsoluteError;
    private Double rootMeanSquaredError;
    private Double relativeAbsoluteError;
    private Double rootRelativeSquaredError;
    private Double correlationCoefficient;
    private boolean classification;
    private String project;
    private byte[] arff;



    public void populateEvaluationResults(Evaluation evaluation, boolean classification) {
        try {
            this.setClassification(classification);
            setCorrect(evaluation.correct());
            setPctCorrect(evaluation.pctCorrect());
            setIncorrect(evaluation.incorrect());
            setPctIncorrect(evaluation.pctIncorrect());
            setTotalCost(evaluation.totalCost());
            setAvgCost(evaluation.avgCost());
            setSFPriorEntropy(evaluation.SFPriorEntropy());
            setSFMeanPriorEntropy(evaluation.SFMeanPriorEntropy());
            setSFSchemeEntropy(evaluation.SFSchemeEntropy());
            setSFMeanSchemeEntropy(evaluation.SFMeanSchemeEntropy());
            setSFEntropyGain(evaluation.SFEntropyGain());
            setSFMeanEntropyGain(evaluation.SFMeanEntropyGain());
            setMeanAbsoluteError(evaluation.meanAbsoluteError());
            setRootMeanSquaredError(evaluation.rootMeanSquaredError());
            setRelativeAbsoluteError(evaluation.relativeAbsoluteError());
            setRootRelativeSquaredError(evaluation.rootRelativeSquaredError());
            if (classification) {
                setKappa(evaluation.kappa());
                setKBRelativeInformation(evaluation.KBRelativeInformation());
                setKBInformation(evaluation.KBInformation());
                setKBMeanInformation(evaluation.KBMeanInformation());
            } else {
                setCorrelationCoefficient(evaluation.correlationCoefficient());
            }
        } catch (Exception e) {
            throw new TahminException(e);
        }

    }


    public Set<Kazanc> getKazanclar() {
        return kazanclar;
    }

    public void setKazanclar(Set<Kazanc> kazanclar) {
        this.kazanclar = kazanclar;
    }

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }

    public Double getCorrect() {
        return correct;
    }

    public void setCorrect(Double correct) {
        this.correct = correct;
    }

    public Double getPctCorrect() {
        return pctCorrect;
    }

    public void setPctCorrect(Double pctCorrect) {
        this.pctCorrect = pctCorrect;
    }

    public Double getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(Double incorrect) {
        this.incorrect = incorrect;
    }

    public Double getPctIncorrect() {
        return pctIncorrect;
    }

    public void setPctIncorrect(Double pctIncorrect) {
        this.pctIncorrect = pctIncorrect;
    }

    public Double getKappa() {
        return kappa;
    }

    public void setKappa(Double kappa) {
        this.kappa = kappa;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(Double avgCost) {
        this.avgCost = avgCost;
    }

    public Double getKBRelativeInformation() {
        return KBRelativeInformation;
    }

    public void setKBRelativeInformation(Double KBRelativeInformation) {
        this.KBRelativeInformation = KBRelativeInformation;
    }

    public Double getKBInformation() {
        return KBInformation;
    }

    public void setKBInformation(Double KBInformation) {
        this.KBInformation = KBInformation;
    }

    public Double getKBMeanInformation() {
        return KBMeanInformation;
    }

    public void setKBMeanInformation(Double KBMeanInformation) {
        this.KBMeanInformation = KBMeanInformation;
    }

    public Double getSFPriorEntropy() {
        return SFPriorEntropy;
    }

    public void setSFPriorEntropy(Double SFPriorEntropy) {
        this.SFPriorEntropy = SFPriorEntropy;
    }

    public Double getSFMeanPriorEntropy() {
        return SFMeanPriorEntropy;
    }

    public void setSFMeanPriorEntropy(Double SFMeanPriorEntropy) {
        this.SFMeanPriorEntropy = SFMeanPriorEntropy;
    }

    public Double getSFSchemeEntropy() {
        return SFSchemeEntropy;
    }

    public void setSFSchemeEntropy(Double SFSchemeEntropy) {
        this.SFSchemeEntropy = SFSchemeEntropy;
    }

    public Double getSFMeanSchemeEntropy() {
        return SFMeanSchemeEntropy;
    }

    public void setSFMeanSchemeEntropy(Double SFMeanSchemeEntropy) {
        this.SFMeanSchemeEntropy = SFMeanSchemeEntropy;
    }

    public Double getSFEntropyGain() {
        return SFEntropyGain;
    }

    public void setSFEntropyGain(Double SFEntropyGain) {
        this.SFEntropyGain = SFEntropyGain;
    }

    public Double getSFMeanEntropyGain() {
        return SFMeanEntropyGain;
    }

    public void setSFMeanEntropyGain(Double SFMeanEntropyGain) {
        this.SFMeanEntropyGain = SFMeanEntropyGain;
    }

    public Double getMeanAbsoluteError() {
        return meanAbsoluteError;
    }

    public void setMeanAbsoluteError(Double meanAbsoluteError) {
        this.meanAbsoluteError = meanAbsoluteError;
    }

    public Double getRootMeanSquaredError() {
        return rootMeanSquaredError;
    }

    public void setRootMeanSquaredError(Double rootMeanSquaredError) {
        this.rootMeanSquaredError = rootMeanSquaredError;
    }

    public Double getRelativeAbsoluteError() {
        return relativeAbsoluteError;
    }

    public void setRelativeAbsoluteError(Double relativeAbsoluteError) {
        this.relativeAbsoluteError = relativeAbsoluteError;
    }

    public Double getRootRelativeSquaredError() {
        return rootRelativeSquaredError;
    }

    public void setRootRelativeSquaredError(Double rootRelativeSquaredError) {
        this.rootRelativeSquaredError = rootRelativeSquaredError;
    }

    public Double getCorrelationCoefficient() {
        return correlationCoefficient;
    }

    public void setCorrelationCoefficient(Double correlationCoefficient) {
        this.correlationCoefficient = correlationCoefficient;
    }

    public boolean isClassification() {
        return classification;
    }

    public void setClassification(boolean classification) {
        this.classification = classification;
    }


    @Override
    public String toString() {
        return "ExperimentResult{" +
                "experiment='" + experiment + '\'' +
                ", kazanclar=" + kazanclar +
                ", correct=" + correct +
                ", pctCorrect=" + pctCorrect +
                ", incorrect=" + incorrect +
                ", pctIncorrect=" + pctIncorrect +
                ", kappa=" + kappa +
                ", totalCost=" + totalCost +
                ", avgCost=" + avgCost +
                ", KBRelativeInformation=" + KBRelativeInformation +
                ", KBInformation=" + KBInformation +
                ", KBMeanInformation=" + KBMeanInformation +
                ", SFPriorEntropy=" + SFPriorEntropy +
                ", SFMeanPriorEntropy=" + SFMeanPriorEntropy +
                ", SFSchemeEntropy=" + SFSchemeEntropy +
                ", SFMeanSchemeEntropy=" + SFMeanSchemeEntropy +
                ", SFEntropyGain=" + SFEntropyGain +
                ", SFMeanEntropyGain=" + SFMeanEntropyGain +
                ", meanAbsoluteError=" + meanAbsoluteError +
                ", rootMeanSquaredError=" + rootMeanSquaredError +
                ", relativeAbsoluteError=" + relativeAbsoluteError +
                ", rootRelativeSquaredError=" + rootRelativeSquaredError +
                ", correlationCoefficient=" + correlationCoefficient +
                ", classification=" + classification +
                '}';
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public byte[] getArff() {
        return arff;
    }

    public void setArff(byte[] arff) {
        this.arff = arff;
    }
}
