package habot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import habot.exception.HaBotException;
import habot.task.Task;
import habot.task.ToDo;

@DisplayName("TaskList: add, get, remove, list, mark, bounds")
class TaskListTest {

    @Test
    @DisplayName("Add and size reflect correct count")
    void addAndSize() {
        TaskList tl = new TaskList();
        assertEquals(0, tl.size());
        tl.add(new ToDo("a"));
        assertEquals(1, tl.size());
    }

    @Test
    @DisplayName("Get and remove by valid index")
    void getAndRemove() {
        TaskList tl = new TaskList();
        ToDo t = new ToDo("a");
        tl.add(t);
        Task got = tl.get(0);
        assertSame(t, got);
        Task removed = tl.remove(0);
        assertSame(t, removed);
        assertEquals(0, tl.size());
    }

    @Test
    @DisplayName("List throws when empty and returns formatted listing when populated")
    void listBehaviour() {
        TaskList tl = new TaskList();
        HaBotException ex = assertThrows(HaBotException.class, tl::list);
        assertTrue(ex.getMessage().contains("No task stored yet."));

        tl.add(new ToDo("first"));
        tl.add(new ToDo("second"));
        String listing = tl.list();
        assertEquals("1.[T][ ] first\n2.[T][ ] second", listing);
    }

    @Test
    @DisplayName("Mark/unmark toggles state and returns the updated task")
    void markAndUnmark() {
        TaskList tl = new TaskList();
        ToDo t = new ToDo("x");
        tl.add(t);
        Task marked = tl.mark(0, true);
        assertEquals("[T][X] x", marked.toString());
        Task unmarked = tl.mark(0, false);
        assertEquals("[T][ ] x", unmarked.toString());
    }

    @Test
    @DisplayName("Index validation throws for negative and out-of-range")
    void indexValidation() {
        TaskList tl = new TaskList();
        tl.add(new ToDo("a"));
        for (int idx : new int[]{-1, 1}) {
            assertThrows(HaBotException.class, () -> tl.get(idx));
            assertThrows(HaBotException.class, () -> tl.remove(idx));
            assertThrows(HaBotException.class, () -> tl.mark(idx, true));
        }
    }
}
