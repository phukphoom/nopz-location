package Models.Screen;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import Models.sample.Location;

import java.io.IOException;
import java.util.ArrayList;

public class ApproximateMapDrawer extends MapDrawer {
    private double radius;

    public ApproximateMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws IOException {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);
        radius = KNearest.approximate(this.getLocs(), new Location(this.getUser_x(), this.getUser_y(), "User"));
    }

    @Override
    public Parent getDrawScene() throws Exception {
        Pane mapPane = (Pane) super.getDrawScene();
        Circle radiusOfInterested = new Circle(radius / this.getRATIO());
        radiusOfInterested.setCenterX(this.getMAP_WIDTH() / 2);
        radiusOfInterested.setCenterY(this.getMAP_HEIGHT() / 2);
        radiusOfInterested.setOpacity(0.3);
        radiusOfInterested.setFill(Color.GREEN);
        mapPane.getChildren().add(radiusOfInterested);
        return mapPane;
    }
}