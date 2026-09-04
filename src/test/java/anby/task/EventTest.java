package anby.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void toString_validDates_returnsFormattedDates() {
        Event event = new Event("my backyard burger fest", "2026-08-12", "2026-08-22");

        assertEquals("[E][ ] my backyard burger fest (from: Aug 12 2026, to: Aug 22 2026)", event.toString());
    }

    @Test
    public void constructor_startDateAfterEndDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Event("burger fest but after time warping", "2026-08-12", "2021-08-12"));
    }
}
