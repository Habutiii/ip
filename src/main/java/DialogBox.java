import java.io.IOException;
import java.util.Collections;

import habot.command.CommandType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        this.initialize();
    }

    /**
     * Constructor for DialogBox.
     *
     * @param className
     */
    private void setDialogClass(String className) {
        dialog.getStyleClass().add(className);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for the user.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img The image to be displayed in the dialog box.
     * @return A DialogBox representing the user's dialog.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.setDialogClass("user-label");
        return db;
    }

    /**
     * Creates a dialog box for the bot.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img The image to be displayed in the dialog box.
     * @param commandType The type of command that generated the bot's response.
     * @return A DialogBox representing the bot's dialog.
     */
    public static DialogBox getBotDialog(String text, Image img, CommandType commandType) {
        var db = new DialogBox(text, img);
        db.setDialogClass("reply-label");
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }

    /**
     * Changes the style of the dialog box based on the command type.
     *
     * @param commandType The type of command that generated the bot's response.
     */
    private void changeDialogStyle(CommandType commandType) {
        switch(commandType) {
        case TODO:
        case DEADLINE:
        case EVENT:
            dialog.getStyleClass().add("add-label");
            break;
        case MARK:
        case UNMARK:
            dialog.getStyleClass().add("marked-label");
            break;
        case DELETE:
            dialog.getStyleClass().add("delete-label");
            break;
        default:
            // Do nothing
        }
    }

    /**
     * Initializes the dialog box.
     * Binds the width of the dialog label to 80% of the width of the parent container.
     */
    @FXML
    public void initialize() {
        // Bind the width of the dialog label to the width of the parent container
        dialog.maxWidthProperty().bind(this.widthProperty().multiply(0.8));
    }
}
