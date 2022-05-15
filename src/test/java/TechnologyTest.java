import controller.Controller;
import controller.LoginController;
import model.Technology;
import model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import Enum.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class TechnologyTest {
    Technology technology = new Technology(TechnologyType.MINING, TechnologyType.MINING.getTime());

    @Test
    public void technologyTest() {
        Assertions.assertEquals(TechnologyType.MINING, technology.getTechnologyType());
    }

    @Test
    public void technologyTimeTest() {
        technology.setTimeRemain(TechnologyType.MINING.getTime() + 20);
        technology.decreaseTimeRemain(10);
        Assertions.assertEquals(technology.getTechnologyType().getTime() + 10, technology.getTimeRemain());
    }
}
