package habot.command;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.task.ToDo;
import habot.ui.FakeUi;

@DisplayName("ListCommand")
class ListCommandTest {

    @Test
    @DisplayName("emits header and listing; throws when empty")
    void listCommandBehaviour(@TempDir Path tmp) {
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
