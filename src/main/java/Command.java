public class Command {
    public void execute(TaskList taskList, Ui ui, Storage storage) throws HaBotException {
        // does nothing by default
    }

    public boolean toExit() {
        return false; // default implementation, can be overridden
    }
}


