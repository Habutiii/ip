package habot.command;

import habot.Storage;
import habot.TaskList;

/**
 * Bye command to terminate the program
 */
public class ByeCommand extends Command {

    /**
     * Constructs a ByeCommand.
     */
    public ByeCommand() {
        super(CommandType.BYE);
    }

    /**
     * Executes the command to exit the application.
     *
     * @param taskList The HaBot.TaskList to operate on.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) {
        output = "Bye. Hope to see you again soon! (•̀ᴗ•́)و ✧";
    }

    /**
     * Indicates that this command should terminate the application.
     *
     * @return true, indicating that the application should exit.
     */
    @Override
    public boolean isExisting() {
        return true; // This command indicates that the application should exit
    }
}
