package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestMainWindow extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Project OOP");
        Button btn1 = new Button("Click me!");
        Button btn2 = new Button("Click me!");
        VBox btnContainer = new VBox();
        // btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().addAll(btn1, btn2);
        Scene scene = new Scene(btnContainer, 400, 1000);
        stage.setScene(scene);
        stage.show();
    }

    public static void _main(String[] args) {
        launch(args);
    }
}
