package view.game;

import controller.CityMenuController;
import model.City;
import model.Ground;
import model.Player;
import view.Menu;
import Enum.Message;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CityView {
    private final CityMenuController controller = new CityMenuController();
    public void cityMenus(Player player) {
        System.out.println("You enter the menu of cities.");
        System.out.println("Now you can choice the city you want.");
        City choiceCity = listOfCities(player);
        this.run(choiceCity);
    }

    private void run(City city) {
        System.out.println("Name of city: " + city.getName());
        System.out.println("City strength in combats: " + city.getCityStrength());
        //TODO add other methods it should have...
        String input;
        String regex;
        Matcher matcher;
        input = Menu.getScanner().nextLine();
        if (input.matches("^show grounds$")) {
            this.showGrounds(city);
            this.run(city);
        } else if (input.matches("^exit menu$")) return;
        else if (input.matches("^how far it remains$")) {
            this.showRemainTimes(city);
            this.run(city);
        } else if (input.matches((regex = "^lock a citizen to ground (?<groundNumber>\\d+)$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            lockCitizen(matcher, city);
        }
        else if (input.matches("choice another city")) this.cityMenus(city.getPlayer());
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run(city);
        }
    }

    private void lockCitizen(Matcher matcher, City city) {
        int groundNumber = Integer.parseInt(matcher.group("groundNumber"));
        Message message = controller.lockCitizenToGround(city, groundNumber);
        this.run(city);
    }

    private void showRemainTimes(City city) {
        //TODO... show the time remains to complete technology, buildings, units and new citizen
    }

    private void showGrounds(City city) {
        for (Ground ground: city.getRangeOfCity()) {
            System.out.println("Ground number is: " + ground.getNumber());
        }
    }

    private City listOfCities(Player player) {
        ArrayList<City> listOfCities = player.getCities();
        this.showTheListOfCities(listOfCities);
        int index = getTheCityFromUser(listOfCities.size());
        return listOfCities.get(index - 1);
    }

    private int getTheCityFromUser(int maxAmountOfCities) {
        System.out.println("Enter the number of the city you want: ");
        int index = Menu.getScanner().nextInt();
        if (index > maxAmountOfCities || index < 1) {
            System.out.println("you enter an invalid number");
            this.getTheCityFromUser(maxAmountOfCities);
        }

        return index;
    }

    private void showTheListOfCities(ArrayList<City> listOfCities) {
        int index = 1;
        for (City city: listOfCities) {
            System.out.println(index + ":");
            System.out.println("name of city: " + city.getName());
            System.out.println("Ground number : " + city.getGround().getNumber());
            index++;
        }
    }
}
