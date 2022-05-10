package view.game;

import model.Player;
import Enum.TechnologyType;
import model.Technology;
import view.Menu;

import Enum.Message;
import java.util.ArrayList;

public class TechnologyMenu {
    public TechnologyMenu(Player player) {
        System.out.println("You enter technology menu.");
        this.showListOfTechnologies(player);
        if (this.showObtainedTechnology(player)) this.run(player);
    }

    private void run(Player player) {
        String secondInput= Menu.getScanner().nextLine();

        if (secondInput.matches("^((--number)|(-n)) \\d+$")){
            String[] strings = secondInput.split(" +");
            int intInput = Integer.parseInt(strings[1]);
            ArrayList<Technology> technologies = player.technologiesThatCanBeObtained();
            if (intInput >= 1 && intInput <= technologies.size()){
                intInput--;
                player.setUnderConstructionTechnology(technologies.get(intInput).getTechnologyType());
                return;
            }
        }

        if (secondInput.equals("end")) return;
        System.out.println(Message.INVALID_COMMAND);
    }

    private void showListOfTechnologies(Player player) {
        System.out.println("lists of technologies:");
        for (TechnologyType technologyType: player.getTechnologyType()) {
            System.out.println(technologyType);
        }
    }

    private boolean showObtainedTechnology(Player player) {
        System.out.println("list of technologies that can be obtained:");
        if (player.technologiesThatCanBeObtained().size() == 0) {
            System.out.println("There is nothing to show:(");
            return false;
        }

        for (int i = 0; i < player.technologiesThatCanBeObtained().size(); i++){
            System.out.println(i+1 + " - type: "+player.technologiesThatCanBeObtained().get(i).getTechnologyType()
                    +"  time remain: "+player.technologiesThatCanBeObtained().get(i).getTimeRemain());
        }
        return true;
    }
}
