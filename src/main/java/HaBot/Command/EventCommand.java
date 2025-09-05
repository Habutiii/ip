package habot.command;

import habot.exception.HaBotException;
import habot.task.Event;

/**
 * Command to add Event task
 */
public class EventCommand extends AddTaskCommand {
    private static final String HINT = "Please provide a valid description, start time, and end time in the format: "
            + "'event <description> /from <datetime> /to <datetime>' (e.g., '2/12/2019 1800').";;

    /**
     * Constructs an EventCommand with the specified task details.
     *
     * @param taskDetails String of the content after the command word "event"
     */
    public EventCommand(String taskDetails) {
        super(CommandType.EVENT, resolveTask(taskDetails));
    }

    private static Event resolveTask(String taskDetails) {
        taskDetails = taskDetails.trim();

        String[] parts = taskDetails.split(" /from ", 2);
        if (parts.length != 2) {
            throw new HaBotException(HINT);
        }

        String description = parts[0].trim();

        if (description.isEmpty()) {
            throw new HaBotException(HINT);
        }

        parts = parts[1].split(" /to ", 2);
        if (parts.length != 2) {
            throw new HaBotException(HINT);
        }

        String from = parts[0].trim();
        String to = parts[1].trim();

        try {
            return new Event(description, from, to);
        } catch (Exception e) {
            throw new HaBotException(e.getMessage() + "\n" + HINT);
        }
    }
}
