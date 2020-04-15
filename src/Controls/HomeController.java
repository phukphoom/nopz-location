package Controls;

import Models.Utilities.FileWorker;
import Models.sample.Location;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    // Data
    private double MAP_HEIGHT = 750.f, MAP_WIDTH = 1125.f;
    private double RATIO = 10.0f;

    private double user_x = 1300.f, user_y = 900.f;
    private ArrayList<Location> locations = FileWorker.readFileToLocations();

    public HomeController() throws IOException {
    }

    @FXML
    private Label userPositionLabel;
    @FXML
    private Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userPositionLabel.setText("พิกัดปัจจุบันของคุณ : ( " + (int)this.user_x + " , " + (int)this.user_y + " )");
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        System.out.println(">> Exit Application");
        Platform.exit();
    }
}
