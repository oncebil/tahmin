package com.oncebil.tahmin.otamasyon.task;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by erkinkarincaoglu on 31/05/2016.
 */
public class ExperimentAnalyzeResults {

    HashMap<BigDecimal, ThresholdResult> thresholdResults = new HashMap<>();

    public ThresholdResult getThresholdResults(BigDecimal threshold) {
        return thresholdResults.get(threshold);
    }

    public ThresholdResult createAndAddThresholdResult(BigDecimal threshold,BigDecimal kazanc) {
        ThresholdResult thresholdResult = new ThresholdResult(threshold).createNewGanyanKazanc(kazanc);
        thresholdResults.put( threshold, thresholdResult);
        return thresholdResult;
    }



    public static class ThresholdResult {
        final BigDecimal threshold;
        GanyanKazanc ganyanKazanc;

        public ThresholdResult(BigDecimal threshold) {
            this.threshold = threshold;
        }

        public ThresholdResult createNewGanyanKazanc(BigDecimal kazanc) {
            ganyanKazanc = new GanyanKazanc(kazanc);
            return this;
        }

        public GanyanKazanc getGanyanKazanc() {
            return ganyanKazanc;
        }
    }

    public static class GanyanKazanc {

        final BigDecimal kazanc;

        public GanyanKazanc(BigDecimal kazanc) {
            this.kazanc = kazanc;
        }

        public BigDecimal getToplamKazanc() {
            return kazanc;
        }
    }
}
