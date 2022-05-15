import controller.Game;
import controller.InitializeMap;
import model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GlobalTest {
    GlobalVariables globalVariables = new GlobalVariables();

    @Test
    public void testDistance1() {
        Assertions.assertEquals(10, globalVariables.distanceOfTwoPoints(2, 2, 7, 7));
    }

    @Test
    public void testDistance2() {
        Assertions.assertEquals(12, globalVariables.distanceOfTwoPoints(2, 2, 7, -5));
    }

    @Test
    public void testDistance3() {
        Assertions.assertEquals(5, globalVariables.distanceOfTwoPoints(2, 2, 2, 7));
    }

    @Test
    public void testEqual1() {
        Assertions.assertEquals(1, globalVariables.isEqual(10.9, 10.88));
    }

    @Test
    public void testEqual2() {
        Assertions.assertEquals(0, globalVariables.isEqual(10.9, 11.01));
    }

    @Test
    public void testEqual3() {
        Assertions.assertEquals(1, globalVariables.isEqual(10.9, 10.97));
    }

    @Test
    public void testNotification() {
        Player player = mock(Player.class);
        ArrayList<Notification> list = new ArrayList<>();

        when(player.getNotificationHistory()).thenReturn(list);

        Notification notification = new Notification("hello", 10, player);
        Assertions.assertEquals("title: hello in turn: 10", notification.toString());
    }

    @Test
    public void testPairNotEqual() {
        Pair pair1 = new Pair(10, 20);
        Pair pair2 = new Pair(20, 10);

        Assertions.assertNotEquals(pair1, pair2);
    }

    @Test
    public void testPairEqual() {
        Pair pair1 = new Pair(10, 20);
        Pair pair2 = new Pair(10, 20);

        Assertions.assertEquals(pair1, pair2);
    }

    @Test
    public void testPairGetter1() {
        Pair pair1 = new Pair(10, 20);

        Assertions.assertEquals(10, pair1.getFirstInt());
    }

    @Test
    public void testPairGetter2() {
        Pair pair1 = new Pair(10, 20);

        Assertions.assertEquals(20, pair1.getSecondInt());
    }

    @Test
    public void testPairHash() {
        Pair pair1 = new Pair(10, 20);
        int number = pair1.hashCode();

        Assertions.assertEquals(Objects.hash(pair1.getFirstInt(), pair1.getSecondInt()), number);
    }

    @Test
    public void testInitializeMap() {
        new User(".", ".", ".");
        new User(",", ",", ",");
        Game.getInstance();
        InitializeMap initializeMap = new InitializeMap(User.getListOfUsers());
        initializeMap.run();
    }
}
