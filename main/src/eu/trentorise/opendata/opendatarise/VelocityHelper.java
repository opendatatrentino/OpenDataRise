/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import java.text.NumberFormat;

/**
 * Class to help me out with Velocity
 * @author David Leoni
 */
public class VelocityHelper {
    public VelocityHelper(){
    }

    /**
     * 
     * @param n
     * @return 
     */
    public static final String formatAvg(double n){        
        return formatAvg(n,2);
    }

    /**
     * 2 digits, by default
     * @param n
     * @return 
     */
    
    public static final String formatAvg(double n, int decimals){        
        NumberFormat format = NumberFormat.getNumberInstance(ODR.getLocale());
        format.setMaximumFractionDigits(decimals);
        return format.format(n);                                            
    }
    
    /**
     * Thank you stack overflow
     */    
    public static final String formatFilesize(long bytes){        
        boolean si = true;
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);                                         
    }    
    


    
}
