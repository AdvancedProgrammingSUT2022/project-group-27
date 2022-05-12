package view.game;

import controller.Controller;
import controller.Game;
import controller.UnitController;
import model.Player;
import model.Unit;
import model.Worker;
import view.Menu;
import Enum.Message;

import java.util.regex.Matcher;

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
        System.out.println("delete road and railway");
        System.out.println("repair");
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
            String string = Menu.getScanner().nextLine();
            Matcher matcher = controller.findMatcherFromString(string, "\\d+");
            if (matcher == null) System.out.println(Message.INVALID_COMMAND);
            else {
                int groundNumber = Integer.parseInt(string);
                ImprovementMenu improvementMenu = new ImprovementMenu(player, groundNumber);
            }

            this.run();
            return ;
        }
        if (input.equals("delete road and railway")){
            this.removeRoadAndRailway();
        }
        if (input.equals("repair")){
            this.repair();
        }
    }

    private int showListOfWorkers() {
        System.out.println("choose ground number : ");
        int counter=0;
        for (int i=0;i<player.getUnits().size();i++){
            if (player.getUnits().get(i) instanceof Worker){
                counter++;
                System.out.println(counter + ": " +  player.getUnits().get(i).getGround().getNumber());
            }
        }
        return counter;
    }

    private Worker getWorkerByNumber(int number) {
        int counter = 0;
        for (Unit unit: player.getUnits()){
            if (unit instanceof Worker){
                counter++;
                if (counter == number){
                    return (Worker) unit;
                }
            }
        }

        return null;
    }

    private void clearLand(){
        int numberOfWorker = showListOfWorkers();
        if (numberOfWorker == 0) {
            System.out.println("there is no worker:(");
            return;
        }

        String numberInput = Menu.getScanner().nextLine();
        Matcher matcher = controller.findMatcherFromString(numberInput, "\\d+");
        if (matcher == null) {
            System.out.println(Message.INVALID_COMMAND);
            this.clearLand();
            return;
        }

        int number = Integer.parseInt(numberInput);
        Worker worker = getWorkerByNumber(number);
        if (worker == null) {
            System.out.println(Message.INVALID_COMMAND);
            this.clearLand();
            return ;
        }

        System.out.println(Message.SUCCESS_WORK);
        worker.setWorking(true);
        controller.clearLand(worker.getGround());
    }

    private void buildRoad(){
        int numberOfWorker = showListOfWorkers();
        if (numberOfWorker == 0) {
            System.out.println("there is no worker:(");
            return;
        }

        String numberInput = Menu.getScanner().nextLine();
        Matcher matcher = controller.findMatcherFromString(numberInput, "\\d+");
        if (matcher == null) {
            System.out.println(Message.INVALID_COMMAND);
            this.buildRoad();
            return;
        }

        int number = Integer.parseInt(numberInput);
        Worker worker = getWorkerByNumber(number);
        if (worker == null) {
            System.out.println(Message.INVALID_COMMAND);
            this.buildRoad();
            return ;
        }

        System.out.println(Message.SUCCESS_WORK);
        worker.setWorking(true);
        controller.buildRoad(worker.getGround());
    }

    private void buildRailway(){
        int numberOfWorker = showListOfWorkers();
        if (numberOfWorker == 0) {
            System.out.println("there is no worker:(");
            return;
        }

        String numberInput = Menu.getScanner().nextLine();
        Matcher matcher = controller.findMatcherFromString(numberInput, "\\d+");
        if (matcher == null) {
            System.out.println(Message.INVALID_COMMAND);
            this.buildRailway();
            return;
        }

        int number = Integer.parseInt(numberInput);
        Worker worker = getWorkerByNumber(number);
        if (worker == null) {
            System.out.println(Message.INVALID_COMMAND);
            this.buildRailway();
            return ;
        }

        System.out.println(Message.SUCCESS_WORK);
        worker.setWorking(true);
        controller.buildRailway(worker.getGround());
    }
    private void removeRoadAndRailway(){
        int numberOfWorker = showListOfWorkers();
        if (numberOfWorker == 0) {
            System.out.println("there is no worker:(");
            return;
        }

        String numberInput = Menu.getScanner().nextLine();
        Matcher matcher = controller.findMatcherFromString(numberInput, "\\d+");
        if (matcher == null) {
            System.out.println(Message.INVALID_COMMAND);
            this.buildRailway();
            return;
        }

        int number = Integer.parseInt(numberInput);
        Worker worker = getWorkerByNumber(number);
        if (worker == null) {
            System.out.println(Message.INVALID_COMMAND);
            this.buildRailway();
            return ;
        }

        System.out.println(Message.SUCCESS_WORK);
        worker.setWorking(true);
        worker.getGround().deleteRoadAndRailway();
    }
    public void repair(){
        int numberOfWorker = showListOfWorkers();
        if (numberOfWorker == 0) {
            System.out.println("there is no worker:(");
            return;
        }

        String numberInput = Menu.getScanner().nextLine();
        Matcher matcher = controller.findMatcherFromString(numberInput, "\\d+");
        if (matcher == null) {
            System.out.println(Message.INVALID_COMMAND);
            this.buildRailway();
            return;
        }

        int number = Integer.parseInt(numberInput);
        Worker worker = getWorkerByNumber(number);
        if (worker == null) {
            System.out.println(Message.INVALID_COMMAND);
            this.buildRailway();
            return ;
        }
        if (worker.getGround().getImprovementType()==null){
            System.out.println("no improvement here");
            return ;
        }
        worker.setWorking(true);
        UnitController.freePlundering(worker);
    }

}
