public class HaBot {
    // Define the name and logo of the bot
    private static final String NAME = "HaBot";

    // Scanner for reading user input
    private static final java.util.Scanner SCANNER = new java.util.Scanner(System.in);

    private static final String SEPARATOR = "-".repeat(50);
    private static void printSeparator() {
        System.out.println(SEPARATOR);
    }

    private static void send(String message) {
        printSeparator();
        System.out.println("<|°_°|>");
        System.out.println(message);
        printSeparator();
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


    public static void main(String[] args) {

        String endpoint = "bye";
        // Print the greeting message
        greet();

        // read user input

        String input = readInput();
        while (!input.equalsIgnoreCase(endpoint)) {
            // echo it back
            send(input);
            input = readInput();
        }

        // Print the goodbye message
        bye();

    }
}
