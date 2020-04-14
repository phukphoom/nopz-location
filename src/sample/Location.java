package sample;

import java.io.Serializable;

public class Location implements Serializable {
    private double x = 0.0, y = 0.0;
    private String name;

    public Location(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public double distanceWith(Location loc) {
        return Math.sqrt((loc.x - this.x) * (loc.x - this.x) + (loc.y - this.y) * (loc.y - this.y));
    }

    // Getter & Setter
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
