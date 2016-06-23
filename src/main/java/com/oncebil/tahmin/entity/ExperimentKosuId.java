package com.oncebil.tahmin.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by erkinkarincaoglu on 23/06/2016.
 */
@Embeddable
public class ExperimentKosuId implements Serializable{


    private ExperimentResult experimentResult;

    private Long kosuKodu;

    public ExperimentResult getExperimentResult() {
        return experimentResult;
    }

    public void setExperimentResult(ExperimentResult experimentResult) {
        this.experimentResult = experimentResult;
    }

    public Long getKosuKodu() {
        return kosuKodu;
    }

    public void setKosuKodu(Long kosuKodu) {
        this.kosuKodu = kosuKodu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperimentKosuId that = (ExperimentKosuId) o;

        if (experimentResult != null ? !experimentResult.equals(that.experimentResult) : that.experimentResult != null)
            return false;
        return kosuKodu != null ? kosuKodu.equals(that.kosuKodu) : that.kosuKodu == null;

    }

    @Override
    public int hashCode() {
        int result = experimentResult != null ? experimentResult.hashCode() : 0;
        result = 31 * result + (kosuKodu != null ? kosuKodu.hashCode() : 0);
        return result;
    }
}
