package anby;

import anby.task.Deadline;
import anby.task.Event;
import anby.task.Task;
import anby.task.Todo;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

/**
 * Entry point for the Anby chatbot.
 */
public class Anby {
    private Anby() {
    }

    /**
     * Starts the Anby chatbot and handles user commands until the user exits.
     *
     * @param args command line arguments supplied to the program
     * @throws IOException if task data cannot be saved
     */
    public static void main(String[] args) throws IOException {
        String[] badInput = {
                "what are ya tryna say?",
                "burger?",
                "please speak burger or english only"
        };

        Random random = new Random();
        Parser parser = new Parser();
        Ui ui = new Ui();

        TaskList tasks = new TaskList(Storage.loadTasks());

        Scanner scanner = new Scanner(System.in);

        ui.showGreeting();

        while (true) {
            String input = scanner.nextLine();
            String[] parts = parser.splitInput(input);

            try {
                Command command;
                try {
                    command = parser.parseCommand(parts[0]);
                } catch (IllegalArgumentException e) { // catch illegal or unrecognised command
                    throw new AnbyException(badInput[random.nextInt(badInput.length)]);
                }

                switch (command) {
                    case LIST: {
                        ui.showList(tasks);
                        break;
                    }
                    case MARK: {
                        String taskNumber = parser.parseTaskNumber(parts, "hey give me a valid task number to mark!");
                        Task currTask = tasks.getTask(taskNumber);
                        tasks.mark(taskNumber);
                        Storage.saveTasks(tasks.getTasks());
                        ui.showMarked(currTask);
                        break;
                    }
                    case UNMARK: {
                        String taskNumber = parser.parseTaskNumber(parts, "hey give me a valid task number to unmark!");
                        Task currTask = tasks.getTask(taskNumber);
                        tasks.unmark(taskNumber);
                        Storage.saveTasks(tasks.getTasks());
                        ui.showUnmarked(currTask);
                        break;
                    }
                    case DELETE: {
                        String taskNumber = parser.parseTaskNumber(parts, "hey give me a valid task number to delete!");
                        Task removedTask = tasks.delete(taskNumber);
                        Storage.saveTasks(tasks.getTasks());
                        ui.showDeleted(removedTask);
                        break;
                    }
                    case BYE: {
                        ui.showGoodbye();
                        return;
                    }
                    case TODO: {
                        Todo newTodo = new Todo(parser.parseTodo(parts));
                        tasks.add(newTodo);
                        Storage.saveTasks(tasks.getTasks());
                        ui.showAdded(newTodo, tasks.size());
                        break;
                    }
                    case DEADLINE: {
                        String[] deadlineParts = parser.parseDeadline(parts);

                        try {
                            Deadline newDeadline = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                            tasks.add(newDeadline);
                            Storage.saveTasks(tasks.getTasks());
                            ui.showAdded(newDeadline, tasks.size());
                        } catch (DateTimeParseException e) {
                            throw new AnbyException("yo use this date format for the deadline: yyyy-mm-dd");
                        }

                        break;
                    }
                    case EVENT: {
                        String[] eventParts = parser.parseEvent(parts);

                        try {
                            Event newEvent = new Event(eventParts[0].trim(), eventParts[1].trim(),
                                    eventParts[2].trim());
                            tasks.add(newEvent);
                            Storage.saveTasks(tasks.getTasks());
                            ui.showAdded(newEvent, tasks.size());
                        } catch (DateTimeParseException e) {
                            throw new AnbyException("yo use this date format for the event timings: yyyy-mm-dd");
                        } catch (IllegalArgumentException e) {
                            throw new AnbyException("bruh your end date is before the start date");
                        }

                        break;
                    }
                }
            } catch (AnbyException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
