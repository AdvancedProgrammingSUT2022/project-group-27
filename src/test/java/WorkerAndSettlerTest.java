import model.Ground;
import model.Player;
import model.SettlerUnit;
import model.Worker;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkerAndSettlerTest {

    @Test
    public void workerTest() {
        Ground ground = mock(Ground.class);
        Player player = mock(Player.class);
        Worker worker = new Worker(ground, player);
        worker.setWorking(true);

        Assertions.assertTrue(worker.getIsWorking());
    }

    @Test
    public void settlerTest() {
        Ground ground = mock(Ground.class);
        Player player = mock(Player.class);
        SettlerUnit settlerUnit = new SettlerUnit(ground, player);
        doNothing().when(player).addCityToThisGround(ground);

        settlerUnit.buildCity("hello");
    }
}
