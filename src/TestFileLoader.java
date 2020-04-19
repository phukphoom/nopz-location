import Models.Utilities.FileWorker;

import java.io.*;

public class TestFileLoader {
    public static void main(String[] args) throws Exception {
        FileWorker.writeLocationInListToFile(15052, 1500,"node_1");
        FileWorker.writeLocationInListToFile(16052, 1300,"node_2");
        FileWorker.writeLocationInListToFile(14052, 1280,"node_3");
        FileWorker.writeLocationInListToFile(17152, 1500,"node_4");
        FileWorker.writeUserLocationToFile(16540,1600,"User");
        FileWorker.writeSettings("1234",false);
    }
}
