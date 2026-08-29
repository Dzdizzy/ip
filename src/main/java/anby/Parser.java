package anby;

/**
 * Parses user input into commands and command arguments.
 */
public class Parser {
    /**
     * Creates a parser.
     */
    public Parser() {
    }

    /**
     * Splits user input into a command word and the remaining arguments.
     *
     * @param input full user input
     * @return array containing the command word and, if present, the remaining arguments
     */
    public String[] splitInput(String input) {
        return input.split(" ", 2);
    }

    /**
     * Converts a command word into the matching command.
     *
     * @param commandWord command word entered by the user
     * @return matching command
     * @throws IllegalArgumentException if the command word is not recognised
     */
    public Command parseCommand(String commandWord) {
        return Command.valueOf(commandWord.toUpperCase());
    }

    /**
     * Returns the task number argument from a command.
     *
     * @param parts command input split into command word and arguments
     * @param errorMessage message to show if the task number is missing
     * @return task number argument
     * @throws AnbyException if the task number is missing
     */
    public String parseTaskNumber(String[] parts, String errorMessage) throws AnbyException {
        if (parts.length < 2) {
            throw new AnbyException(errorMessage);
        }

        return parts[1];
    }

    /**
     * Returns the description for a todo command.
     *
     * @param parts todo input split into command word and arguments
     * @return todo description
     * @throws AnbyException if the description is missing
     */
    public String parseTodo(String[] parts) throws AnbyException {
        if (parts.length == 1) {
            throw new AnbyException("hey you forgot to put a todo haha");
        }

        return parts[1];
    }

    /**
     * Parses a deadline command into task description and due date.
     *
     * @param parts deadline input split into command word and arguments
     * @return array containing the deadline description and due date
     * @throws AnbyException if the description or due date is missing
     */
    public String[] parseDeadline(String[] parts) throws AnbyException {
        if (parts.length == 1) {
            throw new AnbyException("hey you forgot to put a deadline task haha");
        }

        String[] deadlineParts = parts[1].split("/by", 2);

        if (deadlineParts.length != 2) {
            throw new AnbyException("hey you forgot to put a deadline on the task\n(do deadline /by [time])");
        }

        return deadlineParts;
    }

    /**
     * Parses an event command into task description, start date, and end date.
     *
     * @param parts event input split into command word and arguments
     * @return array containing the event description, start date, and end date
     * @throws AnbyException if the description, start date, or end date is missing
     */
    public String[] parseEvent(String[] parts) throws AnbyException {
        if (parts.length == 1) {
            throw new AnbyException("hey you forgot to put an event haha");
        }

        String[] eventParts = parts[1].split("/from", 2);

        if (eventParts.length != 2) {
            throw new AnbyException("hey you forgot to put a start time\n(do event /from [time] /to [time])");
        }

        String[] timeParts = eventParts[1].split("/to", 2);

        if (timeParts.length != 2) {
            throw new AnbyException("hey you forgot to put an end time\n(do event /from [time] /to [time])");
        }

        return new String[] {
                eventParts[0],
                timeParts[0],
                timeParts[1]
        };
    }
}
