package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.entity.Bahis;
import com.oncebil.tahmin.entity.Kosu;
import com.oncebil.tahmin.entity.Prediction;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
public class KazancSiraliIkili extends  KazancAbstract {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(KazancSiraliIkili.class);



    private final static int SIRALI_IKILI_BAHISTIP_KODU = 8;

    public KazancSiraliIkili(List<Kosu> kosular) {
        super(kosular);
        for (Kosu kosu : kosular) {
            List<Bahis> bahisler = kosu.getBahisler().stream().
                    filter(bahis -> bahis.getBahisTipKodu() == SIRALI_IKILI_BAHISTIP_KODU).collect(Collectors.toList());
            if (bahisler.isEmpty()) {
                continue;
            }
            oynanabilirKosular.add(kosu);
        }
    }

    @Override
    public List<BigDecimal> getOynanabilirKosulardakiMinumumPrediction() {
        List<BigDecimal> minumumPredictions = oynanabilirKosular.stream().
                map(k -> k.getSecondMinumumPredicted()).collect(Collectors.toList());
        Collections.sort(minumumPredictions, Collections.reverseOrder());
        return minumumPredictions;
    }

    @Override
    public void analyze(BigDecimal threshold) {
        this.threshold = threshold;
        kacKosuVardi = kosular.size();
        if (kacKosuVardi == 0) {
            logger.warn("ikili kazanci kosu count is 0");
            return;
        }
        for (Kosu kosu : oynanabilirKosular) {
            Bahis bahis = kosu.getBahisler().stream().
                    filter(b -> b.getBahisTipKodu() == SIRALI_IKILI_BAHISTIP_KODU).collect(Collectors.toList()).get(0);
            kacKosudaOynanabilirdi++;
            List<Prediction> predictions =
                    kosu.getAtlarWithPredictions();
            List<Prediction> filtered = predictions.
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
                kacliraKazanirdik = kacliraKazanirdik.add(bahis.getTutar());
                kacKosudaBilirdik++;
                hangiKosulardaBilirdik.add(kosu.getKOSUKODU());
                kazancOranlari.add(bahis.getTutar());
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

