import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Starter class. Agree on the booking and time-slot rules before integration.
public class AvailabilityChecker {
    private final List<String> bookedSlots;

    public AvailabilityChecker() {
        bookedSlots = new ArrayList<>();
    }

    public List<String> getBookedSlots() {
        List<String> copy = new ArrayList<>(bookedSlots);
        return Collections.unmodifiableList(copy);
    }

    public boolean checkAvailability(LocalDate date, LocalTime slot) {
        // TODO: Check the requested date and time against existing bookings.
        throw new UnsupportedOperationException("Availability checking is not implemented yet.");
    }

    public void blockSlot(LocalDate date, LocalTime slot) {
        // TODO: Store a confirmed booking and prevent double booking.
        throw new UnsupportedOperationException("Slot blocking is not implemented yet.");
    }
}
