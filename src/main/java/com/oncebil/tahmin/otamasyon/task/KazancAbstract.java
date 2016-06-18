package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.entity.BilinenKosu;
import com.oncebil.tahmin.entity.Kazanc;
import com.oncebil.tahmin.entity.Kosu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
public abstract  class KazancAbstract {
    enum GameType {
        KazancGanyan,
        KazancIkili,
        KazancSiraliIkili
    }

    int kacKosuVardi = 0;
    BigDecimal threshold;
    int kacKosudaOynardik = 0;
    int kacKosudaBilirdik = 0;
    BigDecimal neKadarVerirdik = BigDecimal.ZERO;
    BigDecimal kazancOraninKacOlurdu = BigDecimal.ZERO;
    BigDecimal yuzdeKacindaOynardik = BigDecimal.ZERO;
    BigDecimal yuzdeKacindaBilirdik = BigDecimal.ZERO;
    BigDecimal kacliraKazanirdik = BigDecimal.ZERO;
    List<Long> hangiKosulardaBilirdik = new ArrayList<>();
    int kacKosudaOynanabilirdi = 0;
    List<BigDecimal> kazancOranlari = new ArrayList<>(); //  ganyan , ikili vs.. degeri
    List<Kosu> kosular;
    List<Kosu> oynanabilirKosular = new ArrayList<>();
    int index;

    public abstract  List<BigDecimal> getOynanabilirKosulardakiMinumumPrediction();

    public abstract  void analyze(BigDecimal threshold);

    public KazancAbstract (List<Kosu> kosular, int index) {
        this.kosular = kosular;
        kacKosuVardi = kosular.size();
        this.index = index;
    }
    void setCommonValues() {
        neKadarVerirdik = neKadarVerirdik.setScale(2, RoundingMode.HALF_UP);
        kacliraKazanirdik = kacliraKazanirdik.setScale(2, RoundingMode.HALF_UP);
        if (kacKosudaOynanabilirdi > 0) {
            yuzdeKacindaOynardik = new BigDecimal((100 * kacKosudaOynardik) / kacKosudaOynanabilirdi).setScale(2);

        }
        if (kacKosudaOynardik>0) {
            yuzdeKacindaBilirdik = new BigDecimal((100 * kacKosudaBilirdik) / kacKosudaOynardik).setScale(2);
        }
        if (kacliraKazanirdik.compareTo(BigDecimal.ZERO) > 0) {
            kazancOraninKacOlurdu = kacliraKazanirdik.divide(neKadarVerirdik,
                    RoundingMode.HALF_UP).setScale(2);
        }


    }

    public abstract String getGameType();
    public  String getType() {
        return "oynamayuzdesi-threshold";
    }


    public Kazanc toKazancEntity() {
        Kazanc kazanc = new Kazanc();
        kazanc.setGameType(getGameType());
        kazanc.setThreshold(threshold.doubleValue());
        kazanc.setIndex(index);
        for (Long kosuKodu :  hangiKosulardaBilirdik) {
            BilinenKosu bilinenKosu = new BilinenKosu();
            bilinenKosu.setKOSUKODU( kosuKodu);
            kazanc.getBilinenKosular().add(bilinenKosu);
        }
        kazanc.setKacKosudaBilirdik( kacKosudaBilirdik);
        kazanc.setKacKosudaOynanabilirdi( kacKosudaOynanabilirdi);
        kazanc.setKacKosudaOynardik( kacKosudaOynardik);
        kazanc.setKacKosuVardi( kacKosuVardi);
        kazanc.setKazancOraninKacOlurdu( kazancOraninKacOlurdu.doubleValue());
        kazanc.setKacliraKazanirdik( kacliraKazanirdik.doubleValue());
        kazanc.setKazancOraninKacOlurdu( kazancOraninKacOlurdu.doubleValue());
        kazanc.setNeKadarVerirdik(neKadarVerirdik.doubleValue());
        kazanc.setType(getType());
        kazanc.setYuzdeKacindaBilirdik( yuzdeKacindaBilirdik.doubleValue() );
        kazanc.setYuzdeKacindaOynardik( yuzdeKacindaOynardik.doubleValue() );
        return kazanc;


    }
}
