package Models.Sample;

import Models.Utilities.FileWorker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LocationManagement {

    //Data Fileds
    private ArrayList<Location> locs;
    private Stage stage = new Stage();

    // Cpnstructor
    public LocationManagement() throws IOException {
        render();
    }

    // Setter
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Getter
    public Stage getStage() {
        return stage;
    }

    // Method
    private Node getLocationListVBox() throws IOException {
        locs = FileWorker.readLocationListFromFile();
        VBox locsPane = new VBox();
        for (int i = 0; i < locs.size() ; i++) {
            Location loc = locs.get(i);
            int currentIndex = i;
            Label txt = new Label("ร้าน "+ loc.getName() + " " + "อยู่ที่ตำแหน่ง" + "(" + loc.getX() + ", " + loc.getY() + ")");
            Label deleteLoc = new Label("ลบ");
            deleteLoc.setTextFill(Color.RED);
            deleteLoc.setPadding(new Insets(3));
            deleteLoc.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Stage confirmDeleteStage = new Stage();
                    Button okDelete = new Button("ยืนยัน");
                    Button cancelDelete = new Button("ยกเลิก");
                    VBox confirmDeleteScene = new VBox();
                    HBox btnContainer = new HBox();
                    btnContainer.getChildren().addAll(okDelete, cancelDelete);
                    Label detailDelete = new Label("ยืนยันการลบร้านค้า?");
                    confirmDeleteScene.getChildren().addAll(detailDelete, btnContainer);
                    confirmDeleteStage.setScene(new Scene(confirmDeleteScene));
                    confirmDeleteStage.show();
                    okDelete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                FileWorker.deleteLocationInListByIndex(currentIndex);
                                render();
                                confirmDeleteStage.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    cancelDelete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            confirmDeleteStage.close();
                        }
                    });
                    System.out.println("delete id = " + currentIndex);
                }
            });
            deleteLoc.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    deleteLoc.setUnderline(true);
                }
            });
            deleteLoc.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    deleteLoc.setUnderline(false);
                }
            });
            HBox locGroup = new HBox();
            locGroup.setPadding(new Insets(20));
            locGroup.setBorder(new Border(new BorderStroke(Color.GRAY,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            locGroup.getChildren().addAll(txt, deleteLoc);

            locsPane.getChildren().add(locGroup);
        }
        return locsPane;
    }
    public void render() throws IOException {
        VBox group = new VBox();
        Scene scene = new Scene(group);

        Button addLocBtn = new Button("เพิ่มร้าน");
        addLocBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage addLocStage = new Stage();
                VBox addLocContainer = new VBox();
                addLocContainer.setPadding(new Insets(5));
                FormField locName = new FormField("ชื่อร้าน", 100, false);
                FormField xAxis = new FormField("พิกัดแนวขวาง", 10, true);
                FormField yAxis = new FormField("พิกัดแนวตั้ง", 10, true);
                HBox btnContainer = new HBox();
                Button okBtn = new Button("ตกลง");
                okBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        String name = locName.getEnteredText();
                        double x = Double.parseDouble(xAxis.getEnteredText());
                        double y = Double.parseDouble(yAxis.getEnteredText());
                        try {
                            FileWorker.writeLocationInListToFile(x, y, name);
                            addLocStage.close();
                            render();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Button cancelBtn = new Button("ปิด");
                cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        addLocStage.close();
                    }
                });
                btnContainer.getChildren().addAll(okBtn, cancelBtn);
                btnContainer.setAlignment(Pos.CENTER);
                addLocContainer.getChildren().addAll(locName.getNode(), xAxis.getNode(), yAxis.getNode(), btnContainer);
                addLocStage.setScene(new Scene(addLocContainer));
                addLocStage.show();
            }
        });



        HBox btnContainer = new HBox();
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.setPadding(new Insets(30));
        Button cancelBtn = new Button("ยกเลิก");
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        addLocBtn.setAlignment(Pos.CENTER);
        btnContainer.getChildren().addAll(addLocBtn, cancelBtn);
        Node locsPane = this.getLocationListVBox();
        group.getChildren().addAll(locsPane, btnContainer);
        stage.setScene(scene);
    }
}
