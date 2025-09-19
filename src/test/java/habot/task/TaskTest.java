package habot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import habot.exception.HaBotException;
import habot.exception.HaBotInvalidFormatException;


@DisplayName("Task : factory parsing and error handling")
class TaskTest {

    @Test
    @DisplayName("Task.fromStoreFormat: invalid and unknown types")
    void taskFromStoreFormat_errors() {
        assertThrows(HaBotInvalidFormatException.class, () -> ToDo.fromStoreFormat(
                "T | X"),
                "Too few fields should throw");
        assertThrows(HaBotInvalidFormatException.class, () -> Deadline.fromStoreFormat(
                "D |   | desc"),
                "Too few fields should throw");
        assertThrows(HaBotInvalidFormatException.class, () -> Event.fromStoreFormat(
                "E |   | desc | 1/1/2020 0900"),
                "Too few fields should throw");

        Task t = Task.fromStoreFormat("T |   | alpha");
        assertEquals("[T][ ] alpha", t.toString());
        Exception ex = assertThrows(
                HaBotException.class, () -> Task.fromStoreFormat(
                        "X |   | oops"));
        assertTrue(ex.getMessage().contains("Invalid task format"));
    }

    @Test
    @DisplayName("Task: mark done and undone")
    void taskMarkDoneAndUndone() {
        Task task = Task.fromStoreFormat("T |   | read book");
        assertEquals("[T][ ] read book", task.toString());
        task.markAsDone();
        assertEquals("[T][X] read book", task.toString());
        task.markAsNotDone();
        assertEquals("[T][ ] read book", task.toString());
    }
}
