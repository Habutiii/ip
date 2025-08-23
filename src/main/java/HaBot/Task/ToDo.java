package HaBot.Task;

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

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toStoreFormat() {
        String escapedDescription = description.replace("|", "\\|");
        return "T | " + getMarkStatusIcon() + " | " + escapedDescription;
    }

    public static ToDo fromStoreFormat(String text) {
        String[] parts = text.split(" \\| ", -1);
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid ToDo format: " + text);
        }
        boolean isDone = parts[1].equals("X");
        String description = parts[2].replace("\\|", "|");
        ToDo todo = new ToDo(description);
        if (isDone) {
            todo.markAsDone();
        }
        return todo;
    }
}
