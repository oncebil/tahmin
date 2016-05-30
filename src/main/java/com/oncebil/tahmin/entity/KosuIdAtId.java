package com.oncebil.tahmin.entity;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by erkinkarincaoglu on 30/05/2016.
 */
public class KosuIdAtId implements Serializable {
    @Column(name="KOSUKODU")
    private Long kosuKodu;

    @Column(name="ATKODU")
    private Long atKodu;

    public Long getKosuKodu() {
        return kosuKodu;
    }

    public void setKosuKodu(Long kosuKodu) {
        this.kosuKodu = kosuKodu;
    }

    public Long getAtKodu() {
        return atKodu;
    }

    public void setAtKodu(Long atKodu) {
        this.atKodu = atKodu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KosuIdAtId that = (KosuIdAtId) o;

        if (!kosuKodu.equals(that.kosuKodu)) return false;
        return atKodu.equals(that.atKodu);

    }

    @Override
    public int hashCode() {
        int result = kosuKodu.hashCode();
        result = 31 * result + atKodu.hashCode();
        return result;
    }
}
