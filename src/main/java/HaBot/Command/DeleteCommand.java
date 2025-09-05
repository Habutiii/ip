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
    private Task removedTask = null;

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
     * @param storage The Storage to save/load tasks.
     * @throws HaBotException If an error occurs during execution.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws HaBotException {
        int oldSize = taskList.size();
        removedTask = taskList.remove(taskIndex);
        assert taskList.size() == oldSize - 1 : "Task list size should decrease by 1 after deletion";
        output = "OK! Removed task! (`▽´)/ o()xxxx[{::::::::::::::::::> \n  "
                + removedTask + "\n"
                + taskLeftHint(taskList.size());
        // Save the updated task list to storage
        storage.save(taskList.toStoreFormat());
    }

    /**
     * Indicates whether this command is undoable.
     * This command returns true, allowing it to be undone.
     *
     * @return true, indicating the command can be undone.
     */
    @Override
    public boolean isUndoable() {
        return true;
    }

    /**
     * Undoes the delete command by restoring the removed task to the task list.
     *
     * @param taskList The HaBot.TaskList to operate on.
     * @param storage The Storage to save/load tasks.
     * @throws HaBotException If an error occurs during execution.
     */
    @Override
    public void undo(TaskList taskList, Storage storage) throws HaBotException {
        taskList.insert(taskIndex, removedTask);
        output = "OK! Restored the deleted task! (´▽`ʃ♡\n"
                + removedTask + "\n"
                + taskLeftHint(taskList.size());
        // Save the updated task list to storage
        storage.save(taskList.toStoreFormat());
    }
}
