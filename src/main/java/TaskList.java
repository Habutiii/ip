import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of Task objects, providing methods to add, remove, retrieve, list, and mark tasks.
 */
public class TaskList {
    /**
     * The list of tasks managed by this TaskManager.
     */
    private final ArrayList<Task> tasks;

    private final Storage storage;

    /**
     * Constructs a new TaskManager with an empty list of tasks.
     */
    public TaskList(String filePath) throws HaBotException {
        this.storage = new Storage(filePath);
        this.tasks = this.load(); // Load tasks from file on initialization
    }

    private ArrayList<Task> load() throws HaBotException {
        // Initialize an empty list to hold the tasks
        ArrayList<Task> tasks = new ArrayList<>();
        // Load the tasks from plain text format
        ArrayList<String> lines = storage.load();
        // Parse each line into a Task object and add it to the list
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue; // skip empty line
            }
            try {
                Task task = Task.fromStoreFormat(line);
                tasks.add(task);
            } catch (HaBotException e) {
                throw new HaBotException("Error loading task from file: " + e.getMessage());
            }
        }
        return tasks;
    }

    private void save() throws HaBotException {
        // Save the tasks to plain text format
        List<String> lines = tasks.stream()
                .map(Task::toStoreFormat)
                .toList();
        // Save the lines to storage
        storage.save(lines);
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
