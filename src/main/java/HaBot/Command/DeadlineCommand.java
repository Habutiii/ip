package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.task.Deadline;
import habot.task.Task;
import habot.ui.Ui;

/**
 * Command to add Deadline task
 */
public class DeadlineCommand extends AddTaskCommand {

    private final static String HINT = "Please provide a valid description and deadline in the format: "
            + "'deadline <description> /by <datetime>' (e.g., '2/12/2019 1800').";

    /**
     * Constructs a DeadlineCommand with the specified task details.
     *
     * @param taskDetails String of the content after the command word "deadline"
     */
    public DeadlineCommand(String taskDetails) {
        super(CommandType.DEADLINE, resolveTask(taskDetails));
    }


    private static Deadline resolveTask(String taskDetails) {
        taskDetails = taskDetails.trim();

        String[] parts = taskDetails.split(" /by ", 2);
        if (parts.length != 2) {
            throw new HaBotException(HINT);
        }

        String description = parts[0].trim();

        if (description.isEmpty()) {
            throw new HaBotException(HINT);
        }

        String by = parts[1].trim();

        try {
            return new Deadline(description, by);
        } catch (Exception e) {
            throw new HaBotException(e.getMessage() + "\n" + HINT);
        }
    }
}
