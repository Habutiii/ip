import java.util.ArrayList;

public class HaBot {
    // Define the name of the bot
    private static final String NAME = "HaBot";


    private static final ArrayList<Task> storedTasks = new ArrayList<>();

    // Scanner for reading user input
    private static final java.util.Scanner SCANNER = new java.util.Scanner(System.in);

    private static final String SEPARATOR = "-".repeat(50);

    /**
     * Prints a formatted message in standardised format.
     * @param message The message to display.
     */
    private static void send(String message) {
        System.out.println(SEPARATOR);
        System.out.println("<|°_°|>");
        System.out.println(message);
        System.out.println(SEPARATOR);
    }

    /**
     * Prints the greeting message and bot logo.
     */
    private static void greet() {
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
    private static void bye() {
        send("Bye. Hope to see you again soon! (•̀ᴗ•́)و ✧");
    }

    /**
     * Reads a line of input from the user.
     * @return The user's input as a String.
     */
    private static String readInput() {
        System.out.print("> ");
        return SCANNER.nextLine();
    }

    /**
     * Lists all stored tasks.
     * Throws HaBotException if no tasks are stored.
     */
    private static void listTasks() throws HaBotException {
        // Check if there are any tasks stored
        if (storedTasks.isEmpty()) {
            throw new HaBotException("No task stored yet.");
        }
        // Print the list of tasks
        String out = "Here are the tasks in your list (๑•̀ㅂ•́)ง✧\n";
        for (int i = 0; i < storedTasks.size(); i++) {
            out += (i + 1) + "." + storedTasks.get(i);
            if (i < storedTasks.size() - 1) {
                out += "\n";
            }
        }
        send(out);
    }

    /**
     * Marks or unmarks a task as done or not done.
     * @param indexStr The index of the task as a String (1-based).
     * @param isDone True to mark as done, false to unmark.
     * Throws HaBotException for invalid input or index.
     */
    private static void markTask(String indexStr, Boolean isDone) throws HaBotException {
        try {
            int taskIndex = Integer.parseInt(indexStr) - 1;

            if (taskIndex < 0 || taskIndex >= storedTasks.size()) {
                throw new HaBotException("Invalid task number. List all tasks with 'list' to see available tasks.");
            } else {
                if (isDone) {
                    storedTasks.get(taskIndex).markAsDone();
                    send("OK! Done done done! ᕙ(`▽´)ᕗ \n  " + storedTasks.get(taskIndex));
                } else {
                    storedTasks.get(taskIndex).markAsNotDone();
                    send("Awww, still need do (º﹃º)ᕗ\n  " + storedTasks.get(taskIndex));
                }
            }
        } catch (NumberFormatException e) {
            throw new HaBotException("Invalid input format. Please use '"
                    + (isDone ? "mark" : "unmark") + " <task number>'.");
        }
    }

    /**
     * Deletes a task from the list.
     * @param indexStr The index of the task as a String (1-based).
     * Throws HaBotException for invalid input or index.
     */
    private static void deleteTask(String indexStr) {
        try {
            int taskIndex = Integer.parseInt(indexStr) - 1;

            if (taskIndex < 0 || taskIndex >= storedTasks.size()) {
                throw new HaBotException("Invalid task number. List all tasks with 'list' to see available tasks.");
            } else {
                Task removedTask = storedTasks.get(taskIndex);
                storedTasks.remove(taskIndex);
                send("OK! Removed task! (`▽´)/ o()xxxx[{::::::::::::::::::> \n  "
                        + removedTask
                        + "\nThe number of tasks you have to do: ★ "
                        + storedTasks.size() + " ★ ノ(゜-゜ノ)");
            }
        } catch (NumberFormatException e) {
            throw new HaBotException("Invalid input format. Please use '" + "delete" + " <task number>'.");
        }
    }

    /**
     * Adds a new task to the list.
     * @param task The task to add.
     */
    private static void addTask(Task task) {
        storedTasks.add(task);
        send("Sure! New task \\( ﾟヮﾟ)/\n  " + task
                + "\nThe number of tasks you have to do: ★ " + storedTasks.size() + " ★ ノ(゜-゜ノ)");
    }

    /**
     * Adds a new Deadline task to the list.
     * Parses the input for description and deadline date.
     * @param input The full user input string for the deadline command.
     * @throws HaBotException If the input format is invalid.
     */
    private static void addDeadLine(String input) throws HaBotException {
        String[] parts = getArg(input, "deadline").split("/by", 2);
        // the /by argument is mandatory
        if (parts.length < 2)
            throw new HaBotException(
                    "Please provide a description and a deadline in the format: "
                    + "'deadline <description> /by <date>'.");

        addTask(new Deadline(parts[0].trim(), parts[1].trim()));
    }

    /**
     * Adds a new Event task to the list.
     * Parses the input for description, start, and end times.
     * @param input The full user input string for the event command.
     * @throws HaBotException If the input format is invalid.
     */
    private static void addEvent(String input) throws HaBotException {
        // the /from and /to arguments are mandatory
        String[] firstSplit = getArg(input, "event").split("/from", 2);
        if (firstSplit.length < 2)
            throw new HaBotException(
                    "Please provide a valid description, start time, and end time in the format: "
                    + "'event <description> /from <start> /to <end>'.");

        // the /to argument is mandatory
        String[] secondSplit = firstSplit[1].split("/to", 2);
        if (secondSplit.length < 2)
            throw new HaBotException(
                    "Please provide a valid description, start time, and end time in the format: "
                            + "'event <description> /from <start> /to <end>'.");

        addTask(new Event(firstSplit[0].trim(), secondSplit[0].trim(), secondSplit[1].trim()));
    }

    /**
     * Extracts the argument after the command keyword from the input string.
     * @param input The full user input string.
     * @param command The command keyword to remove.
     * @return The argument string after the command keyword.
     */
    private static String getArg(String input, String command) {
        return input.substring(command.length()).trim();
    }

    /**
     * Handles the user's command input and dispatches to the appropriate method.
     * @param input The full user input string.
     * @throws HaBotException If the command is not recognized or input is invalid.
     */
    private static void handleCommand(String input) throws HaBotException {
        CommandType command = CommandType.fromInput(input);
        switch (command) {
            case LIST -> listTasks();
            case MARK -> markTask(getArg(input, "mark"), true);
            case UNMARK -> markTask(getArg(input, "unmark"), false);
            case DELETE -> deleteTask(getArg(input, "delete"));
            case TODO -> addTask(new ToDo(getArg(input, "todo")));
            case DEADLINE -> addDeadLine(input);
            case EVENT -> addEvent(input);
            // If the command is not recognized, throw an exception
            default -> throw new HaBotException(
                    "Sorry, What are you trying to say? (｡•́︿•̀｡)???\n"
                    + "I don't understand that command.");
        }
    }

    /**
     * The main entry point of the program. Runs the bot loop.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        greet(); // Print the greeting message
        while (true) {
            try {
                String input = readInput();
                if (CommandType.fromInput(input) == CommandType.BYE) {
                    break;
                }
                handleCommand(input);
            // Catch any intended HaBotExceptions thrown by handleCommand
            } catch (HaBotException e) {
                send("Error (ノ•`_´•)ノ︵┻━┻ " + e.getMessage());
            } catch (Exception e) {
                send("Unexpected Error (ノ•`o´•)ノ︵┻━┻ " + e.getMessage());
            }
        }
        bye();  // Print the goodbye message
    }
}
