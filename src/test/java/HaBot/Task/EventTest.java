package HaBot.Task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Event: toString format and store round-trip")
class EventTest {

    @Test
    @DisplayName("toString includes formatted From/To and status toggles")
    void toString_format() {
        Event e = new Event("project meeting", "1/1/2020 0900", "1/1/2020 1030");
        assertEquals("[E][ ] project meeting (From: Jan 1 2020 09:00 To: Jan 1 2020 10:30)", e.toString());
        e.markAsDone();
        assertEquals("[E][X] project meeting (From: Jan 1 2020 09:00 To: Jan 1 2020 10:30)", e.toString());
    }

    @Test
    @DisplayName("store round-trip preserves content and done state")
    void store_roundtrip() {
        Event e = new Event("project meeting", "1/1/2020 0900", "1/1/2020 1030");
        String stored = e.toStoreFormat();
        assertEquals("E |   | project meeting | 1/1/2020 0900 | 1/1/2020 1030", stored);
        Task parsed = Task.fromStoreFormat(stored);
        assertInstanceOf(Event.class, parsed);
        assertEquals(e.toString(), parsed.toString());

        e.markAsDone();
        stored = e.toStoreFormat();
        assertEquals("E | X | project meeting | 1/1/2020 0900 | 1/1/2020 1030", stored);
        parsed = Task.fromStoreFormat(stored);
        assertInstanceOf(Event.class, parsed);
        assertTrue(parsed.toString().startsWith("[E][X] project meeting"));
    }
}

