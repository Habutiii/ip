package habot.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import habot.Storage;
import habot.TaskList;
import habot.task.ToDo;


@DisplayName("FindCommand")
public class FindCommandTest {

    @Test
    @DisplayName("returns 'no tasks found' for empty and non-matching lists")
    void findCommandNoMatches(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        // Empty list
        FindCommand cmd = new FindCommand("milk");
        cmd.execute(tl, storage);
        assertEquals("No tasks found matching the keyword ( ╥ ‸ ╥ ) : milk", cmd.getOutput());

        // Non-matching list
        tl.add(new ToDo("bread"));
        tl.add(new ToDo("butter"));
        cmd = new FindCommand("milk");
        cmd.execute(tl, storage);
        assertEquals("No tasks found matching the keyword ( ╥ ‸ ╥ ) : milk", cmd.getOutput());
    }

    @Test
    @DisplayName("lists matching tasks using original indices; case-insensitive")
    void findCommandMatchesAndFormats(@TempDir Path tmp) {
        TaskList tl = new TaskList();
        tl.add(new ToDo("alpha")); // index 1
        tl.add(new ToDo("Beta")); // index 2
        tl.add(new ToDo("ALPHA two")); // index 3
        tl.add(new ToDo("gamma")); // index 4

        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());

        FindCommand cmd = new FindCommand("AlPhA");
        cmd.execute(tl, storage);
        String expected = "Here are the matching tasks in your list ( ˶ˆᗜˆ˵ ) :\n"
                + "1.[T][ ] alpha\n"
                + "3.[T][ ] ALPHA two";
        assertEquals(expected, cmd.getOutput());
    }
}
