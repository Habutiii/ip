public class HaBot {
    // Define the name of the bot
    private static final String NAME = "HaBot";

    private static final int storeSize = 100;
    private static int taskLen = 0;

    private static final Task[] storedTasks = new Task[storeSize];

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
        // List all stored messages
        if (taskLen == 0) {
            send("No messages stored yet.");
        } else {
            String out = "Here are the tasks in your list (๑•̀ㅂ•́)ง✧\n";
            for (int i = 0; i < taskLen; i++) {
                out += (i + 1) + "." + storedTasks[i] ;
                if (i < taskLen - 1) {
                    out += "\n";
                }
            }
            send(out);
        }
    }

    private static void markTask(String indexStr, Boolean isDone) {
        try {
            int taskIndex = Integer.parseInt(indexStr) - 1;

            if (taskIndex < 0 || taskIndex >= taskLen) {
                send("Invalid task number. List all tasks with 'list' to see available tasks.");
            } else {
                if (isDone) {
                    storedTasks[taskIndex].markAsDone();
                    send("OK! Done done done! ᕙ(`▽´)ᕗ \n  " + storedTasks[taskIndex]);
                } else {
                    storedTasks[taskIndex].markAsNotDone();
                    send("Awww, still need do (º﹃º)ᕗ\n  " + storedTasks[taskIndex]);
                }
            }
        } catch (NumberFormatException e) {
            send("Invalid input format. Please use '" + ( isDone ? "mark" : "unmark" ) + " <task number>'.");
        }
    }

    private static void addTask(Task task) {
        if (taskLen < storeSize) {
            storedTasks[taskLen++] = task;
            send("Sure! New task \\( ﾟヮﾟ)/\n  " + task + "\nNow you have " + taskLen + " tasks in the list ノ(゜-゜ノ)");
        } else {
            send("Sorry, I can't store more tasks right now.");
        }
    }

    public static void main(String[] args) {
        // Print the greeting message
        greet();

        // read user input
        while (true) {
            String input = readInput();


            if (input.equalsIgnoreCase("bye")) {
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                listTasks();
                continue;
            }

            // if input is "mark \d", mark the task as done
            if (input.startsWith("mark ")) {
                markTask(input.substring(5), true);
                continue;
            }

            // if input is "unmark \d", unmark the task as done
            if (input.startsWith("unmark ")) {
                markTask(input.substring(7), false);
                continue;
            }

            // Different task types
            if (input.startsWith("todo ")) {
                String description = input.substring(5).trim();
                addTask(new ToDo(description));
                continue;
            }

            if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split("/by", 2);
                if (parts.length < 2) {
                    send("Please provide a description and a deadline in the format: 'deadline <description> /by <date>'.");
                    continue;
                }
                String description = parts[0].trim();
                String by = parts[1].trim();
                addTask(new Deadline(description, by));
                continue;
            }

            if (input.startsWith("event ")) {
                String[] firstSplit = input.substring(6).split("/from", 2);
                if (firstSplit.length < 2) {
                    send("Please provide a valid description, start time, and end time in the format: 'event <description> /from <start> /to <end>'.");
                    continue;
                }
                String description = firstSplit[0].trim();
                String[] secondSplit = firstSplit[1].split("/to", 2);
                if (secondSplit.length < 2) {
                    send("Please provide a valid description, start time, and end time in the format: 'event <description> /from <start> /to <end>'.");
                    continue;
                }
                String from = secondSplit[0].trim();
                String to = secondSplit[1].trim();

                if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    send("Please provide a valid description, start time, and end time in the format: 'event <description> /from <start> /to <end>'.");
                    continue;
                }

                addTask(new Event(description, from, to));
                continue;
            }

            send("Sorry, What are you trying to say? (｡•́︿•̀｡)???\nI don't understand that command.");

        }

        // Print the goodbye message
        bye();
    }
}
