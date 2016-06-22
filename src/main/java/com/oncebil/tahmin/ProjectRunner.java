package com.oncebil.tahmin;

import com.oncebil.tahmin.dao.AtKosuDAO;
import com.oncebil.tahmin.entity.AtKosu;
import com.oncebil.tahmin.entity.Kosu;
import com.oncebil.tahmin.otamasyon.task.Project;
import de.laliluna.transactions.Transactional;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by erkinkarincaoglu on 08/06/2016.
 */
public class ProjectRunner {
    @Inject
    EntityManager manager;

    public static void main(String[] args) {

        try {
            String path = new File(ProjectRunner.class.getClassLoader().getResource(".").getFile()).getAbsolutePath();
            ApplicationConstants.repositoryPath = "/Users/erkinkarincaoglu/Documents/code/tahmin-repository";
            ApplicationConstants.repositoryOut = "/Users/erkinkarincaoglu/Documents/code/tahmin-repository/";

            ProjectRunner projectRunner = WeldGlobal.get(ProjectRunner.class);
            projectRunner.run();

        } catch (Exception ex) {
          ex.printStackTrace();
        } finally {
            System.exit(0);
        }


    }
    private void run() {

        createProjectData();
        Project project = Project.loadProject("son7kosular-heuristic-analysis");
        project.run();
    }



    @Transactional
    void createProjectData() {
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

        Query query = manager.createNativeQuery(sql, AtKosu.class);
        List<AtKosu> atKosuList = (List<AtKosu>) query.getResultList();

        // relativeSon73egirisyuzdesi gayet kotu oldu
        // relativeSon7BitirisOrtalamasi daha da kotu oldU
        // addd additional relativeSon7BitirisOrtalamasi daha da bok
        List<Kosu> kosular = Kosu.createKosular(WeldGlobal.get(AtKosuDAO.class).getAtKosularBySql(sql));
        List<Util.Row> values = new ArrayList<>();
        for (Kosu kosu : kosular) {
            int atSayisi = kosu.getAtlar().size();
            for (AtKosu atKosu : kosu.getAtlar()) {
                Double weight = (atKosu.getSONUCNO() == 1) ? (double)atSayisi : null;
                values.add( new Util.Row(Arrays.asList(new Object[]{
                        atKosu.getKosuKoduAtKodu(),
                        atKosu.getSon7birincilikYuzdesi().doubleValue() + atKosu.getSon7ikincilikYuzdesi().doubleValue() + atKosu.getSon7ucunculukYuzdesi().doubleValue(),
                        atKosu.getSon7bitirisOrtalamasi().doubleValue(),
                        atKosu.getSon7DereceFarkiOrtalamasi().doubleValue(),
                        atKosu.getSon3DereceFarkiOrtalamasi().doubleValue(),
                        (atKosu.getSONUCNO() == 1) ? "Y" : "N"
                }),weight));
            }
        }

        FastVector atts = new FastVector();
        atts.addElement(new Attribute("KosuId_AtId", (FastVector) null));
        atts.addElement(new Attribute("Son73egirisyuzdesi") );
        atts.addElement(new Attribute("Son7BitirisOrtalamasi") );
        atts.addElement(new Attribute("Son7DereceFarkiOrtalamasi") );
        atts.addElement(new Attribute("Son3DereceFarkiOrtalamasi") );
        FastVector attVals = new FastVector();
        attVals.addElement("Y");
        attVals.addElement("N");
        atts.addElement(new Attribute("BirinciMi", attVals));
        Instances data = Util.createInstances(atts, values);

        Util.saveInstances(data, new File(ApplicationConstants.repositoryOut + "/data/arffs/son7kosular_nominal_heuristic_relativeSon73egirisyuzdesi.arff"));
    }




}
