import habot.HaBot;
import habot.command.CommandType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private ImageView sendIcon;

    private HaBot bot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User_128.png"));
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/Bot_128.png"));

    /**
     * Initializes the main window and binds the scroll pane to the dialog container height.
     * Also adds a greeting message from the bot.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Add greet message
        dialogContainer.getChildren().add(
                DialogBox.getBotDialog(
                        "Hello! I'm HaBot! (* v *)ノシ\nWhat can I do for you?",
                        botImage,
                        CommandType.UNKNOWN)
        );
    }

    /** Injects the HaBot instance */
    public void setBot(HaBot d) {
        bot = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bot.getResponse(input);
        CommandType commandType = bot.getCommandType();
        DialogBox userDialog = DialogBox.getUserDialog(input, userImage);
        DialogBox botDialog = DialogBox.getBotDialog(response, botImage, commandType);
        dialogContainer.getChildren().addAll(userDialog, botDialog);
        userInput.clear();

        if (bot.isExiting()) {
            int sleepWaitTime = 3000;
            // disable user input and button when exiting
            userInput.setDisable(true);
            sendButton.setDisable(true);
            // Create a new thread for sleeping and exiting
            new Thread(() -> {
                try {
                    Thread.sleep(sleepWaitTime); // Sleep for 5 seconds in the background
                } catch (Exception e) {
                    // Do nothing
                }

                // Exit the application after sleeping
                System.exit(0); // Exit on the UI thread
            }).start(); // Start the background thread
        }
    }
}
