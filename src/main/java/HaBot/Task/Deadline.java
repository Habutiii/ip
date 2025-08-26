package HaBot.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * Extends the HaBot.Task.Task class and adds a deadline date/time.
 */
public class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Formatter for parsing and printing date/time.
     */
    protected static final DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    protected static final DateTimeFormatter printFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

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

        this(description, LocalDateTime.parse(by, parseFormatter));
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
                + " (By: " + by.format(printFormatter) + ")";
    }

    @Override
    public String toStoreFormat() {
        String escapedDescription = description.replace("|", "\\|");
        return "D | " + getMarkStatusIcon()
                + " | " + escapedDescription
                + " | " + by.format(parseFormatter);
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
        LocalDateTime by = LocalDateTime.parse(parts[3], parseFormatter);
        Deadline deadline = new Deadline(description, by);
        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }
}
