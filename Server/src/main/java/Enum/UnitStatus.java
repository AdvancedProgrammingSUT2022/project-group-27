package Enum;

public enum UnitStatus {
    SLEEP("Sleeping..."),
    AWAKE("awaking..."),
    READY("Ready to fight..."),
    IMPROVING("Improving status"),
    HEALTH_IMPROVING("health improving is on"),
    READY_TO_FIGHT("ready to fight..."),
    READY_TO_RANGED("ready to ranged fight...");

    private final String status;

    UnitStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
