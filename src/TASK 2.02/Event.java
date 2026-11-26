import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Event {
    private final LocalDate date;
    private final LocalTime time;
    private final String eventType;
    private final int expectedPopulation;

    public Event(LocalDate date, LocalTime time, String eventType, int expectedPopulation) {
        if (expectedPopulation < 25 || expectedPopulation > 50) {
            throw new IllegalArgumentException("Expected population must be 25 to 50.");
        }
        if (date == null) {
            throw new NullPointerException("Date cannot be null.");
        }
        if (time == null) {
            throw new NullPointerException("Time cannot be null.");
        }
        if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event date and time cannot be in the past.");
        }
        if (eventType == null || eventType.trim().isEmpty()) {
            throw new IllegalArgumentException("Event type cannot be null or empty.");
        }
        this.date = date;
        this.time = time;
        this.eventType = eventType.trim();
        this.expectedPopulation = expectedPopulation;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getEventType() {
        return eventType;
    }

    public int getExpectedPopulation() {
        return expectedPopulation;
    }

    public boolean validateExpectedPopulation() {
        return expectedPopulation >= 25 && expectedPopulation <= 50;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== EVENT MENU =====");
            System.out.println("1. Create an event");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    Event event = null;
                    while (event == null) {
                        try {
                            System.out.print("Enter date (yyyy-MM-dd): ");
                            LocalDate date = LocalDate.parse(scanner.nextLine().trim());

                            System.out.print("Enter time (HH:mm): ");
                            LocalTime time = LocalTime.parse(scanner.nextLine().trim());

                            System.out.print("Enter event type: ");
                            String type = scanner.nextLine();

                            System.out.print("Enter expected population (25-50): ");
                            int population = Integer.parseInt(scanner.nextLine().trim());

                            event = new Event(date, time, type, population);
                        } catch (DateTimeParseException e) {
                            System.out.println("Refused: Invalid date or time. Use yyyy-MM-dd and HH:mm.\n");
                        } catch (NumberFormatException e) {
                            System.out.println("Refused: Population must be a whole number.\n");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Refused: " + e.getMessage() + "\n");
                        }
                    }
                    System.out.println("\nEvent accepted:");
                    System.out.println(event.getEventType() + " | " + event.getDate() + " "
                            + event.getTime() + " | " + event.getExpectedPopulation() + " guests");
                    break;

                case "2":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }
}