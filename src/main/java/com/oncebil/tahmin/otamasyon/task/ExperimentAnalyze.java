package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.dao.ExperimentResultDAO;
import com.oncebil.tahmin.dao.KosuDAO;
import com.oncebil.tahmin.entity.ExperimentResult;
import com.oncebil.tahmin.entity.Kosu;
import weka.classifiers.Evaluation;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by erkinkarincaoglu on 31/05/2016.
 */
public class ExperimentAnalyze {
    @Inject
    KosuDAO kosuDAO;
    @Inject
    ExperimentResultDAO  experimentResultDAO;

    public ExperimentResult analyze(String experiment, boolean classification, Evaluation evaluation) {
        List<Kosu> kosular = (classification)  ?
                kosuDAO.findbyExperimentWithClassificationPredictions( experiment ) :
                kosuDAO.findbyExperimentWithRegressionPredictions( experiment );
        return analyze(kosular,experiment,classification,evaluation);
    }

    public ExperimentResult analyze(List<Kosu> kosular, String experiment, boolean classification, Evaluation evaluation) {
        ExperimentResult results = new ExperimentResult();
        results.setExperiment(experiment);
        dynamicAnalysis2( results, kosular, new KazancFactory( KazancAbstract.GameType.KazancGanyan ) );
        dynamicAnalysis2( results, kosular, new KazancFactory( KazancAbstract.GameType.KazancIkili ));
        dynamicAnalysis2( results, kosular, new KazancFactory(KazancAbstract.GameType.KazancSiraliIkili));
        if (evaluation != null) {
            results.populateEvaluationResults(evaluation, classification);
        }
        experimentResultDAO.removeAndInsert(results);
        return results;
    }


    private static class KazancFactory {
        KazancAbstract.GameType type;


        KazancFactory(KazancAbstract.GameType type) {
            this.type = type;

        }
        KazancAbstract createKazancAbstract(List<Kosu> kosular, int index) {
            switch (type) {
                case KazancGanyan :
                    KazancGanyan kazancGanyan = new KazancGanyan(kosular, index);
                    return kazancGanyan;
                case KazancIkili:
                    KazancIkili kazancIkili = new KazancIkili(kosular, index);
                    return kazancIkili;
                case KazancSiraliIkili:
                    KazancSiraliIkili kazancSiraliIkili =  new KazancSiraliIkili(kosular, index);
                    return kazancSiraliIkili;

            }
            throw new RuntimeException("unknown type");

        }
    }
    public void dynamicAnalysis2( ExperimentResult experimentResult, List<Kosu> kosular,KazancFactory kazancFactory) {
        KazancAbstract kazancAbstract = kazancFactory.createKazancAbstract(kosular, 0);
        List<BigDecimal> minumumPredictions = kazancAbstract.getOynanabilirKosulardakiMinumumPrediction();
        Collections.sort(minumumPredictions, Collections.reverseOrder());
        System.out.println(minumumPredictions.size());
        for (int i = 0 ; i<minumumPredictions.size()  ; i = i + (minumumPredictions.size() / 10)) {
            // create new one for each threshold. first one is already created
            if (i > 0) {
                kazancAbstract = kazancFactory.createKazancAbstract(kosular, i);
            }
            BigDecimal threshold = minumumPredictions.get( i   );
            kazancAbstract.analyze(threshold);
            System.out.println( "i=" + i + " threshold=" + threshold + " kacanc=" + kazancAbstract);
            experimentResult.getKazanclar().add( kazancAbstract.toKazancEntity());

        }

    }


}
