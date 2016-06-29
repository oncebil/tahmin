/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin.dao;


import com.oncebil.tahmin.ApplicationConstants;
import com.oncebil.tahmin.Base;
import com.oncebil.tahmin.WeldGlobal;
import com.oncebil.tahmin.entity.AtKosu;
import com.oncebil.tahmin.entity.Kazanc;
import de.laliluna.transactions.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.Date;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @author ekarincaoglu
 */
public class AtKosuDAOTest extends Base {

    private AtKosuDAO atKosuDAO;

    @Before
    public void before() throws Exception {
        atKosuDAO = WeldGlobal.get(AtKosuDAO.class);
        //Base.insertTestData();
    }

    @Test

    public void testSelectByKosuKodu() {

        Assert.assertNotNull(atKosuDAO.getAtKosu(80048955L, 80024494L));
    }

    public static String[] fields = new String[]{"jokey","at"};
    public static int[] sonKosuSayilari = new int[]{3, 7, 13, 23, 37, 53};
    public static int[] tabela = new int[]{1, 2, 3, 4};

    @Test
    public void testUpdateJokeyKazanc() throws SQLException {
        Connection jdbcConnection = DriverManager.getConnection(
                ApplicationConstants.jdbcUrl, ApplicationConstants.username, ApplicationConstants.password);

        //updateTableJokeyKazanclari(jdbcConnection);
        String sql = " select sonucno, kosukoduatkodu,jokeykisaadi,jokeykodu,kosutarihi from AtKosu order by kosutarihi desc, kosukodu, sonucno ";
        Statement st = jdbcConnection.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        int i = 0;
        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(10);
        while (resultSet.next()) {
            i++;
            service.submit(new UpdateJokeyInAtKosu(resultSet.getString("kosutarihi"), jdbcConnection, resultSet.getLong("jokeykodu"), resultSet.getString("kosukoduatkodu")));
            if (i == 1000) {
                break;
            }
        }



        shutdownAndAwaitTermination(service);
        long end = System.currentTimeMillis();
        System.out.println("time=" + (end - start) / 1000);
    }

    public void updateTableJokeyKazanclari(Connection connection) throws SQLException {

        for (String field: fields) {
            for (int sonKosuSayisi : sonKosuSayilari) {
                String sql2 = " ALTER TABLE AtKosu DROP COLUMN " + field +"Kazanc " + sonKosuSayisi;
                connection.createStatement().execute(sql2);
                String sql = " ALTER TABLE AtKosu ADD COLUMN "+field+"Kazanc" + sonKosuSayisi + " bigint";
                connection.createStatement().execute(sql);

                for (int t : tabela) {
                    String sql3 = " ALTER TABLE AtKosu DROP COLUMN IF EXISTS "+field+"Tabela" + t + "_" + sonKosuSayisi;
                    connection.createStatement().execute(sql3);
                    String sql4 = " ALTER TABLE AtKosu ADD COLUMN "+field+"Tabela" + t + "_" + sonKosuSayisi + " bigint";
                    connection.createStatement().execute(sql4);
                }
            }
        }

    }

    static class UpdateJokeyInAtKosu implements Runnable {

        private final Connection jdbcConnection;
        private final String beforeDate;
        private final Long jokeyKodu;
        private final String kosukoduAtKodu;

        public UpdateJokeyInAtKosu(String beforeDate, Connection jdbcConnection, Long jokeyKodu, String kosukoduAtKodu) {
            this.beforeDate = beforeDate;
            this.jdbcConnection = jdbcConnection;
            this.jokeyKodu = jokeyKodu;
            this.kosukoduAtKodu = kosukoduAtKodu;
        }

        @Override
        public void run() {
            try {
                updateJokeyInAtKosu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void updateJokeyInAtKosu() throws SQLException {
            String sql = "select atadi,atkodu,kosutarihi, kosukoduatkodu, jokeykodu, jokeykisaadi, sonucno, ikramiye1,ikramiye2,ikramiye3,ikramiye4 from atkosu where jokeykodu=? and kosutarihi < ?  order by kosutarihi desc, kosukodu, sonucno limit " + sonKosuSayilari[sonKosuSayilari.length - 1];
            PreparedStatement st = jdbcConnection.prepareStatement(sql);
            st.setLong(1, jokeyKodu);
            st.setString(2, beforeDate);
            ResultSet resultSet = st.executeQuery();
            Map<Integer, Long> kazancToplamlari = new HashMap<>();
            int i = 0;
            Long toplamKazanc = 0L;
            Map<Integer, Integer> tabelalar = new HashMap<>();
            Map<Integer, Map<Integer, Integer>> tumtabelalar = new HashMap<>();
            for (int t : tabela) {
                tabelalar.put(t, 0);
            }
            while (resultSet.next()) {
                i++;
                switch (resultSet.getInt("sonucno")) {
                    case 1:
                        toplamKazanc += resultSet.getLong("ikramiye1");
                        tabelalar.put(1, tabelalar.get(1) + 1);
                        break;
                    case 2:
                        toplamKazanc += resultSet.getLong("ikramiye2");
                        tabelalar.put(2, tabelalar.get(2) + 1);
                        break;
                    case 3:
                        toplamKazanc += resultSet.getLong("ikramiye3");
                        tabelalar.put(3, tabelalar.get(3) + 1);
                        break;
                    case 4:
                        toplamKazanc += resultSet.getLong("ikramiye4");
                        tabelalar.put(4, tabelalar.get(4) + 1);
                        break;
                    default:
                        break;
                }
                kazancToplamlari.put(i, new Long(toplamKazanc));
                tumtabelalar.put(i, new HashMap<>(tabelalar));
            }
            st.close();

            for (int sonKosuSayisi : sonKosuSayilari) {
                if (kazancToplamlari.containsKey(sonKosuSayisi)) {
                    String sqlupdate = " Update  AtKosu set jokeyKazanc" + sonKosuSayisi + "=? where kosukoduAtKodu=?";
                    st = jdbcConnection.prepareStatement(sqlupdate);
                    st.setLong(1, kazancToplamlari.get(sonKosuSayisi));
                    st.setString(2, kosukoduAtKodu);
                    st.executeUpdate();
                    st.close();
                }
                if (tumtabelalar.containsKey(sonKosuSayisi)) {
                    for (int t : tabela) {
                        String sqlupdate = " Update  AtKosu set jokeyTabela" + t + "_" + sonKosuSayisi + "=? where kosukoduAtKodu=?";
                        st = jdbcConnection.prepareStatement(sqlupdate);
                        st.setLong(1, tumtabelalar.get(sonKosuSayisi).get(t));
                        st.setString(2, kosukoduAtKodu);
                        st.executeUpdate();
                        st.close();
                    }
                }
            }
        }
    }

    void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.DAYS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.DAYS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }



}
