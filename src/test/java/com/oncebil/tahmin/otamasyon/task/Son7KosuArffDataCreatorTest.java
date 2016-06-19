package com.oncebil.tahmin.otamasyon.task;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.Util;
import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.dao.AtKosuDAO;
import com.oncebil.tahmin.entity.AtKosu;
import de.laliluna.transactions.Transactional;
import org.junit.Ignore;
import org.junit.Test;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.File;
import java.util.List;

/**
 * Created by erkinkarincaoglu on 19/06/2016.
 */
public class Son7KosuArffDataCreatorTest {



    @Test
    @Ignore("Transfered from old project useful to see the sql that we used to create test data")
    public void testCreateSon7KosuData() {
        AtKosuDAO atKosuDAO = WeldGlobal.get(AtKosuDAO.class);
        String sql = "   select * from AtKosu where KosuKodu in (\n" +
                "    select B.kosukodu from\n" +
                "    (select kosukodu,count(*) as atcount from AtKosu group by kosukodu) A,\n" +
                "    (select kosukodu,count(*) as atcount from AtKosu where son7derecefarkiortalamasi is not null group by kosukodu ) B\n" +
                "    where A.kosukodu = B.kosukodu\n" +
                "    AND A.atcount = B.atcount " +
                "    AND A.atcount > 6  AND A.atcount < 10)\n" +
                "    AND KOSUTARIHI<='2011-08-01'\n" +
                "    AND KOSUTARIHI>='2011-05-01'\n" +
                "    AND BirinciIleDereceFarki is not null" +
                "    order by KosuKodu asc,SonucNo asc ";
        List<AtKosu> atKosuList = atKosuDAO.getAtKosularBySql(sql);
        System.out.println("size=" + atKosuList.size());

        FastVector atts = new FastVector();
        atts.addElement(new Attribute("KosuId_AtId", (FastVector) null));
        atts.addElement(new Attribute("Son73egirisyuzdesi") );
        atts.addElement(new Attribute("Son7BitirisOrtalamasi") );
        atts.addElement(new Attribute("Son7DereceFarkiOrtalamasi") );
        atts.addElement(new Attribute("Son3DereceFarkiOrtalamasi") );
        FastVector attVals = new FastVector();
        attVals.addElement("Y");
        attVals.addElement("N");
        atts.addElement(new Attribute("BirinciMi", attVals ));
        Instances data = new Instances("MyRelation", atts, 0);
        double[] vals;
        for (AtKosu atKosu : atKosuList) {
            vals = new double[data.numAttributes()];
            vals[0] = data.attribute(0).addStringValue(atKosu.getKosuKoduAtKodu());
            vals[1] = atKosu.getSon7birincilikYuzdesi().doubleValue() + atKosu.getSon7ikincilikYuzdesi().doubleValue() + atKosu.getSon7ucunculukYuzdesi().doubleValue();
            vals[2] = atKosu.getSon7bitirisOrtalamasi().doubleValue();
            vals[3] = atKosu.getSon7DereceFarkiOrtalamasi().doubleValue();
            vals[4] = atKosu.getSon3DereceFarkiOrtalamasi().doubleValue();
            vals[5] = (atKosu.getSONUCNO() == 1) ? 0 : 1;
            data.add(new Instance(1.0, vals));

        }
        Util.saveInstances(data, new File (Base.getTestFilesPath() + "/son7kosular_nominal_for_test.arff"));
    }
}
