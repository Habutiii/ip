package HaBot.Command;

import HaBot.Exception.HaBotException;
import HaBot.Storage;
import HaBot.TaskList;
import HaBot.Task.ToDo;
import HaBot.Ui.Ui;

public class ToDoCommand extends Command {
    private final String description;

    /**
     * Constructs an AddToDoCommand with the specified description.
     *
     * @param description The description of the HaBot.Task.ToDo task.
     */
    public ToDoCommand(String description) {
        this.description = description.trim();
        if (this.description.isEmpty()) {
            throw new HaBotException("The description of a HaBot.Task.ToDo cannot be empty.");
        }
    }

    /**
     * Executes the command to add a HaBot.Task.ToDo task to the task list.
     *
     * @param taskList The HaBot.TaskList to operate on.
     * @param ui The UI to interact with the user.
     * @throws HaBotException If an error occurs during execution.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        ToDo task = new ToDo(description);
        taskList.add(task);
        ui.send("Sure! New task \\( ﾟヮﾟ)/\n  " + task + "\n"
                + ui.taskLeftHint(taskList.size()));

        // Save the updated task list to storage
        storage.save(taskList.toStoreFormat());
    }
}
