package com.oncebil.tahmin.otamasyon.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by erkinkarincaoglu on 18/06/2016.
 */
public class EvaluationComparisonResult {

    List<GanyanComparison> ganyanComparisons = new ArrayList<>();

    public static class GanyanComparison {
        String experiment;
        Map<Integer,BigDecimal> yuzdeOynanabilirToKazancOranlari = new HashMap<>();
        BigDecimal totalKazancOnAllThresholds = BigDecimal.ZERO;

        @Override
        public String toString() {
            return "GanyanComparison{" +
                    "experiment='" + experiment + '\'' +
                    ", yuzdeOynanabilirToKazancOranlari=" + yuzdeOynanabilirToKazancOranlari +
                    ", totalKazancOnAllThresholds=" + totalKazancOnAllThresholds +
                    '}';
        }
    }
}
