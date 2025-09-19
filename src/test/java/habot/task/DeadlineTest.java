package habot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Deadline: toString format and store round-trip")
class DeadlineTest {

    @Test
    @DisplayName("toString includes formatted By and status toggles")
    void toStringFormat() {
        Deadline d = new Deadline("submit report", "2/12/2019 1800");
        assertEquals("[D][ ] submit report (By: 2 Dec 2019 18:00)", d.toString());
        d.markAsDone();
        assertEquals("[D][X] submit report (By: 2 Dec 2019 18:00)", d.toString());
    }

    @Test
    @DisplayName("store round-trip preserves content and done state")
    void storeRoundtrip() {
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

