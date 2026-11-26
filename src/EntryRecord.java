import java.time.Duration;
import java.time.LocalDateTime;

public class EntryRecord {
    private final String guestName;
    private final LocalDateTime timeIn;
    private LocalDateTime timeOut;

    public EntryRecord(String guestName) {
        this.guestName = guestName;
        this.timeIn = LocalDateTime.now();
    }

    public String getGuestName() { return guestName; }
    public LocalDateTime getTimeIn() { return timeIn; }
    public LocalDateTime getTimeOut() { return timeOut; }
    public boolean recordTimeOut() {
        if (timeOut != null) return false;
        timeOut = LocalDateTime.now();
        return true;
    }
    // -1 means the guest has not exited yet; otherwise returns whole minutes.
    public long provideStayTime() {
        if (timeOut == null) return -1;
        return Duration.between(timeIn, timeOut).toMinutes();
    }
}
