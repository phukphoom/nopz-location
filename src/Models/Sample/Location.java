package Models.Sample;

import java.io.Serializable;

public class Location implements Serializable {

    // Data Fileds
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
    public double distanceWith(Location loc) {
        return Math.sqrt((loc.x - this.x) * (loc.x - this.x) + (loc.y - this.y) * (loc.y - this.y));
    }
    @Override
    public String toString() {
        return "Location{" + "x=" + x + ", y=" + y + ", name='" + name + "\'" + "}";
    }
}
