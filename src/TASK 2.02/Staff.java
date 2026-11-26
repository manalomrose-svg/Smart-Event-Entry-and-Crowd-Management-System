import java.util.Scanner;

public class Staff {
    private final String staffID;
    private final String name;
    private String reservationNumber;
    private String guestName;

    public Staff(String staffID, String name) {
        if (staffID == null || staffID.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff name cannot be null or empty.");
        }
        this.staffID = staffID.trim();
        this.name = name.trim();
    }

    public String getStaffID() {
        return staffID;
    }

    public String getName() {
        return name;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    // Store the input; the controller will verify it in the next task.
    public void enterReservationNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation number cannot be null or empty.");
        }
        if (!number.trim().matches("[A-Za-z0-9\\-]{3,20}")) {
            throw new IllegalArgumentException(
                "Reservation number must be 3-20 characters and contain only letters, digits or '-'.");
        }
        reservationNumber = number.trim();
        guestName = null;
    }

    public void enterGuestName(String name) {
        if (reservationNumber == null) {
            throw new IllegalStateException("Enter the reservation number before the guest name.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be null or empty.");
        }
        guestName = name.trim();
    }

    //  Delegate exit processing to the entry controller.
    public void markGuestExit(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be null.");
        }
        throw new UnsupportedOperationException("Guest exit integration is pending.");
    }

    // Retrieve the count from the shared CrowdControl object.
    public int viewLiveCrowdCounter() {
        throw new UnsupportedOperationException("Live crowd counter integration is pending.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Staff staff = null;

        // Create the staff member first.
        while (staff == null) {
            try {
                System.out.print("Enter staff ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter staff name: ");
                String name = scanner.nextLine();
                staff = new Staff(id, name);
            } catch (IllegalArgumentException e) {
                System.out.println("Refused: " + e.getMessage() + "\n");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n===== STAFF MENU (" + staff.getName() + ") =====");
            System.out.println("1. Enter reservation number");
            System.out.println("2. Enter guest name");
            System.out.println("3. View current input");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter reservation number: ");
                        staff.enterReservationNumber(scanner.nextLine());
                        System.out.println("Reservation number stored: " + staff.getReservationNumber());
                        break;
                    case "2":
                        System.out.print("Enter guest name: ");
                        staff.enterGuestName(scanner.nextLine());
                        System.out.println("Guest name stored: " + staff.getGuestName());
                        break;
                    case "3":
                        System.out.println("Reservation number: " + staff.getReservationNumber());
                        System.out.println("Guest name: " + staff.getGuestName());
                        break;
                    case "4":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Refused: " + e.getMessage());
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }
}