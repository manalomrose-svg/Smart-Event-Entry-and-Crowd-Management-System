import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GuestList {
    private static final int MIN_GUESTS = 25;
    private static final int MAX_GUESTS = 50;

    private final List<Guest> guests;
    private boolean locked;

    public GuestList() {
        guests = new ArrayList<>();
        locked = false;
    }

    // Returns the reason a name would be refused, or null if it is acceptable.
    public String getAddGuestError(String name) {
        if (locked) {
            return "The guest list is locked.";
        }
        if (guests.size() >= MAX_GUESTS) {
            return "The guest list is full (maximum " + MAX_GUESTS + " guests).";
        }
        if (name == null) {
            return "Guest name cannot be null.";
        }
        if (name.trim().isEmpty()) {
            return "Guest name cannot be empty.";
        }
        // Name-only lookup cannot distinguish two guests with the same name.
        if (checkGuestName(name)) {
            return "A guest named '" + name.trim() + "' is already on the list.";
        }
        return null;
    }

    public boolean addGuest(String name) {
        if (getAddGuestError(name) != null) {
            return false;
        }
        Guest newGuest = new Guest(name);
        guests.add(newGuest);
        return true;
    }

    public int getGuestCount() {
        return guests.size();
    }

    public boolean validateGuestCount() {
        int count = guests.size();
        return count >= MIN_GUESTS && count <= MAX_GUESTS;
    }

    public boolean checkGuestName(String name) {
        Guest foundGuest = findGuest(name);
        return foundGuest != null;
    }

    public Guest findGuest(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        for (Guest guest : guests) {
            String guestName = guest.getName();
            if (guestName.equalsIgnoreCase(name.trim())) {
                return guest;
            }
        }
        return null;
    }

    public List<Guest> getGuests() {
        List<Guest> copy = new ArrayList<>(guests);
        return Collections.unmodifiableList(copy);
    }

    // Call only after reservation validation and availability checks succeed.
    public boolean lockGuestList() {
        if (!validateGuestCount()) {
            return false;
        }
        locked = true;
        return true;
    }

    public boolean isLocked() {
        return locked;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GuestList list = new GuestList();
        boolean running = true;

        while (running) {
            System.out.println("\n===== GUEST LIST MENU (" + list.getGuestCount() + " guests) =====");
            System.out.println("1. Add a guest");
            System.out.println("2. View guests");
            System.out.println("3. Check a guest name");
            System.out.println("4. Lock the guest list");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter guest name: ");
                    String name = scanner.nextLine();
                    String error = list.getAddGuestError(name);
                    if (error != null) {
                        System.out.println("Refused: " + error);
                    } else {
                        list.addGuest(name);
                        System.out.println("Guest added: " + name.trim());
                    }
                    break;

                case "2":
                    if (list.getGuestCount() == 0) {
                        System.out.println("No guests yet.");
                    } else {
                        for (Guest g : list.getGuests()) {
                            System.out.println(g.getName() + " | " + g.getStatus());
                        }
                    }
                    break;

                case "3":
                    System.out.print("Enter name to check: ");
                    String query = scanner.nextLine();
                    System.out.println(list.checkGuestName(query)
                            ? "Found on the guest list."
                            : "Not on the guest list.");
                    break;

                case "4":
                    if (list.lockGuestList()) {
                        System.out.println("Guest list locked.");
                    } else {
                        System.out.println("Refused: the list needs " + MIN_GUESTS + " to "
                                + MAX_GUESTS + " guests before it can be locked. Current count: "
                                + list.getGuestCount() + ".");
                    }
                    break;

                case "5":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, 4, or 5.");
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }
}
