package viewControllers.Info;

import Main.Main;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Player;
import model.User;
import viewControllers.Menus;
import viewControllers.TradePanel;

import java.util.ArrayList;

public class DiplomacyPanel extends Menus {
    private static Stage stage;
    private static Player player;

    @FXML
    private Button declareWar;

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

    public void declareWar(MouseEvent mouseEvent) {
        final Stage preStage = new Stage();
        ArrayList<String> users = new ArrayList<>();
        for (Player player: Player.getAllPlayers()) {
            if (player != Player.whichPlayerTurnIs()) users.add(player.getUser().getUsername());
        }

        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(users));

        Button submit = new Button("submit");
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Player other = Player.findPlayerByUser(User.findUser(comboBox.getSelectionModel().getSelectedItem()));
                player.setInWar(other);
                other.setInWar(player);
                showAlert("from now you and " + comboBox.getSelectionModel().getSelectedItem() + " are in war");
                preStage.close();
            }
        });
        VBox vBox = new VBox(comboBox, submit);
        vBox.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        vBox.getStyleClass().add("background");
        Scene scene = new Scene(vBox);
        preStage.setScene(scene);
        preStage.initOwner(stage);
        preStage.show();
    }


}
