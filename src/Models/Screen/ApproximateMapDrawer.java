package Models.Screen;

import Models.Calculation.Approximate;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import Models.Blueprint.Location;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.IOException;

public class ApproximateMapDrawer extends MapDrawer {

    // Data Fields
    private double radius;

    // Constructor
    public ApproximateMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws Exception {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);
        radius = Approximate.approximate(this.getLocs(), new Location(this.getUser_x(), this.getUser_y(), "User"));
    }

    // Method
    @Override
    public Parent getDrawScene() throws Exception {
        Pane mapPane = (Pane) super.getDrawScene();

        Circle radiusOfInterested = new Circle(radius / this.getRATIO());

        Line radiusLine = new Line(this.getMAP_WIDTH() / 2, this.getMAP_HEIGHT() / 2,
                this.getMAP_WIDTH() / 2 + radius / getRATIO(), this.getMAP_HEIGHT() / 2);
        radiusLine.setStroke(Color.GREENYELLOW);

        Label distanceLabel = new Label("ระยะเฉลี่ย = " + (int) radius + " หน่วย");
        distanceLabel.setTranslateX(this.getMAP_WIDTH() / 2 + (radius / 2) / getRATIO());
        distanceLabel.setTranslateY(this.getMAP_HEIGHT() / 2);
        distanceLabel.setTextFill(Color.DARKGREEN);
        distanceLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));

        radiusOfInterested.setCenterX(this.getMAP_WIDTH() / 2);
        radiusOfInterested.setCenterY(this.getMAP_HEIGHT() / 2);
        radiusOfInterested.setOpacity(0.3);
        radiusOfInterested.setFill(Color.GREEN);
        mapPane.getChildren().addAll(radiusOfInterested, radiusLine, distanceLabel);
        return mapPane;
    }
}