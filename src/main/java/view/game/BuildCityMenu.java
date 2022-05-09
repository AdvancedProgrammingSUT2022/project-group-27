package view.game;

import controller.BuildCityController;
import model.City;
import model.Player;
import view.Menu;
import Enum.Message;
import Enum.MilitaryType;

import java.util.regex.Matcher;

public class BuildCityMenu extends ViewOfCity{
    private final BuildCityController controller = new BuildCityController();

    public void buildCityMenu(Player player) {
        System.out.println("Welcome to BuildCity Menu.");
        City choiceCity = whichCityPlayerWant(player);
        if (choiceCity == null) return;
        this.run(choiceCity);
    }

    private void run(City city) {
        this.showThingsToBuild(city.getPlayer());
        String input =  Menu.getScanner().nextLine();
        String regex;
        Matcher matcher;
        if (input.matches("^exit menu$")) exitMenu();
        else if (input.matches((regex = "^build unit ((--unitName)|(-u)) (?<unitName>\\S+)$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            buildUnit(matcher, city);
        }
        else if (input.matches((regex = "^change production to ((--nameOfNewProduct)|(-p)) (?<newBuild>\\S+)$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            changeProduction(matcher, city);
        } else {
            System.out.println(Message.INVALID_COMMAND);
            this.run(city);
        }
    }

    private void exitMenu() {
        //Don't do anything special
    }

    private void showThingsToBuild(Player player) {
        System.out.println("**Units which you can buy:");
        for (MilitaryType militaryType: MilitaryType.values()) {
            if (player.doWeHaveThisTechnology(militaryType.getTechnologyTypes())) {
                System.out.println("Unit name: " + militaryType.name());
            }
        }
    }

    private void buildUnit(Matcher matcher, City city) {
        this.unitListCanBuy(city.getPlayer());
        String unitName = matcher.group("unitName");
        Message message = controller.buildUnit(city, unitName);
        System.out.println(message);
        this.run(city);
    }

    private void changeProduction(Matcher matcher, City city) {
        String productionName = matcher.group("newBuild");
        Message message = controller.changeConstruction(city, productionName);
        System.out.println(message);
        this.run(city);
    }
}
