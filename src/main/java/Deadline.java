import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate date;

    public Deadline(String name, String date) {
        super(name);
        this.date = LocalDate.parse(date);
    }

    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + this.date;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
