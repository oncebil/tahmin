package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Util;
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
public class KazancIkili extends  KazancAbstract {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(KazancIkili.class);
    private final static int IKILI_BAHISTIP_KODU = 7;

    public KazancIkili(List<Kosu> kosular, int index) {
        super(kosular,index);
        for (Kosu kosu : kosular) {
            List<Bahis> bahisler = kosu.getBahisler().stream().
                    filter(bahis -> bahis.getBahisTipKodu() == IKILI_BAHISTIP_KODU).collect(Collectors.toList());
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
                    filter(b -> b.getBahisTipKodu() == IKILI_BAHISTIP_KODU).collect(Collectors.toList()).get(0);
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
            neKadarVerirdik = neKadarVerirdik.add(new BigDecimal(Util.getIkiliCombination(filtered.size())));
            boolean birinciBildik = false, ikincibildik = false;
            for (Prediction rp : filtered) {
                if (rp.getAtKosu().getSONUCNO() == 1) {
                    birinciBildik = true;
                }
                if (rp.getAtKosu().getSONUCNO() == 2) {
                    ikincibildik = true;
                }
            }
            if (birinciBildik && ikincibildik) {
                kacliraKazanirdik = kacliraKazanirdik.add(bahis.getTutar());
                kacKosudaBilirdik++;
                hangiKosulardaBilirdik.add(kosu.getKOSUKODU());
                kazancOranlari.add(bahis.getTutar());

            }
        }
        setCommonValues();
    }


    @Override
    public String getGameType() {
        return GameType.KazancIkili.toString();
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
     where count >=2
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

     // classification
     // ne kadar kazanirdik
     select sum(tutar) from Bahisler where kosukodu in(
     select kosukodu from (
     select c.kosukodu,count(*) count from ClassificationPrediction  a, Distribution b, AtKosu c
     where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
     and a.experiment = b.experiment
     and a.instanceid = b.instanceid
     and c.KosuKoduAtKodu = a.instanceId
     and b.distribution > (1 - 0.91600001)
     and (sonucno =1 or sonucno =2)
     and b.classificationclass = 'Y'
     group by kosukodu
     order by kosukodu desc
     ) a where count >=2
     )
     and bahistipkodu = 7


     // ne kadar verirdik
     select sum (  (count * (count-1))/2 ) from (
     select kosukodu,count from (
     select c.kosukodu,count(*) count from ClassificationPrediction  a, Distribution b, AtKosu c,Bahisler d
     where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
     and a.experiment = b.experiment
     and a.instanceid = b.instanceid
     and c.KosuKoduAtKodu = a.instanceId
     and b.distribution > (1 - 0.91600001)
     --	     and (sonucno =1 or sonucno =2)
     and b.classificationclass = 'Y'
     and d.kosukodu = c.kosukodu
     and bahistipkodu = 7
     group by c.kosukodu
     order by c.kosukodu desc
     ) a where count >=2
     ) b

     */
}
