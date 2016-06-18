package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.dao.ExperimentResultDAO;
import com.oncebil.tahmin.entity.Kazanc;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 18/06/2016.
 */
public class EvaluationComparison {
    @Inject
    ExperimentResultDAO experimentResultDAO;

    public EvaluationComparisonResult compare(List<String> experiments) {
        EvaluationComparisonResult result = new EvaluationComparisonResult();
        List<EvaluationComparisonResult.GanyanComparison> ganyanComparisons = new ArrayList<>();
        for (String experiment : experiments) {
            EvaluationComparisonResult.GanyanComparison ganyanComparison = new EvaluationComparisonResult.GanyanComparison();
            List<Kazanc> kazanclar = experimentResultDAO.findKazancGanyansOnThresholds(experiment);
            ganyanComparison.experiment = experiment;
            for (Kazanc k : kazanclar) {
                BigDecimal kazancOrani = new BigDecimal(k.getKazancOraninKacOlurdu()).setScale(2, RoundingMode.HALF_UP);
                ganyanComparison.yuzdeOynanabilirToKazancOranlari.put(k.getIndex(),kazancOrani );
                ganyanComparison.totalKazancOnAllThresholds = ganyanComparison.totalKazancOnAllThresholds.add(kazancOrani);
            }
            ganyanComparisons.add(ganyanComparison);
        }
        ganyanComparisons.sort((p1, p2) -> p2.totalKazancOnAllThresholds.compareTo(p1.totalKazancOnAllThresholds));
        result.ganyanComparisons = ganyanComparisons;
        return result;


    }
}
