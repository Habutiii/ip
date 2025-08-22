/**
 * Represents a generic task with a description and completion status.
 */
public class Task {
    //The description of the task.
    protected String description;
    // Indicates whether the task is completed.
    protected boolean isDone;

    /**
     * Constructs a new Task with the specified description.
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
    public String getStatusIcon() {

        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the string representation of the task, including status and description.
     *
     * @return String representation of the task.
     */
    public String toString() {

        return "[" + this.getStatusIcon() + "] " + this.description;
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
}