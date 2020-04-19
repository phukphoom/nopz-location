import Models.Sample.Setting;
import Models.Utilities.FileWorker;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javafx.scene.image.Image;
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

    // Method
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Views/HomeView.fxml"));
        Scene homeScene = new Scene(root, 800, 600);

        stage.setScene(homeScene);
        stage.setTitle("NOPZ Location  |  Home");
        stage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
        stage.setResizable(false);
        root.setDisable(FileWorker.readSettings().isLock());
        stage.show();

        if(root.isDisable() && FileWorker.readSettings().isLock()){
            login(root, stage);
        }

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println(">> Exit Application");
                System.exit(0);
            }
        });
    }
    public void login (Parent root, Stage mainStage) throws FileNotFoundException {
        Stage loginStage = new Stage();
        loginStage.setTitle("Authentication");
        try{
            loginStage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
        }
        catch (Exception exception){

        }
        loginStage.setWidth(300);
        loginStage.setHeight(150);
        loginStage.setResizable(false);

        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(10);
        mainContainer.setPadding(new Insets(10,10,10,10));

        Label detailLogin = new Label("โปรดกรอก รหัสผ่าน");
        detailLogin.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        detailLogin.setStyle("-fx-text-fill: #008887");
        detailLogin.setAlignment(Pos.CENTER);
        detailLogin.setPrefWidth(300);
        detailLogin.setPrefHeight(150/3);

        PasswordField passwordField = new PasswordField();
        passwordField.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("เข้าสู่ระบบ");
        loginBtn.setStyle("-fx-background-color:#00BECF; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
        try {
            loginBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),18));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        loginBtn.setPrefWidth(80);
        loginBtn.setPrefHeight(150/3);

        mainContainer.getChildren().addAll(detailLogin, passwordField, loginBtn);
        loginStage.setScene(new Scene(mainContainer));

        loginBtn.setOnAction(event->{
            try {
                Setting setting = FileWorker.readSettings();
                if(passwordField.getText().compareTo(setting.getPassword()) == 0) {
                    loginStage.close();
                    root.setDisable(false);
                } else {
                    detailLogin.setText("รหัสผ่านไม่ถูกต้อง! โปรดลองอีกครั้ง");
                    detailLogin.setTextFill(Color.RED);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        loginStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    login(root,mainStage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        mainStage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                loginStage.close();
            }
        });

        loginStage.setAlwaysOnTop(true);
        loginStage.show();
    }
}
