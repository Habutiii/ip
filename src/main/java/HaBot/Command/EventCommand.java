package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.task.Event;
import habot.ui.Ui;

/**
 * Command to add Event task
 */
public class EventCommand extends Command {
    private final String taskDetails;

    /**
     * Constructs an EventCommand with the specified task details.
     *
     * @param taskDetails String of the content after the command word "event"
     */
    public EventCommand(String taskDetails) {
        super(CommandType.EVENT);
        this.taskDetails = taskDetails.trim();
    }

    /**
     * Executes the command to add an Event task to the task list.
     *
     * @param taskList The HaBot.TaskList to operate on.
     * @param ui The UI to interact with the user.
     * @param storage The Storage to save/load tasks.
     * @throws HaBotException If an error occurs during execution.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        String hint = "Please provide a valid description, start time, and end time in the format: "
                + "'event <description> /from <datetime> /to <datetime>' (e.g., '2/12/2019 1800').";

        String[] parts = taskDetails.split(" /from ", 2);
        if (parts.length != 2) {
            throw new HaBotException(hint);
        }

        String description = parts[0].trim();

        if (description.isEmpty()) {
            throw new HaBotException(hint);
        }

        parts = parts[1].split(" /to ", 2);
        if (parts.length != 2) {
            throw new HaBotException(hint);
        }

        String from = parts[0].trim();
        String to = parts[1].trim();

        try {
            Event task = new Event(description, from, to);

            int oldSize = taskList.size();

            taskList.add(task);

            assert taskList.size() == oldSize + 1 : "Task list size should increase by 1 after adding a task";

            output = "Sure! New task \\( ﾟヮﾟ)/\n  " + task + "\n"
                    + ui.taskLeftHint(taskList.size());
            ui.send(output);
        } catch (Exception e) {
            throw new HaBotException(e.getMessage() + "\n" + hint);
        }

        // Save the updated task list to storage
        storage.save(taskList.toStoreFormat());
    }
}
