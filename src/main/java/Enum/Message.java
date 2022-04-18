package Enum;

public enum Message {
    INVALID_COMMAND("Sorry but you enter an invalid command."),
    ENTER_MENU("You enter the menu you want!"),
    INVALID_NAVIGATION("Menu navigation is not possible."),
    NICKNAME_CHANGED("Your nickname changed successfully!"),
    PASSWORD_CHANGED("Your password changed successfully!"),
    EXIST_NICKNAME("The nickname you choice is already exist"),
    INVALID_PASSWORD("Password is invalid");

    private String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
