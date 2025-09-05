package habot.task;

import habot.exception.HaBotInvalidFormatException;

/**
 * Represents a to-do task that has no functional difference from the HaBot.Task.Task Class.
 */
public class ToDo extends Task {

    /**
     * Constructs a HaBot.Task.ToDo task with the specified description.
     *
     * @param description The description of the to-do task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the to-do task, prefixed with "[T]".
     *
     * @return A string representation of the to-do task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Converts the to-do task to a plain text format for saving.
     *
     * @return A string representation of the to-do task for storage.
     */
    @Override
    public String toStoreFormat() {
        return super.partsToStoreFormat("T", getMarkStatusIcon(), description);
    }

    /**
     * Creates a ToDo task from its stored string format.
     *
     * @param parts The parts of the stored string split by " | ".
     * @return A ToDo object represented by the given string.
     * @throws HaBotInvalidFormatException If the format is invalid.
     */
    public static ToDo fromStoreFormat(String... parts) throws HaBotInvalidFormatException {
        if (parts.length < 3) {
            throw new HaBotInvalidFormatException("ToDo", String.join(" | ", parts));
        }
        boolean isDone = !parts[1].equals(" ");
        String description = parts[2].replace("\\|", "|");
        ToDo todo = new ToDo(description);
        if (isDone) {
            todo.markAsDone();
        }
        return todo;
    }
}
