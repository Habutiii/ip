public class ListCommand extends Command {

    /**
     * Executes the list command, which lists all tasks.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        ui.listTasks(taskList.list());
    }
}
