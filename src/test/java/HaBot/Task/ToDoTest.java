package HaBot.Task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ToDo: toString format and store round-trip")
class ToDoTest {

    @Test
    @DisplayName("toString shows type, status, and description")
    void toString_format() {
        ToDo t = new ToDo("read book");
        assertEquals("[T][ ] read book", t.toString());
        t.markAsDone();
        assertEquals("[T][X] read book", t.toString());
    }

    @Test
    @DisplayName("store format and round-trip")
    void store_roundtrip_with_escape() {
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

