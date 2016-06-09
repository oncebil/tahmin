/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin;


import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Util {

    private final static Logger logger = Logger.getLogger(Util.class.getName());

    //http://en.wikipedia.org/wiki/Phi_coefficient
    public static double calculatePhicoefficient(double n11, double n10, double n01, double n00) {
        return ((n11 * n00) - (n10 * n01)) / Math.sqrt((n11 + n10) * (n01 + n00) * (n10 + n00) * (n11 + n01));

    }

    public static void main(String[] arr) {
        System.out.println(calculatePhicoefficient(6047, 7881, 25133, 87070));
    }

    public static void writeToFile(String filename, String str, String encoding) {
        if (encoding == null) {
            FileWriter fstream = null;
            try {
                fstream = new FileWriter(filename);
                fstream.write(str);
                fstream.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } finally {
                try {
                    if (fstream != null) {
                        fstream.close();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), encoding));
                out.write(str);
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }



        }
    }

    public static String readFromFile(String filename) {
        BufferedReader in = null;
        StringBuffer out = new StringBuffer();
        try {
            in = new BufferedReader(new FileReader(filename));
            String str;
            while ((str = in.readLine()) != null) {
                out.append(str);
                out.append(System.getProperty("line.separator"));
            }
            in.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return out.toString();

    }

    public static BigDecimal getBigDecimalListAVG(List<BigDecimal> values) {
        List<Integer> integers = new ArrayList<>();
        for (BigDecimal val : values) {
            integers.add(val.intValue());
        }
        return new BigDecimal(getListAVG(integers)).setScale(2);
    }

    public static Double getListAVG(List<Integer> values) {
        int toplam = 0;
        if (values.isEmpty()) {
            return null;
        }
        for (Integer value : values) {
            toplam += value;
        }
        return (double) toplam / (double) values.size();


    }

    public static Double getListVariance(List<Integer> values) {
        Double avg = getListAVG(values);
        if (avg == null) {
            return null;
        }
        int toplam = 0;
        for (Integer value : values) {
            toplam += Math.pow((value - avg), 2);
        }
        return (double) toplam / (double) values.size();
    }

    public static Double getListStandardDeviation(List<Integer> values) {
        Double var = getListVariance(values);
        if (var == null) {
            return null;
        }

        return Math.sqrt(var);

    }
    public static void saveInstances(Instances instances,File file) {
        ArffSaver saver = new ArffSaver();
        saver.setInstances(instances);
        try {
            saver.setFile(file);
            saver.writeBatch();
        } catch (IOException ex) {
            throw new TahminException(ex);
        }
    }
    public static int getHowManyCount(List<Integer> values, int findval) {
        int count = 0;
        for (Integer value : values) {
            if (value == findval) {
                count++;
            }
        }
        return count;
    }

    public static double getFrequecy(List<Integer> values, int findval) {
        if (values.size() == 0) {
            return 0;
        }
        int count = getHowManyCount(values, findval);
        return (double) count / (double) values.size() * 100.0;
    }

    public static void appendToFile(String filename, String str) {
        try {
            FileWriter fstream = new FileWriter(filename, true);
            fstream.write(str);
            fstream.close();
        } catch (Exception e) {//Catch exception if any
            e.printStackTrace();
        }
    }

    public static Date daysAdd(Date date, long addDays) {
        return new Date(date.getTime() + (1000 * 60 * 60 * 24 * addDays));
    }



    public static Double getListAVGDouble(List<Double> values) {
        double toplam = 0;
        if (values.isEmpty()) {
            return null;
        }
        for (Double value : values) {
            if (value == null) {
                continue;
            }
            toplam += value;
        }
        return (double) toplam / (double) values.size();
    }

    public static int getListCountIgnoreNull(List<Double> values) {
        int count = 0;
        for (Double value : values) {
            if (value == null) {
                continue;
            }
            count++;
        }
        return count;
    }

    public static double getListSum(List<Double> values) {
        double sum = 0;
        for (Double value : values) {
            if (value == null) {
                continue;
            }
            sum += value;
        }
        return sum;
    }

    public static void writeInstances(Instances instances, String arffFileName) throws IOException {
        logger.log(Level.INFO, "Saving instances. filename={0}", arffFileName);
        ArffSaver saver = new ArffSaver();
        saver.setInstances(instances);
        saver.setFile(new File(arffFileName));
        saver.writeBatch();

    }

    public static Instances loadInstances(File file) throws IOException {
        ArffLoader arffLoader = new ArffLoader();
        arffLoader.setFile(file);
        return arffLoader.getDataSet();

    }

    public static Instances loadInstances(String arffFileName) throws IOException {        
        ArffLoader arffLoader = new ArffLoader();
        arffLoader.setFile(new File (arffFileName));
        return arffLoader.getDataSet();

    }

    public static void createCahitArffPropertiesFile(String relation, String cahitArfPropertiesfile, String selectQuery, String attributesList) {
        StringBuffer out = new StringBuffer();
        out.append("relation=");
        out.append(relation);
        out.append(System.getProperty("line.separator"));
        out.append("org.postgresql.Driver");
        out.append(System.getProperty("line.separator"));
        //out.append("jdbc.url=" + WeldGlobal.get(ApplicationConstants.class).getJdbcurl_tjkdb());
        out.append(System.getProperty("line.separator"));
        out.append("jdbc.user=");
        out.append(System.getProperty("line.separator"));
        out.append("jdbc.select=");
        out.append(selectQuery);
        out.append(System.getProperty("line.separator"));
        out.append(attributesList);
        out.append(System.getProperty("line.separator"));

        Util.writeToFile(cahitArfPropertiesfile, out.toString(), "UTF8");
    }

    public static String bufferedReaderToString(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String aux = "";
        while ((aux = reader.readLine()) != null) {
            builder.append(aux);
            builder.append( System.getProperty("line.separator"));
        }
        return builder.toString();
    }

    public static  List<String> getInstancesClassValues(Instances instances) {
        List<String> classes = new ArrayList<>();
        Enumeration e = instances.classAttribute().enumerateValues();
        while(e.hasMoreElements()) {
            classes.add( e.nextElement().toString());
        }
        return classes;
    }

    public static int getIkiliCombination(int size) {
        return (size * (size-1))/2;
    }
}
