public class Guest {
    private final String name;
    private Status status;

    public Guest(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name is required.");
        }
        this.name = name.trim();
        this.status = Status.NOT_REGISTERED;
    }

    public String getName() { return name; }
    public Status getStatus() { return status; }

    // The entry controller must verify eligibility and capacity before calling this.
    public boolean register() {
        if (status != Status.NOT_REGISTERED) return false;
        status = Status.REGISTERED;
        return true;
    }

    public boolean markExited() {
        if (status != Status.REGISTERED) return false;
        status = Status.EXITED;
        return true;
    }
}
