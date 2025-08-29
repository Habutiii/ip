package habot;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import habot.command.ByeCommand;
import habot.command.DeadlineCommand;
import habot.command.DeleteCommand;
import habot.command.EventCommand;
import habot.command.FindCommand;
import habot.command.ListCommand;
import habot.command.MarkCommand;
import habot.command.ToDoCommand;
import habot.exception.HaBotException;

@DisplayName("Parser: command recognition and error handling")
class ParserTest {

    @Test
    @DisplayName("Recognizes each supported command type")
    void recognizesSupportedCommands() {
        assertInstanceOf(ListCommand.class, Parser.parse("list"));
        assertInstanceOf(ByeCommand.class, Parser.parse("bye"));
        assertInstanceOf(ToDoCommand.class, Parser.parse("todo buy milk"));
        assertInstanceOf(DeadlineCommand.class, Parser.parse("deadline x /by 2/12/2019 1800"));
        assertInstanceOf(EventCommand.class, Parser.parse("event x /from 2/12/2019 1800 /to 2/12/2019 2000"));
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
        assertInstanceOf(MarkCommand.class, Parser.parse("unmark 1"));
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 1"));
        assertInstanceOf(FindCommand.class, Parser.parse("find keyword"));
    }

    @Test
    @DisplayName("Unknown command produces user-friendly error")
    void unknownCommandError() {
        HaBotException ex = assertThrows(HaBotException.class, () -> Parser.parse("foobar"));
        assertTrue(ex.getMessage().contains("I don't understand that command."));
    }
}
