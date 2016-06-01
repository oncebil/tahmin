package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.entity.AtKosu;
import com.oncebil.tahmin.entity.RegressionPrediction;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by erkinkarincaoglu on 31/05/2016.
 */
public class ExperimentAnalyze {
    public ExperimentAnalyzeResults analyze(RegressionPredictions predictions, BigDecimal threshold) {

        Stream<RegressionPrediction> regressionPredictions = predictions.getRegressionPredictions().stream().
                filter( p -> p.getAtKosu().getSONUCNO() == 1 && p.getPredicted().compareTo( threshold) <=0);

        doOtherStuff(predictions);
//        BigDecimal toplamKazanc =  regressionPredictions
//                .map(RegressionPrediction::getAtKosu).map(AtKosu::getGANYAN)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<BigDecimal> ganyans = regressionPredictions
                .map(RegressionPrediction::getAtKosu).map(AtKosu::getGANYAN).collect(Collectors.toList());
        System.out.println("kac kosu vardi=" + predictions.getRegressionPredictions().
                stream().filter(distinctByKey(p -> p.getAtKosu().getKOSUKODU())).count());
        System.out.println("kac kosuda bildik=" + ganyans.size());
        BigDecimal toplamKazanc = ganyans.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        toplamKazanc = toplamKazanc.setScale(2, RoundingMode.HALF_UP);

        //regressionPredictions.map(RegressionPrediction::getAtKosu().getAtKosu().getGANYAN();
        ExperimentAnalyzeResults results = new ExperimentAnalyzeResults();
        results.createAndAddThresholdResult(threshold,toplamKazanc);
        return results;
    }

    public void doOtherStuff(RegressionPredictions predictions) {
        List<AtKosu> atKosuList = predictions.getRegressionPredictions().stream().
                filter( p -> p.getPredicted().compareTo( new BigDecimal(3)) <=0 ).
                map(RegressionPrediction::getAtKosu).
                collect(Collectors.toList());
        System.out.println("kac atkosu vardi 2=" + atKosuList.size());
        System.out.println("kac kosu vardi 2=" + atKosuList.
                stream().filter(distinctByKey(p -> p.getKOSUKODU())).count());
        List<Long> kosuKodlari = atKosuList.stream().map(AtKosu::getKOSUKODU).collect(Collectors.toList());
        Map<Long,Integer> kosuCounts = new HashMap<>();
        for (Long kosuKodu : kosuKodlari) {
            if (kosuCounts.get(kosuKodu) == null) {
                kosuCounts.put(kosuKodu,1);
            } else {
                kosuCounts.put(kosuKodu, kosuCounts.get(kosuKodu) +1 );
            }
        }
        //System.out.println("aaaa="+kosuCounts);
        Set<Long> oynanabilirIkililer = new HashSet<>();
        for (Map.Entry<Long,Integer> entry : kosuCounts.entrySet()) {

            if (entry.getValue() > 1) {
                //System.out.println("bbbb="+entry);
                oynanabilirIkililer.add( entry.getKey());
            }


        }
        System.out.println("oynanbilir ikililer 2=" + oynanabilirIkililer.size());

        List<AtKosu> oynadiklarim = predictions.getRegressionPredictions().stream().
                filter( p -> oynanabilirIkililer.contains( p.getAtKosu().getKOSUKODU())
                && p.getPredicted().compareTo( new BigDecimal(3)) <=0).
                map(RegressionPrediction::getAtKosu).
                collect(Collectors.toList());

        Map<Long,List<AtKosu>> oynananKosular = new HashMap<>();
        for (AtKosu oynadigim : oynadiklarim) {
            if (oynananKosular.get(oynadigim.getKOSUKODU()) == null) {
                ArrayList<AtKosu> a= new ArrayList<>();
                a.add(oynadigim);
                oynananKosular.put( oynadigim.getKOSUKODU(), a);
            } else {
                oynananKosular.get(oynadigim.getKOSUKODU()).add(oynadigim);
            }
        }

        System.out.println("2.oynanbilir ikililer 2=" + oynanabilirIkililer.size());

        List<Long> bilinenKosular = new ArrayList<>();
        for (Map.Entry<Long,List<AtKosu>> kosu : oynananKosular.entrySet()) {


            List<AtKosu> atlar = kosu.getValue();
            //System.out.println("kosu kodu="+ kosu.getKey()+"+atlar size=" + atlar.size());
            if (atlar.size() < 2) {
                throw  new RuntimeException("logic is wrong");
            }
            boolean birincivar = false, ikincivar = false;
            for (AtKosu atKosu : atlar) {
                if (atKosu.getSONUCNO() == 1) {
                    birincivar = true;
                }
                if (atKosu.getSONUCNO() == 2) {
                    ikincivar = true;
                }

            }
            if (birincivar && ikincivar) {
                bilinenKosular.add( kosu.getKey());
            }

        }

        System.out.println("2.bilirdik ikili=" + bilinenKosular);
        System.out.println("2.verirdik ikili=" + atKosuList.size());

        //80048422


    }

    public static  <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
