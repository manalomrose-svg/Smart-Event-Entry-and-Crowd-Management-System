// Starter class. Complete entry decisions and record collection during integration.
public class EntryController {
    private final Staff staff;
    private final GuestList guestList;
    private final CrowdControl crowdCounter;
    private EntryRecord entryRecord;
    private DeniedEntry deniedEntry;
    private final int limit;

    public EntryController(Staff staff, GuestList guestList, CrowdControl crowdCounter, int limit) {
        if (limit < 25 || limit > 50) {
            throw new IllegalArgumentException("Limit must be 25 to 50.");
        }
        if (staff == null) {
            throw new NullPointerException("Staff cannot be null.");
        }
        this.staff = staff;
        if (guestList == null) {
            throw new NullPointerException("Guest list cannot be null.");
        }
        this.guestList = guestList;
        if (crowdCounter == null) {
            throw new NullPointerException("Crowd counter cannot be null.");
        }
        this.crowdCounter = crowdCounter;
        this.limit = limit;
    }

    public Staff getStaff() {
        return staff;
    }

    public GuestList getGuestList() {
        return guestList;
    }

    public CrowdControl getCrowdCounter() {
        return crowdCounter;
    }

    public EntryRecord getEntryRecord() {
        return entryRecord;
    }

    public DeniedEntry getDeniedEntry() {
        return deniedEntry;
    }

    public int getLimit() {
        return limit;
    }

    public boolean checkGuestName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be null or empty.");
        }
        return guestList.checkGuestName(name.trim());
    }

    public void allowEntry(Guest guest) {
        validateGuest(guest);

        if (!checkGuestName(guest.getName())) {
            throw new IllegalStateException("Entry refused: guest is not on the guest list.");
        }
        if (isLimitReached()) {
            throw new IllegalStateException("Entry refused: the crowd limit of " + limit + " is reached.");
        }

        // Verify the reservation and guest status, then update the entryRecord and crowdCounter. Use your teammates' methods to do this.

        // entryRecord and the crowd counter using your teammates' methods.
        throw new UnsupportedOperationException("Allowing entry is not implemented yet.");
    }

    public void denyEntry( Guest guest) {
        validateGuest(guest);

        // Works for names not on the guest list too.
        DeniedEntry entry = new DeniedEntry(guest.getName());
        entry.recordDeniedAttempt();
        this.deniedEntry = entry;
    }

    public boolean isLimitReached() {
        int count = crowdCounter.getCurrentCount();
        if (count >= limit) {
            return true;
        }
        return crowdCounter.isLimitReached();
    }

    // Shared check so no state-changing method skips validation.
    private void validateGuest(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be null.");
        }
        String guestName = guest.getName();
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name cannot be null or empty.");
        }
    }
}