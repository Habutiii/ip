public class ByeCommand extends Command{

    /**
     * Executes the command to exit the application.
     *
     * @param taskList The TaskList to operate on.
     * @param ui The UI to interact with the user.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage)  {
        ui.send("Bye. Hope to see you again soon! (•̀ᴗ•́)و ✧");
    }

    @Override
    public boolean toExit() {
        return true; // This command indicates that the application should exit
    }
}
