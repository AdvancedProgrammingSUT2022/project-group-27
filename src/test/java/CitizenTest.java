import controller.Controller;
import controller.LoginController;
import model.Citizen;
import model.City;
import model.Ground;
import model.User;
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
public class CitizenTest {
    ArrayList<Citizen> list = new ArrayList<>();

    @Test
    public void testAddingCitizenDifferentId() {
        City city = mock(City.class);
        when(city.getListOfCitizens()).thenReturn(list);
        Citizen.addCitizen(city);
        Citizen.addCitizen(city);

        Assertions.assertNotEquals(list.get(0).getId(), list.get(1).getId());
    }

    @Test
    public void testSettingGroundFalse() {
        Ground ground = mock(Ground.class);
        when(ground.isWorkedOn()).thenReturn(true);
        doNothing().when(ground).setWorkedOn();

        City city = mock(City.class);
        when(city.getListOfCitizens()).thenReturn(list);
        Citizen.addCitizen(city);

        Assertions.assertFalse(list.get(0).setGround(ground));
    }

    @Test
    public void testSettingGroundTrue() {
        Ground ground = mock(Ground.class);
        when(ground.isWorkedOn()).thenReturn(false);
        doNothing().when(ground).setWorkedOn();

        City city = mock(City.class);
        when(city.getListOfCitizens()).thenReturn(list);
        Citizen.addCitizen(city);

        Assertions.assertTrue(list.get(0).setGround(ground));
        Assertions.assertTrue(list.get(0).isHaveWork());
    }

    @Test
    public void testRemoveGround() {
        City city = mock(City.class);
        when(city.getListOfCitizens()).thenReturn(list);
        Citizen.addCitizen(city);

        list.get(0).removeFromGround();
        Assertions.assertNull(list.get(0).getGround());
    }
}
