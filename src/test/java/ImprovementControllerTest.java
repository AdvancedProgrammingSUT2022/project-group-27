import controller.*;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import Enum.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class ImprovementControllerTest {
    ImprovementMenuController improvementMenuController = new ImprovementMenuController();
    static MockedStatic<Player> playerMockedStatic = Mockito.mockStatic(Player.class);

    @Test
    public void menuTestTrue() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        Worker worker = new Worker(ground, player);
        when(player.getUnits()).thenReturn(new ArrayList<>(List.of(worker)));
        when(ground.getNumber()).thenReturn(10);

        Assertions.assertTrue(improvementMenuController.haveWorker(player, 10));
    }

    @Test
    public void menuTestFalse() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        SettlerUnit settlerUnit = new SettlerUnit(ground, player);
        when(player.getUnits()).thenReturn(new ArrayList<>(List.of(settlerUnit)));
        when(ground.getNumber()).thenReturn(10);

        Assertions.assertFalse(improvementMenuController.haveWorker(player, 10));
    }

    @Test
    public void settingTestFarmForest() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        when(ground.getFeatureType()).thenReturn(FeatureType.FOREST);
        playerMockedStatic.when(Player::whichPlayerTurnIs).thenReturn(player);
        when(player.doWeHaveThisTechnology(any())).thenReturn(false);

        Assertions.assertFalse(ImprovementSettingController.canWeAddThisImprovement(ImprovementType.FARM, ground));
    }

    @Test
    public void settingTestFarmJungle() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        when(ground.getFeatureType()).thenReturn(FeatureType.JUNGLE);
        playerMockedStatic.when(Player::whichPlayerTurnIs).thenReturn(player);
        when(player.doWeHaveThisTechnology(any())).thenReturn(false);

        Assertions.assertFalse(ImprovementSettingController.canWeAddThisImprovement(ImprovementType.FARM, ground));
    }

    @Test
    public void settingTestFarmMarsh() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        when(ground.getFeatureType()).thenReturn(FeatureType.MARSH);
        playerMockedStatic.when(Player::whichPlayerTurnIs).thenReturn(player);
        when(player.doWeHaveThisTechnology(any())).thenReturn(false);

        Assertions.assertFalse(ImprovementSettingController.canWeAddThisImprovement(ImprovementType.FARM, ground));
    }

    @Test
    public void settingTestMineJungle() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        when(ground.getFeatureType()).thenReturn(FeatureType.JUNGLE);
        playerMockedStatic.when(Player::whichPlayerTurnIs).thenReturn(player);
        when(player.doWeHaveThisTechnology(any())).thenReturn(false);

        Assertions.assertFalse(ImprovementSettingController.canWeAddThisImprovement(ImprovementType.MINE, ground));
    }

    @Test
    public void settingTestMineMarsh() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        when(ground.getFeatureType()).thenReturn(FeatureType.MARSH);
        playerMockedStatic.when(Player::whichPlayerTurnIs).thenReturn(player);
        when(player.doWeHaveThisTechnology(any())).thenReturn(false);

        Assertions.assertFalse(ImprovementSettingController.canWeAddThisImprovement(ImprovementType.MINE, ground));
    }

    @Test
    public void settingTestTrue1() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        when(ground.getFeatureType()).thenReturn(FeatureType.FOREST);
        playerMockedStatic.when(Player::whichPlayerTurnIs).thenReturn(player);
        when(player.doWeHaveThisTechnology(any())).thenReturn(true);
        when(ground.getGroundType()).thenReturn(GroundType.PLAIN);

        Assertions.assertTrue(ImprovementSettingController.canWeAddThisImprovement(ImprovementType.CAMP, ground));
    }

    @Test
    public void settingTestTrue2() {
        Player player = mock(Player.class);
        Ground ground = mock(Ground.class);
        when(ground.getFeatureType()).thenReturn(FeatureType.FOREST);
        playerMockedStatic.when(Player::whichPlayerTurnIs).thenReturn(player);
        when(player.doWeHaveThisTechnology(any())).thenReturn(true);
        when(ground.getGroundType()).thenReturn(GroundType.DESERT);

        Assertions.assertTrue(ImprovementSettingController.canWeAddThisImprovement(ImprovementType.CAMP, ground));
    }

    @AfterAll
    public static void clean() {
        playerMockedStatic.close();
    }
}
