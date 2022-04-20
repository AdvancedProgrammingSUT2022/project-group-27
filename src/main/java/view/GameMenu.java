package view;

import controller.Controller;
import controller.GameController;
import Enum.Message;
import model.GlobalVariables;
import model.Ground;
import model.Pair;

import java.util.HashMap;
import java.util.regex.Matcher;

import static java.lang.Math.sqrt;

public class GameMenu extends Menu{
    //singleton pattern
    private static GameMenu instance = null;
    private GameController controller;

    private GameMenu() {
        this.controller = GameController.getInstance();
    }

    private static void setInstance(GameMenu instance) {
        GameMenu.instance = instance;
    }

    public static GameMenu getInstance() {
        if (GameMenu.instance == null) GameMenu.setInstance(new GameMenu());
        return GameMenu.instance;
    }

    public void createMap(){
        HashMap <Pair,Integer> hashMap=new HashMap<>();

        GlobalVariables globalVariables = new GlobalVariables();
        int counter=0;
        int cntNumber=1;
        for (int i=0;i<globalVariables.surfaceHeight;i+=globalVariables.arz6Zelie){
            counter++;
            int starter=0;
            if (counter%2==0) starter+=globalVariables.tool6Zelie/2;
            for (int j=starter;j<globalVariables.surfaceWidth;j+=globalVariables.tool6Zelie){
                Ground ground= new Ground(i,j,cntNumber++);
                if (globalVariables.checkIsGroundInPage(ground)==0){
                    ground.number=0;
                    cntNumber--;
                }
                Pair pair = new Pair(i,j);
                System.out.println(i + " " + j);
                hashMap.put(pair,1);
                Ground.allGround.add(ground);
            }
        }
        for (int i=0;i<globalVariables.surfaceHeight;i++){
            for (int j=0;j< globalVariables.surfaceWidth;j++){
                double best=1e9;
                int p1=0;
                Pair id=new Pair(0,0);
                for (int a=0;a<globalVariables.surfaceHeight;a++){
                    for (int b=0;b< globalVariables.surfaceWidth;b++){
                        Pair pair=new Pair(a,b);
                        Ground ground=Ground.getGroundByXY(a,b);
                        if (ground==null) continue;
                        if (globalVariables.isEqual(best, globalVariables.distanceOfTwoPoints(i, j, a, b))==1){
                            p1=1;
                            continue;
                        }
                        if (best>globalVariables.distanceOfTwoPoints(i, j, a, b)){
                            best=globalVariables.distanceOfTwoPoints(i,j,a,b);
                            p1=0;
                            id= new Pair(a,b);
                        }
                    }
                }
                //System.out.println(best + " " + p1);
                if (p1==0){
                    Ground ground=Ground.getGroundByXY(id.F,id.S);
                    if (ground==null){
                        System.out.println("Didnt find the ground!!!!");
                    }
                    Pair pixel=new Pair(i,j);
                    ground.pixelsOfThisGround.add(pixel);
                    Ground.pixelInWitchGround.put(Ground.PairToInt(pixel.F,pixel.S),ground);
                }
            }
        }
    }
    public void showMap(){
        GlobalVariables globalVariables = new GlobalVariables();
        String[][] s=new String[globalVariables.surfaceHeight][globalVariables.surfaceWidth];
        for (int i=1;i<globalVariables.surfaceHeight-1;i++){
            for (int j=1;j<globalVariables.surfaceWidth-1;j++){
                Ground ground=Ground.pixelInWitchGround.get(Ground.PairToInt(i,j));
                if (Ground.getGroundByXY(i,j)!=null){
                    Integer number= ground.number;
                    s[i][j]=number.toString();
                    while(s[i][j].length()<3){
                        s[i][j]='0'+s[i][j];
                    }
                    //s[i][j]=globalVariables.ANSI_GREEN+"███" ;
                    continue;
                }
                if (!Ground.pixelInWitchGround.containsKey(Ground.PairToInt(i,j))){
                    s[i][j]=globalVariables.ANSI_RED+"███" ;
                }
                else if (ground.getxLocation()==0 || ground.getxLocation()==globalVariables.surfaceHeight-1 || ground.getyLocation()==0 || ground.getyLocation()==globalVariables.surfaceWidth-1){
                    s[i][j]=globalVariables.ANSI_BLACK+"███" ;
                }
                else s[i][j]=globalVariables.ANSI_BLUE +"███";
            }
        }

        for (int i=1;i<globalVariables.surfaceHeight-1;i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                System.out.print(s[i][j]);
            }
            System.out.println("");
        }

    }

    @Override
    public void run() {
        String input = getScanner().nextLine();
        Matcher matcher;
        String regex;

        if (input.matches("^menu show-current$")) showMenu();
        else if (input.matches("^menu exit$")) exitMenu();
        else if (input.matches((regex = "^menu enter (?<menuName>\\S+)$"))) {
            matcher = Controller.findMatcherFromString(input, regex);
            if (matcher.find()) enterMenu(matcher.group("menuName"));
            else System.out.println(Message.INVALID_COMMAND);
        }
        else if (input.equals("create map")){
            System.out.println("mamad");
            createMap();
            System.out.println(" ejrfjen ");
            this.run();
        }
        else if (input.equals("show map")){
            showMap();
            this.run();
        }
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    @Override
    protected void showMenu() {
        System.out.println("Game Menu");
        this.run();
    }

    @Override
    protected void enterMenu(String newMenuName) {
        switch (newMenuName) {
            case "Main_Menu":
                return;
            case "Profile_Menu", "Login_Menu":
                System.out.println(Message.INVALID_NAVIGATION);
                break;
            case "Game_Menu" :
                System.out.println(Message.CURRENT_MENU);
                break;
            default :
                System.out.println(Message.INVALID_MENU_NAME);
                break;
        }

        this.run();
    }
}
