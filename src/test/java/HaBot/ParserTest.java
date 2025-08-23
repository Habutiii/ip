package HaBot;

import HaBot.Command.*;
import HaBot.Exception.HaBotException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Parser: command recognition and error handling")
class ParserTest {

    @Test
    @DisplayName("Recognizes each supported command type")
    void recognizes_supported_commands() {
        assertInstanceOf(ListCommand.class, Parser.parse("list"));
        assertInstanceOf(ByeCommand.class, Parser.parse("bye"));
        assertInstanceOf(ToDoCommand.class, Parser.parse("todo buy milk"));
        assertInstanceOf(DeadlineCommand.class, Parser.parse("deadline x /by 2/12/2019 1800"));
        assertInstanceOf(EventCommand.class, Parser.parse("event x /from 2/12/2019 1800 /to 2/12/2019 2000"));
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
        assertInstanceOf(MarkCommand.class, Parser.parse("unmark 1"));
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 1"));
    }

    @Test
    @DisplayName("Unknown command produces user-friendly error")
    void unknown_command_error() {
        HaBotException ex = assertThrows(HaBotException.class, () -> Parser.parse("foobar"));
        assertTrue(ex.getMessage().contains("I don't understand that command."));
    }
}
