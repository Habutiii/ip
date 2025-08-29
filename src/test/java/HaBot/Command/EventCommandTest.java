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
import habot.ui.FakeUi;

@DisplayName("EventCommand")
class EventCommandTest {

    @Test
    @DisplayName("validates format and adds task")
    void eventCommandValidatesAndAdds(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        FakeUi ui = new FakeUi();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        // Missing /from
        HaBotException e1 = assertThrows(HaBotException.class, () -> new EventCommand(
                "desc only").execute(tl, ui, storage));
        assertTrue(e1.getMessage().contains("Please provide a valid description, start time, and end time"));

        // Missing /to
        HaBotException e2 = assertThrows(HaBotException.class, () -> new EventCommand(
                "meet /from 2/12/2019 1800").execute(tl, ui, storage));
        assertTrue(e2.getMessage().contains("Please provide a valid description, start time, and end time"));

        // Invalid date
        HaBotException e3 = assertThrows(HaBotException.class, () -> new EventCommand(
                "meet /from bad /to 2/12/2019 2000").execute(tl, ui, storage));
        assertTrue(e3.getMessage().contains("Please provide a valid description, start time, and end time"));

        // Success
        new EventCommand("project /from 1/1/2020 0900 /to 1/1/2020 1030").execute(tl, ui, storage);
        assertEquals(1, tl.size());
        Task t = tl.get(0);
        assertTrue(t.toString().contains("(From:"));
        assertTrue(ui.getLastMessage().contains("The number of tasks you have to do: ★ 1 ★"));
    }
}

