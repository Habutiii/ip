package HaBot.Command;

import HaBot.Exception.HaBotException;
import HaBot.Storage;
import HaBot.Task.Task;
import HaBot.TaskList;
import HaBot.Ui.FakeUi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DeadlineCommand")
class DeadlineCommandTest {

    @Test
    @DisplayName("validates format and adds task")
    void deadline_command_validates_and_adds(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        FakeUi ui = new FakeUi();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        // Missing /by
        HaBotException ex1 = assertThrows(HaBotException.class, () -> new DeadlineCommand("desc only").execute(tl, ui, storage));
        assertTrue(ex1.getMessage().contains("Please provide a valid description and deadline"));

        // Invalid datetime
        HaBotException ex2 = assertThrows(HaBotException.class, () -> new DeadlineCommand("x /by not-a-date").execute(tl, ui, storage));
        assertTrue(ex2.getMessage().contains("Please provide a valid description and deadline"));

        // Success
        new DeadlineCommand("submit report /by 2/12/2019 1800").execute(tl, ui, storage);
        assertEquals(1, tl.size());
        Task t = tl.get(0);
        assertTrue(t.toString().contains("(By:"));
        assertTrue(ui.getLastMessage().contains("The number of tasks you have to do: ★ 1 ★"));

        assertEquals("[D][ ] submit report (By: Dec 2 2019 18:00)", t.toString());
    }
}

