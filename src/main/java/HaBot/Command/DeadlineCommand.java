package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.task.Deadline;
import habot.ui.Ui;

/**
 * Command to add Deadline task
 */
public class DeadlineCommand extends Command {
    private final String content;

    /**
     * Constructs a DeadlineCommand with the specified content.
     *
     * @param content String of the content after the command word "deadline"
     */
    public DeadlineCommand(String content) {
        super(CommandType.DEADLINE);
        this.content = content.trim();
    }

    /**
     * Executes the command to add a Deadline task to the task list.
     *
     * @param taskList The HaBot.TaskList to operate on.
     * @param ui The UI to interact with the user.
     * @throws HaBotException If an error occurs during execution.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        String hint = "Please provide a valid description and deadline in the format: "
                + "'deadline <description> /by <datetime>' (e.g., '2/12/2019 1800').";

        String[] parts = content.split(" /by ", 2);
        if (parts.length != 2) {
            throw new HaBotException(hint);
        }

        String description = parts[0].trim();

        if (description.isEmpty()) {
            throw new HaBotException(hint);
        }

        String by = parts[1].trim();

        try {
            Deadline task = new Deadline(description, by);
            taskList.add(task);
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
