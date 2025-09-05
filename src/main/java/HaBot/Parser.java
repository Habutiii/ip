package habot;

import java.util.Stack;

import habot.command.ByeCommand;
import habot.command.Command;
import habot.command.CommandType;
import habot.command.DeadlineCommand;
import habot.command.DeleteCommand;
import habot.command.EventCommand;
import habot.command.FindCommand;
import habot.command.ListCommand;
import habot.command.MarkCommand;
import habot.command.ToDoCommand;
import habot.command.UndoCommand;
import habot.exception.HaBotException;

/**
 * Parse command input from the user and call the respective function.
 */
public class Parser {

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
    public static Command parse(String command, Stack<Command> undoableCommandHistory) {
        CommandType commandType = CommandType.fromInput(command);

        return switch (commandType) {
        case LIST -> new ListCommand();
        case MARK -> new MarkCommand(parseArguments(command), true);
        case UNMARK -> new MarkCommand(parseArguments(command), false);
        case FIND -> new FindCommand(parseArguments(command));
        case DELETE -> new DeleteCommand(parseArguments(command));
        case TODO -> new ToDoCommand(parseArguments(command));
        case DEADLINE -> new DeadlineCommand(parseArguments(command));
        case EVENT -> new EventCommand(parseArguments(command));
        case UNDO -> new UndoCommand(undoableCommandHistory);
        case BYE -> new ByeCommand();
        // If the command is not recognized, throw an exception
        default -> throw new HaBotException("Sorry, What are you trying to say? ( ˶°ㅁ°) !???\n"
                + "I don't understand that command.");
        };
    }
}
