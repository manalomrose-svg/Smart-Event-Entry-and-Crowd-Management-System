import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class DeniedEntry {
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    // Creating a record captures the date and time of one denied attempt.
    public DeniedEntry(String name) {
        LocalDateTime now = LocalDateTime.now();
        this.name = name;
        this.date = now.toLocalDate();
        this.time = now.toLocalTime();
    }

    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    // TODO: Agree on where the collection of denied attempts is stored.
    public void recordDeniedAttempt() {
        throw new UnsupportedOperationException("Denied-entry collection is pending.");
    }

    public java.util.List<DeniedEntry> provideDeniedEntryList() {
        throw new UnsupportedOperationException("Denied-entry collection is pending.");
    }
}
