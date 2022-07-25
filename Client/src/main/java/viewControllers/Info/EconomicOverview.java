package viewControllers.Info;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.City;
import model.Player;
import viewControllers.Main;
import viewControllers.Menus;

public class EconomicOverview extends Menus {
    private static Stage stage;
    private static Player player;

    @FXML
    private Button back;

    @FXML
    private VBox list;

    @FXML
    public void initialize() {
        Label firstText = new Label("Economic Panel: ");
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        list.getChildren().add(firstText);

        setList();
        back.setCursor(Cursor.HAND);
    }

    public static void setPlayer(Player player) {
        EconomicOverview.player = player;
    }

    @Override
    public void start(Stage stage) throws Exception {
        EconomicOverview.stage = stage;
        Parent root = Main.loadFXML("economic-panel");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Economic Overview");
        stage.show();
    }

    public void setList() {
        for (City city: player.getCities()) {
            String string = "city name: " + city.getName() + "\ndemography: " + city.getListOfCitizens().size() +
                    "\npower: " + city.getCityStrength() + "\noutput of food: " + city.getFoodPerTurn() + "\nscience: " +
                    city.getScience() + "\ngold: " + city.getGold();

            if (city.getConstruction() != null) string += ("what is being produced: " + city.getConstruction().toString() +
                    " how much turn: " + city.getRemainedTurnsToBuild());
            else string += "nothing produced:(";

            list.getChildren().add(new Label(string));
        }

        if (player.getCities().size() == 0) list.getChildren().add(new Label("you haven't have any cities, yet"));
    }

    public void back(MouseEvent mouseEvent) {
        stage.close();
    }
}
