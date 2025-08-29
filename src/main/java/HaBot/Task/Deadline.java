package habot.task;

import java.time.LocalDateTime;

/**
 * Represents a task with a deadline.
 * Extends the HaBot.Task.Task class and adds a deadline date/time.
 */
public class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Constructs a HaBot.Task.Deadline task with a description and deadline date/time.
     * @param description The description of the task.
     * @param by The deadline date/time for the task in LocalDateTime format.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Constructs a HaBot.Task.Deadline task with a description and deadline.
     *
     * @param description The description of the task.
     * @param by The deadline date/time for the task in String format.
     */
    public Deadline(String description, String by) {

        this(description, LocalDateTime.parse(by, DATE_FORMATTER_PARSE));
    }

    /**
     * Returns a string representation of the HaBot.Task.Deadline task.
     * The format is: [D][status] description (by: MMM d yyyy HH:mm)
     * where status is "X" if the task is done, and " " if not done.
     *
     * @return A string representation of the HaBot.Task.Deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (By: " + by.format(DATE_FORMATTER_PRINT) + ")";
    }

    @Override
    public String toStoreFormat() {
        String escapedDescription = description.replace("|", "\\|");
        return "D | " + getMarkStatusIcon()
                + " | " + escapedDescription
                + " | " + by.format(DATE_FORMATTER_PARSE);
    }

    /**
     * Creates a Deadline task from its stored string representation.
     * The expected format is: D | status | description | by
     * where status is "X" if the task is done, and " " if not done.
     *
     * @param text The stored string representation of the Deadline task.
     * @return A Deadline task constructed from the string.
     * @throws IllegalArgumentException If the input string is not in the correct format.
     */
    public static Deadline fromStoreFormat(String text) {
        String[] parts = text.split(" \\| ", -1);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid Deadline format: " + text);
        }
        boolean isDone = parts[1].equals("X");
        String description = parts[2].replace("\\|", "|");
        LocalDateTime by = LocalDateTime.parse(parts[3], DATE_FORMATTER_PARSE);
        Deadline deadline = new Deadline(description, by);
        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }
}
