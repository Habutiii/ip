public enum CommandType {
    BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, UNKNOWN;

    public static CommandType fromInput(String input) {
        if (input.equalsIgnoreCase("bye")) return BYE;
        if (input.equalsIgnoreCase("list")) return LIST;
        if (input.startsWith("mark ")) return MARK;
        if (input.startsWith("unmark ")) return UNMARK;
        if (input.startsWith("delete ")) return DELETE;
        if (input.startsWith("todo ")) return TODO;
        if (input.startsWith("deadline ")) return DEADLINE;
        if (input.startsWith("event ")) return EVENT;
        return UNKNOWN;
    }
}