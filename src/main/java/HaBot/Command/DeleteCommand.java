package HaBot.Command;

import HaBot.Exception.HaBotException;
import HaBot.Storage;
import HaBot.Task.Task;
import HaBot.TaskList;
import HaBot.Ui.Ui;

public class DeleteCommand extends Command {
    private final Integer index;

    /**
     * Constructs a DeleteCommand with the specified index string.
     *
     * @param indexStr The index of the task to delete, as a string.
     */
    public DeleteCommand(String indexStr) {
        try {
            this.index = Integer.parseInt(indexStr.trim()) - 1; // Convert to 0-based index
        } catch (NumberFormatException e) {
            throw new HaBotException(
                    "Invalid input format. Please use 'delete <task number>'."
            );
        }
    }

    /**
     * Executes the delete command on the given task list and UI.
     *
     * @param taskList The HaBot.TaskList to operate on.
     * @param ui The UI to interact with the user.
     * @throws HaBotException If an error occurs during execution.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        Task removedTask = taskList.remove(index);
        ui.send("OK! Removed task! (`▽´)/ o()xxxx[{::::::::::::::::::> \n  "
                + removedTask + "\n"
                + ui.taskLeftHint(taskList.size()));
        // Save the updated task list to storage
        storage.save(taskList.toStoreFormat());
    }
}
