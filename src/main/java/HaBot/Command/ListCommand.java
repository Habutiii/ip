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
     * Constructs a ListCommand.
     */
    public ListCommand() {
        super(CommandType.LIST);
    }

    /**
     * Executes the list command, which lists all tasks.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        // Print the list of tasks
        String hint = "Here are the tasks in your list (๑•̀ㅂ•́)ง✧\n";
        output = hint + taskList.list();
        ui.send(output);
    }
}
