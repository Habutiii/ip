package habot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ToDo: toString format and store round-trip")
class ToDoTest {

    @Test
    @DisplayName("toString shows type, status, and description")
    void toStringFormat() {
        ToDo t = new ToDo("read book");
        assertEquals("[T][ ] read book", t.toString());
        t.markAsDone();
        assertEquals("[T][X] read book", t.toString());
    }

    @Test
    void storeRoundtripWithEscape() {
        ToDo t = new ToDo("read | book");
        String stored = t.toStoreFormat();
        assertEquals("T |   | read \\| book", stored);

        Task parsed = Task.fromStoreFormat(stored);
        assertInstanceOf(ToDo.class, parsed);
        assertEquals("[T][ ] read | book", parsed.toString());

        t.markAsDone();
        stored = t.toStoreFormat();
        assertEquals("T | X | read \\| book", stored);
        parsed = Task.fromStoreFormat(stored);
        assertInstanceOf(ToDo.class, parsed);
        assertEquals("[T][X] read | book", parsed.toString());
    }
}
