package anby;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ParserTest {
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
}
