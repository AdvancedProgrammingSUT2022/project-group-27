package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.City;
import model.Player;

public class ConquerCityPanel extends Application {
    private static Stage stage;
    private static Player player;
    private static City city;

    @FXML
    private Button getCity;

    @FXML
    private Button puppetCity;

    @FXML
    private Button destroyCity;

    @FXML
    public void initialize() {
        destroyCity.setCursor(Cursor.HAND);
        puppetCity.setCursor(Cursor.HAND);
        getCity.setCursor(Cursor.HAND);
    }

    public static void setPlayer(Player player) {
        ConquerCityPanel.player = player;
    }

    public static void setCity(City city) {
        ConquerCityPanel.city = city;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ConquerCityPanel.stage = stage;
        Parent root = Main.loadFXML("conquer-city-panel");
        root.getStylesheets().add(Main.class.getResource("/css/conquer.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Conquer city panel");
        stage.show();
    }

    private void setAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Success");
        alert.setContentText(text);
        alert.show();
        stage.close();
        try {
            GraphicOfGame.getInstance().initializing();
        } catch (Exception e) {
            GraphicOfGame.showMap();
        }
    }

    public void destroy(MouseEvent mouseEvent) {
        city.getPlayer().getCities().remove(city);
        setAlert("you destroy the city");
    }

    public void puppet(MouseEvent mouseEvent) {
        city.setPuppet(true, player);
        player.increaseHappiness(4);
        setAlert("you puppet the city:)");
    }

    public void get(MouseEvent mouseEvent) {
        city.setPlayer(player);
        player.increaseHappiness(-4);
        setAlert("From now the city is your own");
    }
}
