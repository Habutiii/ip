/**
 * Represents an event task with a start and end time.
 * Extends the Task class and adds 'from' and 'to' fields.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Constructs an Event task with a description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toStoreFormat() {
        String escapedDescription = description.replace("|", "\\|");
        return "E|" + getMarkStatusIcon() + "|" + escapedDescription + "|" + from + "|" + to;
    }

    public static Event fromStoreFormat(String text) {
        String[] parts = text.split("\\|", -1);
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid Event format: " + text);
        }
        boolean isDone = parts[1].equals("X");
        String description = parts[2].replace("\\|", "|");
        String from = parts[3];
        String to = parts[4];
        Event event = new Event(description, from, to);
        if (isDone) {
            event.markAsDone();
        }
        return event;
    }
}
