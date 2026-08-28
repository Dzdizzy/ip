import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);

        if (this.from.isAfter(this.to)) {
            throw new IllegalArgumentException("From date: " + this.from + " is after To date: " + this.to);
        }
    }

    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | " + this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + this.from.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ", to: "
                + this.to.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
