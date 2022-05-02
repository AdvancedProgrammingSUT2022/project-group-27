package view.game;

import controller.BuildCityController;
import model.City;
import view.Menu;
import Enum.Message;

import java.util.regex.Matcher;

public class BuildCityMenu {
    private final BuildCityController controller = new BuildCityController();

    public void buildCityMenu(City city) {
        System.out.println("Welcome to BuildCity Menu.");
        this.run(city);
    }

    private void run(City city) {
        this.showThingsToBuild(city);
        String input =  Menu.getScanner().nextLine();
        String regex;
        Matcher matcher;
        if (input.matches("^exit menu$")) exitMenu();
        else if (input.matches((regex = "^build unit --unitName (?<unitName>\\S+)$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            buildUnit(matcher, city);
        }
        else if (input.matches((regex = "^change production to --nameOfNewProduct (?<newBuild>\\S+)$"))) {
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

    private void showThingsToBuild(City city) {
        //TODO... show every units that can build with the technologies that player have right now
    }

    private void buildUnit(Matcher matcher, City city) {
        String unitName = matcher.group("unitName");
        Message message = controller.buildUnit(city, unitName);
        System.out.println(message);
        this.run(city);
    }

    private void changeProduction(Matcher matcher, City city) {
        String productionName = matcher.group("newBuild");
        Message message = controller.changeProduct(city, productionName);
        System.out.println(message);
        this.run(city);
    }
}
