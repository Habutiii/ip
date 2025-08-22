package HaBot;

import HaBot.Command.*;
import HaBot.Exception.HaBotException;

public class Parser {

    private static final String unknownCommandMessage = "Sorry, What are you trying to say? (｡•́︿•̀｡)???\n"
            + "I don't understand that command.";


    /**
     * Parses the input string without flag and extracts the arguments after the command.
     *
     * @param input The input string to parse.
     * @return An ArrayList containing the parsed arguments.
     */
    private static String parseArguments(String input) {
        // remove the command + " " from the input
        String[] parts = input.trim().split(" ", 2);

        return parts[1].trim();
    }

    /**
     * Parses a command string and returns the corresponding command type.
     *
     * @param command The command string to parse.
     * @return The HaBot.Command.CommandType corresponding to the command.
     * @throws IllegalArgumentException If the command is invalid.
     */
    public static Command parse(String command) {
        CommandType commandType = CommandType.fromInput(command);

        return switch (commandType) {
            case LIST -> new ListCommand();
            case MARK -> new MarkCommand(parseArguments(command), true);
            case UNMARK -> new MarkCommand(parseArguments(command),  false);
            case DELETE -> new DeleteCommand(parseArguments(command));
            case TODO -> new ToDoCommand(parseArguments(command));
            case DEADLINE -> new DeadlineCommand(parseArguments(command));
            case EVENT -> new EventCommand(parseArguments(command));
            case BYE -> new ByeCommand();
            // If the command is not recognized, throw an exception
            default -> throw new HaBotException(unknownCommandMessage);
        };
    }
}
