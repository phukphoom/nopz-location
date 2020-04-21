package Models.Screen;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InstructionScreen {
    public InstructionScreen() {
    }

    public Stage getStage() throws FileNotFoundException {
        Stage stage = new Stage();
        ScrollPane scrollPane = new ScrollPane();
        VBox container = new VBox();
        ImageView instructionImage = new ImageView();
        instructionImage.setImage(new Image(new FileInputStream("src/Views/resource/Image/instruction.png")));
        scrollPane.setContent(instructionImage);

        container.getChildren().add(scrollPane);

        stage.setScene(new Scene(container, 900, 800));
        stage.setResizable(false);
        return stage;
    }
}
