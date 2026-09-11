package anby;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import anby.task.Deadline;
import anby.task.Event;
import anby.task.Task;
import anby.task.Todo;

/**
 * Handles loading and saving tasks from persistent storage.
 */
public class Storage {
    private static final String FILE_PATH = "data/anby.txt";

    private Storage() {
    }

    /**
     * Loads saved tasks from the task data file.
     *
     * @return tasks loaded from disk, or an empty list if the file does not exist
     */
    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) { // load an empty ArrayList
            return tasks;
        }

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTask(line);
                tasks.add(task);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return tasks;
        }

        return tasks;
    }

    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "Saved task line should contain type, done status, and description";

        Task task;

        switch (parts[0]) {
            case "T":
                assert parts.length == 3;
                task = new Todo(parts[2]);
                break;
            case "D":
                assert parts.length == 4;
                task = new Deadline(parts[2], parts[3]);
                break;
            case "E":
                assert parts.length == 5;
                task = new Event(parts[2], parts[3], parts[4]);
                break;
            default:
                assert false : "Unknown saved task type: " + parts[0];
                throw new IllegalArgumentException("Unknown task type: " + parts[0]);
        }

        if (parts[1].equals("1")) {
            task.markAsDone();
        }

        return task;
    }

    /**
     * Saves the given tasks to the task data file.
     *
     * @param tasks tasks to save
     * @throws IOException if the task data file cannot be written
     */
    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        FileWriter fileWriter = new FileWriter(file);

        for (int i = 0; i < tasks.size(); i++) {
            fileWriter.write(tasks.get(i).toFileString() + System.lineSeparator());
        }

        fileWriter.close();
    }
}
