/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ekarincaoglu
 */
@Entity
@Table(name = "AtKosu")
public class AtKosu implements Serializable {

    @Id
    @Column(name = "KOSUKODUATKODU")
    private String kosuKoduAtKodu;
    @Column(name = "KOSUKODU")
    private Long KOSUKODU;
    @Column(name = "ATKODU")
    private Long ATKODU;

    @Column(name = "KOSUTARIHI")
    private String KOSUTARIHI;
    @Column(name = "HIPODROMKODU")
    private Long HIPODROMKODU;
    @Column(name = "HIPODROMYERI")
    private String HIPODROMYERI;
    @Column(name = "CIM")
    private String CIM;
    @Column(name = "KUM")
    private String KUM;
    @Column(name = "KOSUNO")
    private Long KOSUNO;
    @Column(name = "ONEMLIKOSUADI")
    private String ONEMLIKOSUADI;
    @Column(name = "KOSUCINSDETAYADI")
    private String KOSUCINSDETAYADI;
    @Column(name = "GRUPADI")
    private String GRUPADI;
    @Column(name = "IKRAMIYE1")
    private Long IKRAMIYE1;
    @Column(name = "IKRAMIYE2")
    private Long IKRAMIYE2;
    @Column(name = "IKRAMIYE3")
    private Long IKRAMIYE3;
    @Column(name = "IKRAMIYE4")
    private Long IKRAMIYE4;
    @Column(name = "MESAFE")
    private Long MESAFE;
    @Column(name = "PISTUZUNADI")
    private String PISTUZUNADI;
    @Column(name = "ATNO")
    private Long ATNO;
    @Column(name = "ATADI")
    private String ATADI;
    @Column(name = "SONKILO")
    private Float SONKILO;
    @Column(name = "JOKEYKISAADI")
    private String JOKEYKISAADI;
    @Column(name = "SAHIPKISAADI")
    private String SAHIPKISAADI;
    @Column(name = "STARTNO")
    private Long STARTNO;
    @Column(name = "PUAN")
    private Long PUAN;
    @Column(name = "PUANKUM")
    private Long PUANKUM;
    @Column(name = "ANNEBABAKODU")
    private Long ANNEBABAKODU;
    @Column(name = "ANNEBABAKODU1")
    private Long ANNEBABAKODU1;
    @Column(name = "ANTRENORKODU")
    private Long ANTRENORKODU;
    @Column(name = "SAHIPKODU")
    private Long SAHIPKODU;
    @Column(name = "JOKEYKODU")
    private Long JOKEYKODU;
    @Column(name = "SONUCNO")
    private Long SONUCNO;
    @Column(name = "DERECE")
    private Long DERECE;
    @Column(name = "GANYAN")
    private BigDecimal GANYAN;
    @Column(name = "AtIkramiye")
    private Long AtIkramiye;
    //@Column(name = "AtinToplam3KosusundakiKazanciOrtalamasi")
    //private Long AtinToplam3KosusundakiKazanciOrtalamasi;
    @Column(name = "birincilikYuzdesi")
    private BigDecimal birincilikYuzdesi;
    @Column(name = "ikincilikYuzdesi")
    private BigDecimal ikincilikYuzdesi;
    @Column(name = "ucunculukYuzdesi")
    private BigDecimal ucunculukYuzdesi;
    @Column(name = "ikinciyeGirisYuzdesi")
    private BigDecimal ikinciyeGirisYuzdesi;
    @Column(name = "ucuncuyeGirisYuzdesi")
    private BigDecimal ucuncuyeGirisYuzdesi;
    @Column(name = "bitirisOrtalamasi")
    private BigDecimal bitirisOrtalamasi;
    @Column(name = "son3DereceFarkiOrtalamasi")
    private BigDecimal son3DereceFarkiOrtalamasi;
    @Column(name = "son7DereceFarkiOrtalamasi")
    private BigDecimal son7DereceFarkiOrtalamasi;
    @Column(name = "birinciIleDereceFarki")
    private BigDecimal birinciIleDereceFarki;

    @Column(name = "son7birincilikYuzdesi")
    private BigDecimal son7birincilikYuzdesi;
    @Column(name = "son7ikincilikYuzdesi")
    private BigDecimal son7ikincilikYuzdesi;
    @Column(name = "son7ucunculukYuzdesi")
    private BigDecimal son7ucunculukYuzdesi;
    @Column(name = "son7bitirisOrtalamasi")
    private BigDecimal son7bitirisOrtalamasi;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "instanceId", insertable = false,updatable = false)
    })
    private Set<RegressionPrediction> regressionPredictions;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "instanceId", insertable = false,updatable = false)
    })
    private Set<ClassificationPrediction> classificationPredictions;


    @Transient
    private HashMap<String,BigDecimal> dynamicValues = new HashMap<>();
    @Transient
    public void addDynamicValue(String key,BigDecimal value) {
        dynamicValues.put(key,value);
    }
    @Transient
    public BigDecimal getDynamicValue(String key) {
        return dynamicValues.get(key);
    }
//


    /**
     * @return the KOSUTARIHI
     */
    public String getKOSUTARIHI() {
        return KOSUTARIHI;
    }

    /**
     * @param KOSUTARIHI the KOSUTARIHI to set
     */
    public void setKOSUTARIHI(String KOSUTARIHI) {
        this.KOSUTARIHI = KOSUTARIHI;
    }

    /**
     * @return the HIPODROMKODU
     */
    public Long getHIPODROMKODU() {
        return HIPODROMKODU;
    }

    /**
     * @param HIPODROMKODU the HIPODROMKODU to set
     */
    public void setHIPODROMKODU(Long HIPODROMKODU) {
        this.HIPODROMKODU = HIPODROMKODU;
    }

    /**
     * @return the HIPODROMYERI
     */
    public String getHIPODROMYERI() {
        return HIPODROMYERI;
    }

    /**
     * @param HIPODROMYERI the HIPODROMYERI to set
     */
    public void setHIPODROMYERI(String HIPODROMYERI) {
        this.HIPODROMYERI = HIPODROMYERI;
    }

    /**
     * @return the CIM
     */
    public String getCIM() {
        return CIM;
    }

    /**
     * @param CIM the CIM to set
     */
    public void setCIM(String CIM) {
        this.CIM = CIM;
    }

    /**
     * @return the KUM
     */
    public String getKUM() {
        return KUM;
    }

    /**
     * @param KUM the KUM to set
     */
    public void setKUM(String KUM) {
        this.KUM = KUM;
    }



    /**
     * @return the KOSUNO
     */
    public Long getKOSUNO() {
        return KOSUNO;
    }

    /**
     * @param KOSUNO the KOSUNO to set
     */
    public void setKOSUNO(Long KOSUNO) {
        this.KOSUNO = KOSUNO;
    }

    /**
     * @return the KOSUCINSDETAYADI
     */
    public String getKOSUCINSDETAYADI() {
        return KOSUCINSDETAYADI;
    }

    /**
     * @param KOSUCINSDETAYADI the KOSUCINSDETAYADI to set
     */
    public void setKOSUCINSDETAYADI(String KOSUCINSDETAYADI) {
        this.KOSUCINSDETAYADI = KOSUCINSDETAYADI;
    }

    /**
     * @return the GRUPADI
     */
    public String getGRUPADI() {
        return GRUPADI;
    }

    /**
     * @param GRUPADI the GRUPADI to set
     */
    public void setGRUPADI(String GRUPADI) {
        this.GRUPADI = GRUPADI;
    }

    /**
     * @return the IKRAMIYE1
     */
    public Long getIKRAMIYE1() {
        return IKRAMIYE1;
    }

    /**
     * @param IKRAMIYE1 the IKRAMIYE1 to set
     */
    public void setIKRAMIYE1(Long IKRAMIYE1) {
        this.IKRAMIYE1 = IKRAMIYE1;
    }

    /**
     * @return the IKRAMIYE2
     */
    public Long getIKRAMIYE2() {
        return IKRAMIYE2;
    }

    /**
     * @param IKRAMIYE2 the IKRAMIYE2 to set
     */
    public void setIKRAMIYE2(Long IKRAMIYE2) {
        this.IKRAMIYE2 = IKRAMIYE2;
    }

    /**
     * @return the IKRAMIYE3
     */
    public Long getIKRAMIYE3() {
        return IKRAMIYE3;
    }

    /**
     * @param IKRAMIYE3 the IKRAMIYE3 to set
     */
    public void setIKRAMIYE3(Long IKRAMIYE3) {
        this.IKRAMIYE3 = IKRAMIYE3;
    }

    /**
     * @return the IKRAMIYE4
     */
    public Long getIKRAMIYE4() {
        return IKRAMIYE4;
    }

    /**
     * @param IKRAMIYE4 the IKRAMIYE4 to set
     */
    public void setIKRAMIYE4(Long IKRAMIYE4) {
        this.IKRAMIYE4 = IKRAMIYE4;
    }

    /**
     * @return the MESAFE
     */
    public Long getMESAFE() {
        return MESAFE;
    }

    /**
     * @param MESAFE the MESAFE to set
     */
    public void setMESAFE(Long MESAFE) {
        this.MESAFE = MESAFE;
    }

    /**
     * @return the PISTUZUNADI
     */
    public String getPISTUZUNADI() {
        return PISTUZUNADI;
    }

    /**
     * @param PISTUZUNADI the PISTUZUNADI to set
     */
    public void setPISTUZUNADI(String PISTUZUNADI) {
        this.PISTUZUNADI = PISTUZUNADI;
    }



    /**
     * @return the ATNO
     */
    public Long getATNO() {
        return ATNO;
    }

    /**
     * @param ATNO the ATNO to set
     */
    public void setATNO(Long ATNO) {
        this.ATNO = ATNO;
    }

    /**
     * @return the ATADI
     */
    public String getATADI() {
        return ATADI;
    }

    /**
     * @param ATADI the ATADI to set
     */
    public void setATADI(String ATADI) {
        this.ATADI = ATADI;
    }

    /**
     * @return the SONKILO
     */
    public Float getSONKILO() {
        return SONKILO;
    }

    /**
     * @param SONKILO the SONKILO to set
     */
    public void setSONKILO(Float SONKILO) {
        this.SONKILO = SONKILO;
    }

    /**
     * @return the JOKEYKISAADI
     */
    public String getJOKEYKISAADI() {
        return JOKEYKISAADI;
    }

    /**
     * @param JOKEYKISAADI the JOKEYKISAADI to set
     */
    public void setJOKEYKISAADI(String JOKEYKISAADI) {
        this.JOKEYKISAADI = JOKEYKISAADI;
    }

    /**
     * @return the SAHIPKISAADI
     */
    public String getSAHIPKISAADI() {
        return SAHIPKISAADI;
    }

    /**
     * @param SAHIPKISAADI the SAHIPKISAADI to set
     */
    public void setSAHIPKISAADI(String SAHIPKISAADI) {
        this.SAHIPKISAADI = SAHIPKISAADI;
    }

    /**
     * @return the STARTNO
     */
    public Long getSTARTNO() {
        return STARTNO;
    }

    /**
     * @param STARTNO the STARTNO to set
     */
    public void setSTARTNO(Long STARTNO) {
        this.STARTNO = STARTNO;
    }

    /**
     * @return the PUAN
     */
    public Long getPUAN() {
        return PUAN;
    }

    /**
     * @param PUAN the PUAN to set
     */
    public void setPUAN(Long PUAN) {
        this.PUAN = PUAN;
    }

    /**
     * @return the PUANKUM
     */
    public Long getPUANKUM() {
        return PUANKUM;
    }

    /**
     * @param PUANKUM the PUANKUM to set
     */
    public void setPUANKUM(Long PUANKUM) {
        this.PUANKUM = PUANKUM;
    }

    /**
     * @return the ANNEBABAKODU
     */
    public Long getANNEBABAKODU() {
        return ANNEBABAKODU;
    }

    /**
     * @param ANNEBABAKODU the ANNEBABAKODU to set
     */
    public void setANNEBABAKODU(Long ANNEBABAKODU) {
        this.ANNEBABAKODU = ANNEBABAKODU;
    }

    /**
     * @return the ANNEBABAKODU1
     */
    public Long getANNEBABAKODU1() {
        return ANNEBABAKODU1;
    }

    /**
     * @param ANNEBABAKODU1 the ANNEBABAKODU1 to set
     */
    public void setANNEBABAKODU1(Long ANNEBABAKODU1) {
        this.ANNEBABAKODU1 = ANNEBABAKODU1;
    }

    /**
     * @return the ANTRENORKODU
     */
    public Long getANTRENORKODU() {
        return ANTRENORKODU;
    }

    /**
     * @param ANTRENORKODU the ANTRENORKODU to set
     */
    public void setANTRENORKODU(Long ANTRENORKODU) {
        this.ANTRENORKODU = ANTRENORKODU;
    }

    /**
     * @return the SAHIPKODU
     */
    public Long getSAHIPKODU() {
        return SAHIPKODU;
    }

    /**
     * @param SAHIPKODU the SAHIPKODU to set
     */
    public void setSAHIPKODU(Long SAHIPKODU) {
        this.SAHIPKODU = SAHIPKODU;
    }

    /**
     * @return the JOKEYKODU
     */
    public Long getJOKEYKODU() {
        return JOKEYKODU;
    }

    /**
     * @param JOKEYKODU the JOKEYKODU to set
     */
    public void setJOKEYKODU(Long JOKEYKODU) {
        this.JOKEYKODU = JOKEYKODU;
    }

    /**
     * @return the SONUCNO
     */
    public Long getSONUCNO() {
        return SONUCNO;
    }

    /**
     * @param SONUCNO the SONUCNO to set
     */
    public void setSONUCNO(Long SONUCNO) {
        this.SONUCNO = SONUCNO;
    }

    /**
     * @return the DERECE
     */
    public Long getDERECE() {
        return DERECE;
    }

    /**
     * @param DERECE the DERECE to set
     */
    public void setDERECE(Long DERECE) {
        this.DERECE = DERECE;
    }

    /**
     * @return the GANYAN
     */
    public BigDecimal getGANYAN() {
        return GANYAN;
    }

    /**
     * @param GANYAN the GANYAN to set
     */
    public void setGANYAN(BigDecimal GANYAN) {
        this.GANYAN = GANYAN;
    }
    
    public Long getAtKazanc() {
        if (SONUCNO == 1  ) {
            return IKRAMIYE1;
        } else if (SONUCNO == 2) {
            return IKRAMIYE2;
        }else if (SONUCNO == 3) {
            return IKRAMIYE3;
        }else if (SONUCNO == 2) {
            return IKRAMIYE4;
        }
        return new Long(0);
    }




    /**
     * @return the ONEMLIKOSUADI
     */
    public String getONEMLIKOSUADI() {
        return ONEMLIKOSUADI;
    }

    /**
     * @param ONEMLIKOSUADI the ONEMLIKOSUADI to set
     */
    public void setONEMLIKOSUADI(String ONEMLIKOSUADI) {
        this.ONEMLIKOSUADI = ONEMLIKOSUADI;
    }

    /**
     * @return the AtIkramiye
     */
    public Long getAtIkramiye() {
        return AtIkramiye;
    }

    /**
     * @param AtIkramiye the AtIkramiye to set
     */
    public void setAtIkramiye(Long AtIkramiye) {
        this.AtIkramiye = AtIkramiye;
    }

    public BigDecimal getBirincilikYuzdesi() {
        return birincilikYuzdesi;
    }

    public void setBirincilikYuzdesi(BigDecimal birincilikYuzdesi) {
        this.birincilikYuzdesi = birincilikYuzdesi;
    }

    public BigDecimal getIkincilikYuzdesi() {
        return ikincilikYuzdesi;
    }

    public void setIkincilikYuzdesi(BigDecimal ikincilikYuzdesi) {
        this.ikincilikYuzdesi = ikincilikYuzdesi;
    }

    public BigDecimal getUcunculukYuzdesi() {
        return ucunculukYuzdesi;
    }

    public void setUcunculukYuzdesi(BigDecimal ucunculukYuzdesi) {
        this.ucunculukYuzdesi = ucunculukYuzdesi;
    }

    public BigDecimal getIkinciyeGirisYuzdesi() {
        return ikinciyeGirisYuzdesi;
    }

    public void setIkinciyeGirisYuzdesi(BigDecimal ikinciyeGirisYuzdesi) {
        this.ikinciyeGirisYuzdesi = ikinciyeGirisYuzdesi;
    }

    public BigDecimal getUcuncuyeGirisYuzdesi() {
        return ucuncuyeGirisYuzdesi;
    }

    public void setUcuncuyeGirisYuzdesi(BigDecimal ucuncuyeGirisYuzdesi) {
        this.ucuncuyeGirisYuzdesi = ucuncuyeGirisYuzdesi;
    }

    public BigDecimal getBitirisOrtalamasi() {
        return bitirisOrtalamasi;
    }

    public void setBitirisOrtalamasi(BigDecimal bitirisOrtalamasi) {
        this.bitirisOrtalamasi = bitirisOrtalamasi;
    }

    public BigDecimal getSon3DereceFarkiOrtalamasi() {
        return son3DereceFarkiOrtalamasi;
    }

    public void setSon3DereceFarkiOrtalamasi(BigDecimal son3DereceFarkiOrtalamasi) {
        this.son3DereceFarkiOrtalamasi = son3DereceFarkiOrtalamasi;
    }

    public BigDecimal getSon7DereceFarkiOrtalamasi() {
        return son7DereceFarkiOrtalamasi;
    }

    public void setSon7DereceFarkiOrtalamasi(BigDecimal son7DereceFarkiOrtalamasi) {
        this.son7DereceFarkiOrtalamasi = son7DereceFarkiOrtalamasi;
    }

    public BigDecimal getBirinciIleDereceFarki() {
        return birinciIleDereceFarki;
    }

    public void setBirinciIleDereceFarki(BigDecimal birinciIleDereceFarki) {
        this.birinciIleDereceFarki = birinciIleDereceFarki;
    }

    public BigDecimal getSon7birincilikYuzdesi() {
        return son7birincilikYuzdesi;
    }

    public void setSon7birincilikYuzdesi(BigDecimal son7birincilikYuzdesi) {
        this.son7birincilikYuzdesi = son7birincilikYuzdesi;
    }

    public BigDecimal getSon7ikincilikYuzdesi() {
        return son7ikincilikYuzdesi;
    }

    public void setSon7ikincilikYuzdesi(BigDecimal son7ikincilikYuzdesi) {
        this.son7ikincilikYuzdesi = son7ikincilikYuzdesi;
    }

    public BigDecimal getSon7ucunculukYuzdesi() {
        return son7ucunculukYuzdesi;
    }

    public void setSon7ucunculukYuzdesi(BigDecimal son7ucunculukYuzdesi) {
        this.son7ucunculukYuzdesi = son7ucunculukYuzdesi;
    }

    public BigDecimal getSon7bitirisOrtalamasi() {
        return son7bitirisOrtalamasi;
    }

    public void setSon7bitirisOrtalamasi(BigDecimal son7bitirisOrtalamasi) {
        this.son7bitirisOrtalamasi = son7bitirisOrtalamasi;
    }





    public String getKosuKoduAtKodu() {
        return kosuKoduAtKodu;
    }

    public void setKosuKoduAtKodu(String kosuKoduAtKodu) {
        this.kosuKoduAtKodu = kosuKoduAtKodu;
    }

    public Long getKOSUKODU() {
        return KOSUKODU;
    }

    public void setKOSUKODU(Long KOSUKODU) {
        this.KOSUKODU = KOSUKODU;
    }

    public Long getATKODU() {
        return ATKODU;
    }

    public void setATKODU(Long ATKODU) {
        this.ATKODU = ATKODU;
    }


    public Set<RegressionPrediction> getRegressionPredictions() {
        return regressionPredictions;
    }

    public void setRegressionPredictions(Set<RegressionPrediction> regressionPredictions) {
        this.regressionPredictions = regressionPredictions;
    }

    public Set<ClassificationPrediction> getClassificationPredictions() {
        return classificationPredictions;
    }

    public void setClassificationPredictions(Set<ClassificationPrediction> classificationPredictions) {
        this.classificationPredictions = classificationPredictions;
    }

    /**
     * If a prediction is  AtKosu is either has regression or classification prediction
     * @return
     */
    @Transient
    public Prediction getPrediction() {

        if (!org.hibernate.Hibernate.isInitialized(regressionPredictions)) {
            return  Prediction.create(getClassificationPredictions().iterator().next());
        } else {
            return  Prediction.create(getRegressionPredictions().iterator().next());
        }
    }
}
