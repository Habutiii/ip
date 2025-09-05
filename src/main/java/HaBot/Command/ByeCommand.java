package habot.command;

import habot.Storage;
import habot.TaskList;
import habot.ui.Ui;

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
     * @param ui The UI to interact with the user.
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
    public boolean toExit() {
        return true; // This command indicates that the application should exit
    }
}
