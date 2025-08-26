package HaBot.Command;

import HaBot.Exception.HaBotException;
import HaBot.Storage;
import HaBot.TaskList;
import HaBot.Ui.Ui;

public class Command {

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
}


