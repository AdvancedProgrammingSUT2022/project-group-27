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
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) throws IOException {
        launch();

        LoginMenu.getInstance().run(); //TODO change these things to graphic

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Database.writeOnDataBase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Database.readFromDatabase();
        Main.stage = stage;
        Parent root = loadFXML("welcome");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static Parent loadFXML(String name) throws IOException {
        URL address = new URL(Main.class.getResource("/" + name + ".fxml").toExternalForm());
        return FXMLLoader.load(address);
    }
}
