package habot;

import java.util.ArrayList;
import java.util.List;

import habot.exception.HaBotException;


/**
 * Manages the storage of tasks to and from a file.
 */
public class Storage {
    private final java.io.File storageFile;

    /**
     * Constructs a HaBot.Storage object with the specified file path.
     *
     * @param filePath The path to the file where tasks will be stored.
     */
    public Storage(String filePath) {
        this.storageFile = new java.io.File(filePath);
        if (!storageFile.exists()) {
            try {
                // Create the file if it does not exist
                storageFile.createNewFile();
            } catch (java.io.IOException e) {
                throw new RuntimeException("Failed to create storage file: " + e.getMessage());
            }
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of task strings loaded from the file.
     * @throws HaBotException
     */
    public ArrayList<String> loadTasks() throws HaBotException {
        ArrayList<String> lines = new ArrayList<>();
        // Check if the file exists before attempting to load
        if (!storageFile.exists()) {
            // No saved tasks found. Starting with an empty task list.
            return lines;
        }

        // Load the tasks from plain text format
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(storageFile))) {
            String line;
            // read all lines and store them in a String list
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (java.io.IOException e) {
            throw new HaBotException("Error reading from file: " + e.getMessage());
        }
    }

    /**
     * Saves the given list of task strings to the storage file.
     *
     * @param lines
     * @throws HaBotException
     */
    public void save(List<String> lines) throws HaBotException {
        // Save the tasks to plain text format
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(storageFile))) {
            for (String line : lines) {
                // Write each line to the file
                writer.println(line);
            }
        } catch (java.io.IOException e) {
            throw new HaBotException("Error writing to file: " + e.getMessage());
        }
    }
}
