package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Player;

public class TechnologyMenu extends Application {
    private static Stage stage;
    private static Player player;

    public static void setPlayer(Player player) {
        TechnologyMenu.player = player;
    }

    @Override
    public void start(Stage stage) throws Exception {
        TechnologyMenu.stage = stage;
        Parent root = Main.loadFXML("technology-menu");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Technology Menu");
        stage.show();
    }
}
