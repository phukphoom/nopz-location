package Controls;

import Models.Screen.*;
import Models.Utilities.FileWorker;
import Models.Sample.Location;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.scene.text.FontWeight.BOLD;
import static javafx.scene.text.FontWeight.NORMAL;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
        try{
            SelectableMapDrawer md = new SelectableMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            Stage stage = md.getMapStage();
            stage.setOnHidden(stageEvent->{
               try{
                   FileWorker.writeUserLocationToFile(md.getPickX(),md.getPickY(),"User");
                   user = FileWorker.readUserLocationFromFile();
                   userPositionLabel.setText("พิกัดปัจจุบันของคุณ  :  ( " + (int)this.user.getX() + " , " + (int)this.user.getY() + " )");
               }
               catch (Exception exception){
                   System.out.println(exception);
               }
            });
            stage.show();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
    }

    //==> Manage Button
    @FXML
    private void handleManageButtonClicked(ActionEvent event){
        System.out.println("  -> Button Manage Clicked");
    }

    //==> Mode1 Button
    @FXML
    private void handleMode1ButtonClicked(ActionEvent event){
        System.out.println("  -> Button Mode1 Clicked");
        try{
            AggregationMapDrawer md = new AggregationMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            md.getMapStage().setTitle("NOPZ Location  |  Aggregation");
            md.getMapStage().show();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
    }

    //==> Mode2 Button
    @FXML
    private void handleMode2ButtonClicked(ActionEvent event){
        System.out.println("  -> Button Mode2 Clicked");
        try{
            KNearestMapDrawer md = new KNearestMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            md.getMapStage().setTitle("NOPZ Location  |  KNearest");
            md.getMapStage().show();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
    }

    //==> Mode3 Button
    @FXML
    private void handleMode3ButtonClicked(ActionEvent event){
        System.out.println("  -> Button Mode3 Clicked");
        try{
            ApproximateMapDrawer md = new ApproximateMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            md.getMapStage().setTitle("NOPZ Location  |  Approximate");
            md.getMapStage().show();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
    }

    //==> Mode4 Button
    @FXML
    private void handleMode4ButtonClicked(ActionEvent event){
        System.out.println("  -> Button Mode4 Clicked");
        try{
            MinMaxMapDrawer md = new MinMaxMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            md.getMapStage().setTitle("NOPZ Location  |  MinMax");
            md.getMapStage().show();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
    }

    //==> Exit Button
    @FXML
    private void handleExitButtonClicked(ActionEvent event) {
        System.out.println(">> Exit Application");
        Platform.exit();
    }
}
