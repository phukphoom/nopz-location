package Models.Screen;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SelectableMapDrawer extends MapDrawer {

    // Data Fields
    private double pickX, pickY;
    private boolean isPicked = false;
    private Stage confirmPickStage = new Stage();

    // Constructor
    public SelectableMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws IOException {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);
        confirmPickStage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
        confirmPickStage.setTitle("Confirmation");
        confirmPickStage.setResizable(false);
        confirmPickStage.setAlwaysOnTop(true);
        confirmPickStage.setHeight(120);
        confirmPickStage.setWidth(250);
    }

    // Setter
    public void setPickX(double pickX) {
        this.pickX = pickX;
    }
    public void setPickY(double pickY) {
        this.pickY = pickY;
    }

    // Getter
    public double getPickX(){
        return this.pickX;
    }
    public double getPickY(){
        return this.pickY;
    }
    public boolean isPicked(){
        return this.isPicked;
    }
    public Stage getConfirmPickStage(){
        return this.confirmPickStage;
    }

    // Method
    @Override
    public Parent getDrawScene() throws Exception {
        Parent sc = super.getDrawScene();
        sc.setOnMouseClicked(eventClick->{
            // re-window when click on new position
            if(confirmPickStage.isShowing()){
                confirmPickStage.close();
            }
            confirmPickStage.show();

            VBox confirmPickContainer = new VBox();
            confirmPickContainer.setAlignment(Pos.CENTER);
            confirmPickContainer.setSpacing(10);
            confirmPickContainer.setPadding(new Insets(10,10,10,10));

            HBox btnContainer = new HBox();
            btnContainer.setSpacing(10);

            Label confirmLabel = new Label("ยืนยันที่จะเลือกตำเเหน่งนี้ ?");
            try {
                confirmLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            confirmLabel.setAlignment(Pos.CENTER);
            confirmLabel.setPrefWidth(confirmPickStage.getWidth());
            confirmLabel.setPrefHeight(confirmPickStage.getHeight()/2);
            confirmLabel.setStyle("-fx-text-fill: #007467 ");

            Button okPick = new Button("ยืนยัน");
            try {
                okPick.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            okPick.setPrefWidth(confirmPickStage.getWidth()/2);
            okPick.setPrefHeight(confirmPickStage.getHeight()/2);
            okPick.setStyle("-fx-background-color:#56c596; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");

            Button cancelPick = new Button("ยกเลิก");
            try {
                cancelPick.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            cancelPick.setPrefWidth(confirmPickStage.getWidth()/2);
            cancelPick.setPrefHeight(confirmPickStage.getHeight()/2);
            cancelPick.setStyle("-fx-background-color:#DB3535; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");

            btnContainer.getChildren().addAll(okPick, cancelPick);
            confirmPickContainer.getChildren().addAll(confirmLabel, btnContainer);
            confirmPickStage.setScene(new Scene(confirmPickContainer));
            confirmPickStage.show();

            // All Event Handler
            okPick.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    okPick.setStyle("-fx-background-color:#3bab7c; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
                }
            });
            okPick.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    okPick.setStyle("-fx-background-color:#56c596; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
                }
            });
            okPick.setOnAction(event -> {
                pickX = eventClick.getX();
                pickY = eventClick.getY();
                pickX = (pickX - this.getMAP_WIDTH()/2) * this.getRATIO() + this.getUser_x();
                pickY = (pickY - this.getMAP_HEIGHT()/2) * this.getRATIO() + this.getUser_y();

                if(pickX < -56130.f || pickY < -37240 || pickX > 56075 || pickY > 37280) {
                    confirmPickStage.close();
                    Alert alertBox = new Alert(Alert.AlertType.WARNING, "ไม่สามารถเลือกจุดที่อยู่นอกแผนที่ได้ !");
                    alertBox.setHeaderText(null);
                    Stage alertstage = (Stage)alertBox.getDialogPane().getScene().getWindow();
                    try{
                        alertstage.getIcons().add(new Image(new FileInputStream((new File("src/Views/resource/Image/warning.png")))));
                        ImageView alertImage = new ImageView(new Image( new FileInputStream(new File("src/Views/resource/Image/warning.png"))));
                        alertImage.setFitWidth(50);
                        alertImage.setFitHeight(50);
                        alertBox.setGraphic(alertImage);
                    }
                    catch (FileNotFoundException fileNotFoundException){
                    }
                    alertBox.showAndWait();

                    return;
                }

                isPicked = true;
                confirmPickStage.close();
                this.getStage().close();
            });

            cancelPick.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    cancelPick.setStyle("-fx-background-color:#c32222; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
                }
            });
            cancelPick.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    cancelPick.setStyle("-fx-background-color:#DB3535; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
                }
            });
            cancelPick.setOnAction(event -> {
                isPicked = false;
                confirmPickStage.close();
            });
        });

        return sc;
    }
}
