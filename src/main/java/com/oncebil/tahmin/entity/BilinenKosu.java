package com.oncebil.tahmin.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by erkinkarincaoglu on 15/06/2016.
 */
@Entity
@Table(name = "BilinenKosu")
public class BilinenKosu  implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private
    Long id;

    @ManyToOne
    private Kazanc kazanc;

    @Column(name= "KOSUKODU")
    private Long KOSUKODU;

    public Long getKOSUKODU() {
        return KOSUKODU;
    }

    public void setKOSUKODU(Long KOSUKODU) {
        this.KOSUKODU = KOSUKODU;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kazanc getKazanc() {
        return kazanc;
    }

    public void setKazanc(Kazanc kazanc) {
        this.kazanc = kazanc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BilinenKosu that = (BilinenKosu) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
