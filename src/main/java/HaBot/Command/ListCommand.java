package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;

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
     *
     * @param taskList The TaskList to operate on.
     * @param storage The Storage to save/load tasks (not used in this command).
     * @throws HaBotException If an error occurs during execution.
     */
    public void execute(TaskList taskList, Storage storage) throws HaBotException {
        // Print the list of tasks
        String hint = "Here are the tasks in your list (๑•̀ㅂ•́)ง✧\n";
        output = hint + taskList.list();
    }
}
