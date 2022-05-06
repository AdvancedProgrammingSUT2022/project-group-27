import controller.LoginController;
import model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import Enum.*;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class LoginTest {

    @BeforeEach
    public void setup() {
        new User("a", "a", "a");
        new User("b", "b", "b");
    }

    @Test
    public void testCreateUserCheckUsername() {
        new User("arefe_", "8765476", "hello");
        LoginController loginController = LoginController.getInstance();
        Message result = loginController.createUser("arefe_", "1234", "pargol");
        Assertions.assertEquals(result, Message.EXIST_USERNAME);
    }

    @Test
    public void testCreateUserCheckNickname() {
        new User("aaa", "875267", "pargol_");
        LoginController loginController = LoginController.getInstance();
        Message result = loginController.createUser("arefe__", "1234", "pargol_");
        Assertions.assertEquals(result, Message.EXIST_NICKNAME);
    }

    @Test
    public void testCreateUserCheckAcception() {
        new User("aa", "785q68", "happy");
        LoginController loginController = LoginController.getInstance();
        Message result = loginController.createUser("arefe", "1234", "pargol");
        Assertions.assertEquals(result, Message.CREATE_USER_SUCCESSFUL);
    }
}
