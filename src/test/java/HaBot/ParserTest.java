package habot;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Stack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import habot.command.*;
import habot.exception.HaBotException;

@DisplayName("Parser: command recognition and error handling")
class ParserTest {

    @Test
    @DisplayName("Recognizes each supported command type")
    void recognizesSupportedCommands() {
        Stack<Command> undoableCommandHistory = new Stack<>();
        undoableCommandHistory.add(new ToDoCommand("sample task")); // to avoid empty stack on undo

        assertInstanceOf(ListCommand.class, Parser.parse("list", undoableCommandHistory));
        assertInstanceOf(ByeCommand.class, Parser.parse("bye", undoableCommandHistory));
        assertInstanceOf(ToDoCommand.class, Parser.parse("todo buy milk", undoableCommandHistory));
        assertInstanceOf(DeadlineCommand.class, Parser.parse(
                "deadline x /by 2/12/2019 1800", undoableCommandHistory));
        assertInstanceOf(EventCommand.class, Parser.parse(
                "event x /from 2/12/2019 1800 /to 2/12/2019 2000", undoableCommandHistory));
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1", undoableCommandHistory));
        assertInstanceOf(MarkCommand.class, Parser.parse("unmark 1", undoableCommandHistory));
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 1", undoableCommandHistory));
        assertInstanceOf(FindCommand.class, Parser.parse("find keyword", undoableCommandHistory));
        assertInstanceOf(UndoCommand.class, Parser.parse("undo", undoableCommandHistory));
    }

    @Test
    @DisplayName("Unknown command produces user-friendly error")
    void unknownCommandError() {
        Stack<Command> undoableCommandHistory = new Stack<>();
        HaBotException ex = assertThrows(HaBotException.class, () ->
                Parser.parse("foobar", undoableCommandHistory));
        assertTrue(ex.getMessage().contains("I don't understand that command."));
    }
}
