import java.util.ArrayList;

/**
 * Manages a list of Task objects, providing methods to add, remove, retrieve, list, and mark tasks.
 */
public class TaskManager {
    /**
     * The list of tasks managed by this TaskManager.
     */
    private final ArrayList<Task> tasks;

    /**
     * Constructs a new TaskManager with an empty list of tasks.
     */
    public TaskManager() throws HaBotException {
        this.tasks = new ArrayList<>();
        load(); // Load tasks from file on initialization
    }

    /**
     * Returns the number of tasks in the list.
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Validates that the given index is within the bounds of the task list.
     * @param index The index to validate.
     * @throws HaBotException If the index is out of bounds.
     */
    private void validateIndex(int index) throws HaBotException {
        if (index < 0 || index >= tasks.size()) {
            throw new HaBotException("Invalid task index.");
        }
    }

    private void save() throws HaBotException {
        String path = "tasks.txt";
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(path))) {
            for (Task task : tasks) {
                writer.println(task.toStoreFormat());
            }
        } catch (java.io.IOException e) {
            throw new HaBotException("Error saving tasks: " + e.getMessage());
        }
    }

    private void load() throws HaBotException {
        String path = "tasks.txt";
        java.io.File file = new java.io.File(path);

        // Check if the file exists before attempting to load
        if (!file.exists()) {
            // No saved tasks found. Starting with an empty task list.
            return;
        }

        // Load the tasks from plain text format
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            tasks.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(Task.fromStoreFormat(line));
            }
        } catch (java.io.IOException e) {
            throw new HaBotException("Error loading tasks: " + e.getMessage());
        }
    }

    /**
     * Adds a task to the list.
     * @param task The task to add.
     */
    public void add(Task task) throws HaBotException {
        tasks.add(task);
        save();
    }

    /**
     * Removes and returns the task at the specified index.
     * @param index The index of the task to remove (0-based).
     * @return The removed Task.
     * @throws HaBotException If the index is out of bounds.
     */
    public Task remove(int index) throws HaBotException {
        validateIndex(index);
        Task removedTask = tasks.get(index);
        tasks.remove(index);
        save();
        return removedTask;
    }

    /**
     * Retrieves the task at the specified index.
     * @param index The index of the task to retrieve (0-based).
     * @return The Task at the given index.
     * @throws HaBotException If the index is out of bounds.
     */
    public Task get(int index) throws HaBotException {
        validateIndex(index);
        return tasks.get(index);
    }

    /**
     * Returns a formatted string listing all tasks.
     * @return A string listing all tasks, each on a new line.
     * @throws HaBotException If there are no tasks stored yet.
     */
    public String list() throws HaBotException{
        if (tasks.isEmpty()) {
            throw new HaBotException("No task stored yet.");
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            out.append(i + 1).append(".").append(tasks.get(i));
            if (i < tasks.size() - 1) {
                out.append("\n");
            }
        }
        return out.toString();
    }

    /**
     * Marks or unmarks the task at the specified index as done or not done.
     * @param index The index of the task to mark (0-based).
     * @param isDone True to mark as done, false to unmark.
     * @throws HaBotException If the index is out of bounds.
     */
    public Task mark(int index, boolean isDone) throws HaBotException {
        validateIndex(index);
        Task task = tasks.get(index);
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
        save();
        return task;
    }
}
