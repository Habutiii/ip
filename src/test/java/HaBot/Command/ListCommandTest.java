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

@DisplayName("ListCommand")
class ListCommandTest {

    @Test
    @DisplayName("emits header and listing; throws when empty")
    void list_command_behaviour(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        FakeUi ui = new FakeUi();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        // Empty -> throws
        assertThrows(HaBotException.class, () -> new ListCommand().execute(tl, ui, storage));

        tl.add(new ToDo("first"));
        tl.add(new ToDo("second"));
        new ListCommand().execute(tl, ui, storage);
        String msg = ui.getLastMessage();
        assertTrue(msg.startsWith("Here are the tasks in your list (๑•̀ㅂ•́)ง✧\n"));
        assertTrue(msg.contains("1.[T][ ] first\n2.[T][ ] second"));
    }
}

