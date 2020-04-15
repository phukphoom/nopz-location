package Controls;

import Models.Screen.*;
import Models.Utilities.FileWorker;
import Models.sample.Location;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.text.FontWeight.BOLD;
import static javafx.scene.text.FontWeight.NORMAL;

public class HomeController implements Initializable {

    // Data Fields
    private double MAP_HEIGHT = 750.f, MAP_WIDTH = 1125.f;
    private double RATIO = 10.0f;

    private Location user = FileWorker.readUserLocationFromFile();
    private ArrayList<Location> locations = FileWorker.readLocationListFromFile();

    @FXML
    private Label userPositionLabel;
    @FXML
    private Label editLabel;

    // Constructor
    public HomeController() throws IOException {
    }

    // Event Handle
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userPositionLabel.setText("พิกัดปัจจุบันของคุณ  :  ( " + (int)this.user.getX() + " , " + (int)this.user.getY() + " )");
    }

    //==> Edit Label
    @FXML
    private void handleEditLabelEntered(MouseEvent event){
        this.editLabel.setFont(Font.font(null, BOLD, editLabel.getFont().getSize()));
        this.editLabel.setUnderline(true);
    }
    @FXML
    private void handleEditLabelExited(MouseEvent event){
        this.editLabel.setFont(Font.font(null, NORMAL, editLabel.getFont().getSize()));
        this.editLabel.setUnderline(false);
    }
    @FXML
    private void handleEditLabelClicked(MouseEvent event){
        System.out.println("  -> Label edit Clicked");
    }

    //==> Manage Button
    @FXML
    private void handleManageButtonClicked(ActionEvent event){
        System.out.println("  -> Button Manage Clicked");
    }

    //==> Mode1 Button
    @FXML
    private void handleMode1ButtonClicked(ActionEvent event){
        try{
            AggregationMapDrawer md = new AggregationMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            md.setLocs(FileWorker.readLocationListFromFile());
            md.getMapStage().show();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
        System.out.println("  -> Button Mode1 Clicked");
    }

    //==> Mode2 Button
    @FXML
    private void handleMode2ButtonClicked(ActionEvent event){
        try{
            KNearestMapDrawer md = new KNearestMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            md.setLocs(FileWorker.readLocationListFromFile());
            md.getMapStage().show();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
        System.out.println("  -> Button Mode2 Clicked");
    }

    //==> Mode3 Button
    @FXML
    private void handleMode3ButtonClicked(ActionEvent event){
        try{
            ApproximateMapDrawer md = new ApproximateMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            md.setLocs(FileWorker.readLocationListFromFile());
            md.getMapStage().show();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
        System.out.println("  -> Button Mode3 Clicked");
    }

    //==> Mode4 Button
    @FXML
    private void handleMode4ButtonClicked(ActionEvent event){
        try{
            MinMaxMapDrawer md = new MinMaxMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            md.setLocs(FileWorker.readLocationListFromFile());
            md.getMapStage().show();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
        System.out.println("  -> Button Mode4 Clicked");
    }

    //==> Exit Button
    @FXML
    private void handleExitButtonClicked(ActionEvent event) {
        System.out.println(">> Exit Application");
        Platform.exit();
    }
}
