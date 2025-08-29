package habot;

import java.util.ArrayList;
import java.util.List;

import habot.exception.HaBotException;


/**
 * Manages the storage of tasks to and from a file.
 */
public class Storage {
    private final java.io.File file;

    /**
     * Constructs a HaBot.Storage object with the specified file path.
     *
     * @param filePath The path to the file where tasks will be stored.
     */
    public Storage(String filePath) {
        this.file = new java.io.File(filePath);
        if (!file.exists()) {
            try {
                // Create the file if it does not exist
                file.createNewFile();
            } catch (java.io.IOException e) {
                throw new RuntimeException("Failed to create storage file: " + e.getMessage());
            }
        }
    }

    /**
     * Loads tasks from the storage file.
     * @return
     * @throws HaBotException
     */
    public ArrayList<String> load() throws HaBotException {
        ArrayList<String> lines = new ArrayList<>();
        // Check if the file exists before attempting to load
        if (!file.exists()) {
            // No saved tasks found. Starting with an empty task list.
            return lines;
        }

        // Load the tasks from plain text format
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
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
     * @param lines
     * @throws HaBotException
     */
    public void save(List<String> lines) throws HaBotException {
        // Save the tasks to plain text format
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(file))) {
            for (String line : lines) {
                // Write each line to the file
                writer.println(line);
            }
        } catch (java.io.IOException e) {
            throw new HaBotException("Error writing to file: " + e.getMessage());
        }
    }
}
