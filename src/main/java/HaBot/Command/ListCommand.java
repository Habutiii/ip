package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.ui.Ui;

/**
 * Command to list all tasks
 */
public class ListCommand extends Command {

    /**
     * Executes the list command, which lists all tasks.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        ui.listTasks(taskList.list());
    }
}
