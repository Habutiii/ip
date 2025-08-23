package HaBot.Command;

import HaBot.Exception.HaBotException;
import HaBot.Storage;
import HaBot.Task.ToDo;
import HaBot.TaskList;
import HaBot.Ui.FakeUi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MarkCommand")
class MarkCommandTest {

    @Test
    @DisplayName("toggles task and validates input")
    void mark_command_toggles_and_validates(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        tl.add(new ToDo("x"));
        FakeUi ui = new FakeUi();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        // Invalid index format - mark
        HaBotException e0 = assertThrows(HaBotException.class, () -> new MarkCommand("a", true));
        assertTrue(e0.getMessage().contains("Invalid input format. Please use 'mark <task number>'"));

        // Invalid index format - unmark
        HaBotException e1 = assertThrows(HaBotException.class, () -> new MarkCommand("a", false));
        assertTrue(e1.getMessage().contains("Invalid input format. Please use 'unmark <task number>'"));

        // Out of range
        HaBotException e2 = assertThrows(HaBotException.class, () -> new MarkCommand("2", true).execute(tl, ui, storage));
        assertTrue(e2.getMessage().contains("Invalid task index."));

        // Mark success
        new MarkCommand("1", true).execute(tl, ui, storage);
        assertTrue(ui.getLastMessage().contains("OK! Done done done! ᕙ(`▽´)ᕗ"));
        assertTrue(ui.getLastMessage().contains("[T][X] x"));

        // Unmark success
        new MarkCommand("1", false).execute(tl, ui, storage);
        assertTrue(ui.getLastMessage().contains("Awww, still need do (º﹃º)ᕗ"));
        assertTrue(ui.getLastMessage().contains("[T][ ] x"));
    }
}

