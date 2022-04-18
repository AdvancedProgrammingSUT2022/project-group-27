package view;

import model.User;

import java.util.Scanner;

public abstract class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    protected static User loggedInUser = null;

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setLoggedInUser(User loggedInUser) {
        Menu.loggedInUser = loggedInUser;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public abstract void run();
    protected abstract void enterMenu(String newMenuName);
    protected abstract void showMenu();

    protected void exitMenu(){
        //nothing should do here
    }
}
