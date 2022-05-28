package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView extends Application {
    private static Stage stage;

    @FXML
    private VBox box;

    @FXML
    private Label text;

    @FXML
    private Button logOut;

    @FXML
    private Button profile;

    @FXML
    private Button game;

    @FXML
    public void initialize() {
        //TODO setting place of them and adding username and maybe it's profile image
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainMenuView.stage = stage;
        Parent root = Main.loadFXML("main-menu-view");
        root.getStylesheets().add(Main.class.getResource("/css/mainMenu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }
}
