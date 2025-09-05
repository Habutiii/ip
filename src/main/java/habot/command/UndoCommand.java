package habot.command;

import java.util.Stack;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;

/**
 * Command to undo the last action
 */
public class UndoCommand extends Command {
    private final Command commandToUndo;

    /**
     * Constructs an UndoCommand that will undo the last command in the history.
     *
     * @param commandHistory The stack of commands to retrieve the last command from.
     * @throws HaBotException If there are no commands to undo.
     */
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
     * @param storage The Storage to save/load tasks.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) {
        commandToUndo.undo(taskList, storage);
        output = commandToUndo.getOutput();
    }
}
