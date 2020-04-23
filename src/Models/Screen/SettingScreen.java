package Models.Screen;

import Models.Blueprint.Location;
import Models.Utilities.FileWorker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Models.Blueprint.Setting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingScreen {
    private Setting setting = FileWorker.readSettings();
    private Stage stage;
    private Scene scene;
    private GridPane container;
    private VBox button;
    private Location user = FileWorker.readUserLocationFromFile();

    private double MAP_HEIGHT = 750.f, MAP_WIDTH = 1125.f;
    private double RATIO = 10.0f;

    public SettingScreen() throws IOException {

        container = new GridPane();
        container.setPadding(new Insets(10,10,10,10));
        container.setVgap(10);
        container.setHgap(10);

        scene = new Scene(container, 460,250);

        stage = new Stage();
        stage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/shield.png"))));
        stage.setTitle("Security Setting");
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
    }

    public Stage getStage() throws FileNotFoundException {
        Label title = new Label("ตั้งค่ารหัสผ่าน");
        title.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),40));
        title.setStyle("-fx-text-fill: #008887");
        GridPane.setConstraints(title,1,1);

        Label isLock = new Label("ป้องกันด้วยรหัสผ่าน");
        isLock.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),25));
        isLock.setStyle("-fx-text-fill: #008887");
        GridPane.setConstraints(isLock, 1, 3);

        CheckBox isLockCheckBox = new CheckBox();
        isLockCheckBox.setSelected(this.setting.isLock());
        GridPane.setConstraints(isLockCheckBox, 2, 3);

        Label passwordLabel = new Label("รหัสผ่านใหม่");
        passwordLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),25));
        passwordLabel.setStyle("-fx-text-fill: #008887");
        GridPane.setConstraints(passwordLabel, 1,4);

        TextField passwordVisibleField = new TextField();
        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 2,4);
        GridPane.setConstraints(passwordVisibleField, 2, 4);

        passwordField.setText(this.setting.getPassword());
        passwordVisibleField.setText(this.setting.getPassword());
        passwordField.setDisable(!this.setting.isLock());
        Label showPassword = new Label("แสดงรหัสผ่าน");
        showPassword.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        showPassword.setStyle("-fx-text-fill: #008887");
        GridPane.setConstraints(showPassword, 1, 5);
        passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());


        CheckBox showPasswordCheckBox = new CheckBox();
        passwordVisibleField.managedProperty().bind(showPasswordCheckBox.selectedProperty());
        passwordField.managedProperty().bind(showPasswordCheckBox.selectedProperty().not());
        passwordField.visibleProperty().bind(showPasswordCheckBox.selectedProperty().not());
        passwordVisibleField.visibleProperty().bind(showPasswordCheckBox.selectedProperty());
        GridPane.setConstraints(showPasswordCheckBox, 2, 5);
        if(setting.isLock()){
            showPasswordCheckBox.setDisable(false);
        }
        else{
            showPasswordCheckBox.setDisable(true);
        }

        isLockCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    passwordField.setDisable(false);
                    passwordVisibleField.setDisable(false);
                    showPasswordCheckBox.setDisable(false);
                }
                else {
                    passwordField.setDisable(true);
                    passwordVisibleField.setDisable(true);
                    showPasswordCheckBox.setDisable(true);
                }
            }
        });

        Button okBtn = new Button("บันทึก");
        okBtn.setPrefWidth(80.f);
        okBtn.setPrefHeight(30.f);
        okBtn.setOnMouseEntered(e->{
            okBtn.setStyle("-fx-background-color:#0f9900; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
        });
        okBtn.setOnMouseExited(e->{
            okBtn.setStyle("-fx-background-color:#56c596; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
        });
        okBtn.setOnAction(e->{
            Setting setting = new Setting(!isLockCheckBox.isSelected() ? "" : passwordField.getText(), isLockCheckBox.isSelected());
            if(setting.isPasswordSafe() || !isLockCheckBox.isSelected()) {
                FileWorker.writeSettings(setting);
                stage.close();
            } else {
                Alert alertBox = new Alert(Alert.AlertType.WARNING, "รหัสผ่านไม่ปลอดภัย!\nรหัสที่ปลอดภัยควรมีความยาวมากกว่า 8 ตัวอักษร\nไม่มีช่องว่าง และมีทั้งพิมพ์ใหญ่และพิมพ์ ตัวเลขและอักขระพิเศษ");
                Stage alertStage = (Stage) (alertBox.getDialogPane().getScene().getWindow());
                alertStage.setAlwaysOnTop(true);
                alertBox.setHeaderText(null);
                try{
                    alertStage.getIcons().add(new Image(new FileInputStream((new File("src/Views/resource/Image/warning.png")))));
                    ImageView alertImage = new ImageView(new Image( new FileInputStream(new File("src/Views/resource/Image/warning.png"))));
                    alertImage.setFitWidth(50);
                    alertImage.setFitHeight(50);
                    alertBox.setGraphic(alertImage);
                }
                catch (FileNotFoundException fileNotFoundException){
                }
                alertBox.showAndWait();
            }
        });
        okBtn.setStyle("-fx-background-color:#56c596; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
        okBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));


        Button cancelBtn = new Button("ยกเลิก");
        cancelBtn.setPrefWidth(80.f);
        cancelBtn.setPrefHeight(30.f);
        cancelBtn.setOnAction(e->{
            this.stage.close();
        });
        cancelBtn.setOnMouseEntered(e->{
            cancelBtn.setStyle("-fx-background-color:#990000; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
        });
        cancelBtn.setOnMouseExited(e->{
            cancelBtn.setStyle("-fx-background-color:#DB3535; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
        });
        cancelBtn.setStyle("-fx-background-color:#DB3535; -fx-background-radius: 30px; -fx-text-fill: #ffffff ");
        cancelBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));

        Button easterEggBtn = new Button("เล่นเกม");
        easterEggBtn.setPrefHeight(30.f);
        easterEggBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
        easterEggBtn.setStyle("-fx-background-color: transparent; -fx-background-radius: 30px; -fx-text-fill: transparent; -fx-min-width: 60px");
        GridPane.setConstraints(easterEggBtn, 3, 6);
        easterEggBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(passwordField.getText().compareTo("minigame") == 0) {
                    easterEggBtn.setStyle("-fx-background-color:#56c596; -fx-background-radius: 30px; -fx-text-fill: #ffffff; -fx-min-width: 60px");
                }
            }
        });
        easterEggBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                easterEggBtn.setStyle("-fx-background-color: transparent; -fx-background-radius: 30px; -fx-text-fill: transparent; -fx-min-width: 60px");
            }
        });
        easterEggBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(passwordField.getText().compareTo("minigame") == 0) {
                    stage.close();
                    try{
                        MiniGameMapDrawer mapDrawer = null;
                        mapDrawer = new MiniGameMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, user.getX(), user.getY());
                        mapDrawer.getMapStage().setTitle("NOPZ Location  |  Easter Egg NOPZ_MINIGAME");
                        mapDrawer.getStage().getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
                        Stage mdStage = mapDrawer.getStage();
                        MiniGameMapDrawer finalMapDrawer = mapDrawer;
                        mdStage.setOnHidden(e-> {
                            finalMapDrawer.getAnimTimer().stop();
                        });
                        mdStage.show();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        HBox btnGroup = new HBox();
        btnGroup.setSpacing(10);
        btnGroup.getChildren().addAll(okBtn, cancelBtn);
        GridPane.setConstraints(btnGroup, 2, 6);

        container.getChildren().addAll(title,isLock, isLockCheckBox, passwordLabel, passwordField, passwordVisibleField,
                 showPasswordCheckBox,showPassword,btnGroup, easterEggBtn);
        return this.stage;
    }
}
