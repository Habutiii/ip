package habot;

import java.util.Stack;

import habot.command.Command;
import habot.command.CommandType;
import habot.exception.HaBotException;
import habot.ui.Ui;

/**
 * Main class for the HaBot chatbot application.
 */
public class HaBot {
    // Stack to keep track of undoable commands
    private static final Stack<Command> UNDOABLE_COMMAND_HISTORY = new Stack<>();

    private final TaskList taskList;
    private final Storage storage;
    private CommandType commandType = CommandType.UNKNOWN;
    private boolean isExiting = false; // Flag to control the exit

    /**
     * HaBot Constructor
     * @param filePath file path to store the task
     */
    public HaBot(String filePath) {
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.loadTasks());
    }

    /**
     * Runs the main loop of the HaBot.HaBot application.
     */
    public void run() {
        Ui ui = new Ui();
        ui.greet(); // Print the greeting message
        while (!isExiting) {
            String input = ui.readInput();
            ui.send(getResponse(input));
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
            Command command = Parser.parse(input, UNDOABLE_COMMAND_HISTORY); // Parse the user input into a command
            command.execute(taskList, storage); // Execute the command
            commandType = command.getCommandType();
            isExiting = command.toExit(); // Check if the command is a 'bye' command

            if (command.isUndoable()) {
                UNDOABLE_COMMAND_HISTORY.push(command);
            }

            return command.getOutput();
        } catch (HaBotException e) {
            commandType = CommandType.ERROR;
            return e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred  (ノ•`o´•)ノ︵┻━┻ \n" + e.getMessage();
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
