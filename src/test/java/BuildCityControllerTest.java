import controller.*;
import model.City;
import model.Unit;
import model.User;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import Enum.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class BuildCityControllerTest {
    BuildCityController buildCityController = new BuildCityController();

    @Test
    public void testChangeProduction() throws NoSuchMethodException {
        //TODO mock the private method checkValidationOfUnitName.
    }
}
