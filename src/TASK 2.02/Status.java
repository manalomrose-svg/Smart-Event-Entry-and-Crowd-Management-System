import java.util.Scanner;

public enum Status {
    NOT_REGISTERED, REGISTERED, EXITED;

    // Allowed order: NOT_REGISTERED -> REGISTERED -> EXITED
    public boolean canChangeTo(Status next) {
        if (next == null) {
            return false;
        }
        switch (this) {
            case NOT_REGISTERED:
                return next == REGISTERED;
            case REGISTERED:
                return next == EXITED;
            case EXITED:
            default:
                return false;
        }
    }

    // Call this before changing a status. Throws if the change is not allowed.
    public Status changeTo(Status next) {
        if (next == null) {
            throw new IllegalArgumentException("New status cannot be null.");
        }
        if (!canChangeTo(next)) {
            throw new IllegalStateException(
                "Invalid status change: " + this + " -> " + next + ".");
        }
        return next;
    }

    // Safely converts typed text into a Status instead of crashing.
    public static Status fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty.");
        }
        try {
            return Status.valueOf(text.trim().toUpperCase().replace(' ', '_'));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Unknown status '" + text.trim() + "'. Use NOT_REGISTERED, REGISTERED or EXITED.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Status current = NOT_REGISTERED;
        boolean running = true;

        while (running) {
            System.out.println("\nCurrent status: " + current);
            System.out.print("Enter new status (or type 'exit' to quit): ");
            String input = scanner.nextLine();

            if (input.trim().equalsIgnoreCase("exit")) {
                running = false;
                continue;
            }

            try {
                current = current.changeTo(Status.fromString(input));
                System.out.println("Status changed to " + current);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Refused: " + e.getMessage());
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }
}