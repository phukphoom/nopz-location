package Models.Screen;

import Models.Sample.KNearest;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import Models.Sample.Location;

import java.io.IOException;
import java.util.ArrayList;

public class KNearestMapDrawer extends MapDrawer {

    // Data Fields
    private double radius = 0.0f;
    private ChoiceBox choiceBox = new ChoiceBox();
    private Stage kSelector = new Stage();

    // Constructor
    public KNearestMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws IOException {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);
        kSelector.setHeight(120);
        kSelector.setWidth(300);

        choiceBox.setPrefWidth(kSelector.getWidth());
        choiceBox.setPrefHeight(kSelector.getHeight()/3);

        ArrayList<String> choices = new ArrayList<>();
        for (int i = 1; i <= this.getLocs().size(); i++) {
            choices.add(""+i);
        }
        choiceBox.getItems().addAll(choices);
    }

    // Method
    @Override
    public Parent getDrawScene() throws Exception {
        Pane mapPane = (Pane) super.getDrawScene();

        VBox container = new VBox();

        Label label = new Label("กรุณาเลือกจำนวนร้านที่ต้องการทราบ");
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(kSelector.getWidth());
        label.setPrefHeight(kSelector.getHeight()/3);

        Button okBtn = new Button("ตกลง");
        okBtn.setPrefWidth(kSelector.getWidth());
        okBtn.setPrefHeight(kSelector.getHeight()/3);

        Circle radiusOfInterested = new Circle(radius / this.getRATIO());
        radiusOfInterested.setCenterX(this.getMAP_WIDTH() / 2);
        radiusOfInterested.setCenterY(this.getMAP_HEIGHT() / 2);
        radiusOfInterested.setOpacity(0.3);
        radiusOfInterested.setFill(Color.GREEN);
        container.getChildren().addAll(label, choiceBox, okBtn);

        kSelector.setScene(new Scene(container));
        kSelector.setAlwaysOnTop(true);
        kSelector.setResizable(false);
        kSelector.setTitle("KSelector");
        kSelector.show();

        okBtn.setOnAction(e->{
            if(Integer.parseInt((String) choiceBox.getValue()) > 0) {
                radius = KNearest.kNearest(Integer.parseInt((String) choiceBox.getValue()), this.getLocs(), new Location(this.getUser_x(), this.getUser_y(), "user")) + 100;
                radiusOfInterested.setRadius(radius / this.getRATIO());
            }
        });
        mapPane.getChildren().add(radiusOfInterested);

        this.getStage().setOnHidden(event->{
            kSelector.close();
        });
        return mapPane;
    }
}
