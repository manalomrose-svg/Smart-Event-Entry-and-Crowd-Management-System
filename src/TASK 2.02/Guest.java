import java.util.Scanner;

public class Guest {
    private final String name;
    private Status status;

    public Guest(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Guest name cannot be null.");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be empty.");
        }
        this.name = name.trim();
        this.status = Status.NOT_REGISTERED;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    // The entry controller must verify eligibility and capacity before calling this.
    public boolean register() {
        return changeStatus(Status.REGISTERED);
    }

    public boolean markExited() {
        return changeStatus(Status.EXITED);
    }

    // Every status change goes through here, so none can skip the rules in Status.
    private boolean changeStatus(Status next) {
        if (!status.canChangeTo(next)) {
            return false;
        }
        status = status.changeTo(next);
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Guest guest = null;

        // Create the guest first.
        while (guest == null) {
            try {
                System.out.print("Enter guest name: ");
                guest = new Guest(scanner.nextLine());
                System.out.println("Guest created: " + guest.getName()
                        + " (" + guest.getStatus() + ")");
            } catch (IllegalArgumentException e) {
                System.out.println("Refused: " + e.getMessage() + "\n");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n===== GUEST MENU (" + guest.getName() + ") =====");
            System.out.println("1. Register guest");
            System.out.println("2. Mark guest as exited");
            System.out.println("3. View status");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (guest.register()) {
                        System.out.println("Guest registered.");
                    } else {
                        System.out.println("Refused: cannot register a guest who is "
                                + guest.getStatus() + ".");
                    }
                    break;
                case "2":
                    if (guest.markExited()) {
                        System.out.println("Guest marked as exited.");
                    } else {
                        System.out.println("Refused: only a REGISTERED guest can exit. Current status: "
                                + guest.getStatus() + ".");
                    }
                    break;
                case "3":
                    System.out.println("Status: " + guest.getStatus());
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }
}