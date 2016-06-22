package com.oncebil.tahmin.entity;

import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.Util;
import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.dao.AtKosuDAO;
import org.hibernate.annotations.Sort;
import org.junit.Assert;
import org.junit.Test;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erkin.karincaoglu on 21/06/16.
 */
public class KosuTest {
    @Test
    public void addValuePositionInKosuTest() throws NoSuchFieldException {
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
        Kosu.addValuePositionInKosu(kosular,
                AtKosu.class.getDeclaredField("son7bitirisOrtalamasi"),
                "relativeson7bitirisOrtalamasi",
                Kosu.Sort.ASC);

        Assert.assertEquals(new BigDecimal(414), kosular.get(0).getAtlar().iterator().next().getDynamicValue("relativeson7bitirisOrtalamasi"));

        kosular.forEach(k -> k.getAtlar().
                forEach(at -> System.out.println("" +
                        " kosukodu_atkodu=" + at.getKosuKoduAtKodu() +
                        " SONUCNO=" + at.getSONUCNO() +
                        " son7bitirisOrtalamasi=" + at.getSon7bitirisOrtalamasi() +
                        " relativeson7bitirisOrtalamasi=" + at.getDynamicValue("relativeson7bitirisOrtalamasi"))
                ));

    }
}
