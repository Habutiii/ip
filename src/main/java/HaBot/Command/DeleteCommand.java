package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.task.Task;
import habot.ui.Ui;

/**
 * Command to delete task
 */
public class DeleteCommand extends Command {
    private final Integer taskIndex;

    /**
     * Constructs a DeleteCommand with the specified task index string.
     *
     * @param taskIndexString The index of the task to delete, as a string.
     */
    public DeleteCommand(String taskIndexString) {
        super(CommandType.DELETE);
        try {
            this.taskIndex = Integer.parseInt(taskIndexString.trim()) - 1; // Convert to 0-based index
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
        Task removedTask = taskList.remove(taskIndex);
        output = "OK! Removed task! (`\u25bd\u00b4)/ o()xxxx[{::::::::::::::::::> \n  "
                + removedTask + "\n"
                + ui.taskLeftHint(taskList.size());
        ui.send(output);
        // Save the updated task list to storage
        storage.save(taskList.toStoreFormat());
    }
}
