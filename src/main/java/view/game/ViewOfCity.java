package view.game;

import model.City;
import model.Player;
import view.Menu;

import java.util.ArrayList;

public abstract class ViewOfCity {
    protected City whichCityPlayerWant(Player player) {
        ArrayList<City> listOfCities = player.getCities();
        this.showTheListOfCities(listOfCities);
        int index = getTheCityFromUser(listOfCities.size());
        return listOfCities.get(index - 1);
    }

    private int getTheCityFromUser(int maxAmountOfCities) {
        System.out.println("Enter the number of the city you want: ");
        int index = Menu.getScanner().nextInt();
        if (index > maxAmountOfCities || index < 1) {
            System.out.println("You enter an invalid number");
            this.getTheCityFromUser(maxAmountOfCities);
        }

        return index;
    }

    private void showTheListOfCities(ArrayList<City> listOfCities) {
        int index = 1;
        for (City city: listOfCities) {
            System.out.println(index + ":");
            System.out.println("Name of city: " + city.getName());
            System.out.println("Ground number : " + city.getGround().getNumber());
            index++;
        }
    }
}
