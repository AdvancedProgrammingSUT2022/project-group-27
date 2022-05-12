import controller.Controller;
import controller.LoginController;
import model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import Enum.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class ImprovementTest {
    Improvement improvement = new Improvement(ImprovementType.CAMP, 10);

    @Test
    public void testImprovementGetterType1() {
        improvement.setImprovementType(ImprovementType.MINE);
        Assertions.assertEquals(ImprovementType.MINE, improvement.getImprovementType());
    }

    @Test
    public void testImprovementGetterType2() {
        improvement.setImprovementType(ImprovementType.AGRICULTURE);
        Assertions.assertEquals(ImprovementType.AGRICULTURE, improvement.getImprovementType());
    }

    @Test
    public void testImprovementGetterType3() {
        improvement.setImprovementType(ImprovementType.FARM);
        Assertions.assertEquals(ImprovementType.FARM, improvement.getImprovementType());
    }

    @Test
    public void testImprovementGetterType4() {
        improvement.setImprovementType(ImprovementType.PASTURE);
        Assertions.assertEquals(ImprovementType.PASTURE, improvement.getImprovementType());
    }

    @Test
    public void testImprovementGetterType5() {
        improvement.setImprovementType(ImprovementType.MINE_OF_STONE);
        Assertions.assertEquals(ImprovementType.MINE_OF_STONE, improvement.getImprovementType());
    }

    @Test
    public void testImprovementGetterType6() {
        improvement.setImprovementType(ImprovementType.FACTORY);
        Assertions.assertEquals(ImprovementType.FACTORY, improvement.getImprovementType());
    }

    @Test
    public void testImprovementGetterType7() {
        improvement.setImprovementType(ImprovementType.LUMBER_MILL);
        Assertions.assertEquals(ImprovementType.LUMBER_MILL, improvement.getImprovementType());
    }

    @Test
    public void testImprovementGetterType8() {
        improvement.setImprovementType(ImprovementType.TRADING_POST);
        Assertions.assertEquals(ImprovementType.TRADING_POST, improvement.getImprovementType());
    }

    @Test
    public void testImprovementGetterTurn() {
        Assertions.assertEquals(10, improvement.getTurnRemained());
    }

    @Test
    public void testImprovementDecrease1() {
        improvement.decreaseAmount(3);
        Assertions.assertEquals(7, improvement.getTurnRemained());
    }

    @Test
    public void testImprovementDecrease2() {
        improvement.setTurnRemained(10);
        improvement.decreaseAmount(20);
        Assertions.assertEquals(-10, improvement.getTurnRemained());
    }
}
