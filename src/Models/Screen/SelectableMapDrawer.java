package Models.Screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectableMapDrawer extends MapDrawer {

    // Data Fields
    private double pickX, pickY;
    private boolean isPicked = false;
    private Stage confirmPickStage = new Stage();

    // Constructor
    public SelectableMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws IOException {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);
        confirmPickStage.setTitle("Confirm");
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
            VBox confirmPickContainer = new VBox();
            confirmPickContainer.setAlignment(Pos.CENTER);
            confirmPickContainer.setSpacing(10);
            confirmPickContainer.setPadding(new Insets(10,10,10,10));
            HBox btnContainer = new HBox();
            btnContainer.setSpacing(10);

            Label confirmLabel = new Label("ยืนยันการเลือก ?");
            confirmLabel.setAlignment(Pos.CENTER);
            confirmLabel.setPrefWidth(confirmPickStage.getWidth());
            confirmLabel.setPrefHeight(confirmPickStage.getHeight()/2);

            Button okPick = new Button("ยืนยัน");
            okPick.setPrefWidth(confirmPickStage.getWidth()/2);
            okPick.setPrefHeight(confirmPickStage.getHeight()/2);

            Button cancelPick = new Button("ยกเลิก");
            cancelPick.setPrefWidth(confirmPickStage.getWidth()/2);
            cancelPick.setPrefHeight(confirmPickStage.getHeight()/2);

            btnContainer.getChildren().addAll(okPick, cancelPick);
            confirmPickContainer.getChildren().addAll(confirmLabel, btnContainer);

            confirmPickStage.setScene(new Scene(confirmPickContainer));
            confirmPickStage.show();
            confirmPickStage.setOnShowing(e->{
                sc.setDisable(true);
            });
            confirmPickStage.setOnHidden(e->{
                sc.setDisable(false);
            });
            okPick.setOnAction(event -> {
                pickX = eventClick.getX();
                pickY = eventClick.getY();
                pickX = (pickX - this.getMAP_WIDTH()/2) * this.getRATIO() + this.getUser_x();
                pickY = (pickY - this.getMAP_HEIGHT()/2) * this.getRATIO() + this.getUser_y();

                if(pickX < -56130.f || pickY < -37240 || pickX > 56075 || pickY > 37280) {
                    confirmPickStage.close();
                    Alert alertBox = new Alert(Alert.AlertType.WARNING, "ไม่สามารถเลือกจุดที่อยู่นอกแผนที่ได้!");
                    alertBox.showAndWait();
                    return;
                }

                isPicked = true;
                confirmPickStage.close();
                this.getStage().close();
            });
            cancelPick.setOnAction(event -> {
                isPicked = false;
                confirmPickStage.close();
            });
        });

        return sc;
    }
}
