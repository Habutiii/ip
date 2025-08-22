package HaBot.Command;

import HaBot.Exception.HaBotException;
import HaBot.Storage;
import HaBot.TaskList;
import HaBot.Ui.Ui;

public class Command {
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        // does nothing by default
    }

    public boolean toExit() {
        return false; // default implementation, can be overridden
    }
}


