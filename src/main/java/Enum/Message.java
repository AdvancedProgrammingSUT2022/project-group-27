package Enum;

public enum Message {
    INVALID_COMMAND("Sorry but you enter an invalid command."),
    ENTER_MENU("You enter the menu you want!"),
    INVALID_NAVIGATION("Menu navigation is not possible."),
    CURRENT_MENU("This is your current menu!"),
    INVALID_MENU_NAME("The menu you want doesn't exist."),
    LOGIN_FIRST("Please login first."),
    NICKNAME_CHANGED("Your nickname changed successfully!"),
    PASSWORD_CHANGED("Your password changed successfully!"),
    EXIST_NICKNAME("The nickname you choice is already exist"),
    EXIST_USERNAME("The username you choice is already exist"),
    CREATE_USER_SUCCESSFUL("User created successfully!"),
    USER_IS_INVALID("Username and password didn't match!"),
    INVALID_PASSWORD("Password is invalid"),
    LOGIN_SUCCESSFUL("User logged in successfully!"),
    LOGOUT("User logged out successfully");

    private String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
