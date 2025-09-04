import java.io.IOException;

import habot.HaBot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for HaBot using FXML.
 */
public class Main extends Application {

    private HaBot habot = new HaBot("tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            // Set title
            stage.setTitle("HaBot");

            // Set window icon (make sure the path is correct)
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Bot_128.png")));

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setBot(habot); // inject the HaBot instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
