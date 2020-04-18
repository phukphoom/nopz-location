package Models.Sample;

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
        return x;
    }
    public double getY() {
        return y;
    }
    public String getName() {
        return name;
    }

    // Method
    public double distanceWith(Location location) {
        return Math.sqrt((location.x - this.x) * (location.x - this.x) + (location.y - this.y) * (location.y - this.y));
    }

    public ArrayList<Location> sortByDistanceWith(ArrayList<Location> locs) {
        Location spair;
        ArrayList<Location> outPut = new ArrayList<Location>();

        for (int j = 0; j < locs.size(); j++) {
            for (int i = 0; i < locs.size()-1; i++) {
                if(locs.get(i).distanceWith(this)>locs.get(i+1).distanceWith(this)){
                    spair = locs.get(i);
                    locs.set(i, locs.get(i+1));
                    locs.set(i+1, spair);
                }
            }
        }

        for (int i = 0; i < locs.size(); i++) {
            outPut.add(locs.get(i));
        }
        System.out.println(outPut);
        return outPut;
    }
    @Override
    public String toString() {
        return "Location{" + "x=" + x + ", y=" + y + ", name='" + name + "\'" + "}";
    }
}
