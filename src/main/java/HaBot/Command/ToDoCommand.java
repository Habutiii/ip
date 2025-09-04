package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.task.ToDo;
import habot.ui.Ui;

/**
 * Command to add a ToDo Task
 */
public class ToDoCommand extends AddTaskCommand {

    /**
     * Constructs an ToDoCommand with the specified description.
     *
     * @param description The description of the HaBot.Task.ToDo task.
     */
    public ToDoCommand(String description) {
        super(CommandType.TODO, resolveTask(description));
    }

    private static ToDo resolveTask(String description) {
        description = description.trim();
        if (description.isEmpty()) {
            throw new HaBotException("The description of a ToDo cannot be empty.");
        }
        return new ToDo(description);
    }
}
