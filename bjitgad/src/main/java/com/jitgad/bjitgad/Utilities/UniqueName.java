
package com.jitgad.bjitgad.Utilities;

import java.util.Random;

/**
 *
 * @author jorge
 */
public class UniqueName {
    
    public String nunique(){
        char n;
        Random rnd = new Random();
        String cadena = new String();
        for (int i = 0; i < 15; i++) {
          n = (char) (rnd.nextDouble() * 26.0 + 65.0);
          cadena +=n;
        }
        return cadena;
    }
}
