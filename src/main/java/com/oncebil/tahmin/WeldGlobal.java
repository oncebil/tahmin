/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncebil.tahmin;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 *
 * @author ekarincaoglu
 */
public class WeldGlobal {

    private final static WeldContainer container;

    static {
        Weld weld = new Weld();
        container = weld.initialize();


    }

    public static <T> T get(Class<T> clazz) {
        return container.instance().select(clazz).get();
    }

  
}
