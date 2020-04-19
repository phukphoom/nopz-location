package Models.Calculation;

import Models.Blueprint.Location;

import java.util.ArrayList;

public class Approximate {
    public static double approximate(ArrayList<Location> locationList, Location start){
        double radius = 0;
        for (int i = 0; i < locationList.size(); i++) {
            radius += locationList.get(i).distanceWith(start);
        }
        return radius/locationList.size();
    }
}
