package anby;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

import anby.task.Deadline;
import anby.task.Event;
import anby.task.Task;
import anby.task.Todo;

/**
 * Entry point for the Anby chatbot.
 */
public class Anby {
    private static final String[] BAD_INPUT_MESSAGES = {
        "what are ya tryna say?",
        "burger?",
        "please speak burger or english only"
    };

    private final Parser parser;
    private final Random random;
    private final TaskList tasks;
    private final Ui ui;
    private boolean isExit;

    /**
     * Creates the Anby chatbot.
     */
    public Anby() {
        parser = new Parser();
        random = new Random();
        tasks = new TaskList(Storage.loadTasks());
        ui = new Ui();
        isExit = false;

        assert parser != null;
        assert random != null;
        assert tasks != null;
        assert ui != null;
    }

    /**
     * Starts the Anby chatbot and handles user commands until the user exits.
     *
     * @param args command line arguments supplied to the program
     * @throws IOException if task data cannot be saved
     */
    public static void main(String[] args) throws IOException {
        Anby anby = new Anby();
        Scanner scanner = new Scanner(System.in);

        System.out.println(anby.getGreeting());

        while (!anby.isExit()) {
            String input = scanner.nextLine();
            System.out.println(anby.getResponse(input));
        }
    }

    /**
     * Returns the greeting message.
     *
     * @return greeting message
     */
    public String getGreeting() {
        return ui.getGreeting();
    }

    /**
     * Returns the greeting message to show in the GUI.
     *
     * @return GUI greeting message
     */
    public String getGuiGreeting() {
        return "Hey, I'm Anby\nWhat do you need me for? I accept payment only in burgers";
    }

    /**
     * Returns whether the user has asked Anby to exit.
     *
     * @return true if the current session should exit
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Processes one user input and returns Anby's response.
     *
     * @param input user input
     * @return response to show to the user
     */
    public String getResponse(String input) {
        String[] parts = parser.splitInput(input);
        assert parts.length >= 1 : "Split input should always include the command word";

        try {
            Command command = parseCommand(parts[0]);
            assert command != null;
            return executeCommand(command, parts);
        } catch (AnbyException e) {
            return ui.getError(e.getMessage());
        }
    }

    private Command parseCommand(String commandWord) throws AnbyException {
        try {
            return parser.parseCommand(commandWord);
        } catch (IllegalArgumentException e) {
            throw new AnbyException(BAD_INPUT_MESSAGES[random.nextInt(BAD_INPUT_MESSAGES.length)]);
        }
    }

    private String executeCommand(Command command, String[] parts) throws AnbyException {
        assert command != null;
        assert parts != null;

        try {
            return executeCommandWithStorage(command, parts);
        } catch (IOException e) {
            throw new AnbyException("uh oh i couldn't save your tasks");
        }
    }

    private String executeCommandWithStorage(Command command, String[] parts) throws IOException, AnbyException {
        assert command != null;
        assert parts != null;

        switch (command) {
            case LIST:
                return ui.getList(tasks);
            case MARK:
                return markTask(parts);
            case UNMARK:
                return unmarkTask(parts);
            case DELETE:
                return deleteTask(parts);
            case FIND:
                return findTasks(parts);
            case BYE:
                isExit = true;
                return ui.getGoodbye();
            case TODO:
                return addTodo(parts);
            case DEADLINE:
                return addDeadline(parts);
            case EVENT:
                return addEvent(parts);
            default:
                assert false : "Unexpected command: " + command;
                throw new AnbyException(BAD_INPUT_MESSAGES[random.nextInt(BAD_INPUT_MESSAGES.length)]);
        }
    }

    private String markTask(String[] parts) throws IOException, AnbyException {
        String taskNumber = parser.parseTaskNumber(parts, "hey give me a valid task number to mark!");
        Task currTask = tasks.getTask(taskNumber);
        tasks.mark(taskNumber);
        Storage.saveTasks(tasks.getTasks());
        return ui.getMarked(currTask);
    }

    private String unmarkTask(String[] parts) throws IOException, AnbyException {
        String taskNumber = parser.parseTaskNumber(parts, "hey give me a valid task number to unmark!");
        Task currTask = tasks.getTask(taskNumber);
        tasks.unmark(taskNumber);
        Storage.saveTasks(tasks.getTasks());
        return ui.getUnmarked(currTask);
    }

    private String deleteTask(String[] parts) throws IOException, AnbyException {
        String taskNumber = parser.parseTaskNumber(parts, "hey give me a valid task number to delete!");
        Task removedTask = tasks.delete(taskNumber);
        Storage.saveTasks(tasks.getTasks());
        return ui.getDeleted(removedTask);
    }

    private String findTasks(String[] parts) throws AnbyException {
        String keyword = parser.parseFind(parts);
        assert !keyword.isEmpty();
        TaskList matchingTasks = tasks.find(keyword);
        return ui.getFindResults(matchingTasks);
    }

    private String addTodo(String[] parts) throws IOException, AnbyException {
        Todo newTodo = new Todo(parser.parseTodo(parts));
        tasks.add(newTodo);
        Storage.saveTasks(tasks.getTasks());
        return ui.getAdded(newTodo, tasks.size());
    }

    private String addDeadline(String[] parts) throws IOException, AnbyException {
        String[] deadlineParts = parser.parseDeadline(parts);
        assert deadlineParts.length == 2;

        try {
            Deadline newDeadline = new Deadline(deadlineParts[0], deadlineParts[1]);
            tasks.add(newDeadline);
            Storage.saveTasks(tasks.getTasks());
            return ui.getAdded(newDeadline, tasks.size());
        } catch (DateTimeParseException e) {
            throw new AnbyException("yo use this date format for the deadline: yyyy-mm-dd");
        }
    }

    private String addEvent(String[] parts) throws IOException, AnbyException {
        String[] eventParts = parser.parseEvent(parts);
        assert eventParts.length == 3;

        try {
            Event newEvent = new Event(eventParts[0], eventParts[1], eventParts[2]);
            tasks.add(newEvent);
            Storage.saveTasks(tasks.getTasks());
            return ui.getAdded(newEvent, tasks.size());
        } catch (DateTimeParseException e) {
            throw new AnbyException("yo use this date format for the event timings: yyyy-mm-dd");
        } catch (IllegalArgumentException e) {
            throw new AnbyException("bruh your end date is before the start date");
        }
    }
}
