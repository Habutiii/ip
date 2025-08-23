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

@DisplayName("DeleteCommand")
class DeleteCommandTest {

    @Test
    @DisplayName("validates index and removes the task")
    void delete_command_validates_and_removes(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        tl.add(new ToDo("first"));
        tl.add(new ToDo("second"));
        FakeUi ui = new FakeUi();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        // Invalid index format
        HaBotException e = assertThrows(HaBotException.class, () -> new DeleteCommand("abc"));
        assertTrue(e.getMessage().contains("Invalid input format. Please use 'delete <task number>'"));

        // Out of range
        HaBotException e2 = assertThrows(HaBotException.class, () -> new DeleteCommand("3").execute(tl, ui, storage));
        assertTrue(e2.getMessage().contains("Invalid task index."));

        // Success
        new DeleteCommand("1").execute(tl, ui, storage);
        assertEquals(1, tl.size());
        assertTrue(ui.getLastMessage().contains("OK! Removed task!"));
        assertTrue(ui.getLastMessage().contains("The number of tasks you have to do: ★ 1 ★"));
        assertTrue(ui.getLastMessage().contains("first"));
    }
}

