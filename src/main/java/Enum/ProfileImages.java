package Enum;

public enum ProfileImages {
    HELEN("/profile/troy-helen.jpg"),
    ONE("/profile/troy-1.jpg"),
    TWO("/profile/troy-2.jpg"),
    THREE("/profile/troy-3.jpg");

    private final String address;

    ProfileImages(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address;
    }
}
