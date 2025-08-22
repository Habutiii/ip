/**
 * Represents a task with a deadline.
 * Extends the Task class and adds a deadline date/time.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Constructs a Deadline task with a description and deadline.
     *
     * @param description The description of the task.
     * @param by The deadline date/time for the task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toStoreFormat() {
        String escapedDescription = description.replace("|", "\\|");
        return "D|" + getMarkStatusIcon() + "|" + escapedDescription + "|" + by;
    }

    public static Deadline fromStoreFormat(String text) {
        String[] parts = text.split("\\|", -1);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid Deadline format: " + text);
        }
        boolean isDone = parts[1].equals("X");
        String description = parts[2].replace("\\|", "|");
        String by = parts[3];
        Deadline deadline = new Deadline(description, by);
        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }
}
