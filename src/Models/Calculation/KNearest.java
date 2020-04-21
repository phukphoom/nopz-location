/* @author Asus*/
package Models.Calculation;

import Models.Blueprint.Location;

import java.util.ArrayList;

public class KNearest {

    // Static Method
    public static double kNearest(int k, ArrayList<Location> locationList, Location startLocation) {
        Location spair;
        ArrayList<Location> outPut = new ArrayList<Location>();

        for (int j = 0; j < locationList.size(); j++) {
            for (int i = 0; i < locationList.size()-1; i++) {
                if(locationList.get(i).distanceWith(startLocation)>locationList.get(i+1).distanceWith(startLocation)){
                    spair = locationList.get(i);
                    locationList.set(i, locationList.get(i+1));
                    locationList.set(i+1, spair);
                }
            }
        }

        for (int i = 0; i < k; i++) {
            outPut.add(locationList.get(i));
        }

        return outPut.get(k-1).distanceWith(startLocation);
    }
}