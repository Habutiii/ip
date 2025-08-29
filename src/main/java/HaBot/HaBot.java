package habot;

import habot.command.Command;
import habot.command.CommandType;
import habot.exception.HaBotException;
import habot.ui.Ui;

/**
 * Main class for the HaBot chatbot application.
 */
public class HaBot {
    private final Ui ui;
    private final TaskList taskList;
    private final Storage storage;
    private CommandType commandType = CommandType.UNKNOWN;

    /**
     * HaBot Constructor
     * @param filePath file path to store the task
     */
    public HaBot(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.load());
    }

    /**
     * Runs the main loop of the HaBot.HaBot application.
     */
    public void run() {
        ui.greet(); // Print the greeting message
        boolean toExit = false; // Flag to control the main loop
        while (!toExit) {
            try {
                String input = ui.readInput();
                Command command = Parser.parse(input); // Parse the user input into a command
                command.execute(taskList, ui, storage); // Execute the command
                toExit = command.toExit(); // Check if the command is a 'bye' command

            // Catch any intended HaBotExceptions thrown by handleCommand
            } catch (HaBotException e) {
                ui.error(e.getMessage());
            // Catch any unexpected exceptions and display an error message
            } catch (Exception e) {
                ui.unexpectedError(e.getMessage());
            }
        }
    }

    /**
     * The main entry point of the program.
     */
    public static void main(String[] args) {
        new HaBot("tasks.txt").run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input); // Parse the user input into a command
            command.execute(taskList, ui, storage); // Execute the command
            commandType = command.getCommandType();
            if (command.toExit()) {
                System.exit(0);
            }
            return command.getOutput();
        } catch (HaBotException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }

    /**
     * Returns the type of the last executed command.
     * @return The CommandType of the last executed command.
     */
    public CommandType getCommandType() {
        return commandType;
    }
}
