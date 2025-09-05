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

@DisplayName("ListCommand")
class ListCommandTest {

    @Test
    @DisplayName("emits header and listing; throws when empty")
    void listCommandBehaviour(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        // Empty -> throws
        assertThrows(HaBotException.class, () -> new ListCommand().execute(tl, storage));

        tl.add(new ToDo("first"));
        tl.add(new ToDo("second"));
        ListCommand cmd = new ListCommand();
        cmd.execute(tl, storage);
        String msg = cmd.getOutput();
        assertTrue(msg.startsWith("Here are the tasks in your list (๑•̀ㅂ•́)ง✧\n"));
        assertTrue(msg.contains("1.[T][ ] first\n2.[T][ ] second"));
    }
}
