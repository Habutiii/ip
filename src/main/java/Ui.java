public class Ui {
    // Define the name of the bot
    private final String NAME = "HaBot";
    private final String SEPARATOR = "-".repeat(50);

    // Scanner for reading user input
    private final java.util.Scanner SCANNER = new java.util.Scanner(System.in);

    /**
     * Reads a line of input from the user.
     * @return The user's input as a String.
     */
    public String readInput() {
        System.out.print("> ");
        return SCANNER.nextLine();
    }

    /**
     * Prints a formatted message in standardised format.
     * @param message The message to display.
     */
    public void send(String message) {
        System.out.println(SEPARATOR);
        System.out.println("<|°_°|>");
        System.out.println(message);
        System.out.println(SEPARATOR);
    }

    /**
     * Prints the greeting message and bot logo.
     */
    public void greet() {
        String logo = """
                 _     _           ____ \s
                 |_____|  ____    |____]   ____  __|__  \s
                 |     | |____|__ |_____] |____|   |__  \s
                """;
        System.out.println(logo);
        send("Hello! I'm " + NAME + "! (* v *)ノシ\nWhat can I do for you?");
    }

    /**
     * Prints the goodbye message.
     */
    public void bye() {
        send("Bye. Hope to see you again soon! (•̀ᴗ•́)و ✧");
    }

    /**
     * Returns a hint string showing the number of tasks left to do.
     * @return A formatted string with the number of tasks remaining.
     */
    public String taskLeftHint(int taskCount) {
        return "The number of tasks you have to do: ★ " + taskCount + " ★ ノ(゜-゜ノ)";
    }

    /**
     * Lists all stored tasks.
     * @throws HaBotException If no tasks are stored.
     */
    public void listTasks(String taskListStr) throws HaBotException {
        // Print the list of tasks
        String hint = "Here are the tasks in your list (๑•̀ㅂ•́)ง✧\n";
        send(hint + taskListStr);
    }

    public void error(String message) {
        send("Error (ノ•`_´•)ノ︵┻━┻ " + message);
    }

    public void unexpectedError(String message) {
        send("Unexpected Error (ノ•`o´•)ノ︵┻━┻ " + message);
    }
}
