package Controls;

import Models.Screen.*;
import Models.Utilities.FileWorker;
import Models.Blueprint.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    // Data Fields
    private double MAP_HEIGHT = 750.f, MAP_WIDTH = 1125.f;
    private double RATIO = 10.0f;
    private Location user = FileWorker.readUserLocationFromFile();

    @FXML
    private Label userPositionLabel;
    @FXML
    private Label editLabel;
    @FXML
    private Menu setting;
    @FXML
    private Menu instruction;
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
    public HomeController() throws Exception {
    }

    // Method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initStyle();
        this.userPositionLabel.setText("พิกัดปัจจุบันของคุณ  :  ( " + (int)this.user.getX() + " , " + (int)this.user.getY() + " )");
    }
    private void initStyle(){

        // init userPositionLabel
        try {
            this.userPositionLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        this.userPositionLabel.setStyle("-fx-text-fill: #008887");

        // init editLabel
        try {
            this.editLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        this.editLabel.setStyle("-fx-text-fill: #FF5733");

        // init manageButton
        try {
            this.manageButton.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),30));
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        this.manageButton.setStyle("-fx-background-color:#008B8A; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");

        // init mode1Button
        try {
            this.mode1Button.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        this.mode1Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");

        // init mode2Button
        try {
            this.mode2Button.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        this.mode2Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");

        // init mode3Button
        try {
            this.mode3Button.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        this.mode3Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");

        // init mode4Button
        try {
            this.mode4Button.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        this.mode4Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");

        // init exitButton
        try {
            this.exitButton.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        this.exitButton.setStyle("-fx-background-color:#DB3535; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }

    // Event Handle
    // ==> All Menu
    @FXML
    private void handleSettingMenuClicked(){
        try{
            SettingScreen screen = new SettingScreen();
            screen.getStage().show();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
    @FXML
    private void handleInstructionMenuClicked(){
        try{
            InstructionScreen screen = new InstructionScreen();
            screen.getStage().show();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    // ==> editLabel
    @FXML
    private void handleEditLabelEntered(MouseEvent event){
        this.editLabel.setStyle("-fx-text-fill: #C70039 ");
        this.editLabel.setUnderline(true);
    }
    @FXML
    private void handleEditLabelExited(MouseEvent event){
        this.editLabel.setStyle("-fx-text-fill: #FF5733");
        this.editLabel.setUnderline(false);
    }
    @FXML
    private void handleEditLabelClicked(MouseEvent event){
        System.out.println("  >>> Label edit Clicked");
        try{
            SelectableMapDrawer mapDrawer = new SelectableMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  Set User Location");
            mapDrawer.getMapStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));

            mapDrawer.getMapStage().setOnHidden(mapEvent->{
                mapDrawer.getConfirmPickStage().close();
                try{
                    if(mapDrawer.isPicked()){
                        FileWorker.writeUserLocationToFile(mapDrawer.getPickX(),mapDrawer.getPickY(),"User");
                        user = FileWorker.readUserLocationFromFile();
                        userPositionLabel.setText("พิกัดปัจจุบันของคุณ  :  ( " + (int)this.user.getX() + " , " + (int)this.user.getY() + " )");
                    }
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }
            });

            mapDrawer.getMapStage().show();
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }

    // ==> manageButton
    @FXML
    private void handleManageButtonMouseEntered(MouseEvent event) {
        this.manageButton.setStyle("-fx-background-color:#006666; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleManageButtonMouseExited(MouseEvent event) {
        this.manageButton.setStyle("-fx-background-color:#008B8A; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleManageButtonClicked(ActionEvent event){
        System.out.println("  >>> Button Manage Clicked");
        try{
            LocationManagement locationManagement = new LocationManagement();
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }

    // ==> mode1Button
    @FXML
    private void handleMode1ButtonMouseEntered(MouseEvent event) {
        this.mode1Button.setStyle("-fx-background-color:#0f9900; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleMode1ButtonMouseExited(MouseEvent event) {
       this.mode1Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleMode1ButtonClicked(ActionEvent event){
        System.out.println("  >>> Button Mode1 Clicked");
        try{
            AggregationMapDrawer mapDrawer = new AggregationMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  Aggregation");
            mapDrawer.getStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
            mapDrawer.getStage().show();
        }
        catch(Exception exception){
            exception.printStackTrace();
            Alert alertBox = new Alert(Alert.AlertType.ERROR,exception.getMessage());
            alertBox.setHeaderText(null);

            Stage alertStage = (Stage)alertBox.getDialogPane().getScene().getWindow();
            alertStage.setAlwaysOnTop(true);
            try{
                alertStage.getIcons().add(new Image(new FileInputStream((new File("src/Views/resource/Image/error.png")))));
                ImageView alertImage = new ImageView(new Image( new FileInputStream(new File("src/Views/resource/Image/error.png"))));
                alertImage.setFitWidth(50);
                alertImage.setFitHeight(50);
                alertBox.setGraphic(alertImage);
            }
            catch (Exception sub_exception){
            }

            if(exception.getMessage()=="Vertex is lees than 3 nodes"){
                alertBox.setContentText("จำนวนร้านค้าไม่เพียงพอต่อการคำนวน !");
            }
            else{
                alertBox.setContentText(exception.getMessage());
            }
            alertBox.showAndWait();
        }
    }

    // ==> mode2Button
    @FXML
    private void handleMode2ButtonMouseEntered(MouseEvent event) {
        this.mode2Button.setStyle("-fx-background-color:#0f9900; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleMode2ButtonMouseExited(MouseEvent event) {
        this.mode2Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleMode2ButtonClicked(ActionEvent event){
        System.out.println("  >>> Button Mode2 Clicked");
        try{
            if(FileWorker.readLocationListFromFile().size() <= 0) {
                throw new Exception("รายการร้านค้าว่างเปล่า กรุณาเพิ่มร้านค้า !");
            }
            KNearestMapDrawer mapDrawer = new KNearestMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  KNearest");
            mapDrawer.getMapStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
            mapDrawer.getMapStage().show();
        }
        catch(Exception exception){
            exception.printStackTrace();
            Alert alertBox = new Alert(Alert.AlertType.ERROR,exception.getMessage());
            alertBox.setHeaderText(null);
            Stage alertStage = (Stage)alertBox.getDialogPane().getScene().getWindow();
            alertStage.setAlwaysOnTop(true);
            try{
                alertStage.getIcons().add(new Image(new FileInputStream((new File("src/Views/resource/Image/error.png")))));
                ImageView alertImage = new ImageView(new Image( new FileInputStream(new File("src/Views/resource/Image/error.png"))));
                alertImage.setFitWidth(50);
                alertImage.setFitHeight(50);
                alertBox.setGraphic(alertImage);
            }
            catch (Exception sub_exception){
            }
            alertBox.showAndWait();
        }
    }

    // ==> mode3Button
    @FXML
    private void handleMode3ButtonMouseEntered(MouseEvent event) {
        this.mode3Button.setStyle("-fx-background-color:#0f9900; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleMode3ButtonMouseExited(MouseEvent event) {
        this.mode3Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleMode3ButtonClicked(ActionEvent event){
        System.out.println("  >>> Button Mode3 Clicked");
        try{
            if(FileWorker.readLocationListFromFile().size() <= 0) {
                throw new Exception("รายการร้านค้าว่างเปล่า กรุณาเพิ่มร้านค้า !");
            }
            ApproximateMapDrawer mapDrawer = new ApproximateMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  Approximate");
            mapDrawer.getMapStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
            mapDrawer.getMapStage().show();
        }
        catch(Exception exception){
            exception.printStackTrace();
            Alert alertBox = new Alert(Alert.AlertType.ERROR,exception.getMessage());
            alertBox.setHeaderText(null);
            Stage alertStage = (Stage)alertBox.getDialogPane().getScene().getWindow();
            alertStage.setAlwaysOnTop(true);
            try{
                alertStage.getIcons().add(new Image(new FileInputStream((new File("src/Views/resource/Image/error.png")))));
                ImageView alertImage = new ImageView(new Image( new FileInputStream(new File("src/Views/resource/Image/error.png"))));
                alertImage.setFitWidth(50);
                alertImage.setFitHeight(50);
                alertBox.setGraphic(alertImage);
            }
            catch (Exception sub_exception){
            }
            alertBox.showAndWait();
        }
    }

    // ==> mode4Button
    @FXML
    private void handleMode4ButtonMouseEntered(MouseEvent event) {
        this.mode4Button.setStyle("-fx-background-color:#0f9900; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleMode4ButtonMouseExited(MouseEvent event) {
        this.mode4Button.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleMode4ButtonClicked(ActionEvent event){
        System.out.println("  >>> Button Mode4 Clicked");
        try{
            if(FileWorker.readLocationListFromFile().size() <= 0) {
                throw new Exception("รายการร้านค้าว่างเปล่า กรุณาเพิ่มร้านค้า !");
            }

            MinMaxMapDrawer mapDrawer = new MinMaxMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, this.user.getX(), this.user.getY());
            mapDrawer.getMapStage().setTitle("NOPZ Location  |  MinMax");
            mapDrawer.getMapStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
            mapDrawer.getMapStage().show();
        }
        catch(Exception exception){
            exception.printStackTrace();
            Alert alertBox = new Alert(Alert.AlertType.ERROR,exception.getMessage());
            alertBox.setHeaderText(null);
            Stage alertStage = (Stage)alertBox.getDialogPane().getScene().getWindow();
            alertStage.setAlwaysOnTop(true);
            try{
                alertStage.getIcons().add(new Image(new FileInputStream((new File("src/Views/resource/Image/error.png")))));
                ImageView alertImage = new ImageView(new Image( new FileInputStream(new File("src/Views/resource/Image/error.png"))));
                alertImage.setFitWidth(50);
                alertImage.setFitHeight(50);
                alertBox.setGraphic(alertImage);
            }
            catch (Exception sub_exception){
            }
            alertBox.showAndWait();
        }
    }

    // ==> exitButton
    @FXML
    private void handleExitButtonMouseEntered(MouseEvent event) {
        this.exitButton.setStyle("-fx-background-color:#990000; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleExitButtonMouseExited(MouseEvent event) {
        this.exitButton.setStyle("-fx-background-color:#DB3535; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
    }
    @FXML
    private void handleExitButtonClicked(ActionEvent event) {
        System.out.println(">> Exit Application");
        System.exit(0);
    }
}
