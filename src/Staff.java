public class Staff {
    private final String staffID;
    private final String name;
    private String reservationNumber;
    private String guestName;

    public Staff(String staffID, String name) {
        this.staffID = staffID;
        this.name = name;
    }

    public String getStaffID() { return staffID; }
    public String getName() { return name; }
    public String getReservationNumber() { return reservationNumber; }
    public String getGuestName() { return guestName; }

    // Store the input; the controller will verify it in the next task.
    public void enterReservationNumber(String number) {
        reservationNumber = number;
        guestName = null;
    }
    public void enterGuestName(String name) { guestName = name; }

    // TODO: Delegate exit processing to the entry controller.
    public void markGuestExit(Guest guest) {
        throw new UnsupportedOperationException("Guest exit integration is pending.");
    }

    // TODO: Retrieve the count from the shared CrowdControl object.
    public int viewLiveCrowdCounter() {
        throw new UnsupportedOperationException("Live crowd counter integration is pending.");
    }
}
