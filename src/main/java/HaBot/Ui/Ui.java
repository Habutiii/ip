package habot.ui;

/**
 * Handles UI operations
 */
public class Ui {
    // Define the name of the bot
    private final String name = "HaBot";
    private final String separator = "-".repeat(50);

    // Scanner for reading user input
    private final java.util.Scanner scanner = new java.util.Scanner(System.in);

    /**
     * Reads a line of input from the user.
     * @return The user's input as a String.
     */
    public String readInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    /**
     * Prints a formatted message in standardised format.
     * @param message The message to display.
     */
    public void send(String message) {
        System.out.println(separator);
        System.out.println("<|°_°|>");
        System.out.println(message);
        System.out.println(separator);
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
        send("Hello! I'm " + name + "! (* v *)ノシ\nWhat can I do for you?");
    }

    /**
     * Returns a hint string showing the number of tasks left to do.
     * @return A formatted string with the number of tasks remaining.
     */
    public String taskLeftHint(int taskCount) {
        return "The number of tasks you have to do: ★ " + taskCount + " ★ ノ(゜-゜ノ)";
    }


    public void error(String message) {
        send("Error (ノ•`_´•)ノ︵┻━┻ " + message);
    }

    public void unexpectedError(String message) {
        send("Unexpected Error (ノ•`o´•)ノ︵┻━┻ " + message);
    }
}
