/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Screen;
import Models.sample.Location;

import java.util.ArrayList;
/**
 *
 * @author Asus
 */
public class KNearest {
    public static ArrayList<Location> kNearest(int k, ArrayList<Location> locationList, Location start){
        Location spair;
        ArrayList<Location> outPut = new ArrayList<Location>();
        
        for (int j = 0; j < locationList.size(); j++) {
            for (int i = 0; i < locationList.size()-1; i++) {
                if(locationList.get(i).distanceWith(start)>locationList.get(i+1).distanceWith(start)){
                    spair = locationList.get(i);
                    locationList.set(i, locationList.get(i+1));
                    locationList.set(i+1, spair);
                }
            }
            
        }
        
        for (int i = 0; i < k; i++) {
            System.out.println("Add " + i);
            outPut.add(locationList.get(i));
        }
        
        return outPut; 
    }
    
    public static double approximate(ArrayList<Location> locationList, Location start){
        double r = 0;
        for (int i = 0; i < locationList.size(); i++) {
            r+=locationList.get(i).distanceWith(start);
        }
        return r/locationList.size();
    }
}
