package com.oncebil.tahmin.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
@Entity
@Table(name = "AtKosu")
public class Kosu {
    @Id
    @Column(name = "KOSUKODU")
    private Long KOSUKODU;


    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "KOSUKODU", referencedColumnName = "KOSUKODU",insertable = false,updatable = false)
    })

    private Set<Bahis> bahisler;

    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "KOSUKODU", referencedColumnName = "KOSUKODU",insertable = false,updatable = false)
    })

    private Set<AtKosu> atlar;


    public Long getKOSUKODU() {
        return KOSUKODU;
    }

    public void setKOSUKODU(Long KOSUKODU) {
        this.KOSUKODU = KOSUKODU;
    }


    public Set<Bahis> getBahisler() {
        return bahisler;
    }

    public void setBahisler(Set<Bahis> bahisler) {
        this.bahisler = bahisler;
    }

    public Set<AtKosu> getAtlar() {
        return atlar;
    }

    public void setAtlar(Set<AtKosu> atlar) {
        this.atlar = atlar;
    }

    /**
     * This method assumes that atlar has filled with regression predictions already
     * Normally at has one regression prediction when kosular is selected with regression experiment
     * So only the first regression is selected
     * @return
     */
    @Transient
    public List<RegressionPrediction> getAtlarWithRegressionPredictions() {
        List<RegressionPrediction> regressionPredictions = new ArrayList<>();
        for (AtKosu at : getAtlar()) {
            regressionPredictions.add( at.getRegressionPredictions().iterator().next());
        }
        return regressionPredictions;
    }

    @Transient
    public BigDecimal getMinumumRegressionPredicted() {
        List<RegressionPrediction> regressionPredictions = getAtlarWithRegressionPredictions();
        regressionPredictions.sort((p1, p2) -> p1.getPredicted().compareTo(p2.getPredicted()));
        return regressionPredictions.get(0).getPredicted();
    }
    @Transient
    public BigDecimal getSecondMinumumRegressionPredicted() {
        List<RegressionPrediction> regressionPredictions = getAtlarWithRegressionPredictions();
        regressionPredictions.sort((p1, p2) -> p1.getPredicted().compareTo(p2.getPredicted()));
        return regressionPredictions.get(1).getPredicted();
    }

}
