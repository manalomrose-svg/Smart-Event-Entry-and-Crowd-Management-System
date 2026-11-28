import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

// Task 2.03: console menu that runs the main flow once, start to finish.
// It follows the use case diagram: Customer reserves, then Staff handles the event.
// This class only asks for input and calls the other classes. The rules live in them.
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    runMainFlow();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Enter 1 or 0.");
            }
        }
        System.out.println("Goodbye.");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== SMART EVENT ENTRY AND CROWD MANAGEMENT =====");
        System.out.println("1. Run the main flow (reserve -> entry -> exit -> report)");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    // The whole flow, one step per method.
    private static void runMainFlow() {
        try {
            AvailabilityChecker checker = new AvailabilityChecker();

            // Customer side
            Customer customer = askCustomer();
            Event event = askEvent();
            if (!checkAvailability(checker, event)) {
                return;
            }
            GuestList guestList = buildGuestList(event);
            Reservation reservation = bookReservation(customer, event, guestList, checker);
            if (reservation == null) {
                return;
            }

            // Staff side
            EntryController controller = new EntryController(
                    askStaff(), guestList, new CrowdControl(), event.getExpectedPopulation());
            if (!verifyReservationNumber(controller, reservation)) {
                return;
            }
            runStaffMenu(controller, event);
            showSummary(controller, event);
        } catch (RuntimeException e) {
            System.out.println("Main flow stopped: " + e.getMessage());
        }
    }

    // ================= CUSTOMER =================

    // Use case: Enter customer details
    private static Customer askCustomer() {
        System.out.println("\n--- Step 1: Customer details ---");
        while (true) {
            try {
                String name = ask("Enter name: ");
                String address = ask("Enter address: ");
                int age = Integer.parseInt(ask("Enter age: "));
                String contact = ask("Enter contact number: ");
                return new Customer(name, address, age, contact);
            } catch (NumberFormatException e) {
                System.out.println("Refused: Age must be a whole number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Refused: " + e.getMessage());
            }
        }
    }

    // Use case: Reserve event
    private static Event askEvent() {
        System.out.println("\n--- Step 2: Reserve event ---");
        while (true) {
            try {
                LocalDate date = LocalDate.parse(ask("Enter date (yyyy-MM-dd): "));
                LocalTime time = LocalTime.parse(ask("Enter time (HH:mm): "));
                String type = ask("Enter event type: ");
                int population = Integer.parseInt(ask("Enter expected population (25-50): "));
                return new Event(date, time, type, population);
            } catch (DateTimeParseException e) {
                System.out.println("Refused: Invalid date or time. Use yyyy-MM-dd and HH:mm.");
            } catch (NumberFormatException e) {
                System.out.println("Refused: Population must be a whole number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Refused: " + e.getMessage());
            }
        }
    }

    // Use case: Check availability
    private static boolean checkAvailability(AvailabilityChecker checker, Event event) {
        System.out.println("\n--- Step 3: Check availability ---");
        if (checker.checkAvailability(event.getDate(), event.getTime())) {
            System.out.println(event.getDate() + " " + event.getTime() + " is available.");
            return true;
        }
        System.out.println(event.getDate() + " " + event.getTime() + " is already booked.");
        return false;
    }

    // Use case: Enter guest name list (one name per guest, up to the expected population)
    private static GuestList buildGuestList(Event event) {
        System.out.println("\n--- Step 4: Guest name list ---");
        int expected = event.getExpectedPopulation();
        System.out.println("Enter the name of each guest (" + expected + " guests needed).");

        GuestList guestList = new GuestList();
        while (guestList.getGuestCount() < expected) {
            String name = ask("Guest " + (guestList.getGuestCount() + 1) + " of " + expected + ": ");
            String error = guestList.getAddGuestError(name);
            if (error != null) {
                System.out.println("Refused: " + error);
            } else {
                guestList.addGuest(name);
                System.out.println("Added: " + name);
            }
        }
        showGuestList(guestList);
        return guestList;
    }

    // Use case: Confirm and view details (with reservation number)
    private static Reservation bookReservation(Customer customer, Event event,
                                               GuestList guestList, AvailabilityChecker checker) {
        System.out.println("\n--- Step 5: Confirm reservation ---");
        Reservation reservation = new Reservation(customer, event, guestList);
        if (!reservation.confirmReservation(checker)) {
            System.out.println("Reservation refused: that date and time is already booked.");
            return null;
        }
        guestList.lockGuestList();
        System.out.println("Reservation confirmed. Guest list locked.");
        System.out.println(reservation.getReservationDetails());
        System.out.println("Customer: " + customer.provideCustomerDetails());
        return reservation;
    }

    // ================= STAFF =================

    private static Staff askStaff() {
        System.out.println("\n--- Step 6: Staff on duty ---");
        while (true) {
            try {
                return new Staff(ask("Enter staff ID: "), ask("Enter staff name: "));
            } catch (IllegalArgumentException e) {
                System.out.println("Refused: " + e.getMessage());
            }
        }
    }

    // Staff enters the reservation number once before registering guests (3 tries).
    private static boolean verifyReservationNumber(EntryController controller, Reservation reservation) {
        System.out.println("\n--- Step 7: Verify reservation ---");
        Staff staff = controller.getStaff();
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                staff.enterReservationNumber(ask("Enter reservation number (shown in Step 5): "));
                if (controller.verifyReservationNumber(reservation, staff.getReservationNumber())) {
                    System.out.println("Reservation verified.");
                    return true;
                }
                System.out.println("Refused: reservation number does not match.");
            } catch (IllegalArgumentException e) {
                System.out.println("Refused: " + e.getMessage());
            }
        }
        System.out.println("Too many wrong attempts. Entry is closed.");
        return false;
    }

    // Staff use cases: register guest, mark guest exit, view live crowd counter,
    // view denied entry list, search event report.
    private static void runStaffMenu(EntryController controller, Event event) {
        boolean open = true;
        while (open) {
            System.out.println("\n===== STAFF MENU (" + controller.getStaff().getName() + ") =====");
            System.out.println("1. Register guest (entry)");
            System.out.println("2. Mark guest exit");
            System.out.println("3. View live crowd counter");
            System.out.println("4. View denied entry list");
            System.out.println("5. View event report");
            System.out.println("6. Search event report");
            System.out.println("0. Finish");
            switch (ask("Choose: ")) {
                case "1": registerGuest(controller); break;
                case "2": markGuestExit(controller); break;
                case "3": viewCrowdCounter(controller); break;
                case "4": viewDeniedEntries(controller); break;
                case "5": viewReport(controller, event); break;
                case "6": searchReport(controller, event); break;
                case "0": open = false; break;
                default: System.out.println("Invalid choice. Enter 0 to 6.");
            }
        }
    }

    // Use case: Register guest (time in is recorded automatically)
    private static void registerGuest(EntryController controller) {
        Staff staff = controller.getStaff();
        try {
            staff.enterGuestName(ask("Enter guest name: "));
            String name = staff.getGuestName();

            // Confirm the guest is on the guest list of this reservation.
            if (!controller.checkGuestName(name)) {
                controller.denyEntry(new Guest(name));
                System.out.println("Entry denied: " + name + " is NOT on the guest list of this reservation.");
                return;
            }
            Guest guest = controller.getGuestList().findGuest(name);
            System.out.println("Confirmed: " + guest.getName() + " is on the guest list and reserved ("
                    + guest.getStatus() + ").");

            controller.allowEntry(guest);
            System.out.println("Entry allowed: " + guest.getName() + " (" + guest.getStatus() + ")");
            System.out.println("Time in: " + controller.getEntryRecord().getTimeIn().withNano(0));
            controller.getCrowdCounter().showAlert();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Refused: " + e.getMessage());
        }
    }

    // Use case: Mark guest exit (time out is recorded automatically)
    private static void markGuestExit(EntryController controller) {
        Staff staff = controller.getStaff();
        try {
            String name = ask("Enter name of the guest leaving: ");
            Guest guest = controller.getGuestList().findGuest(name);
            if (guest == null) {
                System.out.println("Refused: " + name + " is not on the guest list.");
                return;
            }
            EntryRecord record = staff.markGuestExit(guest);
            System.out.println("Exit recorded: " + guest.getName() + " (" + guest.getStatus() + ")");
            System.out.println("Time out: " + record.getTimeOut().withNano(0));
            System.out.println("Stay time: " + record.provideStayTime() + " minute(s)");
            controller.getCrowdCounter().showAlert();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Refused: " + e.getMessage());
        }
    }

    // Use case: View live crowd counter
    private static void viewCrowdCounter(EntryController controller) {
        int count = controller.getStaff().viewLiveCrowdCounter();
        System.out.println("Guests inside now: " + count + " / " + controller.getLimit());
        controller.getCrowdCounter().showAlert();
    }

    // Use case: View denied entry list
    private static void viewDeniedEntries(EntryController controller) {
        System.out.println(DeniedEntry.provideDeniedEntryList(controller.getDeniedEntries()));
    }

    // Use case: View event report
    private static void viewReport(EntryController controller, Event event) {
        EventReport report = compileReport(controller, event);
        System.out.println("Event: " + report.getEvent());
        System.out.println("No-shows: " + report.getNoShows());
        System.out.println("Peak hour: " + report.getPeakHours() + ":00");
        System.out.println("Average stay: " + report.getAverageStay() + " minute(s)");
    }

    // Use case: Search event report (by name, date and/or event)
    private static void searchReport(EntryController controller, Event event) {
        EventReport report = compileReport(controller, event);
        try {
            String name = ask("Guest name (leave blank to skip): ");
            String dateText = ask("Date yyyy-MM-dd (leave blank to skip): ");
            LocalDate date = dateText.isEmpty() ? null : LocalDate.parse(dateText);
            String eventName = ask("Event (leave blank to skip): ");

            List<EntryRecord> found = report.search(name, date, eventName);
            System.out.println(found.size() + " record(s) found.");
            for (EntryRecord r : found) {
                System.out.println("  " + r.getGuestName() + " | in: " + r.getTimeIn().withNano(0)
                        + " | out: " + (r.getTimeOut() == null ? "still inside" : r.getTimeOut().withNano(0)));
            }
        } catch (DateTimeParseException e) {
            System.out.println("Refused: Invalid date. Use yyyy-MM-dd.");
        } catch (IllegalArgumentException e) {
            System.out.println("Refused: " + e.getMessage());
        }
    }

    private static EventReport compileReport(EntryController controller, Event event) {
        EventReport report = new EventReport(event.getEventType());
        report.compile(controller.getGuestList(), controller.getEntryRecords());
        return report;
    }

    // ================= END =================

    private static void showSummary(EntryController controller, Event event) {
        System.out.println("\n--- Done ---");
        showGuestList(controller.getGuestList());
        System.out.println("Guests inside now: " + controller.getCrowdCounter().getCurrentCount());
        viewDeniedEntries(controller);
        viewReport(controller, event);
    }

    private static void showGuestList(GuestList guestList) {
        System.out.println("Guest list (" + guestList.getGuestCount() + "):");
        int number = 1;
        for (Guest g : guestList.getGuests()) {
            System.out.println("  " + number++ + ". " + g.getName() + " | " + g.getStatus());
        }
    }

    private static String ask(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
