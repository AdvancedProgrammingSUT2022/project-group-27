package view.game;

import controller.ImprovementMenuController;
import model.*;
import Enum.ImprovementType;
import view.Menu;
import Enum.Message;

import java.util.ArrayList;

public class ImprovementMenu {
    private final ImprovementMenuController controller = new ImprovementMenuController();

    public ImprovementMenu(Player player, int groundNumber) {
        System.out.println("Welcome to Improvement Menu.");
        Ground ground = Ground.getGroundByNumber(groundNumber);
        if (ground == null) {
            System.out.println("Sorry but you enter an invalid number for grounds");
            return;
        }

        Improvement improvementInProgress = ground.getImprovementTypeInProgress();
        if (improvementInProgress != null) System.out.println("The improvement that you have here is: " +
                improvementInProgress.getImprovementType().name() + "turns in progress: " + improvementInProgress.getTurnRemained()); //TODO test if .name work as we want

        if (!controller.haveWorker(player, groundNumber)) {
            System.out.println("404, there's no worker");
            return;
        }

        ArrayList<ImprovementType> list=ground.listOfImprovementTypes();
        showListOfImprovementType(list);
        this.run(ground, player, list);
    }

    private void run(Ground ground, Player player, ArrayList<ImprovementType> list) {
        String secondInput= Menu.getScanner().nextLine();

        if (secondInput.matches("^\\d+$")){
            int intInput = Integer.parseInt(secondInput);
            if (intInput >= 1 && intInput <= list.size()){
                ground.setImprovementTypeInProgress(list.get(intInput - 1));
                for (Unit unit: player.getUnits()){
                    if (unit.getGround().getNumber() == ground.getNumber() && unit instanceof Worker){
                        ((Worker) unit).setWorking(true);
                    }
                }
                return;
            }
        }
        if (secondInput.equals("end")) return;
        System.out.println(Message.INVALID_COMMAND);
    }

    private void showListOfImprovementType(ArrayList<ImprovementType> list) {
        for (int i=0;i<list.size();i++){
            System.out.println(i+1+"- : " + list.get(i));
        }
    }
}
