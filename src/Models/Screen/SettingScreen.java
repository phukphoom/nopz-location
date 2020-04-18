package Models.Screen;

import Models.Utilities.FileWorker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Models.Sample.Setting;

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

    public SettingScreen() throws IOException {
        stage = new Stage();


        container = new GridPane();
        container.setPadding(new Insets(10,10,10,10));
        container.setVgap(10);
        container.setHgap(10);
        container.setStyle("-fx-background-color: #ffffff;");

        scene = new Scene(container, 380,250);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/shield.png"))));
        stage.setTitle("Security setting");
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

        //passwordVisibleField.setVisible(false);
        passwordField.setDisable(!this.setting.isLock());
        Label showPassword = new Label("แสดงรหัสผ่าน");
        showPassword.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        showPassword.setStyle("-fx-text-fill: #008887");
        GridPane.setConstraints(showPassword, 1, 5);
        passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());
        isLockCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    passwordField.setDisable(false);
                    passwordVisibleField.setDisable(false);
                } else {
                    passwordField.setDisable(true);
                    passwordVisibleField.setDisable(true);
                }
            }
        });

        CheckBox showPasswordCheckBox = new CheckBox();
        passwordVisibleField.managedProperty().bind(showPasswordCheckBox.selectedProperty());
        passwordField.managedProperty().bind(showPasswordCheckBox.selectedProperty().not());
        passwordField.visibleProperty().bind(showPasswordCheckBox.selectedProperty().not());
        passwordVisibleField.visibleProperty().bind(showPasswordCheckBox.selectedProperty());
        GridPane.setConstraints(showPasswordCheckBox, 2, 5);

        Button okBtn = new Button("บันทึก");
        okBtn.setOnMouseEntered(e->{
            okBtn.setStyle("-fx-background-color:#0f9900; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
        });
        okBtn.setOnMouseExited(e->{
            okBtn.setStyle("-fx-background-color:#13C300; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
        });
        okBtn.setOnAction(e->{
            FileWorker.writeSettings(new Setting(!isLockCheckBox.isSelected() ? "" : passwordField.getText(), isLockCheckBox.isSelected()));
            stage.close();
        });
        okBtn.setStyle("-fx-background-color:#13C300; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
        okBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));


        Button cancelBtn = new Button("ยกเลิก");
        cancelBtn.setOnAction(e->{
            this.stage.close();
        });
        cancelBtn.setOnMouseEntered(e->{
            cancelBtn.setStyle("-fx-background-color:#990000; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
        });
        cancelBtn.setOnMouseExited(e->{
            cancelBtn.setStyle("-fx-background-color:#C50000; -fx-background-radius: 30px; -fx-text-fill: #ffffff;");
        });
        cancelBtn.setStyle("-fx-background-color:#C50000; -fx-background-radius: 30px; -fx-text-fill: #ffffff ");
        cancelBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
//        cancelBtn.setFont();

        GridPane.setConstraints(okBtn, 2, 6);
        GridPane.setConstraints(cancelBtn, 2, 7);
        //button.getChildren().addAll(okBtn,cancelBtn);
        //GridPane.setConstraints(button,2,5);
        container.getChildren().addAll(title,isLock, isLockCheckBox, passwordLabel, passwordField, passwordVisibleField,
                 showPasswordCheckBox,showPassword,okBtn,cancelBtn);
        return this.stage;
    }
}
