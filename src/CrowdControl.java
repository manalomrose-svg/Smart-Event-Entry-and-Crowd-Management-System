// Starter class. Alert thresholds and entry/exit integration are pending.
public class CrowdControl {
    private int currentCount;
    private final int maximumCapacity;
    private String alertStatus;

    public CrowdControl() {
        currentCount = 0;
        maximumCapacity = 50;
        alertStatus = "GREEN";
    }

    public int getCurrentCount() { return currentCount; }
    public int getMaximumCapacity() { return maximumCapacity; }
    public String getAlertStatus() { return alertStatus; }

    public void addGuest() {
        // TODO: Update the count and alert after an approved entry.
        throw new UnsupportedOperationException("Crowd entry update is not implemented yet.");
    }

    public void subtractGuest() {
        // TODO: Update the count and alert after a valid exit.
        throw new UnsupportedOperationException("Crowd exit update is not implemented yet.");
    }

    public boolean checkCapacity() { return currentCount < maximumCapacity; }
    public boolean isLimitReached() { return currentCount >= maximumCapacity; }

    public void showAlert() {
        // TODO: Define green/yellow/red thresholds with the team.
        throw new UnsupportedOperationException("Crowd alert is not implemented yet.");
    }
}
