package com.oncebil.tahmin;

import com.oncebil.tahmin.dao.AtKosuDAO;
import com.oncebil.tahmin.entity.AtKosu;
import com.oncebil.tahmin.entity.Kosu;

import org.junit.Assert;
import org.junit.Test;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import javax.persistence.Query;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erkin.karincaoglu on 21/06/16.
 */
public class UtilTest {



    @Test
    public void testCreateInstances() {
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

        List<Kosu> kosular = Kosu.createKosular(WeldGlobal.get(AtKosuDAO.class).getAtKosularBySql(sql));
        List<Util.Row> values = new ArrayList<>();
        for (Kosu kosu : kosular) {
            for (AtKosu atKosu : kosu.getAtlar()) {
                Double weight = null;
                if (atKosu.getSONUCNO() == 1) {
                    weight = 8.0;
                }
                values.add( new Util.Row(Arrays.asList(new Object[]{
                        atKosu.getKosuKoduAtKodu(),
                        atKosu.getSon7birincilikYuzdesi().doubleValue() + atKosu.getSon7ikincilikYuzdesi().doubleValue() + atKosu.getSon7ucunculukYuzdesi().doubleValue(),
                        atKosu.getSon7DereceFarkiOrtalamasi().doubleValue(),
                        atKosu.getSon3DereceFarkiOrtalamasi().doubleValue(),
                        (atKosu.getSONUCNO() == 1) ? "Y" : "N"
                }),weight));
            }
        }

        FastVector atts = new FastVector();
        atts.addElement(new Attribute("KosuId_AtId", (FastVector) null));
        atts.addElement(new Attribute("Son73egirisyuzdesi"));
        atts.addElement(new Attribute("Son7DereceFarkiOrtalamasi"));
        atts.addElement(new Attribute("Son3DereceFarkiOrtalamasi"));
        FastVector attVals = new FastVector();
        attVals.addElement("Y");
        attVals.addElement("N");
        atts.addElement(new Attribute("BirinciMi", attVals));

        Instances data = Util.createInstances(atts, values);
        Assert.assertEquals(1181, data.numInstances());
        Util.saveInstances(data,
                new File(Base.getTestFilesPath() + "/test-arff-utils.arff"));


    }
}
