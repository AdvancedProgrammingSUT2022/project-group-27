package view.game;

import controller.CityMenuController;
import model.*;
import view.Menu;
import Enum.Message;
import Enum.StrategicResource;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CityView extends ViewOfCity{
    private final CityMenuController controller = new CityMenuController();

    public void cityMenus(Player player) {
        System.out.println("You enter the menu of cities.");
        System.out.println("Now you can choice the city you want.");
        City choiceCity = whichCityPlayerWant(player);
        if (choiceCity == null) return;
        this.run(choiceCity);
    }

    private void run(City city) {
        System.out.println("Name of city: " + city.getName());
        System.out.println("City strength in combats: " + city.getCityStrength());
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
        } else if (input.matches((regex = "^lock a citizen to ground ((--groundNumber)|(-n)) (?<groundNumber>\\d+)$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            this.lockCitizen(matcher, city);
        } else if (input.matches((regex = "^remove a citizen from work of ((--groundNumber)|(-n)) (?<groundNumber>\\d+)$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            this.removeFromWork(matcher, city);
        } else if (input.matches("^output of city$")) this.showOutputOfCity(city);
        else if (input.matches("^output of civilization$")) this.showOutputOfCivilization(city);
        else if (input.matches("^show without work citizens$")) this.showWithoutWorkCitizens(city);
        else if (input.matches("^buy ground$")) this.buyGround(city);
        else if (input.matches("^lets buy$")) this.buy(city);
        else if (input.matches("^choice another city$")) this.cityMenus(city.getPlayer());
        else if (input.matches((regex = "^fight to ground ((--groundNumber)|(-n)) (?<groundNumber>\\d+)$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            this.fightToGround(matcher, city);
        }
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run(city);
        }
    }

    private void exitMenu() {
        //Don't do anything special
    }

    private void fightToGround(Matcher matcher, City city) {
        int groundNumber = Integer.parseInt(matcher.group("groundNumber"));
        Ground ground = Ground.getGroundByNumber(groundNumber);
        if (ground == null) {
            System.out.println(Message.INVALID_GROUND_NUMBER);
            return;
        }

        System.out.println("Fighting is starting...");
        city.combat(ground);
    }

    private void buy(City city) {
        this.unitListCanBuy(city.getPlayer());
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
        System.out.println("The city production: " + city.getProduction());
        System.out.println("Happiness of civilization: " + player.getHappiness());
        System.out.println("Food produce per turn: " + city.getFoodPerTurn());
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
        System.out.println("How much food does this city store? " + city.getSavedFood());
        System.out.println("How much production does this city have? " + city.getProduction());
        System.out.println("How much gold does this city have? " + city.getGold());
        System.out.println("How much science does this city have? " + city.getScience());
        //TODO add turns of increasing for citizens
        this.run(city);
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
        System.out.println("Technologies which remain:");
        for (Technology technology: city.getPlayer().technologiesThatCanBeObtained()){
            if (city.getPlayer().getUnderConstructionTechnology() == null) break;
            if (technology.getTechnologyType().name().equals(city.getPlayer().getUnderConstructionTechnology().name()))
                System.out.println(technology.getTechnologyType().name() + "remain time is: " +
                        technology.getTimeRemain());
        }

        System.out.println("Unit which remains:");
        if (city.getConstruction() != null) System.out.println(city.getConstruction().getMilitaryType().name() +
                "remain time is: " + city.getRemainedTurnsToBuild());
        if (city.getBuildingUnit() != null) System.out.println(city.getBuildingUnit().name() + "remain time is: " +
                city.getRemainedTurnsToBuild());
    }

    private void showGrounds(City city) {
        for (Ground ground: city.getRangeOfCity()) {
            System.out.println("Ground number is: " + ground.getNumber());
        }
    }
}
