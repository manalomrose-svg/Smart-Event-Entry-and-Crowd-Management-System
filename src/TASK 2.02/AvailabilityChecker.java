import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
 
// Starter class. Agree on the booking and time-slot rules before integration.
public class AvailabilityChecker {
    private final List<String> bookedSlots;
 
    public AvailabilityChecker() {
        bookedSlots = new ArrayList<>();
    }
 
    public List<String> getBookedSlots() {
        List<String> copy = new ArrayList<>(bookedSlots);
        return Collections.unmodifiableList(copy);
    }
 
    public boolean checkAvailability(LocalDate date, LocalTime slot) {
        validateDateAndSlot(date, slot);
        // TODO: Check the requested date and time against existing bookings.
        throw new UnsupportedOperationException("Availability checking is not implemented yet.");
    }
 
    public void blockSlot(LocalDate date, LocalTime slot) {
        validateDateAndSlot(date, slot);
        // TODO: Store a confirmed booking and prevent double booking.
        throw new UnsupportedOperationException("Slot blocking is not implemented yet.");
    }
 
    // Task 2.02: one shared check, so no method can skip validation.
    private void validateDateAndSlot(LocalDate date, LocalTime slot) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (slot == null) {
            throw new IllegalArgumentException("Time slot cannot be null");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }
    }
 
    // ---------------------------------------------------------------
    // Temporary manual tester so this file can be run on its own.
    // Remove it before final submission; the real console menu belongs to Task 2.03.
    // ---------------------------------------------------------------
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AvailabilityChecker checker = new AvailabilityChecker();
        boolean running = true;
 
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
 
            switch (choice) {
                case "1":
                    runAction(scanner, checker, false);
                    break;
                case "2":
                    runAction(scanner, checker, true);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Enter 1, 2, or 0.");
            }
        }
        System.out.println("Goodbye.");
    }
 
    private static void printMenu() {
        System.out.println();
        System.out.println("=== Availability Checker Test ===");
        System.out.println("1. Check availability");
        System.out.println("2. Block a slot");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }
 
    private static void runAction(Scanner scanner, AvailabilityChecker checker, boolean block) {
        System.out.print("Enter date (yyyy-mm-dd): ");
        String dateText = scanner.nextLine().trim();
        System.out.print("Enter time slot (HH:mm, e.g. 14:30): ");
        String timeText = scanner.nextLine().trim();
 
        try {
            LocalDate date = LocalDate.parse(dateText);
            LocalTime slot = LocalTime.parse(timeText);
 
            if (block) {
                checker.blockSlot(date, slot);
            } else {
                checker.checkAvailability(date, slot);
            }
            System.out.println("Accepted.");
        } catch (DateTimeParseException e) {
            System.out.println("REFUSED -> Invalid date or time. Use yyyy-mm-dd and HH:mm.");
        } catch (IllegalArgumentException e) {
            System.out.println("REFUSED -> " + e.getMessage());
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed validation (booking logic not implemented yet).");
        }
    }
}
 