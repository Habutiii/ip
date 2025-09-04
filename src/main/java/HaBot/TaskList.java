package habot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import habot.exception.HaBotException;
import habot.task.Task;

/**
 * Manages a list of HaBot.Task.Task objects, providing methods to add, remove, retrieve, list, and mark tasks.
 */
public class TaskList {
    /**
     * The list of tasks managed by this TaskManager.
     */
    private final ArrayList<Task> tasks;

    /**
     * Constructs a new TaskManager with an empty list of tasks.
     */
    public TaskList() throws HaBotException {
        this.tasks = new ArrayList<>(); // Load tasks from file on initialization
    }

    /**
     * Constructs a TaskList and loads tasks from the given list of strings.
     * Each string represents a task in a specific storage format.
     *
     * @param lines The list of strings representing stored tasks.
     * @throws HaBotException If there is an error during loading.
     */
    public TaskList(List<String> lines) throws HaBotException {
        this();
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue; // skip empty line
            }
            try {
                Task task = Task.fromStoreFormat(line);
                this.tasks.add(task);

            } catch (HaBotException e) {
                throw new HaBotException("Error loading task from file: " + e.getMessage());
            }
        }
    }

    /**
     * Converts the current list of tasks to a list of strings in storage format.
     *
     * @return A list of strings representing the tasks in storage format.
     * @throws HaBotException If there is an error during conversion.
     */
    public List<String> toStoreFormat() throws HaBotException {
        // Save the tasks to plain text format
        return tasks.stream()
                .map(Task::toStoreFormat)
                .toList();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Validates that the given index is within the bounds of the task list.
     *
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
     *
     * @param task The task to add.
     */
    public void add(Task task) throws HaBotException {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index The index of the task to remove (0-based).
     * @return The removed HaBot.Task.Task.
     * @throws HaBotException If the index is out of bounds.
     */
    public Task remove(int index) throws HaBotException {
        validateIndex(index);
        Task removedTask = tasks.get(index);
        tasks.remove(index);
        return removedTask;
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index The index of the task to retrieve (0-based).
     * @return The HaBot.Task.Task at the given index.
     * @throws HaBotException If the index is out of bounds.
     */
    public Task get(int index) throws HaBotException {
        validateIndex(index);
        return tasks.get(index);
    }

    /**
     * Returns a formatted string listing all tasks.
     *
     * @return A string listing all tasks, each on a new line.
     * @throws HaBotException If there are no tasks stored yet.
     */
    public String list() throws HaBotException {
        if (tasks.isEmpty()) {
            throw new HaBotException("No task stored yet.");
        }
        return IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Marks or unmarks the task at the specified index as done or not done.
     *
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
        return task;
    }
}
