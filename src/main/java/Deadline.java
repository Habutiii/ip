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
}
