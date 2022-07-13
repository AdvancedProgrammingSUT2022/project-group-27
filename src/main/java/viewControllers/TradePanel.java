package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TradePanel extends Application {
    private static Stage stage;

    @FXML
    private TextField sendingNumber;

    @FXML
    private ComboBox receivingObject;

    @FXML
    private TextField receivingNumber;

    @FXML
    private ComboBox sendingObject;

    @FXML
    private ComboBox comboBox;

    @FXML
    public void initialize() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        TradePanel.stage = stage;
        Parent root = Main.loadFXML("trade-panel");
        root.getStylesheets().add(Main.class.getResource("/css/tradePanel.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Trade Panel");
        stage.show();
    }
}
