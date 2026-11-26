import java.time.LocalDate;

// Starter class. Report values are not calculated until compile() is implemented.
public class EventReport {
    private final String event;
    private int noShows;
    private int peakHours;
    private int averageStay;

    public EventReport(String event) {
        this.event = event;
        noShows = 0;
        peakHours = 0;
        averageStay = 0;
    }

    public String getEvent() {
        return event;
    }

    public int getNoShows() {
        return noShows;
    }

    public int getPeakHours() {
        return peakHours;
    }

    public int getAverageStay() {
        return averageStay;
    }

    public void compile() {
        // TODO: Calculate attendance, no-shows, peak hours and average stay from records.
        throw new UnsupportedOperationException("Report compilation is not implemented yet.");
    }

    public void search(String name, LocalDate date, String event) {
        // TODO: Agree on the return type and implement report searching.
        throw new UnsupportedOperationException("Report searching is not implemented yet.");
    }
}
