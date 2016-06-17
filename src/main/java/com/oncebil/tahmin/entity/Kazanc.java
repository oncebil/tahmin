package com.oncebil.tahmin.entity;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by erkinkarincaoglu on 15/06/2016.
 */
@Entity
@Table(name = "Kazanc")
public class Kazanc implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private
    Long id;

    @ManyToOne
    @JoinColumn(name="experiment",referencedColumnName="experiment")
    private ExperimentResult experimentResult;

    @Column(name = "gameType")
    private String gameType;

    @Column(name = "type")
    private String type;

    @Column(name = "index")
    private Integer index;
    @Column(name = "kacKosuVardi")
    private Integer kacKosuVardi;
    @Column(name = "threshold")
    private Double threshold;
    @Column(name = "kacKosudaOynardik")
    private Integer kacKosudaOynardik;
    @Column(name = "kacKosudaBilirdik")
    private Integer kacKosudaBilirdik;
    @Column(name = "neKadarVerirdik")
    private Double neKadarVerirdik;
    @Column(name = "kazancOraninKacOlurdu")
    private Double kazancOraninKacOlurdu;
    @Column(name = "yuzdeKacindaOynardik")
    private Double yuzdeKacindaOynardik;
    @Column(name = "yuzdeKacindaBilirdik")
    private Double yuzdeKacindaBilirdik;
    @Column(name = "kacliraKazanirdik")
    private Double kacliraKazanirdik;
    @Column(name = "kacKosudaOynanabilirdi")
    private Integer kacKosudaOynanabilirdi = 0;
    @OneToMany( mappedBy = "kazanc",cascade = CascadeType.ALL)
    private Set<BilinenKosu> bilinenKosular = new HashSet<>();

    public ExperimentResult getExperimentResult() {
        return experimentResult;
    }

    public void setExperimentResult(ExperimentResult experimentResult) {
        this.experimentResult = experimentResult;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getKacKosuVardi() {
        return kacKosuVardi;
    }

    public void setKacKosuVardi(Integer kacKosuVardi) {
        this.kacKosuVardi = kacKosuVardi;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public Integer getKacKosudaOynardik() {
        return kacKosudaOynardik;
    }

    public void setKacKosudaOynardik(Integer kacKosudaOynardik) {
        this.kacKosudaOynardik = kacKosudaOynardik;
    }

    public Integer getKacKosudaBilirdik() {
        return kacKosudaBilirdik;
    }

    public void setKacKosudaBilirdik(Integer kacKosudaBilirdik) {
        this.kacKosudaBilirdik = kacKosudaBilirdik;
    }

    public Double getNeKadarVerirdik() {
        return neKadarVerirdik;
    }

    public void setNeKadarVerirdik(Double neKadarVerirdik) {
        this.neKadarVerirdik = neKadarVerirdik;
    }

    public Double getKazancOraninKacOlurdu() {
        return kazancOraninKacOlurdu;
    }

    public void setKazancOraninKacOlurdu(Double kazancOraninKacOlurdu) {
        this.kazancOraninKacOlurdu = kazancOraninKacOlurdu;
    }

    public Double getYuzdeKacindaOynardik() {
        return yuzdeKacindaOynardik;
    }

    public void setYuzdeKacindaOynardik(Double yuzdeKacindaOynardik) {
        this.yuzdeKacindaOynardik = yuzdeKacindaOynardik;
    }

    public Double getYuzdeKacindaBilirdik() {
        return yuzdeKacindaBilirdik;
    }

    public void setYuzdeKacindaBilirdik(Double yuzdeKacindaBilirdik) {
        this.yuzdeKacindaBilirdik = yuzdeKacindaBilirdik;
    }

    public Double getKacliraKazanirdik() {
        return kacliraKazanirdik;
    }

    public void setKacliraKazanirdik(Double kacliraKazanirdik) {
        this.kacliraKazanirdik = kacliraKazanirdik;
    }

    public Integer getKacKosudaOynanabilirdi() {
        return kacKosudaOynanabilirdi;
    }

    public void setKacKosudaOynanabilirdi(Integer kacKosudaOynanabilirdi) {
        this.kacKosudaOynanabilirdi = kacKosudaOynanabilirdi;
    }

    public Set<BilinenKosu> getBilinenKosular() {
        return bilinenKosular;
    }

    public void setBilinenKosular(Set<BilinenKosu> bilinenKosular) {
        this.bilinenKosular = bilinenKosular;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kazanc kazanc = (Kazanc) o;

        return id != null ? id.equals(kazanc.id) : kazanc.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
