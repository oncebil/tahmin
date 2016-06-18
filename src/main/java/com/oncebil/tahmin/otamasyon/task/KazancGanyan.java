package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.entity.Kosu;
import com.oncebil.tahmin.entity.Prediction;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
public class KazancGanyan extends KazancAbstract {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(KazancGanyan.class);

    public KazancGanyan(List<Kosu> kosular, int index) {
        super(kosular,index);
        oynanabilirKosular.addAll(kosular);
    }



    @Override
    public List<BigDecimal> getOynanabilirKosulardakiMinumumPrediction() {
        List<BigDecimal> minumumPredictions = oynanabilirKosular.stream().
                map( k -> k.getMinumumPredicted() ).collect(Collectors.toList());
        Collections.sort(minumumPredictions, Collections.reverseOrder());
        return  minumumPredictions;

    }

    @Override
    public void analyze(BigDecimal threshold) {
        this.threshold = threshold;
        if (kacKosuVardi == 0) {
            logger.warn("ganyan kazanci kosu count is 0");
            return;
        }
        kacKosudaOynanabilirdi = oynanabilirKosular.size();
        for (Kosu kosu : oynanabilirKosular) {
            List<Prediction> predictions = kosu.getAtlarWithPredictions();
            long kacAtaOynardik = predictions.
                    stream().filter(p ->
                        p.getPredicted().compareTo(threshold) <= 0).
                    count();
            if (kacAtaOynardik == 0) {
                continue;
            }
            kacKosudaOynardik++;
            neKadarVerirdik = neKadarVerirdik.add(new BigDecimal(kacAtaOynardik));
            List<Prediction> bildik = predictions.
                    stream().filter(
                    p -> p.getPredicted().compareTo(threshold) <= 0
                    && p.getAtKosu().getSONUCNO() == 1).collect(Collectors.toList());

            if (!bildik.isEmpty()) {
                Prediction prediction = bildik.get(0);
                kazancOranlari.add(prediction.getAtKosu().getGANYAN().setScale(2, RoundingMode.HALF_UP));
                kacKosudaBilirdik++;
                hangiKosulardaBilirdik.add(kosu.getKOSUKODU());
                kacliraKazanirdik = kacliraKazanirdik.add(prediction.getAtKosu().getGANYAN());

            }
        }
        setCommonValues();


    }


    @Override
    public String toString() {
        return "KazancGanyan     {" +
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

    @Override
    public String getGameType() {
        return GameType.KazancGanyan.toString();
    }



    /*** test SQLS
     *
     // ganyan kazanc
     select sum(genyen) from (
     select  ganyan genyen , a.*,b.* from RegressionPrediction a, AtKosu b
     where experiment = 'test-son7kosu-kstar-experiment'
     and a.instanceId = b.KosuKoduAtKodu
     and predicted < 2.1
     and actual =1
     order by b.KosuKodu desc, predicted asc
     ) a

     23.10
    // ne kadar verirdik
     select count(*) from(
     select  ganyan genyen , a.*,b.* from RegressionPrediction a, AtKosu b
     where experiment = 'test-son7kosu-kstar-experiment'
     and a.instanceId = b.KosuKoduAtKodu
     and predicted < 2.1
     order by b.KosuKodu desc, predicted asc
     ) a

     21


     classification
     // ganyan kazanc
     select sum(ganyan) from ClassificationPrediction  a, Distribution b, AtKosu c
     where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
     and a.experiment = b.experiment
     and a.instanceid = b.instanceid
     and c.KosuKoduAtKodu = a.instanceId
     and b.classificationclass = 'Y'
     and b.distribution > (1 - 0.84600002)
     and actual = 'Y'

     // oynanabilir kosular
     select count (*) from (
     select distinct KosuKodu from
     (
     select * from ClassificationPrediction  a, Distribution b, AtKosu c
     where a.experiment = 'test-son7kosu-nominal-kstar-experiment'
     and a.experiment = b.experiment
     and a.instanceid = b.instanceid
     and c.KosuKoduAtKodu = a.instanceId
     and b.classificationclass = 'Y'
     and b.distribution > (1 - 0.84600002)
     order by c.KosuKodu, b.distribution desc
     ) b
     ) c

     */
}
