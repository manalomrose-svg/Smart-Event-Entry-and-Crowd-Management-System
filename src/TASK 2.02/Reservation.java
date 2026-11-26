import java.util.UUID;

public class Reservation {
    private final String reservationNumber;
    private final Customer customer;
    private final Event event;
    private final GuestList guestList;

    public Reservation(Customer customer, Event event, GuestList guestList) {
        if (customer == null) {
            throw new NullPointerException("Customer cannot be null.");
        }
        this.customer = customer;
        if (event == null) {
            throw new NullPointerException("Event cannot be null.");
        }
        this.event = event;
        if (guestList == null) {
            throw new NullPointerException("Guest list cannot be null.");
        }
        this.guestList = guestList;

        String number = generateReservationNumber();
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalStateException("Reservation number could not be generated.");
        }
        this.reservationNumber = number;
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

    // TODO: Check availability and confirm the booking.
    public boolean confirmReservation() {
        if (!guestList.validateGuestCount()) {
            throw new IllegalStateException(
                "Reservation refused: the guest list has an invalid number of guests.");
        }
        if (!hasValidGuestList()) {
            throw new IllegalStateException(
                "Reservation refused: the guest count (" + guestList.getGuestCount()
                + ") does not match the expected population (" + event.getExpectedPopulation() + ").");
        }

        // TODO: Call the availability checker, then mark the booking as confirmed.
        throw new UnsupportedOperationException("Reservation confirmation is pending.");
    }
}