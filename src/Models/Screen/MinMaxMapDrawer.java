package Models.Screen;

import Models.Calculation.MinMax;
import Models.Utilities.FileWorker;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import Models.Blueprint.Location;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.IOException;

public class MinMaxMapDrawer extends MapDrawer{

    // Data Fields
    private Location minLocation;
    private Location maxLocation;

    // Constructor
    public MinMaxMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws Exception {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);
        minLocation = MinMax.min();
        maxLocation = MinMax.max();
        Location userLoc = FileWorker.readUserLocationFromFile();
        setUser_x(userLoc.getX()); setUser_y(userLoc.getY());
    }

    // Method
    @Override
    public Parent getDrawScene() throws Exception {
        Pane mapPane = (Pane) super.getDrawScene();
        double relXmax = this.relUser(maxLocation.getX(), 'x');
        double relYmax = this.relUser(maxLocation.getY(), 'y');
        double relXmin = this.relUser(minLocation.getX(), 'x');
        double relYmin = this.relUser(minLocation.getY(), 'y');

        Line maxLine = new Line((getMAP_WIDTH() / 2) - (relXmax / getRATIO()), (getMAP_HEIGHT() / 2) - (relYmax / getRATIO()),
                getMAP_WIDTH() / 2, getMAP_HEIGHT() / 2);
        maxLine.setFill(Color.ORANGE);
        maxLine.setStroke(Color.ORANGE);
        maxLine.setStrokeWidth(2.0);
        maxLine.setOpacity(0.5);
        maxLine.setStrokeWidth(4.f);
        mapPane.getChildren().add(maxLine);

        Label maxLabel = new Label("ระยะร้านที่ไกลที่สุด: " + (int) Math.sqrt(relXmax * relXmax + relYmax + relYmax) + " หน่วย");
        maxLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),18));
        maxLabel.setTextFill(Color.ORANGE);
        maxLabel.setTranslateX(10);
        maxLabel.setTranslateY(30);
        mapPane.getChildren().add(maxLabel);

        Line minLine = new Line((getMAP_WIDTH() / 2) - (relXmin / getRATIO()), (getMAP_HEIGHT() / 2) - (relYmin / getRATIO()),
                (getMAP_WIDTH() / 2), (getMAP_HEIGHT() / 2));
        minLine.setFill(Color.GREEN);
        minLine.setStroke(Color.GREEN);
        minLine.setStrokeWidth(2.0);
        minLine.setOpacity(0.5);
        minLine.setStrokeWidth(4.f);
        mapPane.getChildren().add(minLine);

        Label minLabel = new Label("ระยะร้านที่ใกล้ที่สุด: " + (int) Math.sqrt(relXmin * relXmin + relYmin + relYmin) + " หน่วย");
        minLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),18));
        minLabel.setTextFill(Color.GREEN);
        minLabel.setTranslateX(10);
        minLabel.setTranslateY(50);
        mapPane.getChildren().add(minLabel);

        return mapPane;
    }
}
