package habot.task;

import java.time.LocalDateTime;

/**
 * Represents an event task with a start and end time.
 * Extends the HaBot.Task.Task class and adds 'from' and 'to' fields.
 */
public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

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
     * @return A string representation of the event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (From: " + from.format(DATE_FORMATTER_PRINT)
                + " To: " + to.format(DATE_FORMATTER_PRINT) + ")";
    }

    /**
     * Converts the event to its stored string format.
     *
     * @return The string representation of the event for storage.
     */
    @Override
    public String toStoreFormat() {
        return super.partsToStoreFormat(
                "E",
                getMarkStatusIcon(),
                description,
                from.format(DATE_FORMATTER_PARSE),
                to.format(DATE_FORMATTER_PARSE));
    }

    /**
     * Creates an Event from its stored string representation.
     *
     * @param parts The parts of the stored string split by " | ".
     * @return The reconstructed Event object.
     * @throws IllegalArgumentException If the input format is invalid.
     */
    public static Event fromStoreFormat(String... parts) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid Event format: " + String.join(" | ", parts));
        }
        boolean isDone = !parts[1].equals(" ");
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
