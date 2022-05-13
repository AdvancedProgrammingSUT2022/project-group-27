import controller.Controller;
import controller.LoginController;
import model.Ground;
import model.Player;
import model.User;
import model.Worker;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import Enum.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class WorkerTest {

    @Test
    public void workerTest() {
        Ground ground = mock(Ground.class);
        Player player = mock(Player.class);
        Worker worker = new Worker(ground, player);
        worker.setWorking(true);

        Assertions.assertTrue(worker.getIsWorking());
    }
}
