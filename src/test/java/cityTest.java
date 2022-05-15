import controller.CityMenuController;
import controller.UnitController;
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
public class cityTest {
    Ground ground = mock(Ground.class);
    Player player = mock(Player.class);
    City city = new City(ground, "...", player);

    @Test
    public void findCityByGroundTest1() {
        Assertions.assertNull(City.findCityByGround(ground, null));
    }

    @Test
    public void findCityByGroundTest2() {
        City city = mock(City.class);
        when(player.getCities()).thenReturn(new ArrayList<>(List.of(city)));
        when(city.isThisGroundInThisCityRange(ground)).thenReturn(true);

        City city1 = City.findCityByGround(ground, player);
        Assertions.assertEquals(city, city1);
    }

    @Test
    public void findCityByGroundTest3() {
        City city = mock(City.class);
        when(player.getCities()).thenReturn(new ArrayList<>(List.of(city)));
        when(city.isThisGroundInThisCityRange(ground)).thenReturn(false);

        Assertions.assertNull(City.findCityByGround(ground, player));
    }

    @Test
    public void getScienceTest() {
        Assertions.assertEquals(5 + 1, city.getScience());
    }

    @Test
    public void getFoodPerTurnTest1() {
        when(ground.canWeUseThisLuxuryResource()).thenReturn(true);
        when(ground.getLuxuryResources()).thenReturn(new ArrayList<>(List.of(LuxuryResource.FUR)));
        when(ground.canWeUseThisBonusResource()).thenReturn(true);
        when(ground.getBonusResource()).thenReturn(new ArrayList<>(List.of(BonusResource.GAZELLE)));
        when(ground.canWeUseThisStrategicResource()).thenReturn(true);
        when(ground.getStrategicResources()).thenReturn(new ArrayList<>(List.of(StrategicResource.HORSE)));
        when(ground.isWorkedOn()).thenReturn(true);
        when(ground.getGroundType()).thenReturn(GroundType.PLAIN);
        when(ground.getFeatureType()).thenReturn(FeatureType.FOREST);
        when(player.getHappiness()).thenReturn(10);

        int result = city.getFoodPerTurn();
        Assertions.assertEquals(4, result);
    }

    @Test
    public void getFoodPerTurnTest2() {
        when(ground.canWeUseThisLuxuryResource()).thenReturn(true);
        when(ground.getLuxuryResources()).thenReturn(new ArrayList<>(List.of(LuxuryResource.FUR)));
        when(ground.canWeUseThisBonusResource()).thenReturn(true);
        when(ground.getBonusResource()).thenReturn(new ArrayList<>(List.of(BonusResource.GAZELLE)));
        when(ground.canWeUseThisStrategicResource()).thenReturn(true);
        when(ground.getStrategicResources()).thenReturn(new ArrayList<>(List.of(StrategicResource.HORSE)));
        when(ground.isWorkedOn()).thenReturn(true);
        when(ground.getGroundType()).thenReturn(GroundType.PLAIN);
        when(ground.getFeatureType()).thenReturn(FeatureType.FOREST);
        when(player.getHappiness()).thenReturn(-10);

        int result = city.getFoodPerTurn();
        Assertions.assertEquals(1, result);
    }

    @Test
    public void getFoodPerTurnTest3() {
        when(ground.canWeUseThisLuxuryResource()).thenReturn(false);
        when(ground.canWeUseThisBonusResource()).thenReturn(false);
        when(ground.canWeUseThisStrategicResource()).thenReturn(false);
        when(ground.isWorkedOn()).thenReturn(false);
        when(player.getHappiness()).thenReturn(-10);

        int result = city.getFoodPerTurn();
        Assertions.assertEquals(0, result);
    }

    @Test
    public void getProductionTest() {
        when(ground.canWeUseThisLuxuryResource()).thenReturn(true);
        when(ground.getLuxuryResources()).thenReturn(new ArrayList<>(List.of(LuxuryResource.FUR)));
        when(ground.canWeUseThisBonusResource()).thenReturn(true);
        when(ground.getBonusResource()).thenReturn(new ArrayList<>(List.of(BonusResource.GAZELLE)));
        when(ground.canWeUseThisStrategicResource()).thenReturn(true);
        when(ground.getStrategicResources()).thenReturn(new ArrayList<>(List.of(StrategicResource.HORSE)));
        when(ground.isWorkedOn()).thenReturn(true);
        when(ground.getGroundType()).thenReturn(GroundType.PLAIN);
        when(ground.getFeatureType()).thenReturn(FeatureType.FOREST);

        int result = city.getProduction();
        Assertions.assertEquals(4, result);
    }

    @Test
    public void getGoldTest() {
        when(ground.canWeUseThisLuxuryResource()).thenReturn(true);
        when(ground.getLuxuryResources()).thenReturn(new ArrayList<>(List.of(LuxuryResource.FUR)));
        when(ground.canWeUseThisBonusResource()).thenReturn(true);
        when(ground.getBonusResource()).thenReturn(new ArrayList<>(List.of(BonusResource.GAZELLE)));
        when(ground.canWeUseThisStrategicResource()).thenReturn(true);
        when(ground.getStrategicResources()).thenReturn(new ArrayList<>(List.of(StrategicResource.HORSE)));
        when(ground.isWorkedOn()).thenReturn(true);
        when(ground.getGroundType()).thenReturn(GroundType.PLAIN);
        when(ground.getFeatureType()).thenReturn(FeatureType.FOREST);

        int result = city.getGold();
        Assertions.assertEquals(2, result);
    }

    @Test
    public void getCityStrengthTest() {
        MilitaryUnit militaryUnit = mock(MilitaryUnit.class);
        when(ground.getMilitaryUnit()).thenReturn(militaryUnit);
         double result = city.getCityStrength();
         Assertions.assertEquals(2.0, result);
    }
}
