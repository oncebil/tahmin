package com.oncebil.tahmin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by erkinkarincaoglu on 01/06/2016.
 */
@Entity
@Table(name = "Bahisler")
public class Bahis {
    @Id
    private Long id;
    @Column(name = "kosukodu")
    private Long kosuKodu;
    @Column(name = "bahistipkodu")
    private Integer bahisTipKodu;
    @Column(name = "bahistipadi")
    private String bahisTipAdi;
    @Column(name = "tutar")
    private BigDecimal tutar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKosuKodu() {
        return kosuKodu;
    }

    public void setKosuKodu(Long kosuKodu) {
        this.kosuKodu = kosuKodu;
    }

    public Integer getBahisTipKodu() {
        return bahisTipKodu;
    }

    public void setBahisTipKodu(Integer bahisTipKodu) {
        this.bahisTipKodu = bahisTipKodu;
    }

    public String getBahisTipAdi() {
        return bahisTipAdi;
    }

    public void setBahisTipAdi(String bahisTipAdi) {
        this.bahisTipAdi = bahisTipAdi;
    }

    public BigDecimal getTutar() {
        return tutar;
    }

    public void setTutar(BigDecimal tutar) {
        this.tutar = tutar;
    }
}
