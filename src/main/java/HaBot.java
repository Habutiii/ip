/**
 * Main class for the HaBot task manager application.
 * Handles user interaction, command parsing, and delegates task management to TaskManager.
 */
public class HaBot {
    private final Ui ui;
    private final TaskList taskList;

    public HaBot(String filePath) {
        this.ui = new Ui();
        this.taskList = new TaskList(filePath);

    }

    // Scanner for reading user input
    private static final java.util.Scanner SCANNER = new java.util.Scanner(System.in);

    /**
     * Reads a line of input from the user.
     * @return The user's input as a String.
     */
    private static String readInput() {
        System.out.print("> ");
        return SCANNER.nextLine();
    }

    /**
     * Converts a string input to an integer, or throws a HaBotException with a hint if invalid.
     * @param input The string to convert.
     * @param hint The error message to use if conversion fails.
     * @return The parsed integer value.
     * @throws HaBotException If the input is not a valid integer.
     */
    private Integer deriveInteger(String input, String hint) throws HaBotException {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new HaBotException(hint);
        }
    }


    /**
     * Marks or unmarks a task as done or not done.
     * @param indexStr The index of the task as a String (1-based).
     * @param isDone True to mark as done, false to unmark.
     * @throws HaBotException For invalid input or index.
     */
    private void markTask(String indexStr, Boolean isDone) throws HaBotException {
        int taskIndex = deriveInteger(
                indexStr,
                "Invalid input format. Please use '"
                        + (isDone ? "mark" : "unmark") + " <task number>'.")
                - 1; // Convert to 0-based index

        Task markedTask = taskList.mark(taskIndex, isDone);

        ui.send(
                (isDone ? "OK! Done done done! ᕙ(`▽´)ᕗ" : "Awww, still need do (º﹃º)ᕗ")
                + "\n  " + markedTask);
    }

    /**
     * Deletes a task from the list.
     * @param indexStr The index of the task as a String (1-based).
     * @throws HaBotException For invalid input or index.
     */
    private void deleteTask(String indexStr) throws HaBotException {
        int taskIndex = deriveInteger(
                indexStr,
                "Invalid input format. Please use 'delete <task number>'.")
                - 1; // Convert to 0-based index

        Task removedTask = taskList.remove(taskIndex);
        ui.send("OK! Removed task! (`▽´)/ o()xxxx[{::::::::::::::::::> \n  "
                + removedTask + "\n"
                + ui.taskLeftHint(taskList.size()));
    }

    /**
     * Adds a new task to the list.
     * @param task The task to add.
     */
    private void addTask(Task task) {
        taskList.add(task);
        ui.send("Sure! New task \\( ﾟヮﾟ)/\n  " + task + "\n"
                + ui.taskLeftHint(taskList.size()));
    }

    /**
     * Adds a new Deadline task to the list.
     * Parses the input for description and deadline date.
     * @param input The full user input string for the deadline command.
     * @throws HaBotException If the input format is invalid.
     */
    private void addDeadLine(String input) throws HaBotException {
        String hint = "Please provide a valid description and deadline in the format: "
                + "'deadline <description> /by <datetime>' (e.g., '2/12/2019 1800').";

        String[] parts = getArg(input, "deadline").split("/by", 2);

        // the /by argument is mandatory
        if (parts.length < 2) {
            throw new HaBotException(hint);
        }

        try {
            // Create a new Deadline task with the description and deadline
            addTask(new Deadline(parts[0].trim(), parts[1].trim()));
        } catch (Exception e) {
            throw new HaBotException(e.getMessage() + "\n" + hint);
        }
    }

    /**
     * Adds a new Event task to the list.
     * Parses the input for description, start, and end times.
     * @param input The full user input string for the event command.
     * @throws HaBotException If the input format is invalid.
     */
    private void addEvent(String input) throws HaBotException {
        String hint = "Please provide a valid description, start time, and end time in the format: "
                + "'event <description> /from <datetime> /to <datetime>' (e.g., '2/12/2019 1800').";

        // the /from and /to arguments are mandatory
        String[] firstSplit = getArg(input, "event").split("/from", 2);
        if (firstSplit.length < 2) {
            throw new HaBotException(hint);
        }

        // the /to argument is mandatory
        String[] secondSplit = firstSplit[1].split("/to", 2);
        if (secondSplit.length < 2) {
            throw new HaBotException(hint);
        }

        try {
            addTask(new Event(firstSplit[0].trim(), secondSplit[0].trim(), secondSplit[1].trim()));
        } catch (Exception e) {
            throw new HaBotException(e.getMessage() + "\n" + hint);
        }
    }

    /**
     * Extracts the argument after the command keyword from the input string.
     * @param input The full user input string.
     * @param command The command keyword to remove.
     * @return The argument string after the command keyword.
     */
    private String getArg(String input, String command) {
        return input.substring(command.length()).trim();
    }

    /**
     * Handles the user's command input and dispatches to the appropriate method.
     * @param input The full user input string.
     * @throws HaBotException If the command is not recognized or input is invalid.
     */
    private void handleCommand(String input) throws HaBotException {
        CommandType command = CommandType.fromInput(input);
        switch (command) {
            case LIST -> this.ui.listTasks(taskList.list());
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
     * Runs the main loop of the HaBot application.
     */
    public void run() {
        ui.greet(); // Print the greeting message
        while (true) {
            try {
                String input = readInput();
                if (CommandType.fromInput(input) == CommandType.BYE) {
                    break;
                }
                handleCommand(input);
                // Catch any intended HaBotExceptions thrown by handleCommand
            } catch (HaBotException e) {
                ui.error( e.getMessage());
            } catch (Exception e) {
                ui.unexpectedError(e.getMessage());
            }
        }
        ui.bye();  // Print the goodbye message
    }

    /**
     * The main entry point of the program.
     */
    public static void main(String[] args) {
        new HaBot("tasks.txt").run();
    }
}
