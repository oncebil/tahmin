package com.oncebil.tahmin.entity;

import com.oncebil.tahmin.TahminException;
import org.hibernate.annotations.Sort;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by erkinkarincaoglu on 02/06/2016.
 */
@Entity
@Table(name = "AtKosu")
public class Kosu {
    @Id
    @Column(name = "KOSUKODU")
    private Long KOSUKODU;


    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "KOSUKODU", referencedColumnName = "KOSUKODU",insertable = false,updatable = false)
    })

    private Set<Bahis> bahisler;

    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "KOSUKODU", referencedColumnName = "KOSUKODU",insertable = false,updatable = false)
    })

    private Set<AtKosu> atlar;


    public Long getKOSUKODU() {
        return KOSUKODU;
    }

    public void setKOSUKODU(Long KOSUKODU) {
        this.KOSUKODU = KOSUKODU;
    }


    public Set<Bahis> getBahisler() {
        return bahisler;
    }

    public void setBahisler(Set<Bahis> bahisler) {
        this.bahisler = bahisler;
    }

    public Set<AtKosu> getAtlar() {
        return atlar;
    }

    public void setAtlar(Set<AtKosu> atlar) {
        this.atlar = atlar;
    }

    /**
     * This method assumes that atlar has filled with regression or classification predictions already
     * Normally at has one regression prediction (or classification prediction)
     * when kosular is selected with  experiment
     * @return
     */
    @Transient
    public List<Prediction> getAtlarWithPredictions() {
        List<Prediction> predictions = new ArrayList<>();
        for (AtKosu at : getAtlar()) {
            predictions.add( at.getPrediction() );
        }
        return predictions;
    }


    @Transient
    public BigDecimal getMinumumPredicted() {
        List<Prediction> predictions = getAtlarWithPredictions();
        predictions.sort((p1, p2) -> p1.getPredicted().compareTo(p2.getPredicted()));
        return predictions.get(0).getPredicted();
    }
    @Transient
    public BigDecimal getSecondMinumumPredicted() {
        List<Prediction> predictions = getAtlarWithPredictions();
        predictions.sort((p1, p2) -> p1.getPredicted().compareTo(p2.getPredicted()));
        return predictions.get(1).getPredicted();
    }

    @Transient
    public static Kosu createWithAtKosular(List<AtKosu> atkosular) {
        Kosu kosu = new Kosu();
        kosu.atlar = new LinkedHashSet<>(atkosular);
        return kosu;
    }

    @Transient
    public static List<Kosu> createKosular(List<AtKosu> atlar) {
        Map<Long,List<AtKosu>> kosular = new TreeMap<>();
        for (AtKosu atKosu : atlar) {
            if (!kosular.containsKey( atKosu.getKOSUKODU()) ) {
                kosular.put(atKosu.getKOSUKODU(), new ArrayList<>());
            }
            kosular.get(atKosu.getKOSUKODU() ).add(atKosu);
        }
        List<Kosu> kosulist = new ArrayList<>();
        kosular.forEach((k,v) -> kosulist.add( Kosu.createWithAtKosular(v)) );
        return kosulist;
    }

    enum Sort
    {
        ASC,
        DESC,
    }
    @Transient
    public static void addValuePositionInKosu(List<Kosu> kosular,Field source,String newDynamicValue, Sort sort) {
        try {
            source.setAccessible(true); //Additional line
            List<BigDecimal> values = new ArrayList<>();
            for (Kosu k : kosular) {
                for (AtKosu at : k.getAtlar()) {
                    values.add( (BigDecimal)source.get(at));
                }
            }
            if (sort == Sort.ASC) {
                Collections.sort(values);
            } else {
                Collections.sort(values, Collections.reverseOrder());
            }
            System.out.println( values);
            for (Kosu k : kosular) {
                int minumumIndex = Integer.MAX_VALUE;
                for (AtKosu at : k.getAtlar()) {
                    if (values.indexOf((BigDecimal)source.get(at)) < minumumIndex) {
                        minumumIndex = values.indexOf((BigDecimal)source.get(at));
                    }
                }
                for (AtKosu at : k.getAtlar()) {
                    at.addDynamicValue(newDynamicValue,
                            new BigDecimal(values.indexOf( (BigDecimal)source.get(at) ) - minumumIndex));
                }
            }
        } catch (IllegalAccessException e) {
            throw new TahminException(e);
        }

    }
    @Transient
    public static void addDynamicRelativeAttributeToAtlar(List<Kosu> kosular,
                                                          String newDynamicValue, String sourceDynamicValue) {

        //Set<BigDecimal> uniquevalues = new TreeSet<>();
        List<BigDecimal> values = new ArrayList<>();
        for (Kosu k : kosular) {
            for (AtKosu at : k.getAtlar()) {
                values.add(at.getDynamicValue(sourceDynamicValue));
            }
        }


        Collections.sort(values);
        System.out.println (values.size() );
        System.out.println (values.indexOf(new BigDecimal("0.00")) );
        System.out.println (values.indexOf(new BigDecimal("0.00")) );
        System.out.println (values.indexOf(new BigDecimal("14.29")) );
        System.out.println (values.indexOf(new BigDecimal("28.58")) );
        System.out.println (values.indexOf(new BigDecimal("42.86")) );
        System.out.println (values.indexOf(new BigDecimal("42.87")) );
        System.out.println (values.indexOf(new BigDecimal("57.15")) );
        // values=son3yuzdesi. 0, 12,12, 25 ,25, 47....

        System.out.println("values="+values);
        for (Kosu k : kosular) {

            int maximum = Integer.MIN_VALUE;
            for (AtKosu at : k.getAtlar()) {
                int index = values.indexOf(at.getDynamicValue(sourceDynamicValue));
                if (index > maximum) {
                    maximum = index;
                }
            }
            for (AtKosu at : k.getAtlar()) {
                int index = values.indexOf(at.getDynamicValue(sourceDynamicValue));
                Integer dynamicValue = maximum - index;
                at.addDynamicValue(newDynamicValue, new BigDecimal(dynamicValue));
            }
        }


    }
}
