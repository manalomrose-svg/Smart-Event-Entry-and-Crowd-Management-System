import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuestList {
    private final List<Guest> guests;
    private boolean locked;

    public GuestList() {
        guests = new ArrayList<>();
        locked = false;
    }

    public boolean addGuest(String name) {
        if (locked) {
            return false;
        }
        if (guests.size() >= 50) {
            return false;
        }
        if (name == null) {
            return false;
        }
        if (name.trim().isEmpty()) {
            return false;
        }
        // Name-only lookup cannot distinguish two guests with the same name.
        if (checkGuestName(name)) {
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
        return count >= 25 && count <= 50;
    }

    public boolean checkGuestName(String name) {
        Guest foundGuest = findGuest(name);
        return foundGuest != null;
    }

    public Guest findGuest(String name) {
        if (name == null) {
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
}
