package com.oncebil.tahmin;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by erkinkarincaoglu on 29/06/2016.
 */
public class CreateNewFields {


    public static String[] fields = new String[]{"jokey","at","anne","baba","antrenor","sahip"};
    public static int[] sonKosuSayilari = new int[]{1, 3, 7, 13, 23, 37, 53,73,105};
    public static int[] tabela = new int[]{1, 2, 3, 4};

    // drop fields or not. careful !
    public static boolean RECREATE = false;

    Connection connection;

    public static void main() throws SQLException {
        CreateNewFields main = new CreateNewFields();
        main.run();
    }

    public CreateNewFields() throws SQLException {
        connection = DriverManager.getConnection(
                ApplicationConstants.jdbcUrl, ApplicationConstants.username, ApplicationConstants.password);
    }

    public void run() throws SQLException {
        if (RECREATE) {
            createColumns();
        }
        updateData();
    }


    public void createColumns() throws SQLException {
        for (String field: fields) {
            for (int sonKosuSayisi : sonKosuSayilari) {
                // Kazanc columnlari
                createColumn(field +"KazancToplam " + sonKosuSayisi);
                // tabela girisleri
                for (int t : tabela) {
                    createColumn(field + "Tabela" + t + "_" + sonKosuSayisi);
                }
                // kacinciliklar toplami
                createColumn(field +"KacinciToplam " + sonKosuSayisi);
            }
        }
    }
    void createColumn(String name) throws SQLException {
        connection.createStatement().execute(" ALTER TABLE AtKosu DROP COLUMN " + name);
        connection.createStatement().execute(" ALTER TABLE AtKosu ADD COLUMN " + name + " bigint");
    }
    public void updateData() throws SQLException {
        String sql = " select sonucno, kosukoduatkodu,jokeykisaadi,jokeykodu,kosutarihi " +
                "from AtKosu " +
                "order by kosutarihi desc, kosukodu, sonucno ";
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        int i = 0;
        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(10);
        while (resultSet.next()) {
            i++;
            service.submit(new UpdateDataTask(resultSet.getString("kosutarihi"),
                    connection,
                    resultSet.getLong("jokeykodu"),
                    resultSet.getLong("atkodu"),
                    resultSet.getLong("annekodu"),
                    resultSet.getLong("babakodu"),
                    resultSet.getLong("antrenorkodu"),
                    resultSet.getLong("sahipkodu"),
                    resultSet.getString("kosukoduatkodu")));
            if (i == 1000) {
                break;
            }
        }
        shutdownAndAwaitTermination(service);
        long end = System.currentTimeMillis();
        System.out.println("time=" + (end - start) / 1000);
    }




    static class UpdateDataTask implements Runnable {

        private final String beforeDate;
        private final Connection jdbcConnection;
        private final Long jokeyKodu;
        private final Long atKodu;
        private final Long anneKodu;
        private final Long babaKodu;
        private final Long antrenorKodu;
        private final Long sahipKodu;
        private final String kosukoduAtKodu;

        public UpdateDataTask(String beforeDate, Connection jdbcConnection, Long jokeyKodu, Long atKodu, Long anneKodu, Long babaKodu, Long antrenorKodu, Long sahipKodu, String kosukoduAtKodu) {
            this.beforeDate = beforeDate;
            this.jdbcConnection = jdbcConnection;
            this.jokeyKodu = jokeyKodu;
            this.atKodu = atKodu;
            this.anneKodu = anneKodu;
            this.babaKodu = babaKodu;
            this.antrenorKodu = antrenorKodu;
            this.sahipKodu = sahipKodu;
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

