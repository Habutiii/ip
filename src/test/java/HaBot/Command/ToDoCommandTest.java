package habot.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.ui.FakeUi;

@DisplayName("ToDoCommand")
class ToDoCommandTest {

    @Test
    @DisplayName("adds task, saves, and announces")
    void todoCommandAddsAndAnnounces(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        FakeUi ui = new FakeUi();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        new ToDoCommand("buy milk").execute(tl, ui, storage);

        assertEquals(1, tl.size());
        assertEquals("Sure! New task \\( ﾟヮﾟ)/\n"
                + "  [T][ ] buy milk\n"
                + "The number of tasks you have to do: ★ 1 ★ ノ(゜-゜ノ)", ui.getLastMessage());
    }

    @Test
    @DisplayName("rejects empty description")
    void todoCommandRejectsEmpty() {
        HaBotException ex = assertThrows(HaBotException.class, () -> new ToDoCommand(" "));
        assertEquals("The description of a ToDo cannot be empty.", ex.getMessage());
    }
}
