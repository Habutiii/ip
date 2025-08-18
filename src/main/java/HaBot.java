import java.util.ArrayList;

public class HaBot {
    // Define the name of the bot
    private static final String NAME = "HaBot";


    private static final ArrayList<Task> storedTasks = new ArrayList<>();

    // Scanner for reading user input
    private static final java.util.Scanner SCANNER = new java.util.Scanner(System.in);

    private static final String SEPARATOR = "-".repeat(50);

    private static void send(String message) {
        System.out.println(SEPARATOR);
        System.out.println("<|°_°|>");
        System.out.println(message);
        System.out.println(SEPARATOR);
    }


    private static void greet() {
        String logo = """
                 _     _           ____ \s
                 |_____|  ____    |____]   ____  __|__  \s
                 |     | |____|__ |_____] |____|   |__  \s
                """;
        System.out.println(logo);
        send("Hello! I'm " + NAME + "! (* v *)ノシ\nWhat can I do for you?");
    }

    private static void bye() {
        send("Bye. Hope to see you again soon! (•̀ᴗ•́)و ✧");
    }

    private static String readInput() {
        System.out.print("> ");
        return SCANNER.nextLine();
    }

    private static void listTasks() {
        // List all stored tasks
        if (storedTasks.isEmpty()) {
            throw new HaBotException("No task stored yet.");
        }
        String out = "Here are the tasks in your list (๑•̀ㅂ•́)ง✧\n";
        for (int i = 0; i < storedTasks.size(); i++) {
            out += (i + 1) + "." + storedTasks.get(i) ;
            if (i < storedTasks.size() - 1) {
                out += "\n";
            }
        }
        send(out);
    }

    private static void markTask(String indexStr, Boolean isDone) {
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
            throw new HaBotException("Invalid input format. Please use '" + ( isDone ? "mark" : "unmark" ) + " <task number>'.");
        }
    }

    private static void deleteTask(String indexStr) {
        try {
            int taskIndex = Integer.parseInt(indexStr) - 1;

            if (taskIndex < 0 || taskIndex >= storedTasks.size()) {
                throw new HaBotException("Invalid task number. List all tasks with 'list' to see available tasks.");
            } else {
                Task removedTask = storedTasks.get(taskIndex);
                storedTasks.remove(taskIndex);
                send("OK! Removed task! (`▽´)/ o()xxxx[{::::::::::::::::::> \n  " + removedTask + "\nThe number of tasks you have to do: ★ " + storedTasks.size() + " ★ ノ(゜-゜ノ)");
            }
        } catch (NumberFormatException e) {
            throw new HaBotException("Invalid input format. Please use '" + "delete" + " <task number>'.");
        }
    }

    private static void addTask(Task task) {
        storedTasks.add(task);
        send("Sure! New task \\( ﾟヮﾟ)/\n  " + task + "\nThe number of tasks you have to do: ★ " + storedTasks.size() + " ★ ノ(゜-゜ノ)");
    }

    // Helper to extract argument after command keyword
    private static String getArg(String input, String command) {
        return input.substring(command.length()).trim();
    }

    private static void handleCommand(String input) {
        CommandType command = CommandType.fromInput(input);
        switch (command) {
            case LIST:
                listTasks();
                break;
            case MARK:
                markTask(getArg(input, "mark"), true);
                break;
            case UNMARK:
                markTask(getArg(input, "unmark"), false);
                break;
            case DELETE:
                deleteTask(getArg(input, "delete"));
                break;
            case TODO:
                addTask(new ToDo(getArg(input, "todo")));
                break;
            case DEADLINE:
                String[] parts = getArg(input, "deadline").split("/by", 2);
                // the /by argument is mandatory
                if (parts.length < 2) throw new HaBotException("Please provide a description and a deadline in the format: 'deadline <description> /by <date>'.");
                addTask(new Deadline(parts[0].trim(), parts[1].trim()));
                break;
            case EVENT:
                String[] firstSplit = getArg(input, "event").split("/from", 2);
                // the /from and /to arguments are mandatory
                if (firstSplit.length < 2) throw new HaBotException("Please provide a valid description, start time, and end time in the format: 'event <description> /from <start> /to <end>'.");
                String[] secondSplit = firstSplit[1].split("/to", 2);
                if (secondSplit.length < 2) throw new HaBotException("Please provide a valid description, start time, and end time in the format: 'event <description> /from <start> /to <end>'.");
                addTask(new Event(firstSplit[0].trim(), secondSplit[0].trim(), secondSplit[1].trim()));
                break;
            default:
                // If the command is not recognized, throw an exception
                throw new HaBotException("Sorry, What are you trying to say? (｡•́︿•̀｡)???\nI don't understand that command.");
        }
    }

    public static void main(String[] args) {
        greet(); // Print the greeting message
        while (true) {
            try {
                String input = readInput();
                if (CommandType.fromInput(input) == CommandType.BYE) break;
                handleCommand(input);
            } catch (HaBotException e) {
                send("Error (ノ•`_´•)ノ︵┻━┻ " + e.getMessage());
            }
        }
        bye();  // Print the goodbye message
    }
}
