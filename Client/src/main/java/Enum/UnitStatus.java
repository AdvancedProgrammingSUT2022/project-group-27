package Enum;

import javafx.scene.paint.Color;

public enum UnitStatus {
    SLEEP("Sleeping...", Color.GRAY),
    AWAKE("awaking...", Color.GREEN),
    READY("Ready to fight...", Color.YELLOW),
    IMPROVING("Improving status", Color.PINK),
    HEALTH_IMPROVING("health improving is on", Color.PURPLE),
    READY_TO_FIGHT("ready to fight...", Color.WHITE),
    READY_TO_RANGED("ready to ranged fight...", Color.WHITE);

    private final String status;
    private final Color color;

    UnitStatus(String status, Color color) {
        this.status = status;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return status;
    }

    public static UnitStatus getStatusByName(String name) {
        for (UnitStatus unitStatus: UnitStatus.values()) {
            if (unitStatus.name().equals(name)) return unitStatus;
        }
        System.out.println(name);
        return null;
    }
}
