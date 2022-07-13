package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Player;
import model.Trade;
import model.User;
import Enum.*;

import java.util.ArrayList;

public class TradePanel extends Application {
    private static Stage stage;

    @FXML
    private Button submit;

    @FXML
    private TextField sendingNumber;

    @FXML
    private ComboBox<String> receivingObject;

    @FXML
    private TextField receivingNumber;

    @FXML
    private ComboBox<String> sendingObject;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    public void initialize() {
        setComboBox();
        setSendingObject();
        setReceivingObject();
        submit.setCursor(Cursor.HAND);
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

    private void setReceivingObject() {
        ArrayList<String> products = new ArrayList<>();
        products.add("gold");

        for (StrategicResource strategicResource: StrategicResource.values()) {
            if (strategicResource.name().equals("NOTHING")) break;
            products.add(strategicResource.name());
        }

        for (BonusResource bonusResource: BonusResource.values()) {
            if (bonusResource.name().equals("NOTHING")) break;
            products.add(bonusResource.name());
        }

        for (LuxuryResource luxuryResource: LuxuryResource.values()) {
            if (luxuryResource.name().equals("NOTHING")) break;
            products.add(luxuryResource.name());
        }

        receivingObject.setItems(FXCollections.observableArrayList(products));
    }

    private void setSendingObject() {
        ArrayList<String> products = new ArrayList<>();
        products.add("gold");

        for (StrategicResource strategicResource: Player.whichPlayerTurnIs().getAllStrategicResources()) {
            products.add(strategicResource.name());
        }

        for (BonusResource bonusResource: Player.whichPlayerTurnIs().getAllBonusResources()) {
            products.add(bonusResource.name());
        }

        for (LuxuryResource luxuryResource: Player.whichPlayerTurnIs().getAllLuxuryResources()) {
            products.add(luxuryResource.name());
        }

        sendingObject.setItems(FXCollections.observableArrayList(products));
    }

    private void setComboBox() {
        ArrayList<String> users = new ArrayList<>();
        for (Player player: Player.getAllPlayers()) {
            if (player != Player.whichPlayerTurnIs()) users.add(player.getUser().getUsername());
        }

        comboBox.setItems(FXCollections.observableArrayList(users));
    }

    public void submit(MouseEvent mouseEvent) {
        int sendNumber;
        int receiveNumber;
        try {
            sendNumber = Integer.parseInt(sendingNumber.getText());
            receiveNumber = Integer.parseInt(receivingNumber.getText());
            if (sendNumber > 1 && !sendingObject.getSelectionModel().toString().equals("gold")) throw new RuntimeException();
            if (receiveNumber > 1 && !receivingObject.getSelectionModel().toString().equals("gold")) throw new RuntimeException();
            if (Player.whichPlayerTurnIs().getGold() < sendNumber) throw new RuntimeException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("invalid input");
            alert.setContentText("your number formats is invalid");
            alert.show();
            return;
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("invalid input");
            alert.setContentText("you don't have enough number of resource");
            alert.show();
            return;
        }

        new Trade(Player.whichPlayerTurnIs().getUser(), User.findUser(comboBox.getSelectionModel().getSelectedItem()),
                sendingObject.getSelectionModel().getSelectedItem(), sendNumber, receivingObject.getSelectionModel().getSelectedItem(),
                receiveNumber);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("successful trade");
        alert.setContentText("sending object: " + sendingNumber.getText() + "- " + sendingObject.getSelectionModel().getSelectedItem() +
                "\nreceiving object: " + receivingNumber.getText() + "- " + receivingObject.getSelectionModel().getSelectedItem() +
                "\nreceiver is: " + comboBox.getSelectionModel().getSelectedItem());
        alert.show();
        stage.close();
    }
}
