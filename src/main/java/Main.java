import database.Database;
import view.LoginMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Database.readFromDatabase();

        LoginMenu.getInstance().run();

        Database.writeOnDataBase();
    }
}
