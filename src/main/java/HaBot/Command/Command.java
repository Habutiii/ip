package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.ui.Ui;

/**
 * Represents a command that can be executed by the HaBot application.
 * This is an abstract class that serves as a base for specific command implementations.
 */
public class Command {

    protected String output = "Sorry, What are you trying to say? (｡•́︿•̀｡)???\n"
            + "I don't understand that command.";

    private final CommandType commandType;

    public Command(CommandType commandType) {
        this.commandType = commandType;
    }

    /**
     * Executes the command with the given TaskList, Ui, and Storage.
     * This method is intended to be overridden by subclasses to provide specific command functionality.
     *
     * @param taskList The TaskList to operate on.
     * @param ui The Ui to interact with the user.
     * @param storage The Storage to save/load tasks.
     * @throws HaBotException If an error occurs during command execution.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        // does nothing by default
    }

    /**
     * Indicates whether this command should terminate the application.
     * All commands return false by default, unless overridden.
     *
     * @return true if the application should exit, false otherwise.
     */
    public boolean toExit() {
        return false; // default implementation, can be overridden
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getOutput() {
        return output;
    }
}


