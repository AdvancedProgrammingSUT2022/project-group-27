package Enum;

public enum ProfileImages {
    HELEN("troy-helen.jpg"),
    ONE("troy-1.jpg"),
    TWO("troy-2.jpg"),
    THREE("troy-3.jpg");

    private final String address;

    ProfileImages(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address;
    }
}
