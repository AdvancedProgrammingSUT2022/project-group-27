import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import view.GameMenu;
import view.MainMenu;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@ExtendWith(MockitoExtension.class)
public class MainMenuTest {

    @Test
    public void navigationTestForFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MainMenu menu = spy(MainMenu.class);
        doNothing().when(menu).run();

        Method method = MainMenu.class.getDeclaredMethod("enterMenu", String.class);
        method.setAccessible(true);

        method.invoke(menu, "some random strings");
        method.invoke(menu, "Main_Menu");
        method.invoke(menu, "Login_Menu");
    }
}
