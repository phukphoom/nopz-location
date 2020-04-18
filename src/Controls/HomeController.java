package Controls;

import Models.Screen.*;
import Models.Utilities.FileWorker;
import Models.Sample.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import static javafx.scene.text.FontWeight.BOLD;
import static javafx.scene.text.FontWeight.NORMAL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    // Data Fields
    private Location user = FileWorker.readUserLocationFromFile();

    private double MAP_HEIGHT = 750.f, MAP_WIDTH = 1125.f;
    private double RATIO = 10.0f;

    @FXML
    private Label userPositionLabel;
    @FXML
    private Label editLabel;
    @FXML
    private Menu setting;
    @FXML
    private Menu about;

    // Constructor
    public HomeController() throws IOException {
    }

    // Method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userPositionLabel.setText("พิกัดปัจจุบันของคุณ  :  ( " + (int)this.user.getX() + " , " + (int)this.user.getY() + " )");
    }

    // Event Handle
    @FXML
    private void handleSettingMenuClicked(){
        try{
            SettingScreen screen = new SettingScreen();
            screen.getStage().show();
        }
        catch (Exception exception){
        }
    }
    @FXML
    private void handleAboutMenuClicked(){

    }


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
            SelectableMapDrawer mapDrawer = new SelectableMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  Set User Location");
            mapDrawer.getMapStage().show();
            mapDrawer.getMapStage().setOnHidden(stageEvent->{
                mapDrawer.getConfirmPickStage().close();
                try{
                    if(mapDrawer.isPicked()){
                        FileWorker.writeUserLocationToFile(mapDrawer.getPickX(),mapDrawer.getPickY(),"User");
                        user = FileWorker.readUserLocationFromFile();
                        userPositionLabel.setText("พิกัดปัจจุบันของคุณ  :  ( " + (int)this.user.getX() + " , " + (int)this.user.getY() + " )");
                    }
                }
                catch (Exception exception){
                    System.out.println(exception);
                }
            });
        }
        catch(Exception exception){
            System.out.println(exception);
        }
    }

    @FXML
    private void handleManageButtonClicked(ActionEvent event){
        System.out.println("  -> Button Manage Clicked");
        try{
            LocationManagement locationManagement = new LocationManagement();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
    }

    @FXML
    private void handleMode1ButtonClicked(ActionEvent event){
        System.out.println("  -> Button Mode1 Clicked");
        try{
            AggregationMapDrawer mapDrawer = new AggregationMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  Aggregation");
            mapDrawer.getMapStage().show();
        }
        catch(Exception exception){
            System.out.println(exception);
            if(exception.getMessage()=="Vertex is lees than 3 nodes"){
                Alert alertBox = new Alert(Alert.AlertType.ERROR,"จำนวนร้านค้า ไม่เพียงพอต่อการคำนวณเส้นทาง");
                alertBox.showAndWait();
            }
            else{
                Alert alertBox = new Alert(Alert.AlertType.ERROR,exception.getMessage());
                alertBox.showAndWait();
            }
        }
    }

    @FXML
    private void handleMode2ButtonClicked(ActionEvent event){
        System.out.println("  -> Button Mode2 Clicked");
        try{
            KNearestMapDrawer mapDrawer = new KNearestMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  KNearest");
            if(FileWorker.readLocationListFromFile().size() <= 0) {
                throw new Exception("มีร้านไม่เพียงพอ กรุณาเพิ่มร้านค้า!");
            }
            mapDrawer.getMapStage().show();
        }
        catch(Exception exception){
            System.out.println(exception);
            Alert alertBox = new Alert(Alert.AlertType.ERROR, exception.getMessage());
            alertBox.showAndWait();
        }
    }

    @FXML
    private void handleMode3ButtonClicked(ActionEvent event){
        System.out.println("  -> Button Mode3 Clicked");
        try{
            ApproximateMapDrawer mapDrawer = new ApproximateMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  Approximate");
            if(FileWorker.readLocationListFromFile().size() <= 0) {
                throw new Exception("มีร้านไม่เพียงพอ กรุณาเพิ่มร้านค้า!");
            }
            mapDrawer.getMapStage().show();
        }
        catch(Exception exception){
            Alert alertBox = new Alert(Alert.AlertType.ERROR,exception.getMessage());
            alertBox.showAndWait();
            System.out.println(exception);
        }
    }

    @FXML
    private void handleMode4ButtonClicked(ActionEvent event){
        System.out.println("  -> Button Mode4 Clicked");
        try{
            MinMaxMapDrawer mapDrawer = new MinMaxMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  MinMax");
            mapDrawer.getMapStage().show();
        }
        catch(Exception exception){
            Alert alertBox = new Alert(Alert.AlertType.ERROR,exception.getMessage());
            alertBox.showAndWait();
            System.out.println(exception);
        }
    }

    @FXML
    private void handleExitButtonClicked(ActionEvent event) {
        System.out.println(">> Exit Application");
        System.exit(0);
    }
}
