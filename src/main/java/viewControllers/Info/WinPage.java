package viewControllers.Info;

import Main.Main;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Player;
import Enum.TechnologyType;
import viewControllers.Menus;

public class WinPage extends Menus {
    private static Stage stage;
    private static Player player;

    @FXML
    private VBox list;

    @FXML
    private Button back;

    @FXML
    public void initialize() {
        Label firstText = new Label("List of all Technology: ");
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        list.getChildren().add(firstText);

        setList();
        back.setCursor(Cursor.HAND);
    }

    public static void setPlayer(Player player) {
        WinPage.player = player;
    }

    @Override
    public void start(Stage stage) throws Exception {
        WinPage.stage = stage;
        Parent root = Main.loadFXML("win-page");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Win Page");
        stage.show();
    }

    private void setList() {
        for (TechnologyType technology: player.getTechnologyType()) {
            list.getChildren().add(new Label(technology.name()));
        }
        if (player.getTechnologyType().size() == 0) list.getChildren().add(new Label("nothing to show:("));

        Label gold = new Label("Gold is: " + player.getGold());
        Label science = new Label("Science is: " + player.getScience());
        Label happiness = new Label("happiness is: " + player.getHappiness());

        happiness.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        gold.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        science.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        list.getChildren().add(gold);
        list.getChildren().add(science);
        list.getChildren().add(happiness);
    }

    public void back(MouseEvent mouseEvent) {
        stage.close();
    }
}
