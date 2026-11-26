import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DeniedEntry {
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    // Shared collection of all denied attempts (assumed storage location).
    private static final List<DeniedEntry> deniedEntries = new ArrayList<>();

    // Creating a record captures the date and time of one denied attempt.
    public DeniedEntry(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        LocalDateTime now = LocalDateTime.now();
        this.name = name.trim();
        this.date = now.toLocalDate();
        this.time = now.toLocalTime();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    // Stores this denied attempt in the collection.
    public void recordDeniedAttempt() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Cannot record an entry with an empty name.");
        }
        if (date == null || time == null) {
            throw new IllegalStateException("Cannot record an entry without a date and time.");
        }
        if (deniedEntries.contains(this)) {
            throw new IllegalStateException("This denied attempt was already recorded.");
        }
        deniedEntries.add(this);
    }

    // Returns a read-only copy so callers cannot bypass validation by editing the list.
    public java.util.List<DeniedEntry> provideDeniedEntryList() {
        return Collections.unmodifiableList(new ArrayList<>(deniedEntries));
    }

    @Override
    public String toString() {
        return name + " | " + date + " | " + time.withNano(0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        DeniedEntry lastEntry = null;

        while (running) {
            System.out.println("\n===== DENIED ENTRY MENU =====");
            System.out.println("1. Record a denied attempt");
            System.out.println("2. View denied entries");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    DeniedEntry entry = null;
                    while (entry == null) {
                        try {
                            System.out.print("Enter name: ");
                            entry = new DeniedEntry(scanner.nextLine());
                            entry.recordDeniedAttempt();
                            lastEntry = entry;
                            System.out.println("Recorded: " + entry);
                        } catch (IllegalArgumentException | IllegalStateException e) {
                            System.out.println("Refused: " + e.getMessage() + "\n");
                            entry = null;
                        }
                    }
                    break;

                case "2":
                    if (lastEntry == null) {
                        System.out.println("No denied entries recorded yet.");
                    } else {
                        List<DeniedEntry> list = lastEntry.provideDeniedEntryList();
                        System.out.println("\n===== DENIED ENTRIES (" + list.size() + ") =====");
                        for (DeniedEntry e : list) {
                            System.out.println(e);
                        }
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
