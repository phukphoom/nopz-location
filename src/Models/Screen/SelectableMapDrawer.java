package Models.Screen;

import Models.Utilities.FileWorker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Models.sample.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class SelectableMapDrawer extends MapDrawer {
    private double pickX, pickY;

    public SelectableMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws IOException {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);
    }

    @Override
    public Parent getDrawScene() throws Exception {
        Parent sc = super.getDrawScene();
        sc.setOnMouseClicked(e->{
            Stage confirmPickStage = new Stage();
            Button okPick = new Button("ยืนยัน");
            Button cancelPick = new Button("ยกเลิก");
            VBox confirmPickScene = new VBox();
            HBox btnContainer = new HBox();
            btnContainer.getChildren().addAll(okPick, cancelPick);
            Label detailDelete = new Label("ยืนยันการเลือก?");
            confirmPickScene.getChildren().addAll(detailDelete, btnContainer);
            confirmPickStage.setScene(new Scene(confirmPickScene));
            confirmPickStage.show();
            okPick.setOnAction(ei -> {
                pickX = e.getX();
                pickY = e.getY();
                pickX = (pickX - this.getMAP_WIDTH()/2) * this.getRATIO() + this.getUser_x();
                pickY = (pickY - this.getMAP_HEIGHT()/2) * this.getRATIO() + this.getUser_y();
                confirmPickStage.close();
                this.getStage().close();
            });
            cancelPick.setOnAction(ei -> {
                confirmPickStage.close();
            });
        });
        return sc;
    }

    public void setPickX(double pickX) {
        this.pickX = pickX;
    }

    public void setPickY(double pickY) {
        this.pickY = pickY;
    }
}
