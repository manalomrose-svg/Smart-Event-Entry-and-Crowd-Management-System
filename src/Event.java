import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Event {
    private final LocalDate date;
    private final LocalTime time;
    private final String eventType;
    private final int expectedPopulation;

    public Event(LocalDate date, LocalTime time, String eventType, int expectedPopulation) {
        if (expectedPopulation < 25 || expectedPopulation > 50) {
            throw new IllegalArgumentException("Expected population must be 25 to 50.");
        }
        this.date = Objects.requireNonNull(date);
        this.time = Objects.requireNonNull(time);
        this.eventType = eventType;
        this.expectedPopulation = expectedPopulation;
    }

    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getEventType() { return eventType; }
    public int getExpectedPopulation() { return expectedPopulation; }
    public boolean validateExpectedPopulation() {
        return expectedPopulation >= 25 && expectedPopulation <= 50;
    }
}
