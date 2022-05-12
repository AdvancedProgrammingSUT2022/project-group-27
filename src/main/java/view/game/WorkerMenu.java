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
        System.out.println("exit successfully");
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
            this.run();
            return ;
        }
        if (input.equals("build road")){
            this.buildRoad();
            this.run();
            return ;
        }
        if (input.equals("build railway")){
            this.buildRailway();
            this.run();
            return ;
        }
        if (input.equals("improvement menu")){
            System.out.println("enter ground number : ");
            int groundNumber=Integer.parseInt(Menu.getScanner().nextLine());
            ImprovementMenu improvementMenu=new ImprovementMenu(player,groundNumber);
            this.run();
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
        if (counter==0){
            System.out.println("there is no worker:(");
            return ;
        }
        int input=Integer.parseInt(Menu.getScanner().nextLine());
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
                    ((Worker) player.getUnits().get(i)).setWorking(true);
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
        if (counter==0){
            System.out.println("there is no worker:(");
            return ;
        }
        int input=Integer.parseInt(Menu.getScanner().nextLine());
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
                    ((Worker) player.getUnits().get(i)).setWorking(true);
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
        if (counter==0){
            System.out.println("there is no worker:(");
            return ;
        }
        int input=Integer.parseInt(Menu.getScanner().nextLine());
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
                    ((Worker) player.getUnits().get(i)).setWorking(true);
                    controller.buildRailway(player.getUnits().get(i).getGround());
                }
            }
        }
    }
}
