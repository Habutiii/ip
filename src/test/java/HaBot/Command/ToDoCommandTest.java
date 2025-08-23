package HaBot.Command;

import HaBot.Exception.HaBotException;
import HaBot.Storage;
import HaBot.TaskList;
import HaBot.Ui.FakeUi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ToDoCommand")
class ToDoCommandTest {

    @Test
    @DisplayName("adds task, saves, and announces")
    void todo_command_adds_and_announces(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        FakeUi ui = new FakeUi();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        new ToDoCommand("buy milk").execute(tl, ui, storage);

        assertEquals(1, tl.size());
        assertEquals("Sure! New task \\( ﾟヮﾟ)/\n" +
                "  [T][ ] buy milk\n" +
                "The number of tasks you have to do: ★ 1 ★ ノ(゜-゜ノ)", ui.getLastMessage());
    }

    @Test
    @DisplayName("rejects empty description")
    void todo_command_rejects_empty() {
        HaBotException ex = assertThrows(HaBotException.class, () -> new ToDoCommand(" "));
        assertEquals("The description of a ToDo cannot be empty.", ex.getMessage());
    }
}
