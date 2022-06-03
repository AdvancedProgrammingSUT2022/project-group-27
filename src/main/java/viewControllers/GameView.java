package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.User;

import java.util.ArrayList;

public class GameView extends Application {
    private static Stage stage;
    private static MediaPlayer audio;
    public TextField arrayOfEnemies;

    @Override
    public void start(Stage stage) throws Exception {
        GameView.stage = stage;
        audio = Main.loadingAudio("openingTitles.mpeg");
        Parent root = Main.loadFXML("GameView");
        root.getStylesheets().add(Main.class.getResource("/css/login.css").toExternalForm());
        root.getStyleClass().add("background");
        System.out.println("game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Game Menu");
        stage.show();
    }

    public void startGame(MouseEvent mouseEvent) {
    }

    public void textFieldEditor(KeyEvent keyEvent) {
        String s[]=arrayOfEnemies.getText().split(",");
        ArrayList<String> arrayList=new ArrayList<>();
        for (int i=0;i<s.length;i++){
            if (s[i].trim().equals("")) continue;
            arrayList.add(s[i].trim());
        }
        System.out.println(arrayList + " " + arrayOfEnemies.getText());
        for (User user: User.getListOfUsers()){
            System.out.println("username= : " + user.getUsername());
        }
        if (!(User.doWeHaveAllOfTheseUsers(arrayList) && User.isThisListOfUsersUnique(arrayList))){
            arrayOfEnemies.setStyle("-fx-text-fill: red;");
            System.out.println("odijfie");
        }
        else{
            arrayOfEnemies.setStyle("-fx-text-fill: green;");
        }
    }
}
