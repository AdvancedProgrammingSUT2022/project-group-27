package view.game;

import controller.CityMenuController;
import model.Citizen;
import model.City;
import model.Ground;
import model.Player;
import view.Menu;
import Enum.Message;
import Enum.StrategicResource;

import java.awt.*;
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
        System.out.println("City strength in combats: " + city.getPower());
        String input;
        String regex;
        Matcher matcher;
        input = Menu.getScanner().nextLine();
        if (input.matches("^show grounds$")) {
            this.showGrounds(city);
            this.run(city);
        } else if (input.matches("^exit menu$")) this.exitMenu();
        else if (input.matches("^how far it remains$")) {
            this.showRemainTimes(city);
            this.run(city);
        } else if (input.matches((regex = "^lock a citizen to ground --groundNumber (?<groundNumber>\\d+)$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            lockCitizen(matcher, city);
        } else if (input.matches((regex = "^remove a citizen from work of --groundNumber (?<groundNumber>\\d+)$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            this.removeFromWork(matcher, city);
        } else if (input.matches("^output of city$")) this.showOutputOfCity(city);
        else if (input.matches("^output of civilization$")) this.showOutputOfCivilization(city);
        else if (input.matches("^show without work citizens$")) this.showWithoutWorkCitizens(city);
        else if (input.matches("^buy ground$")) this.buyGround(city);
        else if (input.matches("^lets buy$")) this.buy(city);
        else if (input.matches("^choice another city$")) this.cityMenus(city.getPlayer());
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run(city);
        }
    }

    private void exitMenu() {
        //Don't do anything special
    }

    private void buy(City city) {
        //TODO show the list of things to buy and prices
        String input = Menu.getScanner().nextLine();
        Message message = controller.buyThings(city, input);
        System.out.println(message);
        this.run(city);
    }

    private void buyGround(City city) {
        System.out.println("Please enter the number of the ground you want.");
        System.out.println("If you regretful from buying enter -1 below.");

        for (Ground ground: city.groundsNearTheCity()) {
            System.out.println("** ground number is " + ground.getNumber() +
                    "and the price of it is equal to " + ground.getCost());
        }

        int groundNumber = Menu.getScanner().nextInt();
        Message message = controller.buyGround(city, groundNumber);
        System.out.println(message);
        this.run(city);
    }

    private void showWithoutWorkCitizens(City city) {
        ArrayList<Citizen> listOfWithoutWork = city.withoutWorkCitizens();

        int index = 1;
        for (Citizen citizen: listOfWithoutWork) {
            System.out.println(index + "- citizen id: " + citizen.getId());
            index++;
        }
        this.run(city);
    }

    private void showOutputOfCivilization(City city) {
        Player player = city.getPlayer();
        System.out.println("Science per turn by this city: " + city.getScience());
        System.out.println("Gold of civilization produced by this city: " + city.getGold());
        System.out.println("The city incomes: " + city.getIncome());
        System.out.println("Happiness of civilization: " + player.getHappiness());
        showStrategicResourceOfCity(city);
        this.run(city);
    }

    private void showStrategicResourceOfCity(City city) {
        ArrayList<StrategicResource> listOfStrategicResource = new ArrayList<>();
        for (Ground ground : city.getRangeOfCity()) {
            listOfStrategicResource.addAll(ground.getStrategicResources());
        }

        System.out.println("Strategic resources of city:");
        int index = 1;
        for (StrategicResource strategicResource : listOfStrategicResource) {
            System.out.println(index + ": " + strategicResource);
            index++;
        }
    }

    private void showOutputOfCity(City city) {
        //TODO maybe should change these with how much in one turn
        System.out.println("How much food does this city store? " + city.getSavedFood());
        System.out.println("How much production does this city have? " + city.getProduction());
        System.out.println("How much gold does this city have? " + city.getGold());
        System.out.println("How much science does this city have? " + city.getScience());
        //TODO add turns of increasing for citizens
    }

    private void removeFromWork(Matcher matcher, City city) {
        int groundNumber = Integer.parseInt(matcher.group("groundNumber"));
        Message message = controller.removeFromWork(city, groundNumber);
        System.out.println(message);
        this.run(city);
    }

    private void lockCitizen(Matcher matcher, City city) {
        int groundNumber = Integer.parseInt(matcher.group("groundNumber"));
        Message message = controller.lockCitizenToGround(city, groundNumber);
        System.out.println(message);
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
