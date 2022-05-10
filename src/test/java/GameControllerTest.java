import controller.Controller;
import controller.GameController;
import controller.LoginController;
import model.User;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import Enum.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {
    GameController controller = GameController.getInstance();
    static MockedStatic<User> userMockedStatic = mockStatic(User.class);

    @Test
    public void checkUsernamesValidFalse() {
        when(User.isUsernameExist(anyString())).thenReturn(false);
        Message result = controller.startGame(new ArrayList<>(List.of("a", "b")));
        Assertions.assertEquals(result, Message.USERNAME_NOT_EXIST);
    }

    @Test
    public void checkUsernamesValidTrue() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("a", "a", "a"));

        when(User.isUsernameExist(anyString())).thenReturn(true);
        Message result = controller.startGame(new ArrayList<>(List.of("a")));
        Assertions.assertEquals(result, Message.START_GAME);
    }

    @AfterAll
    public static void close() {
        userMockedStatic.close();
    }
}
