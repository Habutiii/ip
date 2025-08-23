package HaBot.Command;

import HaBot.Storage;
import HaBot.TaskList;
import HaBot.Ui.FakeUi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ByeCommand")
class ByeCommandTest {

    @Test
    @DisplayName("sends goodbye and signals exit")
    void bye_command_goodbye(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        FakeUi ui = new FakeUi();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString()); // not used

        ByeCommand cmd = new ByeCommand();
        cmd.execute(tl, ui, storage);
        assertEquals("Bye. Hope to see you again soon! (•̀ᴗ•́)و ✧", ui.getLastMessage());
        assertTrue(cmd.toExit());
    }
}


