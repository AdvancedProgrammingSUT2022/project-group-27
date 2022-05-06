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
import view.MainMenu;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@ExtendWith(MockitoExtension.class)
public class MainMenuTest {

    @Test
    public void navigationTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final var mainMenuClass = MainMenu.getInstance();
        Method method = mainMenuClass.getClass().getDeclaredMethod("enterMenu", String.class);
        method.setAccessible(true);
        method.invoke(mainMenuClass);
    }
}
