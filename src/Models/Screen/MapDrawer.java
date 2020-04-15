package Models.Screen;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Models.Utilities.FileWorker;
import Models.sample.Location;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MapDrawer {
    private double MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y;
    private ArrayList<Location> locs = FileWorker.readFileToLocations();

    public MapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y, ArrayList<Location> locs) throws IOException {
        this.MAP_HEIGHT = MAP_HEIGHT;
        this.MAP_WIDTH = MAP_WIDTH;
        this.RATIO = RATIO;
        this.user_x = user_x;
        this.user_y = user_y;
//        this.locs = locs;
    }

    public Parent getDrawScene() throws Exception {
        Pane mapPane = new Pane();
        ImageView mapImage = new ImageView();
        mapImage.setImage(new Image(new FileInputStream("res/img/Map-1-1.jpg"), MAP_WIDTH, MAP_HEIGHT, true, false));
        mapImage.setScaleX(100/this.RATIO);
        mapImage.setScaleY(100/this.RATIO);
        mapImage.maxHeight(MAP_HEIGHT);
        mapImage.maxWidth(MAP_WIDTH);
        mapImage.setFitHeight(MAP_HEIGHT);
        mapImage.setFitWidth(MAP_WIDTH);

        mapPane.getChildren().add(mapImage);

        double relX, relY;
        for (Location l : this.locs) {
            relX = this.relUser(l.getX(), 'x');
            relY = this.relUser(l.getY(), 'y');

            ImageView locMarker = new ImageView();
            locMarker.setImage(new Image(new FileInputStream("res/img/location_marker.png")));
            locMarker.setX((this.MAP_WIDTH / 2) - (relX / this.RATIO));
            locMarker.setY((this.MAP_HEIGHT / 2) - (relY / this.RATIO));
            locMarker.setFitWidth(10);
            locMarker.setFitHeight(15);

            Text txt = new Text();
            txt.setText(l.getName() + "\n(" + l.getX() + ", " + l.getY() + ")");
            txt.setFill(Color.RED);
            txt.setX((this.MAP_WIDTH / 2) - (relX / this.RATIO + 20));
            txt.setY((this.MAP_HEIGHT / 2) - (relY / this.RATIO + 20));

            mapPane.getChildren().addAll(locMarker, txt);
        }

        Circle userMarker = new Circle();
        userMarker.setRadius(3.0f);
        userMarker.setCenterX(MAP_WIDTH / 2);
        userMarker.setCenterY(MAP_HEIGHT / 2);
        mapPane.getChildren().add(userMarker);

        return mapPane;
    }

    public Stage getMapStage() throws Exception {
        BorderPane bp = new BorderPane();
        bp.setCenter(this.getDrawScene());

        Scene sc = new Scene(bp, MAP_WIDTH, MAP_HEIGHT);
        sc.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                double deltaY = scrollEvent.getDeltaY();
                if(deltaY != 0) {
                    RATIO = Math.abs(RATIO + 0.05 * deltaY);
                    if(RATIO < 10) RATIO = 10;
                    if(RATIO > 100) RATIO = 100;
                    setLocs(locs);
                    setMAP_HEIGHT(MAP_HEIGHT);
                    setMAP_WIDTH(MAP_WIDTH);
                    setRATIO(RATIO);
                    setUser_x(user_x);
                    setUser_y(user_y);

                    try {
                        Node mapSc = getDrawScene();
                        bp.setCenter(mapSc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }});
        Stage stage = new Stage();
        stage.setScene(sc);
        return stage;
    }

    public double relUser(double pos, char axis) {
        if (axis == 'x') {
            return user_x - pos;
        } else if (axis == 'y') {
            return user_y - pos;
        }
        return 0.0f;
    }

    public double getMAP_HEIGHT() {
        return MAP_HEIGHT;
    }

    public void setMAP_HEIGHT(double MAP_HEIGHT) {
        this.MAP_HEIGHT = MAP_HEIGHT;
    }

    public double getMAP_WIDTH() {
        return MAP_WIDTH;
    }

    public void setMAP_WIDTH(double MAP_WIDTH) {
        this.MAP_WIDTH = MAP_WIDTH;
    }

    public double getRATIO() {
        return RATIO;
    }

    public void setRATIO(double RATIO) {
        this.RATIO = RATIO;
    }

    public double getUser_x() {
        return user_x;
    }

    public void setUser_x(double user_x) {
        this.user_x = user_x;
    }

    public double getUser_y() {
        return user_y;
    }

    public void setUser_y(double user_y) {
        this.user_y = user_y;
    }

    public ArrayList<Location> getLocs() {
        return locs;
    }

    public void setLocs(ArrayList<Location> locs) {
        this.locs = locs;
    }
}
