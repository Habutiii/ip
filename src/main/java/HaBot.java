public class HaBot {
    // Define the name of the bot
    private static final String NAME = "HaBot";

    private static final int storeSize = 100;
    private static int storePtr = 0;

    private static String[] storedMessages = new String[storeSize];

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
        send("Hello! I'm " + NAME + "!\nWhat can I do for you?");
    }

    private static void bye() {
        send("Bye. Hope to see you again soon!");
    }

    private static String readInput() {
        System.out.print("> ");
        return SCANNER.nextLine();
    }

    private static void listMessages() {
        // List all stored messages
        if (storePtr == 0) {
            send("No messages stored yet.");
        } else {
            String out = "";
            for (int i = 0; i < storePtr; i++) {
                out += (i + 1) + ": " + storedMessages[i] ;
                if (i < storePtr - 1) {
                    out += "\n";
                }
            }
            send(out);
        }

    }

    public static void main(String[] args) {

        String endpoint = "bye";
        // Print the greeting message
        greet();

        // read user input
        while (true) {
            String input = readInput();


            if (input.equalsIgnoreCase(endpoint)) {
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                listMessages();
                continue;
            }

            // append the input to the stored messages
            if (storePtr < storeSize) {
                storedMessages[storePtr++] = input;
                send("added: " + input);
            } else {
                send("Store is full, cannot save more messages.");
            }
        }

        // Print the goodbye message
        bye();
    }
}
