package Models.Screen;

import Models.Blueprint.Location;
import Models.Utilities.FileWorker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LocationManagement {

    //Data Fields
    private ArrayList<Location> locationList;
    private Stage locationListStage = new Stage();
    private ScrollPane locationListScroll = new ScrollPane();
    private boolean sortBy = false; // false => distance, true => date
    double pickX, pickY;


    // Constructor
    public LocationManagement() throws IOException {
        this.locationListStage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
        this.locationListStage.setTitle("NOPZ Location  |  Shop Management");
        this.locationListStage.setResizable(false);
        this.locationListStage.setWidth(800);
        this.locationListStage.setHeight(500);

        this.locationListScroll.setMaxWidth(765);
        this.locationListScroll.setMaxHeight(400);

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
    private void updateLocationListScroll() throws IOException {
        locationList = FileWorker.readLocationListFromFile();
        if(sortBy) {
            locationList = FileWorker.readUserLocationFromFile().sortByDistanceWith(locationList);
        }

        VBox locationListVbox = new VBox();
        locationListVbox.setPrefHeight(locationListStage.getHeight() - 100);
        locationListVbox.setPrefWidth(locationListStage.getWidth() - 50);
        for (int i = 0; i < locationList.size() ; i++) {
            int currentIndex = i;
            Location location = locationList.get(i);

            Label nameLocationLabel = new Label("  ร้าน [ "+ location.getName() + " ]");
            nameLocationLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
            nameLocationLabel.setPrefWidth(250);
            nameLocationLabel.setPrefHeight(100);
            nameLocationLabel.setAlignment(Pos.TOP_LEFT);
            nameLocationLabel.setStyle("-fx-text-fill: #008887");

            Label positionLabel = new Label("  อยู่ที่ตำแหน่ง" + " ( " + (int)location.getX() + " , " + (int)location.getY() + " )  ");
            positionLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Light ver 1.00.otf"),20));
            positionLabel.setPrefWidth(450);
            positionLabel.setPrefHeight(50);
            positionLabel.setAlignment(Pos.TOP_LEFT);
            positionLabel.setStyle("-fx-text-fill: #007467 ");

            Label distantLabel = new Label("  ระยะห่าง = " + (int) location.distanceWith(FileWorker.readUserLocationFromFile()) + " หน่วย");
            distantLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Light ver 1.00.otf"),20));
            distantLabel.setPrefWidth(450);
            distantLabel.setPrefHeight(50);
            distantLabel.setAlignment(Pos.TOP_LEFT);
            distantLabel.setStyle("-fx-text-fill: #007467 ");

            Label deleteLocationLabel = new Label("ลบ");
            deleteLocationLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
            deleteLocationLabel.setPrefWidth(50);
            deleteLocationLabel.setPrefHeight(100);
            deleteLocationLabel.setAlignment(Pos.TOP_LEFT);
            deleteLocationLabel.setStyle("-fx-text-fill: #FF5733 ");

            HBox locationGroup = new HBox();
            locationGroup.setPrefWidth(locationListVbox.getWidth());
            locationGroup.setPrefHeight(locationListVbox.getHeight()/2);
            locationGroup.setPadding(new Insets(10,10,10,10));
            locationGroup.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            VBox detailGroup = new VBox();
            detailGroup.setAlignment(Pos.TOP_LEFT);

            detailGroup.getChildren().addAll(positionLabel, distantLabel);
            locationGroup.getChildren().addAll(nameLocationLabel, detailGroup,deleteLocationLabel);
            locationListVbox.getChildren().add(locationGroup);

            // All Event Handler
            deleteLocationLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    deleteLocationLabel.setStyle("-fx-text-fill: #C70039 ");
                    deleteLocationLabel.setUnderline(true);
                }
            });
            deleteLocationLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    deleteLocationLabel.setStyle("-fx-text-fill: #FF5733");
                    deleteLocationLabel.setUnderline(false);
                }
            });
            deleteLocationLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Stage confirmDeleteStage = new Stage();
                    try{
                        confirmDeleteStage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
                    }
                    catch (FileNotFoundException fileNotFoundException){
                        fileNotFoundException.printStackTrace();
                    }
                    confirmDeleteStage.setTitle("Confirmation");
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

                    Label confirmLabel = new Label("ยืนยันที่จะลบร้านค้านี้ ?");
                    try {
                        confirmLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    confirmLabel.setStyle("-fx-text-fill: #008887");
                    confirmLabel.setAlignment(Pos.CENTER);
                    confirmLabel.setPrefWidth(confirmDeleteStage.getWidth());
                    confirmLabel.setPrefHeight(confirmDeleteStage.getHeight()/2);

                    Button okDelete = new Button("ยืนยัน");
                    try {
                        okDelete.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    okDelete.setPrefWidth(confirmDeleteStage.getWidth()/2);
                    okDelete.setPrefHeight(confirmDeleteStage.getHeight()/2);
                    okDelete.setStyle("-fx-background-color:#56c596; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");

                    Button cancelDelete = new Button("ยกเลิก");
                    try {
                        cancelDelete.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    cancelDelete.setPrefWidth(confirmDeleteStage.getWidth()/2);
                    cancelDelete.setPrefHeight(confirmDeleteStage.getHeight()/2);
                    cancelDelete.setStyle("-fx-background-color:#DB3535; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");

                    btnContainer.getChildren().addAll(okDelete, cancelDelete);
                    confirmDeleteContainer.getChildren().addAll(confirmLabel, btnContainer);
                    confirmDeleteStage.setScene(new Scene(confirmDeleteContainer));
                    confirmDeleteStage.show();

                    // All Event Handler
                    okDelete.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            okDelete.setStyle("-fx-background-color:#3bab7c; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
                        }
                    });
                    okDelete.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            okDelete.setStyle("-fx-background-color:#56c596; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
                        }
                    });
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

                    cancelDelete.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            cancelDelete.setStyle("-fx-background-color:#c32222; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
                        }
                    });
                    cancelDelete.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            cancelDelete.setStyle("-fx-background-color:#DB3535; -fx-background-radius: 20px; -fx-text-fill: #ffffff;");
                        }
                    });
                    cancelDelete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            confirmDeleteStage.close();
                        }
                    });

                    locationListStage.setOnHidden(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            confirmDeleteStage.close();
                        }
                    });
                }
            });
        }
        locationListScroll.setContent(locationListVbox);
    }
    public void renderStage() throws IOException {
        this.updateLocationListScroll();

        VBox mainContainer = new VBox();
        mainContainer.setPrefWidth(locationListStage.getWidth());
        mainContainer.setPrefHeight(locationListStage.getHeight());
        mainContainer.setSpacing(10);
        mainContainer.setPadding(new Insets(10,10,10,10));

        HBox topContainer = new HBox();
        topContainer.setSpacing(10);
        topContainer.setPadding(new Insets(10, 10, 10, 10));

        Label changeSortBy = new Label("เปลี่ยน");
        changeSortBy.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        changeSortBy.setTextFill(Color.BLUE);
        topContainer.setAlignment(Pos.TOP_LEFT);
        changeSortBy.setStyle("-fx-text-fill: #FF5733");

        Label sortBy = new Label("จัดเรียงตามวันที่");
        sortBy.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
        sortBy.setStyle("-fx-text-fill: #777777");

        topContainer.getChildren().addAll(changeSortBy, sortBy);

        HBox btnContainer = new HBox();
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.setSpacing(10);

        Button addLocationBtn = new Button("เพิ่มร้านค้า");
        try {
            addLocationBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),18));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        addLocationBtn.setStyle("-fx-background-color:#008B8A; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");

        Button exitBtn = new Button("ปิด");
        try {
            exitBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),18));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        exitBtn.setStyle("-fx-background-color:#00BECF; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");

        btnContainer.getChildren().addAll(addLocationBtn, exitBtn);
        mainContainer.getChildren().addAll(topContainer, locationListScroll, btnContainer);
        Scene scene = new Scene(mainContainer);
        this.locationListStage.setScene(scene);
        this.locationListStage.show();

        // All Event Handler
        changeSortBy.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changeSortBy.setStyle("-fx-text-fill: #C70039");
                changeSortBy.setUnderline(true);
            }
        });
        changeSortBy.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changeSortBy.setStyle("-fx-text-fill: #FF5733");
                changeSortBy.setUnderline(false);
            }
        });
        changeSortBy.setOnMouseClicked(e->{
            if(this.sortBy) {
                sortBy.setText("จัดเรียงตามวันที่");
            } else {
                sortBy.setText("จัดเรียงตามระยะทาง");
            }
            this.sortBy = !this.sortBy;
            try {
                this.updateLocationListScroll();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        addLocationBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addLocationBtn.setStyle("-fx-background-color:#006666; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
            }
        });
        addLocationBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addLocationBtn.setStyle("-fx-background-color:#008B8A; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");

            }
        });
        addLocationBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage addLocationStage = new Stage();
                try{
                    addLocationStage.getIcons().add(new Image(new FileInputStream(new File("src/Views/resource/Image/Icon.png"))));
                }
                catch (FileNotFoundException fileNotFoundException){
                    fileNotFoundException.printStackTrace();
                }
                addLocationStage.setTitle("Add Shop Location");
                addLocationStage.setResizable(false);

                VBox mainContainer = new VBox();
                mainContainer.setAlignment(Pos.CENTER);
                mainContainer.setSpacing(10);
                mainContainer.setPadding(new Insets(10,10,10,10));

                HBox btnContainer = new HBox();
                btnContainer.setAlignment(Pos.BOTTOM_CENTER);
                btnContainer.setPrefHeight(100);
                btnContainer.setSpacing(10);

                // text1
                Label text1 = new Label("ชื่อร้านค้า");
                try {
                    text1.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),25));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                text1.setPrefHeight(100);
                text1.setAlignment(Pos.BOTTOM_CENTER);
                text1.setStyle("-fx-text-fill: #008887");

                // locationName
                FormField locationName = new FormField("", 100, false);
                try{
                    locationName.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
                }
                catch (FileNotFoundException fileNotFoundException){
                    fileNotFoundException.printStackTrace();
                }
                locationName.setPromptText("กรอกชื่อร้านค้า");

                // pickLocationLabel
                Label pickedLocationLabel = new Label("( 0 , 0 )");
                try {
                    pickedLocationLabel.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),20));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                pickedLocationLabel.setStyle("-fx-text-fill: #008887");

                // pickLocation
                Button pickLocation = new Button("เลือกที่อยู่ร้าน");
                try {
                    pickLocation.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),18));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                pickLocation.setStyle("-fx-background-color:#00BECF; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");

                // okBtn
                Button okBtn = new Button("ตกลง");
                try {
                    okBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                okBtn.setStyle("-fx-background-color:#56c596; -fx-background-radius: 8px; -fx-text-fill: #ffffff;");

                // cancleBtn
                Button cancelBtn = new Button("ปิด");
                cancelBtn.setStyle("-fx-background-color:#FF6464 ; -fx-background-radius: 8px; -fx-text-fill: #ffffff;");
                try {
                    cancelBtn.setFont(Font.loadFont(new FileInputStream("src/Views/resource/Fonts/FC Lamoon Bold ver 1.00.otf"),15));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                btnContainer.getChildren().addAll(okBtn, cancelBtn);
                mainContainer.getChildren().addAll(text1,locationName.getNode(), pickedLocationLabel, pickLocation, btnContainer);
                addLocationStage.setScene(new Scene(mainContainer));
                addLocationStage.show();

                // All Event Handler
                pickLocation.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        pickLocation.setStyle("-fx-background-color:#00a4b3; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
                    }
                });
                pickLocation.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        pickLocation.setStyle("-fx-background-color:#00BECF; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
                    }
                });
                pickLocation.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try{
                            double MAP_HEIGHT = 750.f, MAP_WIDTH = 1125.f;
                            double RATIO = 10.0f;
                            Location user = FileWorker.readUserLocationFromFile();
                            SelectableMapDrawer mapDrawer = new SelectableMapDrawer(MAP_HEIGHT, MAP_WIDTH, RATIO, user.getX(), user.getY());
                            mapDrawer.getMapStage().setTitle("NOPZ Location  |  Set Shop Location");
                            mapDrawer.getMapStage().show();

                            mapDrawer.getMapStage().setOnHidden(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent windowEvent) {
                                    try{
                                        if(mapDrawer.isPicked()){
                                            pickX = mapDrawer.getPickX();
                                            pickY = mapDrawer.getPickY();

                                            pickedLocationLabel.setText("( " + (int)pickX + " , " + (int)pickY + " )");
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

                okBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        okBtn.setStyle("-fx-background-color:#3bab7c; -fx-background-radius: 8px; -fx-text-fill: #ffffff;");
                    }
                });
                okBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        okBtn.setStyle("-fx-background-color:#56c596; -fx-background-radius: 8px; -fx-text-fill: #ffffff;");
                    }
                });
                okBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String name = locationName.getEnteredText();

                        try {
                            FileWorker.writeLocationInListToFile(pickX, pickY, name);
                            pickX = 0.f; pickY = 0.f;
                            addLocationStage.close();
                            renderStage();
                        }
                        catch (Exception exception) {
                            Alert alertBox = new Alert(Alert.AlertType.WARNING, exception.getMessage());
                            alertBox.setHeaderText(null);
                            Stage alertstage = (Stage)alertBox.getDialogPane().getScene().getWindow();
                            try{
                                alertstage.getIcons().add(new Image(new FileInputStream((new File("src/Views/resource/Image/warning.png")))));
                                ImageView alertImage = new ImageView(new Image( new FileInputStream(new File("src/Views/resource/Image/warning.png"))));
                                alertImage.setFitWidth(50);
                                alertImage.setFitHeight(50);
                                alertBox.setGraphic(alertImage);
                            }
                            catch (FileNotFoundException fileNotFoundException){
                            }

                            if(exception.getMessage()=="Location is too close"){
                                alertBox.setContentText("ระยะห่างของร้านใกล้กันเกินไป ! \nกรุณาเพิ่มร้านให้อยู่นอกรัศมี 1000 หน่วย จากร้านที่อยู่ก่อนหน้า");
                            }
                            else{
                                alertBox.setContentText(exception.getMessage());
                            }
                            alertBox.showAndWait();
                        }
                    }
                });

                cancelBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        cancelBtn.setStyle("-fx-background-color:#ff3333 ; -fx-background-radius: 8px; -fx-text-fill: #ffffff;");
                    }
                });
                cancelBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        cancelBtn.setStyle("-fx-background-color:#FF6464 ; -fx-background-radius: 8px; -fx-text-fill: #ffffff;");
                    }
                });
                cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        addLocationStage.close();
                    }
                });

                locationListStage.setOnHidden(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        addLocationStage.close();
                    }
                });
            }
        });

        exitBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitBtn.setStyle("-fx-background-color:#00a4b3; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
            }
        });
        exitBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitBtn.setStyle("-fx-background-color:#00BECF; -fx-background-radius: 10px; -fx-text-fill: #ffffff;");
            }
        });
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                locationListStage.close();
            }
        });
    }
}
