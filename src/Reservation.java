import java.util.Objects;
import java.util.UUID;

public class Reservation {
    private final String reservationNumber;
    private final Customer customer;
    private final Event event;
    private final GuestList guestList;

    public Reservation(Customer customer, Event event, GuestList guestList) {
        this.customer = Objects.requireNonNull(customer);
        this.event = Objects.requireNonNull(event);
        this.guestList = Objects.requireNonNull(guestList);
        this.reservationNumber = generateReservationNumber();
    }

    public String generateReservationNumber() { return UUID.randomUUID().toString(); }
    public String getReservationNumber() { return reservationNumber; }
    public Customer getCustomer() { return customer; }
    public Event getEvent() { return event; }
    public GuestList getGuestList() { return guestList; }

    public String getReservationDetails() {
        return reservationNumber + " | " + event.getEventType() + " | "
                + event.getDate() + " " + event.getTime() + " | " + guestList.getGuestCount() + " guests";
    }
    public boolean hasValidGuestList() {
        return guestList.validateGuestCount() && guestList.getGuestCount() == event.getExpectedPopulation();
    }
    // TODO: Check availability, validate the guest list, and confirm the booking.
    public boolean confirmReservation() {
        throw new UnsupportedOperationException("Reservation confirmation is pending.");
    }
}
