package viewControllers.Info;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Player;
import viewControllers.GraphicOfGame;

public class TechnologyTree extends Application {
    private static Stage stage;
    private static Player player;
    private static GraphicOfGame game;

    public static void setPlayer(Player player) {
        TechnologyTree.player = player;
    }

    public static void setGame(GraphicOfGame game) {
        TechnologyTree.game = game;
    }

    @Override
    public void start(Stage stage) throws Exception {
        TechnologyTree.stage = stage;
        //TODO don't forget game.initializing after selecting a technology
        stage.show();
    }
}
