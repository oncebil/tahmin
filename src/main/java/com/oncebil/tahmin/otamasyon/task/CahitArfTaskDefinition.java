/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oncebil.tahmin.otamasyon.task;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author ekarincaoglu
 */
@Root(name = "task")
public class CahitArfTaskDefinition extends TaskDefinition {

    @Element
    public String extraElement;
    @Element
    public CahitArfSplit cahitArfSplit;

    /**
     * @return the extraElement
     */
    public String getExtraElement() {
        return extraElement;
    }

    /**
     * @return the cahitArfSplit
     */
    public CahitArfSplit getCahitArfSplit() {
        return cahitArfSplit;
    }

    
}
