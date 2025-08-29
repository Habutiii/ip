package HaBot.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start and end time.
 * Extends the HaBot.Task.Task class and adds 'from' and 'to' fields.
 */
public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

    protected static final DateTimeFormatter DATE_FORMATTER_PARSE = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    protected static final DateTimeFormatter DATE_FORMATTER_PRINT = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

    /**
     * Constructs an HaBot.Task.Event task with a description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from The start time of the event in LocalDateTime format.
     * @param to The end time of the event in LocalDateTime format.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs an HaBot.Task.Event task with a description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from The start time of the event in String format.
     * @param to The end time of the event in String format.
     */
    public Event(String description, String from, String to) {
        this(description,
                LocalDateTime.parse(from, DATE_FORMATTER_PARSE),
                LocalDateTime.parse(to, DATE_FORMATTER_PARSE));
    }

    /**
     * Returns a string representation of the event, including its type, description, and time range.
     *
     * @return
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (From: " + from.format(DATE_FORMATTER_PRINT)
                + " To: " + to.format(DATE_FORMATTER_PRINT) + ")";
    }

    @Override
    public String toStoreFormat() {
        String escapedDescription = description.replace("|", "\\|");
        return "E | " + getMarkStatusIcon()
                + " | " + escapedDescription
                + " | " + from.format(DATE_FORMATTER_PARSE)
                + " | " + to.format(DATE_FORMATTER_PARSE);
    }

    /**
     * Creates an Event from its stored string representation.
     *
     * @param text The stored string representation of the event.
     * @return The reconstructed Event object.
     * @throws IllegalArgumentException If the input format is invalid.
     */
    public static Event fromStoreFormat(String text) {
        String[] parts = text.split(" \\| ", -1);
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid Event format: " + text);
        }
        boolean isDone = parts[1].equals("X");
        String description = parts[2].replace("\\|", "|");
        LocalDateTime from = LocalDateTime.parse(parts[3], DATE_FORMATTER_PARSE);
        LocalDateTime to = LocalDateTime.parse(parts[4], DATE_FORMATTER_PARSE);
        Event event = new Event(description, from, to);
        if (isDone) {
            event.markAsDone();
        }
        return event;
    }
}
