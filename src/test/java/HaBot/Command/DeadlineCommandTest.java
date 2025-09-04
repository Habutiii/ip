package habot.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import habot.Storage;
import habot.TaskList;
import habot.exception.HaBotException;
import habot.task.Task;


@DisplayName("DeadlineCommand")
class DeadlineCommandTest {

    @Test
    @DisplayName("DeadlineCommand validates and adds")
    void deadlineCommandValidatesAndAdds(@TempDir Path tempDir) throws Exception {
        TaskList tl = new TaskList();
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());

        // Missing /by
        HaBotException ex1 = assertThrows(
                HaBotException.class, (
                ) -> new DeadlineCommand("desc only").execute(tl, storage));
        assertTrue(ex1.getMessage().contains("Please provide a valid description and deadline"));

        // Invalid datetime
        HaBotException ex2 = assertThrows(
                HaBotException.class, (
                ) -> new DeadlineCommand("x /by not-a-date").execute(tl, storage));
        assertTrue(ex2.getMessage().contains("Please provide a valid description and deadline"));

        // Success
        DeadlineCommand cmd = new DeadlineCommand("submit report /by 2/12/2019 1800");
        cmd.execute(tl, storage);
        assertEquals(1, tl.size());
        Task t = tl.get(0);
        assertTrue(t.toString().contains("(By:"));
        assertTrue(cmd.getOutput().contains("The number of tasks you have to do: ★ 1 ★"));

        assertEquals("[D][ ] submit report (By: Dec 2 2019 18:00)", t.toString());
    }

    @Test
    @DisplayName("DeadlineCommand rejects invalid date")
    void deadlineCommandRejectsInvalidDate(@TempDir Path tempDir) {
        TaskList tl = new TaskList();
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());

        // Invalid date format
        assertThrows(HaBotException.class, () ->
                new DeadlineCommand("submit report /by 2019-99-99 1800").execute(tl, storage));
    }
}
