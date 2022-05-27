package Main;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.LoginMenu;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        launch();
        Database.readFromDatabase();

        LoginMenu.getInstance().run();

        Database.writeOnDataBase();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL address = new URL(Main.class.getResource("/welcome.fxml").toExternalForm());
        Parent root = FXMLLoader.load(address);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
