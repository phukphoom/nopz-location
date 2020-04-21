package Models.Screen;

import Models.Calculation.KNearest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Models.Blueprint.Location;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class KNearestMapDrawer extends MapDrawer {

    // Data Fields
    private double radius = 0.0f;
    private Stage kSelectorStage = new Stage();
    private ChoiceBox choiceBox = new ChoiceBox();
    private Circle radiusOfInterested = new Circle(radius / this.getRATIO());

    // Constructor
    public KNearestMapDrawer(double MAP_HEIGHT, double MAP_WIDTH, double RATIO, double user_x, double user_y) throws Exception {
        super(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y);

        kSelectorStage.setTitle("KSelector");
        kSelectorStage.setWidth(300);
        kSelectorStage.setHeight(120);
        kSelectorStage.setResizable(false);
        kSelectorStage.setAlwaysOnTop(true);
        kSelectorStage.centerOnScreen();
        try{
            kSelectorStage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
        }
        catch (FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        }

        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(10);
        mainContainer.setPadding(new Insets(10,10,10,10));

        HBox inputContainer = new HBox();
        inputContainer.setSpacing(10);
        inputContainer.setAlignment(Pos.CENTER);

        // Label
        Label label = new Label("กรุณาเลือกจำนวนร้านที่ต้องการทราบ");
        try {
            label.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(kSelectorStage.getWidth());
        label.setPrefHeight(kSelectorStage.getHeight()/2);
        label.setStyle("-fx-text-fill: #008887");

        // choiceBox
        ArrayList<String> choices = new ArrayList<>();
        for (int i = 1; i <= this.getLocs().size(); i++) {
            choices.add(""+i);
        }
        choiceBox.getItems().addAll(choices);
        choiceBox.setPrefWidth(kSelectorStage.getWidth()/4);
        choiceBox.setPrefHeight(kSelectorStage.getHeight()/2);
        choiceBox.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
        if(!choices.isEmpty()){
            choiceBox.setValue(choiceBox.getItems().get(0));
            kSelectorStage.show();
        }

        // okBtn
        Button okBtn = new Button("ตกลง");
        try {
            okBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),18));
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        okBtn.setPrefWidth(kSelectorStage.getWidth()/4);
        okBtn.setPrefHeight(kSelectorStage.getHeight()/2);
        okBtn.setStyle("-fx-background-color:#00BECF; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");

        inputContainer.getChildren().addAll(choiceBox, okBtn);
        mainContainer.getChildren().addAll(label,inputContainer);
        kSelectorStage.setScene(new Scene(mainContainer));


        // All Event Handler
        choiceBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                choiceBox.setStyle("-fx-background-color:#3bab7c; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
            }
        });
        choiceBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                choiceBox.setStyle("-fx-background-color:#56c596; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
            }
        });

        okBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                okBtn.setStyle("-fx-background-color:#00A4B3; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
            }
        });
        okBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                okBtn.setStyle("-fx-background-color:#00BECF; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
            }
        });
        okBtn.setOnAction(e->{
            if(Integer.parseInt((String) choiceBox.getValue()) > 0) {
                radius = KNearest.kNearest(Integer.parseInt((String) choiceBox.getValue()), this.getLocs(), new Location(this.getUser_x(), this.getUser_y(), "user")) + 100;
                radiusOfInterested.setRadius(radius / this.getRATIO());
            }
        });

        kSelectorStage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                getStage().close();
            }
        });
        this.getStage().setOnHidden(event->{
            kSelectorStage.close();
        });
    }

    // Method
    @Override
    public Parent getDrawScene() throws Exception {
        Pane mapPane = (Pane) super.getDrawScene();

        radiusOfInterested.setCenterX(this.getMAP_WIDTH() / 2);
        radiusOfInterested.setCenterY(this.getMAP_HEIGHT() / 2);
        radiusOfInterested.setOpacity(0.3);
        radiusOfInterested.setFill(Color.GREEN);
        radiusOfInterested.setRadius(radius / this.getRATIO());

        mapPane.getChildren().add(radiusOfInterested);

        return mapPane;
    }
}
