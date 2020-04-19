package Models.Calculation;

import Models.Blueprint.Location;
import Models.Utilities.FileWorker;

import java.io.IOException;
import java.util.ArrayList;

public class MinMax {

    public MinMax() throws IOException {
    }

    public static Location max() throws IOException {
        double relX, relY;
        ArrayList<Location> locs = FileWorker.readLocationListFromFile();
        ArrayList<Double> a = new ArrayList<>();
        for (Location l : locs) {
            relX = relUser(l.getX(), 'x');
            relY = relUser(l.getY(), 'y');
            if(relX != 0 && relY != 0)
                a.add(Math.sqrt((relX*relX) + (relY*relY)));
            else if(relX == 0 && relY != 0)
                a.add(Math.abs(relY));
            else if(relX != 0 && relY == 0)
                a.add(Math.abs(relX));
            else
                a.add(0.0);
        }
        double max = a.get(0);
        int index = 0;
        for (int i=0;i<a.size();i++) {
            if(max<a.get(i)) {
                max = a.get(i);
                index = i;
            }
        }
        return locs.get(index);
    }

    public static Location min() throws IOException {
        double relX, relY;
        ArrayList<Location> locs = FileWorker.readLocationListFromFile();
        ArrayList<Double> a = new ArrayList<>();
        for (Location l : locs) {
            relX = relUser(l.getX(), 'x');
            relY = relUser(l.getY(), 'y');
            if(relX != 0 && relY != 0)
                a.add(Math.sqrt((relX*relX) + (relY*relY)));
            else if(relX == 0 && relY != 0)
                a.add(Math.abs(relY));
            else if(relX != 0)
                a.add(Math.abs(relX));
            else
                a.add(0.0);
        }
        double min = a.get(0);
        int index = 0;
        for (int i=0;i<a.size();i++) {
            if(min>a.get(i)) {
                min = a.get(i);
                index = i;
            }
        }
        return locs.get(index);
    }

    private static double relUser(double diff, char axis) throws IOException {
        Location user = FileWorker.readUserLocationFromFile();
        if(axis == 'x') {
            return user.getX() - diff;
        } else {
            return user.getY() - diff;
        }
    }
}
