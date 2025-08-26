package HaBot.Command;

import HaBot.Exception.HaBotException;
import HaBot.Storage;
import HaBot.Task.Task;
import HaBot.TaskList;
import HaBot.Ui.Ui;

public class MarkCommand extends Command {

    private final int index;
    private final Boolean isDone;

    public MarkCommand(String indexStr, Boolean isDone) {
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

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        Task markedTask = taskList.mark(index, isDone);
        String markMessage = "OK! Done done done! ᕙ(`▽´)ᕗ";
        String unmarkMessage = "Awww, still need do (º﹃º)ᕗ";
        ui.send((isDone ? markMessage : unmarkMessage) + "\n  " + markedTask);
    }
}
