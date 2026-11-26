import java.util.Objects;

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
        this.staff = Objects.requireNonNull(staff);
        this.guestList = Objects.requireNonNull(guestList);
        this.crowdCounter = Objects.requireNonNull(crowdCounter);
        this.limit = limit;
    }

    public Staff getStaff() { return staff; }
    public GuestList getGuestList() { return guestList; }
    public CrowdControl getCrowdCounter() { return crowdCounter; }
    public EntryRecord getEntryRecord() { return entryRecord; }
    public DeniedEntry getDeniedEntry() { return deniedEntry; }
    public int getLimit() { return limit; }

    public boolean checkGuestName(String name) {
        return guestList.checkGuestName(name);
    }

    public void allowEntry(Guest guest) {
        // TODO: Verify the reservation, guest status and capacity; update records.
        throw new UnsupportedOperationException("Allowing entry is not implemented yet.");
    }

    public void denyEntry(Guest guest) {
        // TODO: Record the denied attempt, including names not on the guest list.
        throw new UnsupportedOperationException("Denying entry is not implemented yet.");
    }

    public boolean isLimitReached() {
        return crowdCounter.getCurrentCount() >= limit || crowdCounter.isLimitReached();
    }
}
