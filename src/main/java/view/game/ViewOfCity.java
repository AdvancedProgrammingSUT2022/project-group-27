package view.game;

import model.City;
import model.Player;
import view.Menu;
import Enum.*;

import java.util.ArrayList;

public abstract class ViewOfCity {
    protected void unitListCanBuy(Player player) {
        System.out.println("unit list: ");
        for (MilitaryType militaryType: MilitaryType.values()) {
            if (player.doWeHaveThisTechnology(militaryType.getTechnologyTypes()) &&
                    (militaryType.getStrategicResources().size() == 0 || player.hasStrategicResource(militaryType.getStrategicResources().get(0)))) {
                System.out.println("type: " + militaryType.name() + " cost: " + militaryType.getCost());
            }
        }
    }

    protected City whichCityPlayerWant(Player player) {
        ArrayList<City> listOfCities = player.getCities();
        if (listOfCities.size() == 0) {
            System.out.println("Sorry but you don't have any cities:(");
            return null;
        }
        this.showTheListOfCities(listOfCities);
        int index;
        while ((index = getTheCityFromUser(listOfCities.size())) == 0);
        return listOfCities.get(index - 1);
    }

    private int getTheCityFromUser(int maxAmountOfCities) {
        System.out.println("Enter the number of the city you want: ");
        String input = Menu.getScanner().nextLine();
        if (!input.matches("^\\d+$")) {
            System.out.println("You enter an invalid input");
            return 0;
        }
        int index = Integer.parseInt(input);
        if (index > maxAmountOfCities || index < 1) {
            System.out.println("You enter an invalid number");
            return 0;
        }

        return index;
    }

    private void showTheListOfCities(ArrayList<City> listOfCities) {
        int index = 1;
        for (City city: listOfCities) {
            System.out.println(index + ":");
            System.out.println("Name of city: " + city.getName());
            System.out.println("Ground number : " + city.getGround().getNumber());
            if (city.isPuppet()) System.out.println("it is a puppet city!");
            System.out.println("**************");
            index++;
        }
    }
}
