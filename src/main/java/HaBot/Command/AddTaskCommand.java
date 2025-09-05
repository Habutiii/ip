package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.task.Task;
import habot.ui.Ui;

public class AddTaskCommand extends Command {

    private final Task task;

    protected AddTaskCommand(CommandType commandType, Task task) {
        super(commandType);
        this.task = task;
    }


    /**
     * Executes the command to add a task to the task list.
     *
     * @param taskList The HaBot.TaskList to operate on.
     * @param storage The Storage to save/load tasks.
     * @throws HaBotException If an error occurs during execution.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws habot.exception.HaBotException {
        int oldSize = taskList.size();

        taskList.add(task);

        assert taskList.size() == oldSize + 1 : "Task list size should increase by 1 after adding a task";

        output = "Sure! New task \\( ﾟヮﾟ)/\n  " + task + "\n"
                + taskLeftHint(taskList.size());

        // Save the updated task list to storage
        storage.save(taskList.toStoreFormat());
    }

    /**
     * Indicates that this command is undoable.
     *
     * @return true, as adding a task can be undone.
     */
    @Override
    public boolean isUndoable() {
        return true;
    }

    /**
     * Undoes the addition of the last task in the task list.
     *
     * @param taskList The HaBot.TaskList to operate on.
     * @param storage The Storage to save/load tasks.
     * @throws HaBotException If an error occurs during execution.
     */
    @Override
    public void undo(TaskList taskList, Storage storage) throws HaBotException {
        int oldSize = taskList.size();
        Task removedTask = taskList.remove(taskList.size() - 1); // Remove the last added task
        assert taskList.size() == oldSize - 1 : "Task list size should decrease by 1 after deletion";
        output = "Undo! Removed task! (`▽´)/ o()xxxx[{::::::::::::::::::> \n  "
                + removedTask + "\n"
                + taskLeftHint(taskList.size());
        // Save the updated task list to storage
        storage.save(taskList.toStoreFormat());
    }
}
