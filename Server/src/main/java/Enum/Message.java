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
    DUPLICSTED_PASSWORD("your new password is the same as previous one"),
    LOGIN_SUCCESSFUL("User logged in successfully!"),
    USERNAME_NOT_EXIST("One of the usernames you choice doesn't exist"),
    START_GAME("Game starts..."),
    USER_DOESNT_EXIST("Some of your selected users doesn't exist!"),
    LOGOUT("User logged out successfully"),
    INVALID_GROUND_NUMBER("Sorry but there isn't any ground with this number."),
    NO_CITIZEN_WITHOUT_WORK("Sorry but this city doesn't have any citizens who doesn't have any work."),
    GROUND_NOT_IN_CITY("Sorry but the ground you choice is not in the city."),
    GROUND_NOT_NEAR_CITY("Sorry but the ground you select it's not near your city."),
    NOT_ENOUGH_MONEY("Sorry but you don't have enough gold for buy this:("),
    IS_WORK_GROUND("The ground you choice has been already worked on."),
    NO_CITIZEN_ON_GROUND("There is no citizen who works on this ground."),
    BACKING_TO_PREVIOUS_MENU("Ok, backing to previous direction."),
    INVALID_UNIT_NAME("Sorry but the unit you want doesn't exist."),
    INVALID_BUILDING_NAME("Sorry but the building you want doesn't exist."),
    INVALID_PRODUCTION_NAME("Sorry but production you want doesn't exist."),
    INVALID_TYPE("The type you want is not exist in this ground."),
    UNIT_CHOICE_SUCCESSFUL("Unit choices successfully"),
    PRODUCTION_IS_ON_PROGRESS("Sorry but production is in progress."),
    UNIT_CAN_NOT_DO("Your unit can't do this action"),
    SUCCESS_WORK("Everything happens successfully:)"),
    FIGHTING_START("fighting is start..."),
    LOST_RESPONSE("Unfortunately, we don't get response from server"),
    CONQUER_CITY("ConquerCity");

    private String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
