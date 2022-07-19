import controller.CityMenuController;
import model.*;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import Enum.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class CityMenuControllerTest {
    /*User user = new User("a", "a", "a");
    CityMenuController cityMenuController = new CityMenuController();
    Ground ground1 = new Ground(10, 11, 10);
    static MockedStatic<Ground> groundMockedStatic = Mockito.mockStatic(Ground.class);

    @Test
    public void isGroundValidForCityTest1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Ground ground1 = new Ground(10, 11, 10);

        City city = new City(ground1, "xyz", ground1.ownerOfThisGround());

        Method method = CityMenuController.class.getDeclaredMethod("isGroundValidForCity", City.class, Ground.class);
        method.setAccessible(true);

        Message message = (Message) method.invoke(cityMenuController, city, ground1);
        Assertions.assertNull(message);
    }

    @Test
    public void buyGroundTest1() {
        Ground ground1 = new Ground(10, 11, 10);
        Player player = mock(Player.class);
        City city = new City(ground1, "xyz", player);
        when(player.haveEnoughMoney(50)).thenReturn(true);

        Message message = cityMenuController.buyGround(city, -10);
        Assertions.assertEquals(Message.INVALID_GROUND_NUMBER, message);
    }

    @Test
    public void buyGroundTest2() {
        Ground ground1 = new Ground(10, 11, 10);
        Player player = mock(Player.class);
        City city = new City(ground1, "xyz", player);
        when(player.haveEnoughMoney(50)).thenReturn(true);

        Message message = cityMenuController.buyGround(city, -1);
        Assertions.assertEquals(Message.BACKING_TO_PREVIOUS_MENU, message);
    }

    @Test
    public void buyGroundTest3() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        when(city.getPlayer()).thenReturn(player);
        when(player.haveEnoughMoney(50)).thenReturn(true);
        when(city.isThisGroundNearThisCity(any())).thenReturn(false);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(10)).thenReturn(ground1);

        Message message = cityMenuController.buyGround(city, 10);
        Assertions.assertEquals(Message.GROUND_NOT_NEAR_CITY, message);
    }

    @Test
    public void buyGroundTest4() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        when(city.getPlayer()).thenReturn(player);
        when(player.haveEnoughMoney(anyInt())).thenReturn(false);
        when(city.isThisGroundNearThisCity(ground1)).thenReturn(true);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(10)).thenReturn(ground1);

        Message message = cityMenuController.buyGround(city, 10);
        Assertions.assertEquals(Message.NOT_ENOUGH_MONEY, message);
    }

    @Test
    public void buyGroundTest5() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        when(city.getPlayer()).thenReturn(player);
        when(player.haveEnoughMoney(50)).thenReturn(true);
        when(city.isThisGroundNearThisCity(any())).thenReturn(true);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(11)).thenReturn(ground1);

        Message message = cityMenuController.buyGround(city, 11);
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void testBuyThingsTrue() {
        Player player = mock(Player.class);
        when(player.getHappiness()).thenReturn(10);
        when(player.doWeHaveThisTechnology(any())).thenReturn(true);
        when(player.hasStrategicResource(any())).thenReturn(true);
        Ground ground = mock(Ground.class);
        City city = new City(ground, "...", player);

        Message message = cityMenuController.buyThings(city, "SETTLER");
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void testBuyThingsFalse() {
        Player player = mock(Player.class);
        when(player.getHappiness()).thenReturn(10);
        when(player.doWeHaveThisTechnology(any())).thenReturn(true);
        when(player.hasStrategicResource(any())).thenReturn(true);
        Ground ground = mock(Ground.class);
        City city = new City(ground, "...", player);

        Message message = cityMenuController.buyThings(city, "...");
        Assertions.assertEquals(Message.INVALID_UNIT_NAME, message);
    }

    @Test
    public void removeFromWorkTestFalse() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        when(city.getPlayer()).thenReturn(player);
        when(player.haveEnoughMoney(50)).thenReturn(true);
        when(city.isThisGroundInThisCityRange(any())).thenReturn(true);
        when(city.isAnyoneWorkOnGround(any())).thenReturn(null);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(11)).thenReturn(ground1);

        Message message = cityMenuController.removeFromWork(city, 11);
        Assertions.assertEquals(Message.NO_CITIZEN_ON_GROUND, message);
    }

    @Test
    public void removeFromWorkTestTrue() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        Citizen citizen = mock(Citizen.class);
        when(city.getPlayer()).thenReturn(player);
        when(player.haveEnoughMoney(50)).thenReturn(true);
        when(city.isThisGroundInThisCityRange(any())).thenReturn(true);
        when(city.isAnyoneWorkOnGround(any())).thenReturn(citizen);
        doNothing().when(citizen).removeFromGround();
        groundMockedStatic.when(() -> Ground.getGroundByNumber(11)).thenReturn(ground1);

        Message message = cityMenuController.removeFromWork(city, 11);
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void lockTestFalse() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        when(city.getPlayer()).thenReturn(player);
        when(player.haveEnoughMoney(50)).thenReturn(true);
        when(city.isThisGroundInThisCityRange(any())).thenReturn(true);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(11)).thenReturn(ground1);
        when(city.withoutWorkCitizens()).thenReturn(new ArrayList<>());

        Message message = cityMenuController.lockCitizenToGround(city, 11);
        Assertions.assertEquals(Message.NO_CITIZEN_WITHOUT_WORK, message);
    }

    @Test
    public void lockTestTrue() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        Citizen citizen = mock(Citizen.class);
        when(citizen.setGround(any())).thenReturn(true);
        when(city.getPlayer()).thenReturn(player);
        when(player.haveEnoughMoney(50)).thenReturn(true);
        when(city.isThisGroundInThisCityRange(any())).thenReturn(true);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(11)).thenReturn(ground1);
        when(city.withoutWorkCitizens()).thenReturn(new ArrayList<>(List.of(citizen)));

        Message message = cityMenuController.lockCitizenToGround(city, 11);
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @AfterAll
    public static void clean() {
        groundMockedStatic.close();
    }*/
}
