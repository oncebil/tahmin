package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.entity.Kosu;
import com.oncebil.tahmin.entity.Prediction;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by erkinkarincaoglu on 31/05/2016.
 */
public class ExperimentAnalyze {
    public ExperimentAnalyzeResults analyze(List<Kosu> kosular) {
        ExperimentAnalyzeResults results = new ExperimentAnalyzeResults();
        dynamicAnalysis( kosular,new KazancFactory(results,"ganyan"));
        dynamicAnalysis( kosular,new KazancFactory(results,"ikili"));
        dynamicAnalysis( kosular,new KazancFactory(results,"siraliikili"));

        return results;
    }
    public ExperimentAnalyzeResults analyze2(List<Kosu> kosular) {
        ExperimentAnalyzeResults results = new ExperimentAnalyzeResults();
        dynamicAnalysis2(kosular, new KazancFactory(results,"ganyan"));
        dynamicAnalysis2( kosular,new KazancFactory(results,"ikili"));
        dynamicAnalysis2( kosular,new KazancFactory(results,"siraliikili"));

        return results;
    }


    private static class KazancFactory {
        String type;
        ExperimentAnalyzeResults results;

        KazancFactory(ExperimentAnalyzeResults results,String type) {
            this.type = type;
            this.results =results;

        }
        KazancAbstract createKazancAbstract(List<Kosu> kosular) {
            switch (type) {
                case "ganyan" :
                    KazancGanyan kazancGanyan = new KazancGanyan(kosular);
                    results.ganyanKazanclari.add(kazancGanyan);
                    return kazancGanyan;
                case "ikili" :
                    KazancIkili kazancIkili = new KazancIkili(kosular);
                    results.ikiliKazanclari.add(kazancIkili);
                    return kazancIkili;

                case "siraliikili" :
                    KazancSiraliIkili kazancSiraliIkili =  new KazancSiraliIkili(kosular);
                    results.siraliIkiliKazanclari.add(kazancSiraliIkili);
                    return kazancSiraliIkili;

            }
            throw new RuntimeException("unknown type");

        }
    }
    public void dynamicAnalysis2( List<Kosu> kosular,KazancFactory kazancFactory) {
        KazancAbstract kazancAbstract = kazancFactory.createKazancAbstract(kosular);
        List<BigDecimal> minumumPredictions = kazancAbstract.getOynanabilirKosulardakiMinumumPrediction();
        Collections.sort(minumumPredictions, Collections.reverseOrder());
        System.out.println(minumumPredictions.size());
        for (int i = 0 ; i<minumumPredictions.size()  ; i = i + (minumumPredictions.size() / 10)) {
            // create new one for each threshold. first one is already created
            if (i > 0) {
                kazancAbstract = kazancFactory.createKazancAbstract(kosular);
            }
            BigDecimal threshold = minumumPredictions.get( i   );
            KazancAbstract kazanc = kazancFactory.createKazancAbstract(kosular);
            kazanc.analyze(threshold);
            System.out.println( "i=" + i + " threshold=" + threshold + " kacanc=" + kazanc);
        }

    }
    public void dynamicAnalysis( List<Kosu> kosular,KazancFactory kazancFactory) {
        Set<BigDecimal> predictions = new HashSet<>();
        for (Kosu k : kosular) {
            for (Prediction rp : k.getAtlarWithPredictions() ) {
                predictions.add(rp.getPredicted());
            }
        }
        System.out.println("predictions=" + predictions.size());
        List<BigDecimal> sorted = new ArrayList<>(predictions);
        Collections.sort(sorted);
        System.out.println("sorted=" + sorted.size());
        BigDecimal previousKazanc = BigDecimal.ZERO;
        int index = sorted.size() / 2;
        int max = 12;
        int count = 0;
        // select median as threshold and play all the ats whose value is higher than this value
        BigDecimal  threshold = sorted.get( index );
        while (true) {
           
            System.out.println("index=" + index + " median=" + threshold);
            KazancAbstract kazanc = kazancFactory.createKazancAbstract(kosular);
            kazanc.analyze(threshold);
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
