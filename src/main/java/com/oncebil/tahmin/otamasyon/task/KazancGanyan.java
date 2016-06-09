package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Util;
import com.oncebil.tahmin.entity.Kosu;
import com.oncebil.tahmin.entity.RegressionPrediction;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
public class KazancGanyan extends KazancAbstract {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(KazancGanyan.class);

    public KazancGanyan(List<Kosu> kosular) {
        super(kosular);
        oynanabilirKosular.addAll(kosular);
    }

    @Override
    public List<BigDecimal> getOynanabilirKosulardakiMinumumPrediction() {
        List<BigDecimal> minumumPredictions = oynanabilirKosular.stream().map( k -> k.getMinumumRegressionPredicted() ).collect(Collectors.toList());
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
            List<RegressionPrediction> regressionPredictions = kosu.getAtlarWithRegressionPredictions();
            long kacAtaOynardik = regressionPredictions.
                    stream().filter(regressionPrediction -> regressionPrediction.getPredicted().compareTo(threshold) <= 0).
                    count();
            if (kacAtaOynardik == 0) {
                continue;
            }
            kacKosudaOynardik++;
            neKadarVerirdik = neKadarVerirdik.add(new BigDecimal(kacAtaOynardik));
            List<RegressionPrediction> bildik = regressionPredictions.
                    stream().filter(regressionPrediction -> regressionPrediction.getPredicted().compareTo(threshold) <= 0
                    && regressionPrediction.getAtKosu().getSONUCNO() == 1).collect(Collectors.toList());

            if (!bildik.isEmpty()) {
                RegressionPrediction regressionPrediction = bildik.get(0);
                kazancOranlari.add(regressionPrediction.getAtKosu().getGANYAN().setScale(2, RoundingMode.HALF_UP));
                kacKosudaBilirdik++;
                hangiKosulardaBilirdik.add(kosu.getKOSUKODU());
                kacliraKazanirdik = kacliraKazanirdik.add(regressionPrediction.getAtKosu().getGANYAN());

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


     */
}
