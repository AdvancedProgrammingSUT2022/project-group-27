package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;
import view.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import Enum.Message;
public class GameView extends Application {
    private static Stage stage;
    private static MediaPlayer audio;
    public TextField arrayOfEnemies;
    public Button startGameButton;
    public Label errorOfStartGame;

    @Override
    public void start(Stage stage) throws Exception {
        GameView.stage = stage;
        audio = Main.loadingAudio("openingTitles.mpeg");
        Parent root = Main.loadFXML("GameView");
        root.getStylesheets().add(Main.class.getResource("/css/NumberOfEnemies.css").toExternalForm());
        root.getStyleClass().add("background");
        System.out.println("game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Game Menu");
        stage.show();
    }

    public void startGame(MouseEvent mouseEvent) {
        String s[]=arrayOfEnemies.getText().split(",");
        ArrayList<String> arrayList=new ArrayList<>();
        for (int i=0;i<s.length;i++){
            if (s[i].trim().equals("")) continue;
            arrayList.add(s[i].trim());
        }
        ArrayList<User> listOfPlayers=new ArrayList<>();
        for (String playerUsers:arrayList){
            listOfPlayers.add(User.findUser(playerUsers));
        }
        view.game.GameView gameView = new view.game.GameView(new ArrayList<User>(listOfPlayers));
        System.out.println(Message.START_GAME);
        gameView.run();
    }

    public void textFieldEditor(KeyEvent keyEvent) {
        String s[]=arrayOfEnemies.getText().split(",");
        ArrayList<String> arrayList=new ArrayList<>();
        for (int i=0;i<s.length;i++){
            if (s[i].trim().equals("")) continue;
            arrayList.add(s[i].trim());
        }
        System.out.println(arrayList + " " + arrayOfEnemies.getText() + " " + arrayOfEnemies.getLength());
        for (User user: User.getListOfUsers()){
            System.out.println("username= : " + user.getUsername());
        }
        if (!(User.doWeHaveAllOfTheseUsers(arrayList) && User.isThisListOfUsersUnique(arrayList) && arrayList.contains(Menu.getLoggedInUser().getUsername()))){
            arrayOfEnemies.setStyle("-fx-text-fill: red;");
            startGameButton.setDisable(true);
            errorOfStartGame.setTextFill(Color.RED);
            errorOfStartGame.setText("");
            if (!User.doWeHaveAllOfTheseUsers(arrayList)) errorOfStartGame.setText("one of the usernames is invalid.\n");
            if (!User.isThisListOfUsersUnique(arrayList)) errorOfStartGame.setText(errorOfStartGame.getText()+" "+"the list you entered is not unique.\n");
            if (!arrayList.contains(Menu.getLoggedInUser().getUsername()))
                errorOfStartGame.setText(errorOfStartGame.getText()+"I can not see your username in this list ;/");
        }
        else{
            arrayOfEnemies.setStyle("-fx-text-fill: green;");
            startGameButton.setDisable(false);
            errorOfStartGame.setText("");
        }
    }
}
