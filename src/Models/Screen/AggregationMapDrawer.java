package Models.Screen;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import Models.Sample.Location;
import Models.Sample.Aggregation;

import java.util.ArrayList;

public class AggregationMapDrawer extends MapDrawer {

    // Data Fields
    ArrayList<Location> tspSolved;

    // Constructor
    public AggregationMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws Exception {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);
        tspSolved = Aggregation.findShortestTravelingRoute(new Location(getUser_x(), getUser_y(), "user"), this.getLocs());
    }

    // Setter
    @Override
    public void setLocs(ArrayList<Location> locs) throws Exception {
        super.setLocs(locs);
    }

    // Method
    @Override
    public Parent getDrawScene() throws Exception {
        Pane mapPane = (Pane) super.getDrawScene();
        for (int i = 0; i < tspSolved.size() - 1; i++) {
            double relXi = this.relUser(tspSolved.get(i).getX(), 'x');
            double relYi = this.relUser(tspSolved.get(i).getY(), 'y');
            double relX2 = this.relUser(tspSolved.get(i + 1).getX(), 'x');
            double relY2 = this.relUser(tspSolved.get(i + 1).getY(), 'y');

            Line line = new Line((getMAP_WIDTH() / 2) - (relXi / getRATIO()), (getMAP_HEIGHT() / 2) - (relYi / getRATIO()),
                    (getMAP_WIDTH() / 2) - (relX2 / getRATIO()), (getMAP_HEIGHT() / 2) - (relY2 / getRATIO()));
            line.setFill(Color.GREEN);
            line.setStroke(Color.GREEN);
            line.setOpacity(0.5);
            mapPane.getChildren().add(line);
        }
        return mapPane;
    }
}