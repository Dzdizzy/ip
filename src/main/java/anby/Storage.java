package anby;

import anby.task.Deadline;
import anby.task.Event;
import anby.task.Task;
import anby.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String FILE_PATH = "data/anby.txt";

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
        Task task;

        switch (parts[0]) {
        case "T":
            task = new Todo(parts[2]);
            break;
        case "D":
            task = new Deadline(parts[2], parts[3]);
            break;
        case "E":
            task = new Event(parts[2], parts[3], parts[4]);
            break;
        default:
            throw new IllegalArgumentException("Unknown task type: " + parts[0]);
        }

        if (parts[1].equals("1")) {
            task.markAsDone();
        }

        return task;
    }

    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file);

        for (int i = 0; i < tasks.size(); i++) {
            fw.write(tasks.get(i).toFileString() + System.lineSeparator());
        }

        fw.close();
    }
}
