import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

public class NOPZ_LocationApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // Method
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Views/HomeView.fxml"));
        Scene homeScene = new Scene(root, 800, 600);

        stage.setScene(homeScene);
        stage.setTitle("NOPZ Location");
        stage.setResizable(false);
        stage.show();
    }
}
