package Models.Screen;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Models.Utilities.FileWorker;
import Models.Blueprint.Location;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MapDrawer {

    // Data Fields
    private double MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y;
    private ArrayList<Location> locs = FileWorker.readLocationListFromFile();
    private ImageView mapImage = new ImageView();
    private Stage stage;
    private Scene sc;
    private BorderPane bp = new BorderPane();

    // Constructor
    public MapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws IOException {
        this.sc = new Scene(bp, MAP_WIDTH, MAP_HEIGHT);
        this.MAP_HEIGHT = MAP_HEIGHT;
        this.MAP_WIDTH = MAP_WIDTH;
        this.RATIO = RATIO;
        this.user_x = user_x;
        this.user_y = user_y;
        this.stage = new Stage();
        this.stage.setScene(sc);

        mapImage.setImage(new Image(new FileInputStream("src/Views/resource/Image/Map12000,8000.jpg"), MAP_WIDTH, MAP_HEIGHT, true, false));
        mapImage.setScaleX(100/this.RATIO);
        mapImage.setScaleY(100/this.RATIO);
        mapImage.maxHeight(MAP_HEIGHT);
        mapImage.maxWidth(MAP_WIDTH);
        mapImage.setFitHeight(MAP_HEIGHT);
        mapImage.setFitWidth(MAP_WIDTH);
        mapImage.setX(-user_x/RATIO);
        mapImage.setY(-user_y/RATIO);
    }

    // Setter
    public void setMAP_HEIGHT(double MAP_HEIGHT) {
        this.MAP_HEIGHT = MAP_HEIGHT;
    }
    public void setMAP_WIDTH(double MAP_WIDTH) {
        this.MAP_WIDTH = MAP_WIDTH;
    }
    public void setRATIO(double RATIO) {
        this.RATIO = RATIO;
    }
    public void setUser_x(double user_x) {
        this.user_x = user_x;
    }
    public void setUser_y(double user_y) {
        this.user_y = user_y;
    }
    public void setLocs(ArrayList<Location> locs) throws Exception {
        this.locs = locs;
    }

    // Getter
    public double getMAP_HEIGHT() {
        return MAP_HEIGHT;
    }
    public double getMAP_WIDTH() {
        return MAP_WIDTH;
    }
    public double getRATIO() {
        return RATIO;
    }
    public double getUser_x() {
        return user_x;
    }
    public double getUser_y() {
        return user_y;
    }
    public ArrayList<Location> getLocs() {
        return locs;
    }
    public Stage getStage() {
        return stage;
    }

    // Method
    public double relUser(double pos, char axis) {
        if (axis == 'x') {
            return user_x - pos;
        } else if (axis == 'y') {
            return user_y - pos;
        }
        return 0.0f;
    }
    public Parent getDrawScene() throws Exception {
        Pane mapPane = new Pane();
        Label ratioDisplay = new Label("อัตราส่วน [ 1 : " + (int) this.getRATIO() + " ]");
        ratioDisplay.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        ratioDisplay.setPadding(new Insets(10,10,10,10));
        ratioDisplay.setTextFill(Color.GRAY);

        mapPane.getChildren().add(mapImage);

        double relX, relY;
        for (Location l : this.locs) {
            relX = this.relUser(l.getX(), 'x');
            relY = this.relUser(l.getY(), 'y');

            ImageView locMarker = new ImageView();
            locMarker.setImage(new Image(new FileInputStream("src/Views/resource/Image/location_marker.png")));
            locMarker.setX((this.MAP_WIDTH / 2) - (relX / this.RATIO) - 15 * (75 / (this.RATIO > 70 ? 80 : 150 - this.RATIO)));
            locMarker.setY((this.MAP_HEIGHT / 2) - (relY / this.RATIO) - 40 * (75 / (this.RATIO > 70 ? 80 : 150 - this.RATIO)));
            locMarker.setFitWidth(30 * (75 / (this.RATIO > 70 ? 80 : 150 - this.RATIO)));
            locMarker.setFitHeight(40 * (75 / (this.RATIO > 70 ? 80 : 150 - this.RATIO)));

            Text txt = new Text();
            txt.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
            txt.setText(l.getName() + "\n( " + (int)l.getX() + " , " + (int)l.getY() + " )");
            txt.setFill(Color.RED);
            txt.setX((this.MAP_WIDTH / 2) - (relX / this.RATIO + 20) - (15 * (75 / (this.RATIO > 70 ? 80 : 150 - this.RATIO))) -25);
            txt.setY((this.MAP_HEIGHT / 2) - (relY / this.RATIO + 20) + 30);

            mapPane.getChildren().addAll(locMarker, txt);
        }

        ImageView userMarker = new ImageView();
        userMarker.setImage(new Image(new FileInputStream("src/Views/resource/Image/user-marker.png")));
        userMarker.setFitWidth(40 * (75 / (this.RATIO > 70 ? 80 : 150 - this.RATIO)));
        userMarker.setFitHeight(40 * (75 / (this.RATIO > 70 ? 80 : 150 - this.RATIO)));
        userMarker.setX(MAP_WIDTH / 2 - 20 * (75 / (this.RATIO > 70 ? 80 : 150 - this.RATIO)));
        userMarker.setY(MAP_HEIGHT / 2 - 40 * (75 / (this.RATIO > 70 ? 80 : 150 - this.RATIO)));
        mapPane.getChildren().addAll(userMarker, ratioDisplay);

        return mapPane;
    }
    public Stage getMapStage() throws Exception {
        bp.setCenter(this.getDrawScene());

        sc.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                double deltaY = scrollEvent.getDeltaY();
                if(deltaY != 0) {
                    RATIO = Math.abs(RATIO + 0.05 * -deltaY);
                    if(RATIO < 10) RATIO = 10;
                    if(RATIO > 100) RATIO = 100;
                    updateMap();
                }
            }});

        this.updateMap();
        this.stage.setResizable(false);
        return this.stage;
    }

    protected void updateMap() {
        try {
            setLocs(locs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setMAP_HEIGHT(MAP_HEIGHT);
        setMAP_WIDTH(MAP_WIDTH);
        setRATIO(RATIO);
        setUser_x(user_x);
        setUser_y(user_y);
        mapImage.setScaleX(100/RATIO);
        mapImage.setScaleY(100/RATIO);

        mapImage.setX(-user_x/RATIO);
        mapImage.setY(-user_y/RATIO);

        try {
            Node mapSc = getDrawScene();
            bp.setCenter(mapSc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}