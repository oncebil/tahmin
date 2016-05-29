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
@Root
public class CahitArfSplit {
    
    @Element
    private String fieldName;
    @Element
    private String splitFrequency;
   

}
