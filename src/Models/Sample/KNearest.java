/* @author Asus*/
package Models.Sample;

import java.util.ArrayList;

public class KNearest {

    // Static Method
    public static double kNearest(int k, ArrayList<Location> locationList, Location start){
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
            outPut.add(locationList.get(i));
        }
//        System.out.println(outPut.get(k-1));

        return outPut.get(k-1).distanceWith(start);
    }

}