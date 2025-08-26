package HaBot.Command;

import HaBot.Exception.HaBotException;
import HaBot.Storage;
import HaBot.TaskList;
import HaBot.Ui.Ui;

public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in tasks.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for tasks that contain the specified keyword.
     *
     * @param tasks   The TaskList containing all tasks.
     * @param ui      The Ui instance for user interaction.
     * @param storage The Storage instance for saving/loading tasks (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {

        int count = 0;
        StringBuilder foundTasks = new StringBuilder();

        if (tasks.size() != 0) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                    count++;

                    foundTasks.append(String.format("%d.%s", i + 1, tasks.get(i).toString()));
                    if (i < tasks.size() - 1) {
                        foundTasks.append("\n");
                    }
                }
            }
        }

        if (count == 0) {
            ui.send("No tasks found matching the keyword ( ╥ ‸ ╥ ) : " + keyword);
        } else {
            ui.send(String.format("Here are the matching tasks in your list ( ˶ˆᗜˆ˵ ) :\n%s", foundTasks.toString().trim()));
        }
    }
}
