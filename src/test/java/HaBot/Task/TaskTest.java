package HaBot.Task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Task : factory parsing and error handling")
class TaskTest {

    @Test
    @DisplayName("Task.fromStoreFormat: invalid and unknown types")
    void task_fromStoreFormat_errors() {
        assertThrows(IllegalArgumentException.class, () -> ToDo.fromStoreFormat("T | X"), "Too few fields should throw");
        assertThrows(IllegalArgumentException.class, () -> Deadline.fromStoreFormat("D |   | desc"), "Too few fields should throw");
        assertThrows(IllegalArgumentException.class, () -> Event.fromStoreFormat("E |   | desc | 1/1/2020 0900"), "Too few fields should throw");

        Task t = Task.fromStoreFormat("T |   | alpha");
        assertEquals("[T][ ] alpha", t.toString());
        Exception ex = assertThrows(HaBot.Exception.HaBotException.class, () -> Task.fromStoreFormat("X |   | oops"));
        assertTrue(ex.getMessage().contains("Unknown task type"));
    }
}
