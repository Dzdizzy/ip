package anby;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import anby.task.Todo;

public class ParserTest {
    @Test
    public void parseCommand_fullCommand_returnsCommand() {
        Parser parser = new Parser();

        assertEquals(Command.TODO, parser.parseCommand("todo"));
    }

    @Test
    public void parseCommand_alias_returnsCommand() {
        Parser parser = new Parser();

        assertEquals(Command.LIST, parser.parseCommand("ls"));
        assertEquals(Command.MARK, parser.parseCommand("m"));
        assertEquals(Command.UNMARK, parser.parseCommand("um"));
        assertEquals(Command.DELETE, parser.parseCommand("del"));
        assertEquals(Command.BYE, parser.parseCommand("q"));
        assertEquals(Command.TODO, parser.parseCommand("t"));
        assertEquals(Command.DEADLINE, parser.parseCommand("d"));
        assertEquals(Command.EVENT, parser.parseCommand("e"));
        assertEquals(Command.FIND, parser.parseCommand("f"));
    }

    @Test
    public void parseEvent_validEvent_returnsEventParts() throws AnbyException {
        Parser parser = new Parser();
        String[] parts = parser.splitInput("event eat burgers for 3 meals /from 2010-01-01 /to 2026-08-28");

        assertArrayEquals(new String[] {"eat burgers for 3 meals", "2010-01-01", "2026-08-28"},
                parser.parseEvent(parts));
    }

    @Test
    public void parseEvent_missingFrom_throwsAnbyException() {
        Parser parser = new Parser();
        String[] parts = parser.splitInput("event eat burgers /to 2026-08-28");

        assertThrows(AnbyException.class, () -> parser.parseEvent(parts));
    }

    @Test
    public void parseEvent_missingTo_throwsAnbyException() {
        Parser parser = new Parser();
        String[] parts = parser.splitInput("event eat burgers /from 2010-01-01");

        assertThrows(AnbyException.class, () -> parser.parseEvent(parts));
    }

    @Test
    public void parseDeadline_missingBy_throwsAnbyException() {
        Parser parser = new Parser();
        String[] parts = parser.splitInput("deadline eat burger");

        assertThrows(AnbyException.class, () -> parser.parseDeadline(parts));
    }

    @Test
    public void add_multipleTasks_addsAllTasks() {
        TaskList tasks = new TaskList(new ArrayList<>());

        tasks.add(new Todo("eat burger"), new Todo("drink tea"));

        assertEquals(2, tasks.size());
    }
}
