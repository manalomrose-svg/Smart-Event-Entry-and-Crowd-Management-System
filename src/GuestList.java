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
        if (locked || guests.size() >= 50 || name == null || name.trim().isEmpty()) {
            return false;
        }
        // Name-only lookup cannot distinguish two guests with the same name.
        if (checkGuestName(name)) return false;
        guests.add(new Guest(name));
        return true;
    }

    public int getGuestCount() { return guests.size(); }
    public boolean validateGuestCount() {
        return guests.size() >= 25 && guests.size() <= 50;
    }
    public boolean checkGuestName(String name) { return findGuest(name) != null; }
    public Guest findGuest(String name) {
        if (name == null) return null;
        for (Guest guest : guests) {
            if (guest.getName().equalsIgnoreCase(name.trim())) return guest;
        }
        return null;
    }
    public List<Guest> getGuests() {
        return Collections.unmodifiableList(new ArrayList<>(guests));
    }
    // Call only after reservation validation and availability checks succeed.
    public boolean lockGuestList() {
        if (!validateGuestCount()) return false;
        locked = true;
        return true;
    }
    public boolean isLocked() { return locked; }
}
