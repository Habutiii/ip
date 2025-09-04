package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.task.Task;
import habot.ui.Ui;

/**
 * Command to make a task as done or not done
 */
public class MarkCommand extends Command {

    private final int index;
    private final Boolean isDone;

    /**
     * Constructs a HaBot.Command.MarkCommand with the specified index string and done status.
     *
     * @param indexStr The index of the task to mark/unmark, as a string.
     * @param isDone   True to mark the task as done, false to unmark it.
     */
    public MarkCommand(String indexStr, Boolean isDone) {
        super(isDone ? CommandType.MARK : CommandType.UNMARK);

        try {
            this.index = Integer.parseInt(indexStr.trim()) - 1; // Convert to 0-based index
            this.isDone = isDone;
        } catch (NumberFormatException e) {
            throw new HaBotException(
                    "Invalid input format. Please use '"
                            + (isDone ? "mark" : "unmark") + " <task number>'."
            );
        }
    }

    /**
     * Executes the mark/unmark command on the given task list and UI.
     *
     * @param taskList The HaBot.TaskList to operate on.
     * @param ui       The UI to interact with the user.
     * @param storage The Storage to save/load tasks (not used in this command).
     * @throws HaBotException If an error occurs during execution.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        Task markedTask = taskList.mark(index, isDone);

        assert taskList.get(index).getMarkStatusIcon().equals(isDone ? "X" : " ") : "Task mark status should match the command";
        
        final String markMessage = "OK! Done done done! ᕙ(`▽´)ᕗ";
        final String unmarkMessage = "Awww, still need do (º﹃º)ᕗ";
      
        output = (isDone ? markMessage : unmarkMessage) + "\n  " + markedTask;
        ui.send(output);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public void undo(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        Task undoneTask = taskList.mark(index, !isDone);

        assert taskList.get(index).getMarkStatusIcon().equals(!isDone ? "X" : " ")
                : "Task mark status should be reverted after undo";

        final String undoMarkMessage = "Undo mark! Awww, task is now not done. (º﹃º)ᕗ";
        final String undoUnmarkMessage = "Undo unmark! OK! Task is now done. ᕙ(`▽´)ᕗ";

        output = (isDone ? undoMarkMessage : undoUnmarkMessage) + "\n  " + undoneTask;
        ui.send(output);
    }
}
