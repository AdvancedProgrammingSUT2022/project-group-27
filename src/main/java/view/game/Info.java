package view.game;

import model.City;
import model.Notification;
import model.Player;
import model.Unit;
import view.Menu;

public class Info {
    public Info(Player player) {
        System.out.println("Welcome to Info menu");
        System.out.println("From here you can go to other panels");
        this.run(player);
    }

    private void run(Player player) {
        String input;
        boolean isExit = false;
        input = Menu.getScanner().nextLine();
        if (input.matches("^research panel$")) new TechnologyMenu(player);
        else if (input.matches("^unit panel$")) new UnitMenu(player);
        else if (input.matches("^city panel$")) (new CityView()).cityMenus(player);
        else if (input.matches("^demographic panel$")) this.demographicMenu(player);
        else if (input.matches("^notification history$")) this.notificationHistory(player);
        else if (input.matches("^military overview$")) this.militaryOverview(player);
        else if (input.matches("^economic overview$")) this.economicOverview(player);
        else if (input.matches("^exit$")) isExit = true;
        if (!isExit) this.run(player);
    }

    private void economicOverview(Player player) {
        for (City city: player.getCities()) {
            System.out.println("demography: " + city.getListOfCitizens().size());
            System.out.println("power: " + city.getPower());
            System.out.println("output of food: " + city.getFoodPerTurn());
            System.out.println("science: " + city.getScience());
            System.out.println("gold: " + city.getGold());
            System.out.println("what is being produced: " + city.getConstruction().toString() +
                    " how much turn: " + city.getRemainedTurnsToBuild());
            System.out.println("***********************");
        }

        System.out.println("Are you want to go to city menu? please enter Yes if you want else you can press enter");
        String string = Menu.getScanner().nextLine();
        if (string.equals("Yes")) (new CityView()).cityMenus(player);
    }

    private void militaryOverview(Player player) {
        System.out.println("List of all units: ");
        for (Unit unit: player.getUnits()) {
            System.out.println(unit);
        }
    }

    private void notificationHistory(Player player) {
        System.out.println("List of notifications you get: ");
        for (Notification notification: player.getNotificationHistory()) {
            System.out.println(notification);
        }
    }

    private void demographicMenu(Player player) {
        System.out.println("how many city do you have? " + player.getCities().size());
        System.out.println("how many gold do you have? " + player.getGold());
        System.out.println("how many science do you have? " + player.getScience());
        System.out.println("how many food do you store? " + player.getFood());
        System.out.println("how many units do you have? " + player.getUnits().size());
    }
}
