package com.oncebil.tahmin;

import com.google.common.annotations.Beta;
import com.oncebil.tahmin.dao.KosuDAO;
import com.oncebil.tahmin.entity.Kosu;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by erkinkarincaoglu on 17/06/2016.
 */
public class RegressionClassificationDataTestBase {
    public static KosuDAO kosuDAO;
    public static List<Kosu> regressionKosular;
    public static List<Kosu> classificationKosular;
    public static AtomicBoolean testDataSetup = new AtomicBoolean(false);

    @BeforeClass
    public  static void  setup()  {
        if (testDataSetup.compareAndSet(false, true) ) {
            kosuDAO = WeldGlobal.get(KosuDAO.class);
            Base.insertTestData("test_son7kosu_kstar_predictions_data.xml");
            regressionKosular = kosuDAO.
                    findbyExperimentWithRegressionPredictions("test-son7kosu-kstar-experiment");
            classificationKosular = kosuDAO.
                    findbyExperimentWithClassificationPredictions("test-son7kosu-nominal-kstar-experiment");
        }
    }

}
