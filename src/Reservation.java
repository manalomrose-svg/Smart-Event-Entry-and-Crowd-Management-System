import java.util.UUID;

public class Reservation {
    private final String reservationNumber;
    private final Customer customer;
    private final Event event;
    private final GuestList guestList;

    public Reservation(Customer customer, Event event, GuestList guestList) {
        if (customer == null) {
            throw new NullPointerException();
        }
        this.customer = customer;
        if (event == null) {
            throw new NullPointerException();
        }
        this.event = event;
        if (guestList == null) {
            throw new NullPointerException();
        }
        this.guestList = guestList;
        this.reservationNumber = generateReservationNumber();
    }

    public String generateReservationNumber() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Event getEvent() {
        return event;
    }

    public GuestList getGuestList() {
        return guestList;
    }

    public String getReservationDetails() {
        return reservationNumber + " | " + event.getEventType() + " | "
                + event.getDate() + " " + event.getTime() + " | " + guestList.getGuestCount() + " guests";
    }

    public boolean hasValidGuestList() {
        if (!guestList.validateGuestCount()) {
            return false;
        }
        int actualCount = guestList.getGuestCount();
        int expectedCount = event.getExpectedPopulation();
        return actualCount == expectedCount;
    }

    // TODO: Check availability, validate the guest list, and confirm the booking.
    public boolean confirmReservation() {
        throw new UnsupportedOperationException("Reservation confirmation is pending.");
    }
}
