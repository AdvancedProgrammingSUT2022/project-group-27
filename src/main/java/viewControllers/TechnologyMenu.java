package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;
import Enum.TechnologyType;
import model.Technology;


import java.util.Optional;

import static java.lang.Math.max;

public class TechnologyMenu extends Application {
    private static Stage stage;
    private static Player player;
    private static GraphicOfGame game;

    @FXML
    private VBox technologyPlayerHave;

    @FXML
    private VBox technologyObtained;

    @FXML
    public void initialize() {
        Label firstText = new Label("list of technologies you have:");
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        technologyPlayerHave.getChildren().add(firstText);

        Label secondText = new Label("list of obtained technology:");
        secondText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        technologyObtained.getChildren().add(secondText);

        addingHaveTechnologyToList();
        addingObtainedTechnologyList();
    }

    public static void setPlayer(Player player) {
        TechnologyMenu.player = player;
    }

    public static void setGame(GraphicOfGame game) {
        TechnologyMenu.game = game;
    }

    @Override
    public void start(Stage stage) throws Exception {
        TechnologyMenu.stage = stage;
        Parent root = Main.loadFXML("technology-menu");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Technology Menu");
        stage.show();
    }

    private void addingObtainedTechnologyList() {
        for (Technology technology: player.technologiesThatCanBeObtained()) {
            Label technologyLabel = new Label("-type: " + technology.getTechnologyType().name() + " -time remain: " +
                    (technology.getTimeRemain() + player.getScience() - 1) / max(1,player.getScience()));
            settingHoverToEach(technologyLabel);

            technologyLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    player.setUnderConstructionTechnology(technology);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("technology which now under constructor is: ");
                    alert.setContentText(technology.getTechnologyType().name());
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        game.initializing();
                        stage.close();
                    }
                }
            });
            technologyObtained.getChildren().add(technologyLabel);
        }
    }

    private void addingHaveTechnologyToList() {
        for (TechnologyType technology: player.getTechnologyType()) {
            Label technologyLabel = new Label("* " + technology.name());
            technologyPlayerHave.getChildren().add(technologyLabel);
        }

        if (player.getTechnologyType().size() == 0) {
            Label text = new Label("* Sorry, but you haven't have any technologies, yet");
            technologyPlayerHave.getChildren().add(text);
        }
    }

    private void settingHoverToEach(Label ourLabel) {
        ourLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ourLabel.setStyle("-fx-background-radius: 5; -fx-background-color: rgba(93,93,93,0.39); -fx-text-fill: white;");
            }
        });
        ourLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ourLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.61); -fx-text-fill: black;");
            }
        });
    }
}
