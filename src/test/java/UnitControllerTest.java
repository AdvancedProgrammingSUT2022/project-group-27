import controller.UnitController;
import model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import Enum.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UnitControllerTest {
    Ground ground1 = new Ground(10, 11, 10);
    static MockedStatic<Ground> groundMockedStatic = Mockito.mockStatic(Ground.class);
    static MockedStatic<City> cityMockedStatic = Mockito.mockStatic(City.class);

    @Test
    public void findUnitFromMatcherTest() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(10)).thenReturn(ground);
        when(ground.isFreeOfMilitaryUnit()).thenReturn(true);

        Message message = UnitController.findUnitFromMatcher(10, "Military", player);
        Assertions.assertEquals(Message.INVALID_TYPE, message);
    }

    @Test
    public void findUnitFromMatcherTest2() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(10)).thenReturn(ground);
        when(ground.isFreeOfMilitaryUnit()).thenReturn(true);

        Message message = UnitController.findUnitFromMatcher(10, "...", player);
        Assertions.assertEquals(Message.UNIT_CHOICE_SUCCESSFUL, message);
    }

    @Test
    public void findUnitFromMatcherTest3() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(10)).thenReturn(ground);
        when(ground.isFreeOfUnMilitaryUnit()).thenReturn(true);

        Message message = UnitController.findUnitFromMatcher(10, "UnMilitary", player);
        Assertions.assertEquals(Message.INVALID_TYPE, message);
    }

    @Test
    public void deleteUnit() {
        Unit unit = mock(Unit.class);
        Player player = mock(Player.class);
        when(unit.getPlayer()).thenReturn(player);
        doNothing().when(player).setGold(anyInt());
        when(player.getGold()).thenReturn(0);
        when(unit.getCost()).thenReturn(0);
        doNothing().when(unit).removeUnit();

        UnitController.deleteUnit(unit);
    }

    @Test
    public void buildUnitTest1() {
        City city = mock(City.class);
        Player player = mock(Player.class);
        when(city.getPlayer()).thenReturn(player);
        when(city.getRemainedTurnsToBuild()).thenReturn(10);
        UnitController.buildUnit(city, MilitaryType.WARRIOR);
    }

    @Test
    public void buildUnitTest2() {
        City city = mock(City.class);
        Player player = mock(Player.class);
        when(city.getPlayer()).thenReturn(player);
        when(city.getRemainedTurnsToBuild()).thenReturn(0);
        doNothing().when(city).setRemainedTurnsToBuild(anyInt());
        when(city.getProduction()).thenReturn(1);
        doNothing().when(city).setBuildingUnit(any());
        UnitController.buildUnit(city, MilitaryType.PANZER);
    }

    @Test
    public void meleeFightTest() {
        Ground ground = mock(Ground.class);
        when(ground.AreTheseTwoGroundAdjacent(any(), any())).thenReturn(true);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(10)).thenReturn(ground1);
        MeleeUnit unit = mock(MeleeUnit.class);
        when(unit.getGround()).thenReturn(ground1);

        Message message = UnitController.meleeFight(unit, 10);
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void meleeFightTest2() {
        Ground ground = mock(Ground.class);
        when(ground.AreTheseTwoGroundAdjacent(any(), any())).thenReturn(true);
        groundMockedStatic.when(() -> Ground.getGroundByNumber(10)).thenReturn(ground1);
        Unit unit = mock(Unit.class);
        when(unit.getGround()).thenReturn(ground1);

        Message message = UnitController.meleeFight(unit, 10);
        Assertions.assertEquals(Message.UNIT_CAN_NOT_DO, message);
    }

    @Test
    public void removeOneOrderTest() {
        Unit unit = mock(Unit.class);
        doNothing().when(unit).setDestination(any());
        when(unit.getMilitaryType()).thenReturn(MilitaryType.KNIGHT);
        String string = UnitController.removeOneOrder(unit);

        Assertions.assertEquals(MilitaryType.KNIGHT.name(), string);
    }

    @Test
    public void freePlunderingTest() {
        Unit unit = mock(Unit.class);
        Ground ground = mock(Ground.class);
        when(unit.getGround()).thenReturn(ground);
        when(ground.getRoad()).thenReturn(null);
        when(ground.getRailWay()).thenReturn(null);
        when(ground.getPlunderingImprovementType()).thenReturn(null);
        Message message = UnitController.freePlundering(unit);
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void plundering() {
        Unit unit = mock(Unit.class);
        Ground ground = mock(Ground.class);
        when(unit.getGround()).thenReturn(ground);
        when(ground.getRoad()).thenReturn(null);
        when(ground.getRailWay()).thenReturn(null);
        when(ground.getImprovementType()).thenReturn(null);
        doNothing().when(ground).setPlunderingImprovementType(any());
        doNothing().when(ground).setImprovementType(null);
        Message message = UnitController.plundering(unit);
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void readyToRangedFight1() {
        RangedUnit unit = mock(RangedUnit.class);
        when(unit.getMilitaryType()).thenReturn(MilitaryType.CATAPULT);
        doNothing().when(unit).setReadyToRangedFight(true);
        Message message = UnitController.readyToRangedFight(unit);
        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void readyToRangedFight2() {
        Unit unit = mock(Unit.class);
        when(unit.getMilitaryType()).thenReturn(MilitaryType.SCOUT);
        Message message = UnitController.readyToRangedFight(unit);
        Assertions.assertEquals(Message.UNIT_CAN_NOT_DO, message);
    }

    @Test
    public void testRangedFight() {
        groundMockedStatic.when(() -> Ground.getGroundByNumber(10)).thenReturn(ground1);
        RangedUnit unit = mock(RangedUnit.class);
        Ground ground = mock(Ground.class);
        when(unit.getMilitaryType()).thenReturn(MilitaryType.ARTILLERY);
        when(unit.getGround()).thenReturn(ground);
        when(ground.getDistance(any())).thenReturn(0);
        when(unit.isReadyToRangedFight()).thenReturn(true);
        doNothing().when(unit).combat((Ground) any());
        Message message = UnitController.rangedFight(unit, 10);

        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void testRangedFight2() {
        groundMockedStatic.when(() -> Ground.getGroundByNumber(10)).thenReturn(ground1);
        RangedUnit unit = mock(RangedUnit.class);
        Ground ground = mock(Ground.class);
        when(unit.getMilitaryType()).thenReturn(MilitaryType.CATAPULT);
        when(unit.getGround()).thenReturn(ground);
        when(ground.getDistance(any())).thenReturn(0);
        when(unit.isReadyToRangedFight()).thenReturn(true);
        doNothing().when(unit).combat((Ground) any());
        Message message = UnitController.rangedFight(unit, 10);

        Assertions.assertEquals(Message.SUCCESS_WORK, message);
    }

    @Test
    public void testRangedFight3() {
        groundMockedStatic.when(() -> Ground.getGroundByNumber(10)).thenReturn(ground1);
        Unit unit = mock(Unit.class);
        Ground ground = mock(Ground.class);
        when(unit.getMilitaryType()).thenReturn(MilitaryType.ARTILLERY);
        when(unit.getGround()).thenReturn(ground);
        when(ground.getDistance(any())).thenReturn(0);
        Message message = UnitController.rangedFight(unit, 10);

        Assertions.assertEquals(Message.UNIT_CAN_NOT_DO, message);
    }

    @Test
    public void establishedTest1() {
        MilitaryUnit unit = mock(MilitaryUnit.class);
        City city = mock(City.class);
        Ground ground = mock(Ground.class);
        cityMockedStatic.when(() -> City.findCityByGround(any(), any())).thenReturn(city);
        when(unit.getGround()).thenReturn(ground);
        when(city.getGround()).thenReturn(ground);
        doNothing().when(unit).setCityGround(ground);
        when(ground.getMilitaryUnit()).thenReturn(null);
        Message message = UnitController.established(unit);
        Assertions.assertEquals(Message.UNIT_CAN_NOT_DO, message);
    }

    @Test
    public void establishedTest2() {
        UnMilitaryUnit unit = mock(UnMilitaryUnit.class);
        City city = mock(City.class);
        Ground ground = mock(Ground.class);
        cityMockedStatic.when(() -> City.findCityByGround(any(), any())).thenReturn(city);
        when(unit.getGround()).thenReturn(ground);
        when(city.getGround()).thenReturn(ground);
        doNothing().when(unit).setCityGround(ground);
        when(ground.getMilitaryUnit()).thenReturn(null);
        Message message = UnitController.established(unit);
        Assertions.assertEquals(Message.UNIT_CAN_NOT_DO, message);
    }

    @Test
    public void spawnUnitTest1() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        when(city.getBuildingUnit()).thenReturn(MilitaryType.WORKER);
        when(city.getPlayer()).thenReturn(player);
        Ground ground = mock(Ground.class);
        when(city.getRangeOfCity()).thenReturn(new ArrayList<>(List.of(ground)));
        doNothing().when(city).setBuildingUnit(null);
        UnitController.spawnUnit(city);
    }

    @Test
    public void spawnUnitTest2() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        when(city.getBuildingUnit()).thenReturn(MilitaryType.SETTLER);
        when(city.getPlayer()).thenReturn(player);
        Ground ground = mock(Ground.class);
        when(city.getRangeOfCity()).thenReturn(new ArrayList<>(List.of(ground)));
        doNothing().when(city).setBuildingUnit(null);
        UnitController.spawnUnit(city);
    }

    @Test
    public void spawnUnitTest3() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        when(city.getBuildingUnit()).thenReturn(MilitaryType.ARTILLERY);
        when(city.getPlayer()).thenReturn(player);
        Ground ground = mock(Ground.class);
        when(city.getRangeOfCity()).thenReturn(new ArrayList<>(List.of(ground)));
        doNothing().when(city).setBuildingUnit(null);
        UnitController.spawnUnit(city);
    }

    @Test
    public void spawnUnitTest4() {
        Player player = mock(Player.class);
        City city = mock(City.class);
        when(city.getBuildingUnit()).thenReturn(MilitaryType.SCOUT);
        when(city.getPlayer()).thenReturn(player);
        Ground ground = mock(Ground.class);
        when(city.getRangeOfCity()).thenReturn(new ArrayList<>(List.of(ground)));
        doNothing().when(city).setBuildingUnit(null);
        UnitController.spawnUnit(city);
    }
}
