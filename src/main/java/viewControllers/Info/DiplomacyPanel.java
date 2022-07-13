package viewControllers.Info;

import Main.Main;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Player;
import viewControllers.Menus;
import viewControllers.TradePanel;

public class DiplomacyPanel extends Menus {
    private static Stage stage;
    private static Player player;

    @FXML
    private Button trade;

    @FXML
    private Button discuss;

    @FXML
    private Button back;

    @FXML
    public void initialize() {
        trade.setCursor(Cursor.HAND);
        discuss.setCursor(Cursor.HAND);
        back.setCursor(Cursor.HAND);
    }

    public static void setPlayer(Player player) {
        DiplomacyPanel.player = player;
    }

    @Override
    public void start(Stage stage) throws Exception {
        DiplomacyPanel.stage = stage;
        Parent root = Main.loadFXML("diplomacy-panel");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("diplomacy panel");
        stage.show();
    }

    public void back(MouseEvent mouseEvent) {
        stage.close();
    }

    public void tradePanel(MouseEvent mouseEvent) throws Exception {
        TradePanel tradePanel = new TradePanel();
        tradePanel.start(new Stage());
    }

    public void discussPanel(MouseEvent mouseEvent) {
    }
}
