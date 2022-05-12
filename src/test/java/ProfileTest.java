import controller.ProfileController;
import model.User;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import Enum.*;
import view.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


@ExtendWith(MockitoExtension.class)
public class ProfileTest {
    ProfileController profileController = ProfileController.getInstance();
    User user = mock(User.class);
    static MockedStatic<Menu> menuMockedStatic = Mockito.mockStatic(Menu.class);

    @Test
    public void changeNicknameChangeTrue() {
        when(user.changeNickname(anyString())).thenReturn(true);
        menuMockedStatic.when(Menu::getLoggedInUser).thenReturn(user);
        Message result = profileController.changeNickname("a");
        Assertions.assertEquals(result, Message.NICKNAME_CHANGED);
    }

    @Test
    public void changeNicknameChangeFalse() {
        when(user.changeNickname(anyString())).thenReturn(false);
        menuMockedStatic.when(Menu::getLoggedInUser).thenReturn(user);
        Message result = profileController.changeNickname("a");
        Assertions.assertEquals(result, Message.EXIST_NICKNAME);
    }

    @Test
    public void changePasswordNotValid() {
        when(user.isPasswordCorrect(anyString())).thenReturn(false);
        when(user.changePassword(anyString())).thenCallRealMethod();
        menuMockedStatic.when(Menu::getLoggedInUser).thenReturn(user);
        Message result = profileController.changePassword("a", "a");
        Assertions.assertEquals(result, Message.INVALID_PASSWORD);
    }

    @Test
    public void changePasswordDuplicated() {
        when(user.isPasswordCorrect(anyString())).thenReturn(true);
        when(user.changePassword(anyString())).thenReturn(false);
        menuMockedStatic.when(Menu::getLoggedInUser).thenReturn(user);
        Message result = profileController.changePassword("a", "a");
        Assertions.assertEquals(result, Message.DUPLICSTED_PASSWORD);
    }

    @Test
    public void changePasswordValid() {
        when(user.isPasswordCorrect(anyString())).thenReturn(true);
        when(user.changePassword(anyString())).thenReturn(true);
        menuMockedStatic.when(Menu::getLoggedInUser).thenReturn(user);
        Message result = profileController.changePassword("aaa", "a");
        Assertions.assertEquals(result, Message.PASSWORD_CHANGED);
    }

    @AfterAll
    public static void close() {
        menuMockedStatic.close();
    }
}
