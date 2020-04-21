package Models.Calculation;

import Models.Blueprint.Location;

import java.util.ArrayList;

public class Approximate {

    // Static Method
    public static double approximate(ArrayList<Location> locationList, Location startLocation){

        double radius = 0;
        for (int i = 0; i < locationList.size(); i++) {
            radius += locationList.get(i).distanceWith(startLocation);
        }

        return radius/locationList.size();
    }
}
