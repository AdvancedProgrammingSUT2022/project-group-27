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
public class GlobalVariableTest {
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
}
