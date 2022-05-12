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
public class MeleeUnitTest {

    @Test
    public void combatGroundTest() {
        Ground ground = mock(Ground.class);
        Player player = mock(Player.class);

        MeleeUnit meleeUnit = new MeleeUnit(ground, player, MilitaryType.WARRIOR);

    }
}
