package viewControllers.Info;

import javafx.application.Application;
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
import model.Trade;
import viewControllers.Main;

public class TradingInformationPanel extends Application {
    private static Stage stage;
    private static Player player;

    @FXML
    private VBox list;

    @FXML
    private Button back;

    @FXML
    public void initialize() {
        Label firstText = new Label("History of trades: ");
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        list.getChildren().add(firstText);

        setList();
        back.setCursor(Cursor.HAND);
    }

    public static void setPlayer(Player player) {
        TradingInformationPanel.player = player;
    }

    @Override
    public void start(Stage stage) throws Exception {
        TradingInformationPanel.stage = stage;
        Parent root = Main.loadFXML("trading-information-panel");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Trading information panel");
        stage.show();
    }

    private void setList() {
        for (Trade trade: Trade.userTradesAll(player.getUser())) {
            list.getChildren().add(new Label("# " + trade.toString() + "\naccepting: " + trade.isAccepted() + " denying: " + trade.isDeny()));
        }

        if (Trade.userTradesAll(player.getUser()).size() == 0) list.getChildren().add(new Label("nothing to show"));
    }

    public void back(MouseEvent mouseEvent) {
        stage.close();
    }
}
