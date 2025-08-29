package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.task.ToDo;
import habot.ui.Ui;

/**
 * Command to add a ToDo Task
 */
public class ToDoCommand extends Command {
    private final String description;

    /**
     * Constructs an ToDoCommand with the specified description.
     *
     * @param description The description of the HaBot.Task.ToDo task.
     */
    public ToDoCommand(String description) {
        super(CommandType.TODO);

        this.description = description.trim();
        if (this.description.isEmpty()) {
            throw new HaBotException("The description of a ToDo cannot be empty.");
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
        output = "Sure! New task \\( ﾟヮﾟ)/\n  " + task + "\n"
                + ui.taskLeftHint(taskList.size());
        ui.send(output);

        // Save the updated task list to storage
        storage.save(taskList.toStoreFormat());
    }
}
