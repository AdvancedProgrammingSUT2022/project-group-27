import controller.*;
import model.*;
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
import static org.mockito.Mockito.*;

import Enum.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class BuildCityControllerTest {
    BuildCityController buildCityController = new BuildCityController();

    @Test
    public void testBuildUnitTrue() {
        Player player = mock(Player.class);
        when(player.getHappiness()).thenReturn(10);
        when(player.doWeHaveThisTechnology(any())).thenReturn(true);
        when(player.hasStrategicResource(any())).thenReturn(true);
        Ground ground = mock(Ground.class);
        City city = new City(ground, "...", player);
        Message message = buildCityController.buildUnit(city, "WORKER");
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void testBuildUnitFalse() {
        Player player = mock(Player.class);
        when(player.getHappiness()).thenReturn(10);
        when(player.doWeHaveThisTechnology(any())).thenReturn(true);
        when(player.hasStrategicResource(any())).thenReturn(true);
        Ground ground = mock(Ground.class);
        City city = new City(ground, "...", player);
        Message message = buildCityController.buildUnit(city, "...");
        Assertions.assertEquals(Message.INVALID_UNIT_NAME, message);
    }

    @Test
    public void testChangeProductionTrue() {
        Player player = mock(Player.class);
        when(player.getHappiness()).thenReturn(10);
        when(player.doWeHaveThisTechnology(any())).thenReturn(true);
        when(player.hasStrategicResource(any())).thenReturn(true);
        Ground ground = mock(Ground.class);
        City city = new City(ground, "...", player);
        Message message = buildCityController.changeConstruction(city, "CATAPULT");
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void testChangeProductionTrue2() {
        Player player = mock(Player.class);
        when(player.getHappiness()).thenReturn(10);
        when(player.doWeHaveThisTechnology(any())).thenReturn(true);
        when(player.hasStrategicResource(any())).thenReturn(true);
        Ground ground = mock(Ground.class);
        City city = new City(ground, "...", player);
        Message message = buildCityController.changeConstruction(city, "TANK");
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void testChangeProductionFalse() {
        Player player = mock(Player.class);
        when(player.getHappiness()).thenReturn(-10);
        when(player.doWeHaveThisTechnology(any())).thenReturn(true);
        when(player.hasStrategicResource(any())).thenReturn(true);
        Ground ground = mock(Ground.class);
        City city = new City(ground, "...", player);
        Message message = buildCityController.changeConstruction(city, "SETTLER");
        Assertions.assertEquals(Message.INVALID_UNIT_NAME, message);
    }
}
