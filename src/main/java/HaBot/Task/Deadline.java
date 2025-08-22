package HaBot.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * Extends the HaBot.Task.Task class and adds a deadline date/time.
 */
public class Deadline extends Task {

    protected LocalDateTime by;

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

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (By: " + by.format(printFormatter) + ")";
    }

    @Override
    public String toStoreFormat() {
        String escapedDescription = description.replace("|", "\\|");
        return "D|" + getMarkStatusIcon()
                + "|" + escapedDescription
                + "|" + by.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static Deadline fromStoreFormat(String text) {
        String[] parts = text.split("\\|", -1);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid HaBot.Task.Deadline format: " + text);
        }
        boolean isDone = parts[1].equals("X");
        String description = parts[2].replace("\\|", "|");
        LocalDateTime by = LocalDateTime.parse(parts[3]);
        Deadline deadline = new Deadline(description, by);
        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }
}
