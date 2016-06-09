package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.entity.Kosu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
public abstract  class KazancAbstract {
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

    public abstract  List<BigDecimal> getOynanabilirKosulardakiMinumumPrediction();

    public abstract  void analyze(BigDecimal threshold);

    public KazancAbstract (List<Kosu> kosular) {
        this.kosular = kosular;
        kacKosuVardi = kosular.size();
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
}
