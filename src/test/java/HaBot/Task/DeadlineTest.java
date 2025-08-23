package HaBot.Task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Deadline: toString format and store round-trip")
class DeadlineTest {

    @Test
    @DisplayName("toString includes formatted By and status toggles")
    void toString_format() {
        Deadline d = new Deadline("submit report", "2/12/2019 1800");
        assertEquals("[D][ ] submit report (By: Dec 2 2019 18:00)", d.toString());
        d.markAsDone();
        assertEquals("[D][X] submit report (By: Dec 2 2019 18:00)", d.toString());
    }

    @Test
    @DisplayName("store round-trip preserves content and done state")
    void store_roundtrip() {
        Deadline d = new Deadline("submit report", "2/12/2019 1800");
        String stored = d.toStoreFormat();
        assertEquals("D |   | submit report | 2/12/2019 1800", stored);
        Task parsed = Task.fromStoreFormat(stored);
        assertInstanceOf(Deadline.class, parsed);
        assertEquals(d.toString(), parsed.toString());

        d.markAsDone();
        stored = d.toStoreFormat();
        assertEquals("D | X | submit report | 2/12/2019 1800", stored);
        parsed = Task.fromStoreFormat(stored);
        assertInstanceOf(Deadline.class, parsed);
        assertTrue(parsed.toString().startsWith("[D][X] submit report"));
    }
}

