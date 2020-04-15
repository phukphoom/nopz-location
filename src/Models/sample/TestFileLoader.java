package Models.sample;

import Models.Utilities.FileWorker;

import java.io.*;

public class TestFileLoader {
    public static void main(String[] args) throws IOException {

//         writeLocationToFile(5.482, 32.226, "Shop 2");
        FileWorker.writeLocationToFile(15052, 1500, "Test Store 2");
        System.out.println(FileWorker.readFileToLocations());
//        writeLocationToFile(17, 902, "Test Store 2");
    }
}
