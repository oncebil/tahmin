package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Util;
import com.oncebil.tahmin.entity.AtKosu;
import com.oncebil.tahmin.entity.Kosu;
import com.oncebil.tahmin.entity.RegressionPrediction;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

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
    public ExperimentAnalyzeResults analyze(List<Kosu> kosular) {

        ExperimentAnalyzeResults results = new ExperimentAnalyzeResults();
        results.ganyanKazanclari.add(new KazancGanyan(kosular,new BigDecimal("2.1")));
        results.ikiliKazanclari.add(new KazancIkili(kosular,new BigDecimal("3.0")));
        results.siraliIkiliKazanclari.add(new KazancSiraliIkili(kosular,new BigDecimal("3.0")));


        dynamicAnalysis( kosular,new KazancFactory("ganyan"));
        dynamicAnalysis( kosular,new KazancFactory("ikili"));
        dynamicAnalysis( kosular,new KazancFactory("siraliikili"));


        return results;
        //return analyzeOld(kosular,threshold);
    }
    private static class KazancFactory {
        String type = "";

        KazancFactory(String type) {
            this.type = type;

        }
        KazancAbstract createKazancAbstract(BigDecimal threshold,List<Kosu> kosular) {
            switch (type) {
                case "ganyan" : return new KazancGanyan(kosular,threshold);
                case "ikili" : return new KazancIkili(kosular,threshold);
                case "siraliikili" : return new KazancSiraliIkili(kosular,threshold);

            }
            throw new RuntimeException("unknown type");

        }
    }
    public void dynamicAnalysis(List<Kosu> kosular,KazancFactory kazancFactory) {
        Set<BigDecimal> predictions = new HashSet<>();
        for (Kosu k : kosular) {
            for (RegressionPrediction rp : k.getAtlarWithRegressionPredictions() ) {
                predictions.add(rp.getPredicted());
            }
        }
        System.out.println("regressionPredictions=" + predictions.size());
        List<BigDecimal> sorted = new ArrayList<>(predictions);
        Collections.sort(sorted);
        System.out.println("sorted=" + sorted.size());
        BigDecimal previousKazanc = BigDecimal.ZERO;
        int index = sorted.size() / 2;
        int max = 12;
        int count = 0;
        while (true) {
            BigDecimal  median = sorted.get( index );
            System.out.println("index=" + index + " median=" + median);
            KazancAbstract kazanc = kazancFactory.createKazancAbstract(median,kosular);

            if ( kazanc.kazancOraninKacOlurdu.compareTo( new BigDecimal(1.0)) >= 0 ){
                index = (int)(index * 0.90);
            } else if (kazanc.kazancOraninKacOlurdu.compareTo( new BigDecimal(0.90)) >= 0) {
                index =  (int)(index * 0.80);
            }else if (kazanc.kazancOraninKacOlurdu.compareTo( new BigDecimal(0.80)) >= 0) {
                index =  (int)(index * 0.70);
            }else {
                index =  (int)(index * 0.5);
            }
            previousKazanc = new BigDecimal(kazanc.kazancOraninKacOlurdu.toString());
            System.out.println("new index=" + index + " kacanc=" + kazanc);
            count++;
            if (count == max){
                break;
            }
        }
    }

//    public ExperimentAnalyzeResults analyzeOld(List<Kosu> kosular, BigDecimal threshold) {
//        List<RegressionPrediction> all = new ArrayList<>();
//        for (Kosu kosu :  kosular) {
//            for (AtKosu atlar : kosu.getAtlar()) {
////                for (RegressionPrediction regressionPrediction : atlar.getRegressionPredictions()) {
////                    all.add(regressionPrediction);
////                }
//            }
//        }
//        System.out.println("regression predictions=" + all.size());
//        RegressionPredictions predictions = new RegressionPredictions();
//        predictions.setRegressionPredictions(all);
//        System.out.println("size=" + predictions.getRegressionPredictions().size());
//        Stream<RegressionPrediction> regressionPredictions = predictions.getRegressionPredictions().stream().
//                filter( p -> p.getAtKosu().getSONUCNO() == 1 && p.getPredicted().compareTo( threshold) <=0);
//
//        doOtherStuff(predictions);
////        BigDecimal toplamKazanc =  regressionPredictions
////                .map(RegressionPrediction::getAtKosu).map(AtKosu::getGANYAN)
////                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        List<BigDecimal> ganyans = regressionPredictions
//                .map(RegressionPrediction::getAtKosu).map(AtKosu::getGANYAN).collect(Collectors.toList());
//        System.out.println("kac kosu vardi=" + predictions.getRegressionPredictions().
//                stream().filter(distinctByKey(p -> p.getAtKosu().getKOSUKODU())).count());
//        System.out.println("kac kosuda bildik=" + ganyans.size());
//        BigDecimal toplamKazanc = ganyans.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
//        toplamKazanc = toplamKazanc.setScale(2, RoundingMode.HALF_UP);
//
//        //regressionPredictions.map(RegressionPrediction::getAtKosu().getAtKosu().getGANYAN();
//        ExperimentAnalyzeResults results = new ExperimentAnalyzeResults();
//        results.createAndAddThresholdResult(threshold,toplamKazanc);
//        return results;
//    }
//
//    public void doOtherStuff(RegressionPredictions predictions) {
//        List<AtKosu> atKosuList = predictions.getRegressionPredictions().stream().
//                filter( p -> p.getPredicted().compareTo( new BigDecimal(3)) <=0 ).
//                map(RegressionPrediction::getAtKosu).
//                collect(Collectors.toList());
//        System.out.println("kac atkosu vardi 2=" + atKosuList.size());
//        System.out.println("kac kosu vardi 2=" + atKosuList.
//                stream().filter(distinctByKey(p -> p.getKOSUKODU())).count());
//        List<Long> kosuKodlari = atKosuList.stream().map(AtKosu::getKOSUKODU).collect(Collectors.toList());
//        Map<Long,Integer> kosuCounts = new HashMap<>();
//        for (Long kosuKodu : kosuKodlari) {
//            if (kosuCounts.get(kosuKodu) == null) {
//                kosuCounts.put(kosuKodu,1);
//            } else {
//                kosuCounts.put(kosuKodu, kosuCounts.get(kosuKodu) +1 );
//            }
//        }
//        //System.out.println("aaaa="+kosuCounts);
//        Set<Long> oynanabilirIkililer = new HashSet<>();
//        for (Map.Entry<Long,Integer> entry : kosuCounts.entrySet()) {
//
//            if (entry.getValue() > 1) {
//                //System.out.println("bbbb="+entry);
//                oynanabilirIkililer.add( entry.getKey());
//            }
//
//
//        }
//        System.out.println("oynanbilir ikililer 2=" + oynanabilirIkililer.size());
//
//        List<AtKosu> oynadiklarim = predictions.getRegressionPredictions().stream().
//                filter( p -> oynanabilirIkililer.contains( p.getAtKosu().getKOSUKODU())
//                && p.getPredicted().compareTo( new BigDecimal(3)) <=0).
//                map(RegressionPrediction::getAtKosu).
//                collect(Collectors.toList());
//
//        Map<Long,List<AtKosu>> oynananKosular = new HashMap<>();
//        for (AtKosu oynadigim : oynadiklarim) {
//            if (oynananKosular.get(oynadigim.getKOSUKODU()) == null) {
//                ArrayList<AtKosu> a= new ArrayList<>();
//                a.add(oynadigim);
//                oynananKosular.put( oynadigim.getKOSUKODU(), a);
//            } else {
//                oynananKosular.get(oynadigim.getKOSUKODU()).add(oynadigim);
//            }
//        }
//
//        System.out.println("2.oynanbilir ikililer 2=" + oynanabilirIkililer.size());
//
//        List<Long> bilinenKosular = new ArrayList<>();
//        for (Map.Entry<Long,List<AtKosu>> kosu : oynananKosular.entrySet()) {
//
//
//            List<AtKosu> atlar = kosu.getValue();
//            //System.out.println("kosu kodu="+ kosu.getKey()+"+atlar size=" + atlar.size());
//            if (atlar.size() < 2) {
//                throw  new RuntimeException("logic is wrong");
//            }
//            boolean birincivar = false, ikincivar = false;
//            for (AtKosu atKosu : atlar) {
//                if (atKosu.getSONUCNO() == 1) {
//                    birincivar = true;
//                }
//                if (atKosu.getSONUCNO() == 2) {
//                    ikincivar = true;
//                }
//
//            }
//            if (birincivar && ikincivar) {
//                bilinenKosular.add( kosu.getKey());
//            }
//
//        }
//
//        System.out.println("2.bilirdik ikili=" + bilinenKosular);
//        System.out.println("2.verirdik ikili=" + atKosuList.size());
//
//        //80048422
//
//
//    }
//
//    public static  <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
//        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
//        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
//    }
}
