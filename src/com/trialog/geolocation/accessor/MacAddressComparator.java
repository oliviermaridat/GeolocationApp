package com.trialog.geolocation.accessor;

import java.util.Comparator;
import java.util.Map;

public class MacAddressComparator implements Comparator<String>{
    private Map<String, Integer> macAdresses;//pour garder une copie du Map que l'on souhaite traiter
      
    public MacAddressComparator(Map<String, Integer> macAdresses){
        this.macAdresses = macAdresses; //stocker la copie pour qu'elle soit accessible dans compare()
    }
      
    @Override
    public int compare(String id1, String id2){
        //r�cup�rer les personnes du Map par leur identifiant
    	Integer p1 = macAdresses.get(id1);
    	Integer p2 = macAdresses.get(id2);
        
        //comparer les deux cl�s en fonction de l'age des personnes qu'ils indexent.
        return p2 - p1;
    }
}