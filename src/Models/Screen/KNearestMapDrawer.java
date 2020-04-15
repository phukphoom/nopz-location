package Models.Screen;

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
import Models.sample.Location;

import java.io.IOException;
import java.util.ArrayList;

public class KNearestMapDrawer extends MapDrawer {
    private double radius = 0.0f;
    private ChoiceBox cb = new ChoiceBox();
    private Stage kSelector = new Stage();
    private int currentK = 1;

    public KNearestMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws IOException {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);
        ArrayList<String> choices = new ArrayList<>();
        for (int i = 1; i <= this.getLocs().size(); i++) {
            choices.add("" + i);
        }
        cb.getItems().addAll(choices);
    }

    @Override
    public Parent getDrawScene() throws Exception {
        Button okBtn = new Button("ตกลง");
        VBox container = new VBox();
        Label label = new Label("กรุณาเลือกจำนวนร้านที่ต้องการทราบ");
        Pane mapPane = (Pane) super.getDrawScene();
        Circle radiusOfInterested = new Circle(radius / this.getRATIO());
        radiusOfInterested.setCenterX(this.getMAP_WIDTH() / 2);
        radiusOfInterested.setCenterY(this.getMAP_HEIGHT() / 2);
        radiusOfInterested.setOpacity(0.3);
        radiusOfInterested.setFill(Color.GREEN);
        container.getChildren().addAll(label, cb, okBtn);
        kSelector.setAlwaysOnTop(true);
        kSelector.setScene(new Scene(container));
        kSelector.show();
        okBtn.setOnAction(e->{
            if(Integer.parseInt((String) cb.getValue()) > 0) {
                radius = KNearest.kNearest(Integer.parseInt((String) cb.getValue()), this.getLocs(), new Location(this.getUser_x(), this.getUser_y(), "user")) + 100;
                radiusOfInterested.setRadius(radius / this.getRATIO());
            }
        });
        mapPane.getChildren().add(radiusOfInterested);
        return mapPane;
    }
}
