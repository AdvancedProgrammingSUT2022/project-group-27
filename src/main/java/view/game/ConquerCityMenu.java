package view.game;

import controller.GameController;
import model.City;
import model.Player;
import view.Menu;

import java.util.regex.Matcher;

public class ConquerCityMenu {
    public static void run(City city, Player player) {
        System.out.println("List of things that you can do with this city: ");
        System.out.println("1. destroy city");
        System.out.println("2. puppet city");
        System.out.println("3. get the city");
        System.out.println("Please enter a number from 1 to 3 below: ");
        while (!doAction(city, player));
    }

    private static boolean doAction(City city, Player player) {
        String input = Menu.getScanner().nextLine();
        Matcher matcher = GameController.getInstance().findMatcherFromString(input, "\\d+");
        if (matcher == null) {
            System.out.println("we just want a number from you not other things:(");
            return false;
        }

        int number = Integer.parseInt(input);
        if (number == 1) {
            city.getPlayer().getCities().remove(city);
            System.out.println("city is destroyed");
        }
        else if (number == 2) {
            city.setPuppet(true, player);
            player.increaseHappiness(10);
            System.out.println("city is puppet to you");
        } else if (number == 3) {
            city.setPlayer(player);
            player.increaseHappiness(-10);
            System.out.println("city is adding to you");
        } else {
            System.out.println("Invalid number");
            return false;
        }

        return true;
    }
}
