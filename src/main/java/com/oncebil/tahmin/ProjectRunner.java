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
import java.util.List;

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
            ProjectRunner arffCreator = WeldGlobal.get(ProjectRunner.class);
            arffCreator.run();
        } catch (Exception ex) {
          ex.printStackTrace();
        } finally {
            System.exit(0);
        }


    }

    private void run() {
        createProjectData();
        Project project = Project.loadProject("TabelaGirisYuzdeleriSingleAttribute");
        project.run();
    }

    @Transactional
    void createProjectData() {
        String sql2= "select distinct a from Kosu a " +
                "inner join  fetch a.atlar b  " +
                "where a.KOSUKODU = b.KOSUKODU " +
                "AND b.KOSUTARIHI<='2011-08-01' " +
                "AND b.KOSUTARIHI>='2011-05-01' " +
                "AND b.birinciIleDereceFarki is not null " +
                "order by a.KOSUKODU desc ";
        Query query1 = manager.createQuery(sql2, Kosu.class);
        List<Kosu> kosular = (List<Kosu>) query1.getResultList();
        System.out.println("kosular size=" + kosular.size());
        for (Kosu k : kosular) {
            System.out.println("kosu id=" + k.getKOSUKODU());
        }

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
        createData1(atKosuList);
    }

    public void createData2(List<AtKosu> atKosuList) {
        FastVector atts = new FastVector();
        atts.addElement(new Attribute("KosuId_AtId", (FastVector) null));
        atts.addElement(new Attribute("Son73egirisyuzdesi-relative") );
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
