import model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RailAndRoadAndRiverTest {

    @Test
    public void roadTest() {
        Road road = new Road();
        road.setTurn(10);
        road.decreaseTurn(2);
        Assertions.assertEquals(8, road.getTurn());
    }

    @Test
    public void railWayTest() {
        RailWay railWay = new RailWay();
        railWay.setTurn(10);
        railWay.decreaseTurn(2);
        Assertions.assertEquals(8, railWay.getTurn());
    }

    @Test
    public void remainedTest() {
        RemainedTurns remainedTurns = new RemainedTurns(20);
        remainedTurns.setTurns(10);

        Assertions.assertEquals(10, remainedTurns.getTurns());
    }

    @Test
    public void riverTest1() {
        Ground ground1 = mock(Ground.class);
        Ground ground2 = mock(Ground.class);
        River river = new River(ground1, ground2);
        Assertions.assertEquals(ground1, river.getFirstGround());
    }

    @Test
    public void riverTest2() {
        Ground ground1 = mock(Ground.class);
        Ground ground2 = mock(Ground.class);
        River river = new River(ground1, ground2);
        Assertions.assertEquals(ground2, river.getSecondGround());
    }

    @Test
    public void riverTest3() {
        for (River river: River.getAllRivers())
            River.getAllRivers().remove(river);

        Ground ground1 = mock(Ground.class);
        Ground ground2 = mock(Ground.class);
        River river = new River(ground1, ground2);
        Assertions.assertEquals(new ArrayList<>(List.of(river)), River.getAllRivers());
    }

    @Test
    public void riverTest4() {
        for (River river: River.getAllRivers())
            River.getAllRivers().remove(river);

        Ground ground1 = mock(Ground.class);
        Ground ground2 = mock(Ground.class);
        River river = new River(ground1, ground2);
        Assertions.assertFalse(River.couldWePutRiverBetweenTheseTwoGround(ground1, ground2));
    }

    @Test
    public void riverTest5() {
        for (River river: River.getAllRivers())
            River.getAllRivers().remove(river);

        Ground ground3 = mock(Ground.class);
        Ground ground4 = mock(Ground.class);
        when(ground3.getxLocation()).thenReturn(1);
        when(ground3.getyLocation()).thenReturn(1);
        when(ground4.getxLocation()).thenReturn(1);
        when(ground4.getyLocation()).thenReturn(1);

        Assertions.assertTrue(River.couldWePutRiverBetweenTheseTwoGround(ground3, ground4));
    }
}
