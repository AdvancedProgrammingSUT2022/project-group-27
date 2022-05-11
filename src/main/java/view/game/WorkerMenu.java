package view.game;

import controller.Game;
import model.Player;
import model.Worker;
import view.Menu;
import Enum.Message;
public class WorkerMenu extends ViewOfCity{
    private Game controller=Game.getInstance();
    private Player player;
    public WorkerMenu(Player player){
        System.out.println("Welcome to Worker Menu.");
        this.player=player;
        this.run();
    }
    public void run(){
        System.out.println("Choose which type of services you want");
        System.out.println("clear land");
        System.out.println("build road");
        System.out.println("build railway");
        System.out.println("improvement menu");
        String input =  Menu.getScanner().nextLine();
        if (input.equals("exit menu")) return ;
        if (input.equals("clear land")){
            this.clearLand();
            return;
        }
        if (input.equals("build road")){
            this.buildRoad();
            return;
        }
        if (input.equals("build railway")){
            this.buildRailway();
            return ;
        }
        if (input.equals("improvement menu")){
            System.out.println("enter ground number : ");
            int groundNumber=Menu.getScanner().nextInt();
            ImprovementMenu improvementMenu=new ImprovementMenu(player,groundNumber);
            return ;
        }
    }
    private void clearLand(){
        System.out.println("choose ground number : ");
        int counter=0;
        for (int i=0;i<player.getUnits().size();i++){
            if (player.getUnits().get(i) instanceof Worker){
                counter++;
                System.out.println(counter + ": " +  player.getUnits().get(i).getGround().getNumber());
            }
        }
        int input=Menu.getScanner().nextInt();
        if (input>counter || input<0){
            System.out.println(Message.INVALID_COMMAND);
            this.clearLand();
            return ;
        }
        System.out.println(Message.SUCCESS_WORK);
        counter=0;
        for (int i=0;i<player.getUnits().size();i++){
            if (player.getUnits().get(i) instanceof Worker){
                counter++;
                if (counter==input){
                    controller.clearLand(player.getUnits().get(i).getGround());
                }
            }
        }
    }
    private void buildRoad(){
        System.out.println("choose ground number : ");
        int counter=0;
        for (int i=0;i<player.getUnits().size();i++){
            if (player.getUnits().get(i) instanceof Worker){
                counter++;
                System.out.println(counter + ": " +  player.getUnits().get(i).getGround().getNumber());
            }
        }
        int input=Menu.getScanner().nextInt();
        if (input>counter || input<0){
            System.out.println(Message.INVALID_COMMAND);
            this.clearLand();
            return ;
        }
        counter=0;
        for (int i=0;i<player.getUnits().size();i++){
            if (player.getUnits().get(i) instanceof Worker){
                counter++;
                if (counter==input){
                    controller.buildRoad(player.getUnits().get(i).getGround());
                }
            }
        }
    }
    private void buildRailway(){
        System.out.println("choose ground number : ");
        int counter=0;
        for (int i=0;i<player.getUnits().size();i++){
            if (player.getUnits().get(i) instanceof Worker){
                counter++;
                System.out.println(counter + ": " +  player.getUnits().get(i).getGround().getNumber());
            }
        }
        int input=Menu.getScanner().nextInt();
        if (input>counter || input<0){
            System.out.println(Message.INVALID_COMMAND);
            this.clearLand();
            return ;
        }
        counter=0;
        for (int i=0;i<player.getUnits().size();i++){
            if (player.getUnits().get(i) instanceof Worker){
                counter++;
                if (counter==input){
                    controller.buildRailway(player.getUnits().get(i).getGround());
                }
            }
        }
    }
}
