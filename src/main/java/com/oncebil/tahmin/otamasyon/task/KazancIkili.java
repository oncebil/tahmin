package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Util;
import com.oncebil.tahmin.entity.Bahis;
import com.oncebil.tahmin.entity.Kosu;
import com.oncebil.tahmin.entity.RegressionPrediction;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
public class KazancIkili extends  KazancAbstract {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(KazancIkili.class);



    private final static int IKILI_BAHISTIP_KODU = 7;


    public KazancIkili(List<Kosu> kosular, BigDecimal threshold) {
        super(kosular,threshold);
        this.threshold = threshold;
        kacKosuVardi = kosular.size();
        if (kacKosuVardi == 0) {
            logger.warn("ikili kazanci kosu count is 0");
            return;
        }
        for (Kosu kosu : kosular) {
            List<Bahis> bahisler = kosu.getBahisler().stream().
                    filter(bahis -> bahis.getBahisTipKodu() == IKILI_BAHISTIP_KODU).collect(Collectors.toList());
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
            neKadarVerirdik = neKadarVerirdik.add(new BigDecimal(Util.getIkiliCombination(filtered.size())));
            boolean birinciBildik = false, ikincibildik = false;
            for (RegressionPrediction rp : filtered) {
                if (rp.getAtKosu().getSONUCNO() == 1) {
                    birinciBildik = true;
                }
                if (rp.getAtKosu().getSONUCNO() == 2) {
                    ikincibildik = true;
                }
            }
            if (birinciBildik && ikincibildik) {
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
        return "KazancIkili      {" +
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


    /** SQLs to test

     // ikili kazanc
     select sum(tutar) from Bahisler where kosukodu in(
     select kosukodu from (
     select kosukodu,count(*) count  from AtKosu a, RegressionPrediction b
     where kosukoduatkodu = b.instanceid
     and experiment = 'test-son7kosu-kstar-experiment'
     and predicted < 3.0
     and (sonucno =1 or sonucno =2)
     group by kosukodu
     order by kosukodu desc
     ) a
     where count =2
     )
     and bahistipkodu = 7

     // ne kadar verirdik
     select sum (  (count * (count-1))/2 ) from (
     select * from (
     select a.kosukodu,count(*)  as count from AtKosu a, RegressionPrediction b, Bahisler c
     where a.kosukoduatkodu = b.instanceid
     and experiment = 'test-son7kosu-kstar-experiment'
     and predicted < 3.0
     and c.kosukodu = a.kosukodu
     and c.bahistipkodu = 7
     group by a.kosukodu
     order by a.kosukodu desc
     ) a
     where count >= 2
     order by kosukodu
     ) b

     */
}
