package Models.Screen;

import Models.Sample.Location;
import Models.Utilities.FileWorker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class LocationManagement {

    //Data Fields
    private ArrayList<Location> locationList;
    private Stage locationListStage = new Stage();
    double pickX, pickY;


    // Constructor
    public LocationManagement() throws IOException {
        this.locationListStage.setWidth(400);
        this.locationListStage.setHeight(500);
        this.locationListStage.setTitle("NOPZ Location  |  Shop Management");
        this.locationListStage.setResizable(false);

        renderStage();
    }

    // Setter
    public void setStage(Stage stage) {
        this.locationListStage = stage;
    }

    // Getter
    public Stage getStage() {
        return locationListStage;
    }

    // Method
    private Node getLocationListScroll() throws IOException {
        locationList = FileWorker.readLocationListFromFile();

        ScrollPane locationListScroll = new ScrollPane();
        locationListScroll.setPrefHeight(450);
        locationListScroll.setPrefWidth(400);
        VBox locatioListVbox = new VBox();

        for (int i = 0; i < locationList.size() ; i++) {
            int currentIndex = i;
            Location location = locationList.get(i);
            Label detailLocationLabel = new Label("ร้าน  "+ location.getName() + "  " + "อยู่ที่ตำแหน่ง" + " ( " + location.getX() + " , " + location.getY() + " ) ");
            Label deleteLocationLabel = new Label("ลบ");
            deleteLocationLabel.setTextFill(Color.RED);
            deleteLocationLabel.setPadding(new Insets(5));
            deleteLocationLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    deleteLocationLabel.setUnderline(true);
                }
            });
            deleteLocationLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    deleteLocationLabel.setUnderline(false);
                }
            });
            deleteLocationLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Stage confirmDeleteStage = new Stage();
                    confirmDeleteStage.setTitle("Confirm");
                    confirmDeleteStage.setResizable(false);
                    confirmDeleteStage.setAlwaysOnTop(true);
                    confirmDeleteStage.setHeight(120);
                    confirmDeleteStage.setWidth(250);

                    VBox confirmDeleteContainer = new VBox();
                    confirmDeleteContainer.setAlignment(Pos.CENTER);
                    confirmDeleteContainer.setSpacing(10);
                    confirmDeleteContainer.setPadding(new Insets(10,10,10,10));
                    HBox btnContainer = new HBox();
                    btnContainer.setSpacing(10);

                    Label confirmLabel = new Label("ยืนยันการลบร้านค้า?");
                    confirmLabel.setAlignment(Pos.CENTER);
                    confirmLabel.setPrefWidth(confirmDeleteStage.getWidth());
                    confirmLabel.setPrefHeight(confirmDeleteStage.getHeight()/2);

                    Button okDelete = new Button("ยืนยัน");
                    okDelete.setPrefWidth(confirmDeleteStage.getWidth()/2);
                    okDelete.setPrefHeight(confirmDeleteStage.getHeight()/2);

                    Button cancelDelete = new Button("ยกเลิก");
                    cancelDelete.setPrefWidth(confirmDeleteStage.getWidth()/2);
                    cancelDelete.setPrefHeight(confirmDeleteStage.getHeight()/2);

                    btnContainer.getChildren().addAll(okDelete, cancelDelete);
                    confirmDeleteContainer.getChildren().addAll(confirmLabel, btnContainer);

                    confirmDeleteStage.setScene(new Scene(confirmDeleteContainer));
                    confirmDeleteStage.show();

                    okDelete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                FileWorker.deleteLocationInListByIndex(currentIndex);
                                confirmDeleteStage.close();
                                renderStage();
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    });
                    cancelDelete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            confirmDeleteStage.close();
                        }
                    });
                }
            });

            HBox locationGroup = new HBox();
            locationGroup.setPadding(new Insets(20));
            locationGroup.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            locationGroup.getChildren().addAll(detailLocationLabel, deleteLocationLabel);
            locatioListVbox.getChildren().add(locationGroup);
        }
        locationListScroll.setContent(locatioListVbox);
        return locationListScroll;
    }
    public void renderStage() throws IOException {
        VBox mainContainer = new VBox();
        mainContainer.setPadding(new Insets(10,10,10,10));
        HBox btnContainer = new HBox();
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.setSpacing(10);
        btnContainer.setPadding(new Insets(10,10,10,10));

        Node locationListScroll = this.getLocationListScroll();

        Button addLocationBtn = new Button("เพิ่มร้านค้า");
        addLocationBtn.setAlignment(Pos.CENTER);
        addLocationBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage addLocationStage = new Stage();

                VBox mainContainer = new VBox();
                mainContainer.setPadding(new Insets(5));

                HBox btnContainer = new HBox();
                btnContainer.setAlignment(Pos.CENTER);

                FormField locationName = new FormField("ชื่อร้าน", 100, false);
                Button pickLocation = new Button("เลือกที่อยู่ร้าน");
                pickLocation.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try{
                            double MAP_HEIGHT = 750.f, MAP_WIDTH = 1125.f;
                            double RATIO = 10.0f;
                            Location user = FileWorker.readUserLocationFromFile();
                            SelectableMapDrawer mapDrawer = new SelectableMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, user.getX(), user.getY());
                            Stage editStage = mapDrawer.getMapStage();
                            editStage.show();

                            editStage.setOnHidden(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent windowEvent) {
                                    try{
                                        if(mapDrawer.isPicked()){
                                            pickX = mapDrawer.getPickX();
                                            pickY = mapDrawer.getPickY();
                                        }
                                    }
                                    catch (Exception exception){
                                        System.out.println(exception);
                                    }
                                }
                            });
                        }
                        catch(Exception exception){
                            System.out.println(exception);
                        }
                    }
                });

                Button okBtn = new Button("ตกลง");
                okBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String name = locationName.getEnteredText();

                        try {
                            FileWorker.writeLocationInListToFile(pickX, pickY, name);
                            addLocationStage.close();
                            renderStage();
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });

                Button cancelBtn = new Button("ปิด");
                cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        addLocationStage.close();
                    }
                });

                btnContainer.getChildren().addAll(okBtn, cancelBtn);
                mainContainer.getChildren().addAll(locationName.getNode(), pickLocation,btnContainer);

                addLocationStage.setScene(new Scene(mainContainer));
                addLocationStage.show();
            }
        });

        Button cancelBtn = new Button("ออก");
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                locationListStage.close();
            }
        });

        btnContainer.getChildren().addAll(addLocationBtn, cancelBtn);
        mainContainer.getChildren().addAll(locationListScroll, btnContainer);

        Scene scene = new Scene(mainContainer);
        this.locationListStage.setScene(scene);
        this.locationListStage.show();
    }
}
