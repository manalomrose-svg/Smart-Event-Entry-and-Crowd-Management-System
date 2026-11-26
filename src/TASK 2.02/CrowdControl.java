public class CrowdControl {
    private int currentCount;
    private final int maximumCapacity;
    private String alertStatus;
 
    public CrowdControl() {
        currentCount = 0;
        maximumCapacity = 50;
        alertStatus = "GREEN";
    }
 
    public int getCurrentCount() {
        return currentCount;
    }
 
    public int getMaximumCapacity() {
        return maximumCapacity;
    }
 
    public String getAlertStatus() {
        return alertStatus;
    }
 
    public void addGuest() {
        // Task 2.02: refuse an entry when the venue is already full.
        if (isLimitReached()) {
            throw new IllegalStateException("Cannot add guest: maximum capacity of "
                    + maximumCapacity + " has been reached");
        }
        //Update the count and alert after an approved entry.

        throw new UnsupportedOperationException("Crowd entry update is not implemented yet.");
    }
 
    public void subtractGuest() {
        //refuse an exit when nobody is inside.
        if (currentCount <= 0) {
            throw new IllegalStateException("Cannot subtract guest: no guests are inside");
        }
        //Update the count and alert after a valid exit.

        throw new UnsupportedOperationException("Crowd exit update is not implemented yet.");
    }
 
    public boolean checkCapacity() {
        return currentCount < maximumCapacity;
    }
 
    public boolean isLimitReached() {
        return currentCount >= maximumCapacity;
    }
 
    public void showAlert() {
        
        throw new UnsupportedOperationException("Crowd alert is not implemented yet.");
    }
}