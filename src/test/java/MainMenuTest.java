import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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


@ExtendWith(MockitoExtension.class)
public class MainMenuTest {
    MainMenu menu = spy(MainMenu.class);

    @Test
    public void navigationTestForFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        doNothing().when(menu).run();
        Method method = MainMenu.class.getDeclaredMethod("enterMenu", String.class);
        method.setAccessible(true);

        method.invoke(menu, "some random strings");
        method.invoke(menu, "Main_Menu");
        method.invoke(menu, "Login_Menu");
    }

    @Test
    public void navigationTestForGameMenu() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        doNothing().when(menu).run();

        Method method = MainMenu.class.getDeclaredMethod("enterMenu", String.class);
        method.setAccessible(true);

        GameMenu gameMenu1 = spy(GameMenu.getInstance().getClass());
        doNothing().when(gameMenu1).run();

        MockedStatic<GameMenu> gameMenu = Mockito.mockStatic(GameMenu.class);
        gameMenu.when(GameMenu::getInstance).thenReturn(gameMenu1);

        method.invoke(menu, "Game_Menu");
        verify(GameMenu.getInstance());
        gameMenu.close();
    }

    @Test
    public void navigationTestForProfile() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        doNothing().when(menu).run();

        Method method = MainMenu.class.getDeclaredMethod("enterMenu", String.class);
        method.setAccessible(true);

        ProfileMenu profileMenu1 = mock(ProfileMenu.getInstance().getClass());
        doNothing().when(profileMenu1).run();

        MockedStatic<ProfileMenu> profileMenu = Mockito.mockStatic(ProfileMenu.class);
        profileMenu.when(ProfileMenu::getInstance).thenReturn(profileMenu1);

        method.invoke(menu, "Profile_Menu");
        verify(ProfileMenu.getInstance());
        profileMenu.close();
    }

    @Test
    public void logoutTest() throws NoSuchMethodException {
        Method method = MainMenu.class.getDeclaredMethod("loggedOutUser");
        method.setAccessible(true);

        Assertions.assertNull(Menu.getLoggedInUser());
    }

    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }
}
