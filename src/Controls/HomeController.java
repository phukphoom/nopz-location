package Controls;

import Models.Sample.Setting;
import Models.Screen.*;
import Models.Utilities.FileWorker;
import Models.Sample.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import static javafx.scene.text.FontWeight.BOLD;
import static javafx.scene.text.FontWeight.NORMAL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    @FXML
    private Button manageButton;
    @FXML
    private Button mode1Button;
    @FXML
    private Button mode2Button;
    @FXML
    private Button mode3Button;
    @FXML
    private Button mode4Button;
    @FXML
    private Button exitButton;
    // Constructor

    public HomeController() throws IOException {
    }

    // Method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userPositionLabel.setText("พิกัดปัจจุบันของคุณ  :  ( " + (int)this.user.getX() + " , " + (int)this.user.getY() + " )");
        userPositionLabel.setStyle("-fx-text-fill: #008887");
        try {
            userPositionLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        editLabel.setStyle("-fx-text-fill: #FF5733");
        try {
            editLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        manageButton.setStyle("-fx-background-color:#008B8A; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
        try {
            manageButton.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),30));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mode1Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
        try {
            mode1Button.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mode2Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
        try {
            mode1Button.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mode3Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
        try {
            mode1Button.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mode4Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
        try {
            mode1Button.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        exitButton.setStyle("-fx-background-color:#DB3535; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
        try {
            exitButton.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        //this.editLabel.setFont(Font.font(null, BOLD, editLabel.getFont().getSize()));
        this.editLabel.setStyle("-fx-text-fill: #C70039 ");
        this.editLabel.setUnderline(true);
    }
    @FXML
    private void handleEditLabelExited(MouseEvent event){
        //this.editLabel.setFont(Font.font(null, NORMAL, editLabel.getFont().getSize()));
        this.editLabel.setStyle("-fx-text-fill: #FF5733");
        this.editLabel.setUnderline(false);
    }
    @FXML
    private void handleEditLabelClicked(MouseEvent event){
        System.out.println("  -> Label edit Clicked");
        try{
            SelectableMapDrawer mapDrawer = new SelectableMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  Set User Location");
            mapDrawer.getMapStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
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
            mapDrawer.getStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
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
            mapDrawer.getMapStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
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
            mapDrawer.getMapStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
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
            mapDrawer.getMapStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
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

//    @FXML
//    private void handleButton1MouseEnter(ActionEvent event) {
//        mode1Button.setStyle("-fx-background-color:#0f9900; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
//    }
//    @FXML
//    private void handleButton1MouseExit(ActionEvent event) {
//        mode1Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
//    }
//
//    @FXML
//    private void handleButton2MouseEnter(ActionEvent event) {
//        mode2Button.setStyle("-fx-background-color:#0f9900; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
//    }
//    @FXML
//    private void handleButton2MouseExit(ActionEvent event) {
//        mode1Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
//    }
//
//    @FXML
//    private void handleButton3MouseEnter(ActionEvent event) {
//        mode3Button.setStyle("-fx-background-color:#0f9900; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
//    }
//    @FXML
//    private void handleButton3MouseExit(ActionEvent event) {
//        mode1Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
//    }
//
//    @FXML
//    private void handleButton4MouseEnter(ActionEvent event) {
//        mode4Button.setStyle("-fx-background-color:#0f9900; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
//    }
//    @FXML
//    private void handleButton4MouseExit(ActionEvent event) {
//        mode1Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
//    }

}
