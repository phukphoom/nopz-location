package Models.Blueprint;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable {

    // Data Fields
    private double x = 0.0, y = 0.0;
    private String name;

    // Constructor
    public Location(){
    }
    public Location(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    // Setter
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public String getName() {
        return this.name;
    }

    // Method
    public double distanceWith(Location location) {
        return Math.sqrt((location.x - this.x) * (location.x - this.x) + (location.y - this.y) * (location.y - this.y));
    }
    public ArrayList<Location> sortByDistanceWith(ArrayList<Location> locationList) {
        Location spair;
        ArrayList<Location> outPut = new ArrayList<Location>();

        for (int j = 0; j < locationList.size(); j++) {
            for (int i = 0; i < locationList.size()-1; i++) {
                if(locationList.get(i).distanceWith(this)>locationList.get(i+1).distanceWith(this)){
                    spair = locationList.get(i);
                    locationList.set(i, locationList.get(i+1));
                    locationList.set(i+1, spair);
                }
            }
        }

        for (int i = 0; i < locationList.size(); i++) {
            outPut.add(locationList.get(i));
        }
        System.out.println("sortByDistanceWith => Output : " + outPut);
        return outPut;
    }
    @Override
    public String toString() {
        return "Location{" + "x=" + this.x + ", y=" + this.y + ", name='" + this.name + "\'" + "}";
    }
}
