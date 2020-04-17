package Models.Utilities;

import Models.Sample.Location;
import Models.Sample.Setting;
import java.io.*;
import java.util.ArrayList;


public class FileWorker {

    //Static Method
    public static void writeUserLocationToFile(double x, double y, String name) throws IOException {
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream("./data/UserLocation.dat"));
            if(dataInputStream.available() >= 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("./data/UserLocation.dat", false));
                dataOutputStream.writeUTF(name);
                dataOutputStream.writeDouble(x);
                dataOutputStream.writeDouble(y);

                dataOutputStream.close();
            }
            dataInputStream.close();
        }
        catch (IOException exception) {
            System.out.println(exception);
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("./data/UserLocation.dat"));
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeDouble(x);
            dataOutputStream.writeDouble(y);

            dataOutputStream.close();
        }
        System.out.println(">> Write :: " + name + " , " + x + " , "  + y + " To " + "UserLocation.dat");
    }
    public static Location readUserLocationFromFile() throws IOException {
        Location returnLocation = new Location();
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("./data/UserLocation.dat"));
        if(dataInputStream.available() != 0){
            returnLocation.setName(dataInputStream.readUTF());
            returnLocation.setX(dataInputStream.readDouble());
            returnLocation.setY(dataInputStream.readDouble());
        }
        dataInputStream.close();

        System.out.println(">> Read : " + returnLocation + " From " + "UserLocation.dat");
        return returnLocation;
    }
    public static void writeLocationInListToFile(double x, double y, String name) throws IOException {
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream("./data/LocationData.dat"));
            if(dataInputStream.available() >= 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("./data/LocationData.dat", true));
                dataOutputStream.writeUTF(name);
                dataOutputStream.writeDouble(x);
                dataOutputStream.writeDouble(y);
                dataOutputStream.close();
            }
            dataInputStream.close();
        }
        catch (IOException exception) {
            System.out.println(exception);
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("./data/LocationData.dat"));
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeDouble(x);
            dataOutputStream.writeDouble(y);

            dataOutputStream.close();

        }
        System.out.println(">> Write :: " + name + " , " + x + " , "  + y + " To " + "LocationData.dat");
    }
    public static ArrayList<Location> readLocationListFromFile() throws IOException {
        ArrayList<Location> returnArrayList = new ArrayList<Location>();
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("./data/LocationData.dat"));
        while(dataInputStream.available() != 0) {
            String name = dataInputStream.readUTF();
            double x = dataInputStream.readDouble();
            double y = dataInputStream.readDouble();

            returnArrayList.add(new Location(x, y, name));
        }
        dataInputStream.close();

        System.out.println(">> Read : " + returnArrayList + " From " + "LocationData.dat");
        return returnArrayList;
    }
    public static void deleteLocationInListByIndex(int delete_index) throws IOException{
        ArrayList<Location> firstReadLocation = readLocationListFromFile();

        System.out.println(">> Delete ::  " + firstReadLocation.get(delete_index) + " From " + "LocationData.dat");
        firstReadLocation.remove(delete_index);

        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("./data/LocationData.dat", false));
        for (int i=0;i<firstReadLocation.size();i++) {
            dataOutputStream.writeUTF(firstReadLocation.get(i).getName());
            dataOutputStream.writeDouble(firstReadLocation.get(i).getX());
            dataOutputStream.writeDouble(firstReadLocation.get(i).getY());
        }
        dataOutputStream.close();
    }

    public static void writeSettings(String password, boolean isLock) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("./data/settings.dat"));
            dataOutputStream.writeBoolean(isLock);
            dataOutputStream.writeUTF(password);
            dataOutputStream.close();
        }
        catch (IOException exception) {
        }
    }
    public static Setting readSettings() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("./data/settings.dat"));
        boolean isLock = dataInputStream.readBoolean();
        String password = dataInputStream.readUTF();

        return new Setting(password, isLock);
    }
    public static void writeSettings(Setting setting) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("./data/settings.dat"));
            dataOutputStream.writeBoolean(setting.isLock());
            dataOutputStream.writeUTF(setting.getPassword());
            dataOutputStream.close();
        }
        catch (IOException exception) {
        }
    }
}
