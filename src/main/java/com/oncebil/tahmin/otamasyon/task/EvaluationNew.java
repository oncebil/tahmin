package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Util;
import weka.classifiers.Classifier;
import weka.classifiers.CostMatrix;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.Range;

import java.util.*;

/**
 * Evaluation which can create train test instances based on KosuId_AtId
 *
 * Created by erkinkarincaoglu on 18/06/2016.
 */
public class EvaluationNew extends Evaluation {
    protected List<Integer> kosuIds = new ArrayList<>();
    protected int eachFoldCount;

    public EvaluationNew(Instances data) throws Exception {
        super(data);
    }

    public EvaluationNew(Instances data, CostMatrix costMatrix) throws Exception {
        super(data, costMatrix);
    }


    @Override
    public void crossValidateModel(Classifier classifier, Instances data, int numFolds, Random random, Object... forPredictionsPrinting) throws Exception {
        // Make a copy of the data we can reorder
        data = new Instances(data);
        prepareFolds(data, numFolds);

        // We assume that the first element is a StringBuffer, the second a Range (attributes
        // to output) and the third a Boolean (whether or not to output a distribution instead
        // of just a classification)
        if (forPredictionsPrinting.length > 0) {
            // print the header first
            StringBuffer buff = (StringBuffer) forPredictionsPrinting[0];
            Range attsToOutput = (Range) forPredictionsPrinting[1];
            boolean printDist = ((Boolean) forPredictionsPrinting[2]).booleanValue();
            printClassificationsHeader(data, attsToOutput, printDist, buff);
        }


        // Do the folds
        for (int i = 0; i < numFolds; i++) {
            TrainTestInstances trainTestInstances = getTrainTestInstances(data, numFolds, i);
            Instances train = trainTestInstances.train;
            Instances test = trainTestInstances.test;
            setPriors(train);
            Classifier copiedClassifier = Classifier.makeCopy(classifier);
            copiedClassifier.buildClassifier(train);
            evaluateModel(copiedClassifier, test, forPredictionsPrinting);
        }
        m_NumFolds = numFolds;
    }

    void prepareFolds(Instances data, int numFolds) {
        TreeSet<Integer> uniquekosuIds = new TreeSet<Integer>();
        for (int i = 0; i < data.numInstances(); i++) {
            String kosuId = Util.getKosuIdFromKosuId_AtId( data.instance(i).stringValue(data.attribute(Util.KOSUID_ATID_ATTRIBUTE)) );
            uniquekosuIds.add(Integer.parseInt(  kosuId  ));
        }
        eachFoldCount = uniquekosuIds.size() / numFolds;
        kosuIds = new ArrayList<Integer>(uniquekosuIds);
    }

    static class TrainTestInstances {
        Instances train;
        Instances test;
    }

    TrainTestInstances getTrainTestInstances(Instances data, int numFolds, int index) {
        int size = kosuIds.size();
        int fromIndex = index * eachFoldCount;
        int endIndex = fromIndex + eachFoldCount;
        if (index + 1 == numFolds) {
            endIndex = size;
        }
        List<Integer> testIds = new ArrayList<Integer>();
        List<Integer> trainIds = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            if (i >= fromIndex && i < endIndex) {
                // this is test
                testIds.add(kosuIds.get(i));
            } else {
                trainIds.add(kosuIds.get(i));
            }
        }
        Instances train = new Instances(data, 0, 1);
        Instances test = new Instances(data, 0, 1);
        train.delete();
        test.delete();
        for (int i = 0; i < data.numInstances(); i++) {
            String kosuIdAtId = data.instance(i).stringValue(data.attribute(Util.KOSUID_ATID_ATTRIBUTE));
            int kosuId = Integer.parseInt(Util.getKosuIdFromKosuId_AtId(kosuIdAtId));
            if (testIds.contains(kosuId)) {
                // add to test
                test.add(data.instance(i));
            } else {
                train.add(data.instance(i));
            }
        }
        TrainTestInstances retval = new TrainTestInstances();
        retval.test = test;
        retval.train = train;
        return retval;
    }
}