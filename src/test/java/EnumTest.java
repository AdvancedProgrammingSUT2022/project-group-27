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
public class EnumTest {
    BonusResource bonusResource = BonusResource.BANANA;
    FeatureType featureType = FeatureType.FOREST;
    GroundType groundType = GroundType.PLAIN;
    ImprovementType improvementType = ImprovementType.CAMP;
    LuxuryResource luxuryResource = LuxuryResource.COLOR;
    MilitaryType militaryType = MilitaryType.TANK;
    StrategicResource strategicResource = StrategicResource.HORSE;

    @Test
    public void bonusTest1() {
        Assertions.assertEquals(0, bonusResource.getGold());
    }

    @Test
    public void bonusTest2() {
        Assertions.assertEquals(0, bonusResource.getProduction());
    }

    @Test
    public void bonusTest3() {
        Assertions.assertEquals(1, bonusResource.getFood());
    }

    @Test
    public void bonusTest4() {
        Assertions.assertEquals(ImprovementType.AGRICULTURE, bonusResource.getImprovementType());
    }

    @Test
    public void featureTypeTest1() {
        Assertions.assertEquals(1, featureType.getFood());
    }

    @Test
    public void featureTypeTest2() {
        Assertions.assertEquals(1, featureType.getProduction());
    }

    @Test
    public void featureTypeTest3() {
        Assertions.assertEquals(0, featureType.getGold());
    }

    @Test
    public void featureTypeTest4() {
        Assertions.assertEquals(25, featureType.getCombatCoefficient());
    }

    @Test
    public void featureTypeTest5() {
        Assertions.assertEquals(2, featureType.getMovementCost());
    }

    @Test
    public void featureTypeTest6() {
        Assertions.assertFalse(featureType.isBlock());
    }

    @Test
    public void featureTypeTest7() {
        Assertions.assertEquals(4, featureType.getTurn());
    }

    @Test
    public void groundTypeTest1() {
        Assertions.assertEquals(1, groundType.getFood());
    }

    @Test
    public void groundTypeTest2() {
        Assertions.assertEquals(1, groundType.getProduction());
    }

    @Test
    public void groundTypeTest3() {
        Assertions.assertEquals(0, groundType.getGold());
    }

    @Test
    public void groundTypeTest4() {
        Assertions.assertEquals(-33, groundType.getCombatCoefficient());
    }

    @Test
    public void groundTypeTest5() {
        Assertions.assertEquals(1, groundType.getMovementCost());
    }

    @Test
    public void groundTypeTest6() {
        Assertions.assertFalse(groundType.isBlock());
    }

    @Test
    public void groundTypeTest7() {
        Assertions.assertEquals("\u001B[36m", groundType.getColor());
    }

    @Test
    public void improvementTypeTest1() {
        Assertions.assertEquals(0, improvementType.getFood());
    }

    @Test
    public void improvementTypeTest2() {
        Assertions.assertEquals(0, improvementType.getProduction());
    }

    @Test
    public void improvementTypeTest3() {
        Assertions.assertEquals(0, improvementType.getGold());
    }

    @Test
    public void improvementTypeTest4() {
        improvementType.setTurn(10);
        Assertions.assertEquals(10, improvementType.getTurn());
    }

    @Test
    public void luxuryResourceTest1() {
        Assertions.assertEquals(2, luxuryResource.getGold());
    }

    @Test
    public void luxuryResourceTest2() {
        Assertions.assertEquals(0, luxuryResource.getProduction());
    }

    @Test
    public void luxuryResourceTest3() {
        Assertions.assertEquals(0, luxuryResource.getFood());
    }

    @Test
    public void luxuryResourceTest4() {
        Assertions.assertEquals(ImprovementType.AGRICULTURE, luxuryResource.getImprovementType());
    }

    @Test
    public void MilitaryTypeTest1() {
        Assertions.assertEquals(0, militaryType.getRangedCombatStrength());
    }

    @Test
    public void MilitaryTypeTest2() {
        Assertions.assertEquals(4, militaryType.getMovement());
    }

    @Test
    public void strategicResourceTest1() {
        Assertions.assertEquals(0, strategicResource.getGold());
    }

    @Test
    public void strategicResourceTest2() {
        Assertions.assertEquals(1, strategicResource.getProduction());
    }

    @Test
    public void strategicResourceTest3() {
        Assertions.assertEquals(0, strategicResource.getFood());
    }

    @Test
    public void strategicResourceTest4() {
        Assertions.assertEquals(ImprovementType.PASTURE, strategicResource.getImprovementType());
    }

    @Test
    public void strategicResourceTest5() {
        Assertions.assertEquals(TechnologyType.ANIMAL_HUSBANDRY, strategicResource.getTechnology());
    }

    @Test
    public void strategicResourceTest6() {
        Assertions.assertEquals("Horse", strategicResource.toString());
    }

    @Test
    public void strategicResourceTest7() {
        Assertions.assertEquals("Iron", StrategicResource.IRON.toString());
    }

    @Test
    public void strategicResourceTest8() {
        Assertions.assertEquals("Coal", StrategicResource.COAL.toString());
    }

    @Test
    public void technologyTypeTest() {
        Assertions.assertEquals(new ArrayList<>(), TechnologyType.AGRICULTURE.getPrerequisites());
    }

    @Test
    public void unitStatusTest() {
        Assertions.assertEquals("Sleeping...", UnitStatus.SLEEP.toString());
    }
}
