import Models.Blueprint.Setting;
import Models.Utilities.FileWorker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NOPZ_LocationApp extends Application {

    // Main
    public static void main(String[] args) {
        launch(args);
    }

    // Start
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Views/HomeView.fxml"));

        Scene homeScene = new Scene(root, 800, 600);

        stage.setScene(homeScene);
        try{
            stage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
        }
        catch (FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        }
        stage.setTitle("NOPZ Location  |  Home");
        stage.setResizable(false);

        stage.show();

        root.setDisable(FileWorker.readSettings().isLock());
        if(root.isDisable()){
            login(root, stage);
        }

        // All Event Handler
        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println(">> Exit Application");
                System.exit(0);
            }
        });

    }

    // Login
    public void login (Parent root, Stage mainStage){

        Stage loginStage = new Stage();
        try{
            loginStage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
        }
        catch (FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        }
        loginStage.setTitle("Authentication");
        loginStage.setAlwaysOnTop(true);
        loginStage.setResizable(false);
        loginStage.setWidth(300);
        loginStage.setHeight(150);

        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(10);
        mainContainer.setPadding(new Insets(10,10,10,10));

        Label detailLogin = new Label("โปรดกรอก รหัสผ่าน");
        try{
            detailLogin.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        }
        catch(FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        }
        detailLogin.setAlignment(Pos.CENTER);
        detailLogin.setPrefWidth(300);
        detailLogin.setPrefHeight(150/3);
        detailLogin.setStyle("-fx-text-fill: #008887");

        PasswordField passwordField = new PasswordField();
        passwordField.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("เข้าสู่ระบบ");
        try {
            loginBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),18));
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        loginBtn.setAlignment(Pos.CENTER);
        loginBtn.setPrefWidth(80);
        loginBtn.setPrefHeight(150/3);
        loginBtn.setStyle(" -fx-text-fill: #ffffff; -fx-background-color:#00BECF; -fx-background-radius: 10px;");

        mainContainer.getChildren().addAll(detailLogin, passwordField, loginBtn);
        loginStage.setScene(new Scene(mainContainer));
        loginStage.show();

        // All Event Handler
        loginBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginBtn.setStyle(" -fx-text-fill: #ffffff; -fx-background-color:#00a4b3; -fx-background-radius: 10px;");
            }
        });
        loginBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginBtn.setStyle(" -fx-text-fill: #ffffff; -fx-background-color:#00BECF; -fx-background-radius: 10px;");
            }
        });
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    Setting setting = FileWorker.readSettings();
                    if(passwordField.getText().compareTo(setting.getPassword()) == 0) {
                        loginStage.close();
                        root.setDisable(false);
                    }
                    else {
                        detailLogin.setText("รหัสผ่านไม่ถูกต้อง! โปรดลองอีกครั้ง");
                        detailLogin.setTextFill(Color.RED);
                    }
                }
                catch (IOException ioException){
                    ioException.printStackTrace();
                }
            }
        });

        loginStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                login(root,mainStage);
            }
        });
    }
}
