package HaBot.Command;

import HaBot.Exception.HaBotException;
import HaBot.Storage;
import HaBot.TaskList;
import HaBot.Ui.Ui;

public class ListCommand extends Command {

    /**
     * Executes the list command, which lists all tasks.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        ui.listTasks(taskList.list());
    }
}
