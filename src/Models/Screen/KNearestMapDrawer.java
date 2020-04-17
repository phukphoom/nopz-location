package Models.Screen;

import Models.Sample.KNearest;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import Models.Sample.Location;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class KNearestMapDrawer extends MapDrawer {

    // Data Fields
    private double radius = 0.0f;
    private ChoiceBox choiceBox = new ChoiceBox();
    private Stage kSelectorStage = new Stage();

    // Constructor
    public KNearestMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws IOException {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);

        kSelectorStage.setTitle("KSelector");
        kSelectorStage.setResizable(false);
        kSelectorStage.setAlwaysOnTop(true);
        kSelectorStage.setWidth(250);
        kSelectorStage.setHeight(300);
        kSelectorStage.centerOnScreen();
        kSelectorStage.show();

        ArrayList<String> choices = new ArrayList<>();
        for (int i = 1; i <= this.getLocs().size(); i++) {
            choices.add(""+i);
        }
        choiceBox.getItems().addAll(choices);
        if(!choices.isEmpty()){
            choiceBox.setValue(choiceBox.getItems().get(0));
        }
    }

    // Method
    @Override
    public Parent getDrawScene() throws Exception {
        Pane mapPane = (Pane) super.getDrawScene();

        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(10);
        mainContainer.setPadding(new Insets(10,10,10,10));

        HBox inputContainer = new HBox();
        inputContainer.setSpacing(10);
        inputContainer.setPrefWidth(kSelectorStage.getWidth());
        inputContainer.setPrefHeight(kSelectorStage.getHeight()/3);

        Label label = new Label("กรุณาเลือกจำนวนร้านที่ต้องการทราบ");
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(kSelectorStage.getWidth());
        label.setPrefHeight(kSelectorStage.getHeight()/3);

        Button okBtn = new Button("ตกลง");
        okBtn.setPrefWidth(kSelectorStage.getWidth()/4);
        okBtn.setPrefHeight(kSelectorStage.getHeight()/3);

        choiceBox.setPrefWidth(kSelectorStage.getWidth()/4);
        choiceBox.setPrefHeight(kSelectorStage.getHeight()/2);


        Circle radiusOfInterested = new Circle(radius / this.getRATIO());
        radiusOfInterested.setCenterX(this.getMAP_WIDTH() / 2);
        radiusOfInterested.setCenterY(this.getMAP_HEIGHT() / 2);
        radiusOfInterested.setOpacity(0.3);
        radiusOfInterested.setFill(Color.GREEN);

        inputContainer.getChildren().addAll(choiceBox, okBtn);
        mainContainer.getChildren().addAll(label,inputContainer);

        kSelectorStage.setScene(new Scene(mainContainer));

        okBtn.setOnAction(e->{
            if(Integer.parseInt((String) choiceBox.getValue()) > 0) {
                radius = KNearest.kNearest(Integer.parseInt((String) choiceBox.getValue()), this.getLocs(), new Location(this.getUser_x(), this.getUser_y(), "user")) + 100;
                radiusOfInterested.setRadius(radius / this.getRATIO());
            }
        });
        mapPane.getChildren().add(radiusOfInterested);

        this.getStage().setOnHidden(event->{
            kSelectorStage.close();
        });
        kSelectorStage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                getStage().close();
            }
        });

        return mapPane;
    }
}
