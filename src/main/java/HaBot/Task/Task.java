package HaBot.Task;

import HaBot.Exception.HaBotException;

/**
 * Represents a generic task with a description and completion status.
 */
public class Task {
    //The description of the task.
    protected String description;
    // Indicates whether the task is completed.
    protected boolean isDone;

    /**
     * Constructs a new HaBot.Task.Task with the specified description.
     * The task is initially not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {

        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if done, otherwise a space.
     */
    public String getMarkStatusIcon() {

        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the string representation of the task, including status and description.
     *
     * @return String representation of the task.
     */
    public String toString() {

        return "[" + this.getMarkStatusIcon() + "] " + this.description;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {

        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {

        this.isDone = false;
    }

    /**
     * Converts the task to a plain text format for saving.
     * Escapes the '|' character in the description to avoid conflicts.
     * @return A string representation of the task.
     */
    public String toStoreFormat() {
        throw new HaBotException("toStoreFormat() not implemented for Task class. Use subclasses instead.");
    }

    /**
     * Creates a Task object from a plain text format.
     * Unescapes the '|' character in the description.
     * @param text The plain text representation of the task.
     * @return A Task object.
     */
    public static Task fromStoreFormat(String text) throws HaBotException {
        String[] parts = text.split(" \\| ", 2); // Split into all parts
        if (parts.length < 2) {
            throw new HaBotException("Invalid task format: " + text);
        }
        String type = parts[0];
        String[] restParts = new String[parts.length - 1];
        return switch (type) {
        case "T" -> ToDo.fromStoreFormat(text);
        case "D" -> Deadline.fromStoreFormat(text);
        case "E" -> Event.fromStoreFormat(text);
        default -> throw new HaBotException("Unknown task type: " + type);
        };
    }
}