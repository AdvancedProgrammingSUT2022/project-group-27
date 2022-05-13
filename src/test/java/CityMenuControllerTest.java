import controller.CityMenuController;
import model.City;
import model.Ground;
import model.Player;
import model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import Enum.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class CityMenuControllerTest {
    User user = new User("a", "a", "a");
    CityMenuController cityMenuController = new CityMenuController();

    @Test
    public void isGroundValidForCityTest1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Ground ground1 = new Ground(10, 11, 10);
        ground1.setOwner(new Player(user));
        City city = new City(ground1, "xyz", ground1.getOwner());

        Method method = CityMenuController.class.getDeclaredMethod("isGroundValidForCity", City.class, Ground.class);
        method.setAccessible(true);

        Message message = (Message) method.invoke(cityMenuController, city, ground1);
        Assertions.assertNull(message);
    }

    @Test
    public void buyGroundTest1() {
        Ground ground1 = new Ground(10, 11, 10);
        ground1.setOwner(new Player(user));
        City city = new City(ground1, "xyz", ground1.getOwner());
        when(city.getPlayer().haveEnoughMoney(any())).thenReturn(true);

        Message message = cityMenuController.buyGround(city, -10);
        Assertions.assertEquals(Message.INVALID_GROUND_NUMBER, message);
    }

    @Test
    public void buyGroundTest2() {
        Ground ground1 = new Ground(10, 11, 10);
        ground1.setOwner(new Player(user));
        City city = new City(ground1, "xyz", ground1.getOwner());
        when(city.getPlayer().haveEnoughMoney(any())).thenReturn(true);

        Message message = cityMenuController.buyGround(city, -1);
        Assertions.assertEquals(Message.BACKING_TO_PREVIOUS_MENU, message);
    }

    @Test
    public void buyGroundTest3() {
        Ground ground1 = new Ground(10, 11, 10);
        ground1.setOwner(new Player(user));
        City city = new City(ground1, "xyz", ground1.getOwner());
        when(city.getPlayer().haveEnoughMoney(any())).thenReturn(true);

        Message message = cityMenuController.buyGround(city, 50);
        Assertions.assertEquals(Message.GROUND_NOT_NEAR_CITY, message);
    }

    @Test
    public void buyGroundTest4() {
        Ground ground1 = new Ground(10, 11, 10);
        ground1.setOwner(new Player(user));
        City city = new City(ground1, "xyz", ground1.getOwner());
        when(city.getPlayer().haveEnoughMoney(any())).thenReturn(false);

        Message message = cityMenuController.buyGround(city, 11);
        Assertions.assertEquals(Message.NOT_ENOUGH_MONEY, message);
    }

    @Test
    public void buyGroundTest5() {
        Ground ground1 = new Ground(10, 11, 10);
        ground1.setOwner(new Player(user));
        City city = new City(ground1, "xyz", ground1.getOwner());
        when(city.getPlayer().haveEnoughMoney(any())).thenReturn(true);

        Message message = cityMenuController.buyGround(city, 11);
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }
}
