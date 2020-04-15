//// Java Program to create a canvas with specified
//// width and height(as arguments of constructor),
//// add it to the stage and also add a circle and
//// rectangle on it
//package Models.sample;
//
//import Models.Screen.AggregationMapDrawer;
//import Models.Screen.ApproximateMapDrawer;
//import Models.Screen.MinMaxMapDrawer;
//import Models.Utilities.FileWorker;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.*;
//import javafx.stage.Stage;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.paint.Color;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class Main extends Application {
//    private double user_x = 1305.f, user_y = 902.f;
//    private double MAP_HEIGHT = 750.f, MAP_WIDTH = 1125.f;
//    private double RATIO = 10.0f;
//    private ArrayList<Location> locs = FileWorker.readLocationListFromFile();
//
//    public Main() throws IOException {
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        LocationManagement locManScreen = new LocationManagement();
//        AggregationMapDrawer md = new AggregationMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y, locs);
//        ApproximateMapDrawer amd = new ApproximateMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y, locs);
//        MinMaxMapDrawer mmmd = new MinMaxMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, user_x, user_y, locs);
//        VBox mainPane = new VBox();
//        mainPane.setPadding(new Insets(10));
//        mainPane.setAlignment(Pos.TOP_CENTER);
//        mainPane.setSpacing(10);
//
//        ImageView logo = new ImageView(new Image(new FileInputStream("res/img/kmitl_logo.png")));
//        logo.setFitHeight(150);
//        logo.setFitWidth(300);
//
//        // Position Group
//        HBox positionGroup = new HBox();
//        positionGroup.setAlignment(Pos.CENTER);
//        Label currentXY = new Label("พิกัดปัจจุบัน (" + (int) user_x + ", " + (int) user_y + ")");
//        Label editPosition = new Label("แก้ไข");
//        editPosition.setTextFill(Color.BLUE);
//        editPosition.setPadding(new Insets(3));
//        // Changing position screen
//        // TODO: Add handling Error
//        editPosition.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Stage editPositionStage = new Stage();
//                VBox editPositionBox = new VBox();
//                editPositionBox.setPadding(new Insets(5));
//                FormField xAxis = new FormField("พิกัดแนวขวาง", 10, true);
//                FormField yAxis = new FormField("พิกัดแนวตั้ง", 10, true);
//                HBox btnContainer = new HBox();
//                Button okBtn = new Button("ตกลง");
//                okBtn.setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent actionEvent) {
//                        user_x =  Double.parseDouble(xAxis.getEnteredText());
//                        user_y =  Double.parseDouble(yAxis.getEnteredText());
//                        currentXY.setText("พิกัดปัจจุบัน (" + (int) user_x + ", " + (int) user_y + ")");
//                        md.setUser_x(user_x);
//                        md.setUser_y(user_y);
//                        editPositionStage.close();
//                    }
//                });
//                Button cancelBtn = new Button("ยกเลิก");
//                cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent actionEvent) {
//                        editPositionStage.close();
//                    }
//                });
//                btnContainer.getChildren().addAll(okBtn, cancelBtn);
//                btnContainer.setAlignment(Pos.CENTER);
//                editPositionBox.getChildren().addAll(xAxis.getNode(), yAxis.getNode(), btnContainer);
//                editPositionStage.setScene(new Scene(editPositionBox));
//                editPositionStage.show();
//            }
//        });
//        editPosition.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                editPosition.setUnderline(true);
//            }
//        });
//        editPosition.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                editPosition.setUnderline(false);
//            }
//        });
//        positionGroup.getChildren().addAll(currentXY, editPosition);
//
//
//        GridPane btnGroup = new GridPane();
//        Button mode1 = new Button("Mode 1");
//        mode1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                try {
//                    md.getMapStage().show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Button mode2 = new Button("Mode 2");
//        Button mode3 = new Button("Mode 3");
//        mode3.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                try {
//                    amd.getMapStage().show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Button mode4 = new Button("Mode 4");
//        mode4.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                try {
//                    mmmd.getMapStage().show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Button manageBtn = new Button("จัดการร้านค้า");
//        manageBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                locManScreen.getStage().show();
//            }
//        });
//        Button exitBtn = new Button("ออก");
//        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Platform.exit();
//                System.exit(0);
//            }
//        });
//        btnGroup.setHgap(10);
//        btnGroup.setVgap(10);
//        btnGroup.setAlignment(Pos.CENTER);
//
//        btnGroup.add(mode1, 0, 0);
//        btnGroup.add(mode2, 1, 0);
//        btnGroup.add(mode3, 0,1);
//        btnGroup.add(mode4, 1, 1);
//        btnGroup.add(manageBtn, 0,2);
//        btnGroup.add(exitBtn, 1, 2);
//
//        mainPane.getChildren().addAll(logo, positionGroup, btnGroup);
//
//        Scene sc = new Scene(mainPane);
//        stage.setScene(sc);
//        stage.show();
//    }
//
//    // Main Method
//    public static void main(String args[]) throws IOException {
//        // launch the application
//        launch(args);
//    }
//}
