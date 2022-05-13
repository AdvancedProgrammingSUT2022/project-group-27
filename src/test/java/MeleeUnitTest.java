import controller.Controller;
import controller.LoginController;
import model.*;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
    Ground ground = mock(Ground.class);
    Ground ground1 = mock(Ground.class);
    UnMilitaryUnit unMilitaryUnit = mock(UnMilitaryUnit.class);
    Player player = mock(Player.class);
    MeleeUnit meleeUnit = new MeleeUnit(ground, player, MilitaryType.WARRIOR);

    @Test
    public void combatGroundTestGroundEqual() {
        when(ground.getNumber()).thenReturn(10);

        meleeUnit.combat(ground);
    }

    @Test
    public void combatGroundTestNoMilitaryAndUnMilitary() {
        when(ground.getNumber()).thenReturn(10);
        when(ground1.getNumber()).thenReturn(20);
        doNothing().when(unMilitaryUnit).changeOwner(any());
        when(ground1.getMilitaryUnit()).thenReturn(null);
        when(ground1.getUnMilitaryUnit()).thenReturn(null);

        meleeUnit.combat(ground1);
    }

    @Test
    public void combatGroundTestJustNoMilitary() {
        when(ground.getNumber()).thenReturn(10);
        when(ground1.getNumber()).thenReturn(20);
        doNothing().when(unMilitaryUnit).changeOwner(any());
        when(ground1.getMilitaryUnit()).thenReturn(null);
        when(ground1.getUnMilitaryUnit()).thenReturn(unMilitaryUnit);

        meleeUnit.combat(ground1);
        verify(unMilitaryUnit).changeOwner(any());
    }
}
