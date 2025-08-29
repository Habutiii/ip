package habot.command;

/**
 * Represents the types of commands supported by HaBot.HaBot.
 */
public enum CommandType {
    BYE, LIST, MARK, UNMARK, FIND, DELETE, TODO, DEADLINE, EVENT, UNKNOWN;

    /**
     * Parses the user input and returns the corresponding HaBot.Command.CommandType.
     *
     * @param input The user input string.
     * @return The matching HaBot.Command.CommandType, or UNKNOWN if no match is found.
     */
    public static CommandType fromInput(String input) {
        if (input.equals("bye")) {
            return BYE;
        } else if (input.equals("list")) {
            return LIST;
        } else if (input.startsWith("mark ")) {
            return MARK;
        } else if (input.startsWith("unmark ")) {
            return UNMARK;
        } else if (input.startsWith("find ")) {
            return FIND;
        } else if (input.startsWith("delete ")) {
            return DELETE;
        } else if (input.startsWith("todo ")) {
            return TODO;
        } else if (input.startsWith("deadline ")) {
            return DEADLINE;
        } else if (input.startsWith("event ")) {
            return EVENT;
        } else {
            return UNKNOWN;
        }
    }
}
