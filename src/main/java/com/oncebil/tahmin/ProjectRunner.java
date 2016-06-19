package com.oncebil.tahmin;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            projectRunner.run2();

        } catch (Exception ex) {
          ex.printStackTrace();
        } finally {
            System.exit(0);
        }


    }
    private void run2() {

        createProjectData2();
        Project project = Project.loadProject("son7kosular-heuristic-analysis");
        project.run();
    }



    @Transactional
    void createProjectData2() {
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
        // relativeSon7BitirisOrtalamasi daha da kotu oldu
        List<Kosu> kosular = Kosu.createKosular(atKosuList);
        System.out.println("size=" + kosular.size() );
        for (Kosu k : kosular) {
            for (AtKosu at : k.getAtlar()) {
                at.addDynamicValue("Son7BitirisOrtalamasi",
                        new BigDecimal(at.getSon7bitirisOrtalamasi().doubleValue()));
            }
        }
        Kosu.addDynamicRelativeAttributeToAtlar(kosular,"relativeSon7BitirisOrtalamasi" , "Son7BitirisOrtalamasi");
        for (Kosu k : kosular) {
            for (AtKosu at : k.getAtlar()) {
                System.out.println("kosu=" + at.getKOSUKODU() + " name=" + at.getATADI()
                        + " Son7BitirisOrtalamasi=" + at.getDynamicValue("Son7BitirisOrtalamasi")
                        + " relativeSon7BitirisOrtalamasi=" + at.getDynamicValue("relativeSon7BitirisOrtalamasi")
                        + " sonucNo=" + at.getSONUCNO()
                );

            }
        }

        System.out.println("size=" + atKosuList.size());



        FastVector atts = new FastVector();
        atts.addElement(new Attribute("KosuId_AtId", (FastVector) null));
        atts.addElement(new Attribute("Son73egirisyuzdesi") );
        atts.addElement(new Attribute("relativeSon7BitirisOrtalamasi") );
        atts.addElement(new Attribute("Son7DereceFarkiOrtalamasi") );
        atts.addElement(new Attribute("Son3DereceFarkiOrtalamasi") );
        FastVector attVals = new FastVector();
        attVals.addElement("Y");
        attVals.addElement("N");
        atts.addElement(new Attribute("BirinciMi", attVals ));
        Instances data = new Instances("MyRelation", atts, 0);
        double[] vals;
        for (Kosu k : kosular) {
            for (AtKosu atKosu : k.getAtlar()) {
                vals = new double[data.numAttributes()];
                vals[0] = data.attribute(0).addStringValue(atKosu.getKosuKoduAtKodu());
                vals[1] = atKosu.getSon7birincilikYuzdesi().doubleValue() + atKosu.getSon7ikincilikYuzdesi().doubleValue() + atKosu.getSon7ucunculukYuzdesi().doubleValue();
                vals[2] = atKosu.getDynamicValue("relativeSon7BitirisOrtalamasi").doubleValue();
                vals[3] = atKosu.getSon7DereceFarkiOrtalamasi().doubleValue();
                vals[4] = atKosu.getSon3DereceFarkiOrtalamasi().doubleValue();
                vals[5] = (atKosu.getSONUCNO() == 1) ? 0 : 1;
                data.add(new Instance(1.0, vals));
            }

        }
        Util.saveInstances(data, new File(ApplicationConstants.repositoryOut + "/data/arffs/son7kosular_nominal_heuristic_relativeSon73egirisyuzdesi.arff"));


    }


    private void run1() {
        createProjectData();
        Project project = Project.loadProject("TabelaGirisYuzdeleriSingleAttribute");
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
        System.out.println("size=" + atKosuList.size());
        createData2(atKosuList);
        //createData1(atKosuList)
    }

    public void createData2(List<AtKosu> atKosuList) {
        List<Kosu> kosular = Kosu.createKosular(atKosuList);
        System.out.println("size=" + kosular.size() );
        for (Kosu k : kosular) {
            for (AtKosu at : k.getAtlar()) {
                at.addDynamicValue("Son7birincilikYuzdesi", at.getSon7birincilikYuzdesi());
            }
        }
        Kosu.addDynamicRelativeAttributeToAtlar(kosular,"relativeSon7birincilikYuzdesi" , "Son7birincilikYuzdesi");
        for (Kosu k : kosular) {
            for (AtKosu at : k.getAtlar()) {
                System.out.println("kosu=" + at.getKOSUKODU() + " name=" + at.getATADI()
                        + " Son7birincilikYuzdesi=" + at.getDynamicValue("Son7birincilikYuzdesi")
                        + " relativeSon7birincilikYuzdesi=" + at.getDynamicValue("relativeSon7birincilikYuzdesi")
                        + " sonucNo=" + at.getSONUCNO()
                );

            }
        }


        FastVector atts = new FastVector();
        atts.addElement(new Attribute("KosuId_AtId", (FastVector) null));
        atts.addElement(new Attribute("Son7birincilikYuzdesi") );
        atts.addElement(new Attribute("SonucNo" ));
        Instances data = new Instances("MyRelation", atts, 0);
        double[] vals;
        for (Kosu k : kosular) {
            for (AtKosu at : k.getAtlar()) {
                vals = new double[data.numAttributes()];
                vals[0] = data.attribute(0).addStringValue(at.getKosuKoduAtKodu());
                vals[1] = at.getDynamicValue("Son7birincilikYuzdesi").doubleValue();
                if (at.getSONUCNO().doubleValue() < 6) {
                    vals[2] = at.getSONUCNO().doubleValue();
                } else {
                    vals[2] = 6.0;
                }

                data.add(new Instance(1.0, vals));
            }
        }
        Util.saveInstances(data, new File(ApplicationConstants.repositoryOut + "/data/arffs/singleattribue.arff"));
        boolean a = false;
        if (a) {
            System.exit(0);
        }
    }

    public void createData1(List<AtKosu> atKosuList) {
        FastVector atts = new FastVector();
        atts.addElement(new Attribute("KosuId_AtId", (FastVector) null));
        atts.addElement(new Attribute("Son73egirisyuzdesi") );
        atts.addElement(new Attribute("SonucNo" ));
        Instances data = new Instances("MyRelation", atts, 0);
        double[] vals;
        for (AtKosu atKosu : atKosuList) {
            vals = new double[data.numAttributes()];
            vals[0] = data.attribute(0).addStringValue(atKosu.getKosuKoduAtKodu());
            vals[1] = atKosu.getSon7birincilikYuzdesi().doubleValue() + atKosu.getSon7ikincilikYuzdesi().doubleValue() + atKosu.getSon7ucunculukYuzdesi().doubleValue();
            vals[2] = atKosu.getSONUCNO().doubleValue();
            data.add(new Instance(1.0, vals));
        }
        Util.saveInstances(data, new File(ApplicationConstants.repositoryOut + "/data/arffs/singleattribue.arff"));
    }

}
