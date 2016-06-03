package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Util;
import com.oncebil.tahmin.entity.Bahis;
import com.oncebil.tahmin.entity.Kosu;
import com.oncebil.tahmin.entity.RegressionPrediction;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
public class KazancSiraliIkili extends  KazancAbstract {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(KazancSiraliIkili.class);



    private final static int SIRALI_IKILI_BAHISTIP_KODU = 8;

    public KazancSiraliIkili(List<Kosu> kosular, BigDecimal threshold) {
        super(kosular,threshold);
        this.threshold = threshold;
        kacKosuVardi = kosular.size();
        if (kacKosuVardi == 0) {
            logger.warn("ikili kazanci kosu count is 0");
            return;
        }
        for (Kosu kosu : kosular) {
            List<Bahis> bahisler = kosu.getBahisler().stream().
                    filter(bahis -> bahis.getBahisTipKodu() == SIRALI_IKILI_BAHISTIP_KODU).collect(Collectors.toList());
            if (bahisler.isEmpty()) {
                continue;
            }
            kacKosudaOynanabilirdi++;
            List<RegressionPrediction> regressionPredictions =
                    kosu.getAtlarWithRegressionPredictions();
            List<RegressionPrediction> filtered = regressionPredictions.
                    stream().filter(rp -> rp.getPredicted().compareTo(threshold) <= 0).
                    collect(Collectors.toList());
            if (filtered.size() < 2) {
                continue;
            }
            kacKosudaOynardik++;
            neKadarVerirdik = neKadarVerirdik.add( new BigDecimal(1));
            Collections.sort(filtered, (p1, p2) -> p1.getPredicted().compareTo(p2.getPredicted()));
            boolean bildik = false;
            if (filtered.get(0).getAtKosu().getSONUCNO() == 1 &&
                    filtered.get(1).getAtKosu().getSONUCNO() == 2 ) {
                bildik=true;
            }
            if (bildik) {
                kacliraKazanirdik = kacliraKazanirdik.add(bahisler.get(0).getTutar());
                kacKosudaBilirdik++;
                hangiKosulardaBilirdik.add(kosu.getKOSUKODU());
                kazancOranlari.add(bahisler.get(0).getTutar());
            }
        }
        setCommonValues();
    }

    @Override
    public String toString() {
        return "KazancSiraliIkili{" +
                "kacKosuVardi=" + kacKosuVardi +
                ", kacKosudaOynanabilirdi=" + kacKosudaOynanabilirdi +
                ", kacKosudaOynardik=" + kacKosudaOynardik +
                ", kazancOraninKacOlurdu=" + kazancOraninKacOlurdu +
                ", yuzdeKacindaOynardik=" + yuzdeKacindaOynardik +
                ", kacKosudaBilirdik=" + kacKosudaBilirdik +
                ", yuzdeKacindaBilirdik=" + yuzdeKacindaBilirdik +
                ", kacliraKazanirdik=" + kacliraKazanirdik +
                ", neKadarVerirdik=" + neKadarVerirdik +
                ", threshold=" + threshold +
                ", hangiKosulardaBilirdik=" + hangiKosulardaBilirdik +
                ", kazancOranlari=" + kazancOranlari +
                '}';
    }
}

