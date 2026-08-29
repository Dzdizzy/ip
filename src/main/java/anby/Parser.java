package anby;

public class Parser {
    public String[] splitInput(String input) {
        return input.split(" ", 2);
    }

    public Command parseCommand(String commandWord) {
        return Command.valueOf(commandWord.toUpperCase());
    }

    public String parseTaskNumber(String[] parts, String errorMessage) throws AnbyException {
        if (parts.length < 2) {
            throw new AnbyException(errorMessage);
        }

        return parts[1];
    }

    public String parseFind(String[] parts) throws AnbyException {
        if (parts.length == 1 || parts[1].trim().isEmpty()) {
            throw new AnbyException("hey you didnt put in anything to find");
        }

        return parts[1].trim();
    }

    public String parseTodo(String[] parts) throws AnbyException {
        if (parts.length == 1 || parts[1].trim().isEmpty()) {
            throw new AnbyException("hey you forgot to put a todo haha");
        }

        return parts[1].trim();
    }

    public String[] parseDeadline(String[] parts) throws AnbyException {
        if (parts.length == 1) {
            throw new AnbyException("hey you forgot to put a deadline task haha");
        }

        String[] deadlineParts = parts[1].split("/by", 2);

        if (deadlineParts.length != 2) {
            throw new AnbyException("hey you forgot to put a deadline on the task\n(do deadline /by [time])");
        }

        return new String[] {
                deadlineParts[0].trim(),
                deadlineParts[1].trim()
        };
    }

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
                eventParts[0].trim(),
                timeParts[0].trim(),
                timeParts[1].trim()
        };
    }
}
