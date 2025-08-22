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

    protected static final DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    protected static final DateTimeFormatter printFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

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
                LocalDateTime.parse(from, parseFormatter),
                LocalDateTime.parse(to, parseFormatter));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (From: " + from.format(printFormatter)
                + " To: " + to.format(printFormatter) + ")";
    }

    @Override
    public String toStoreFormat() {
        String escapedDescription = description.replace("|", "\\|");
        return "E|" + getMarkStatusIcon()
                + "|" + escapedDescription
                + "|" + from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                + "|" + to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static Event fromStoreFormat(String text) {
        String[] parts = text.split("\\|", -1);
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid HaBot.Task.Event format: " + text);
        }
        boolean isDone = parts[1].equals("X");
        String description = parts[2].replace("\\|", "|");
        LocalDateTime from = LocalDateTime.parse(parts[3]);
        LocalDateTime to = LocalDateTime.parse(parts[4]);
        Event event = new Event(description, from, to);
        if (isDone) {
            event.markAsDone();
        }
        return event;
    }
}
