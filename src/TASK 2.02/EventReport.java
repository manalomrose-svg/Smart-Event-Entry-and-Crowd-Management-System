import java.time.LocalDate;
import java.util.Scanner;

// Starter class. Report values are not calculated until compile() is implemented.
public class EventReport {
    private final String event;
    private int noShows;
    private int peakHours;
    private int averageStay;

    public EventReport(String event) {
        if (event == null || event.trim().isEmpty()) {
            throw new IllegalArgumentException("Event cannot be null or empty.");
        }
        this.event = event.trim();
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
        // When you assign the results, reject impossible values first, for example:
        //   if (noShows < 0) throw new IllegalStateException("No-shows cannot be negative.");
        //   if (peakHours < 0 || peakHours > 23) throw new IllegalStateException("Peak hour must be 0 to 23.");
        //   if (averageStay < 0) throw new IllegalStateException("Average stay cannot be negative.");
        throw new UnsupportedOperationException("Report compilation is not implemented yet.");
    }

    // Searches by any combination of name, date and event. At least one must be given.
    public void search(String name, LocalDate date, String event) {
        boolean noName = (name == null || name.trim().isEmpty());
        boolean noEvent = (event == null || event.trim().isEmpty());

        if (noName && date == null && noEvent) {
            throw new IllegalArgumentException(
                "Enter at least a name, a date or an event to search.");
        }
        if (date != null && date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Search date cannot be in the future.");
        }

        // TODO: Agree on the return type and implement report searching.
        throw new UnsupportedOperationException("Report searching is not implemented yet.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventReport report = null;

        // Create the report first.
        while (report == null) {
            try {
                System.out.print("Enter event name: ");
                report = new EventReport(scanner.nextLine());
                System.out.println("Report created for: " + report.getEvent());
            } catch (IllegalArgumentException e) {
                System.out.println("Refused: " + e.getMessage() + "\n");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n===== EVENT REPORT MENU =====");
            System.out.println("1. View report values");
            System.out.println("2. Search (tests input validation)");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("Event: " + report.getEvent());
                    System.out.println("No-shows: " + report.getNoShows());
                    System.out.println("Peak hours: " + report.getPeakHours());
                    System.out.println("Average stay: " + report.getAverageStay());
                    break;

                case "2":
                    try {
                        System.out.print("Guest name (leave blank to skip): ");
                        String name = scanner.nextLine();

                        System.out.print("Date yyyy-MM-dd (leave blank to skip): ");
                        String dateText = scanner.nextLine().trim();
                        LocalDate date = dateText.isEmpty() ? null : LocalDate.parse(dateText);

                        System.out.print("Event (leave blank to skip): ");
                        String eventName = scanner.nextLine();

                        report.search(name, date, eventName);
                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Refused: Invalid date. Use yyyy-MM-dd.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Refused: " + e.getMessage());
                    } catch (UnsupportedOperationException e) {
                        System.out.println("Input accepted, but searching is not implemented yet.");
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