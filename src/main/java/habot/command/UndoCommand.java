package habot.command;

import java.util.Stack;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.ui.Ui;

/**
 * Command to undo the last action
 */
public class UndoCommand extends Command {
    private final Command commandToUndo;

    public UndoCommand(Stack<Command> commandHistory) {
        super(CommandType.UNDO);
        if (commandHistory.isEmpty()) {
            throw new HaBotException("No commands to undo.");
        }
        commandToUndo = commandHistory.pop();
    }

    /**
     * Executes the undo operation for the last command.
     *
     * @param taskList The TaskList to operate on.
     * @param ui The Ui to interact with the user.
     * @param storage The Storage to save/load tasks.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        commandToUndo.undo(taskList, ui, storage);
        // Implementation for undoing the last action goes here
        output = commandToUndo.getOutput();
        ui.send(output);
    }
}
