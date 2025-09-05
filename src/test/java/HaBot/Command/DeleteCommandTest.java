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
import habot.task.ToDo;


@DisplayName("DeleteCommand")
class DeleteCommandTest {

    @Test
    @DisplayName("validates index and removes the task")
    void deleteCommandValidatesAndRemoves(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        tl.add(new ToDo("first"));
        tl.add(new ToDo("second"));
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        // Invalid index format
        HaBotException e = assertThrows(HaBotException.class, () -> new DeleteCommand("abc"));
        assertTrue(e.getMessage().contains("Invalid input format. Please use 'delete <task number>'"));

        // Out of range
        HaBotException e2 = assertThrows(HaBotException.class, () -> new DeleteCommand("3").execute(tl, storage));
        assertTrue(e2.getMessage().contains("Invalid task index."));

        // Success
        DeleteCommand cmd = new DeleteCommand("1");
        cmd.execute(tl, storage);
        String output = cmd.getOutput();
        assertEquals(1, tl.size());
        assertTrue(output.contains("OK! Removed task!"));
        assertTrue(output.contains("The number of tasks you have to do: ★ 1 ★"));
        assertTrue(output.contains("first"));
    }
}

