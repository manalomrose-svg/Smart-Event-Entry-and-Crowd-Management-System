import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class EntryRecord {
    private final String guestName;
    private final LocalDateTime timeIn;
    private LocalDateTime timeOut;

    public EntryRecord(String guestName) {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be null or empty.");
        }
        this.guestName = guestName.trim();
        this.timeIn = LocalDateTime.now();
    }

    public String getGuestName() {
        return guestName;
    }

    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    // Returns false if the guest has already been timed out.
    public boolean recordTimeOut() {
        if (timeOut != null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(timeIn)) {
            throw new IllegalStateException("Time out cannot be earlier than time in.");
        }
        timeOut = now;
        return true;
    }

    // -1 means the guest has not exited yet; otherwise returns whole minutes.
    public long provideStayTime() {
        if (timeOut == null) {
            return -1;
        }
        Duration stay = Duration.between(timeIn, timeOut);
        return stay.toMinutes();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntryRecord record = null;

        // Create the record first.
        while (record == null) {
            try {
                System.out.print("Enter guest name: ");
                record = new EntryRecord(scanner.nextLine());
                System.out.println("Entry recorded for " + record.getGuestName()
                        + " at " + record.getTimeIn().withNano(0));
            } catch (IllegalArgumentException e) {
                System.out.println("Refused: " + e.getMessage() + "\n");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n===== ENTRY RECORD MENU =====");
            System.out.println("1. Record time out");
            System.out.println("2. View stay time");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (record.recordTimeOut()) {
                        System.out.println("Time out recorded at " + record.getTimeOut().withNano(0));
                    } else {
                        System.out.println("Refused: this guest has already exited.");
                    }
                    break;
                case "2":
                    long minutes = record.provideStayTime();
                    if (minutes == -1) {
                        System.out.println("Guest has not exited yet.");
                    } else {
                        System.out.println("Stay time: " + minutes + " minute(s)");
                    }
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }
}