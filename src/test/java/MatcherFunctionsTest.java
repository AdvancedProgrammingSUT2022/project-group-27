import controller.Controller;
import controller.LoginController;
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class MatcherFunctionsTest {

    @Test
    public void testFindMatcherEndSpace() {
        Controller controller = new Controller() {
            @Override
            public Matcher findMatcherFromString(String input, String regex) {
                return super.findMatcherFromString(input, regex);
            }
        };

        Assertions.assertNull(controller.findMatcherFromString("hello ", "^hello$"));
    }

    @Test
    public void testGetInputPermutation1() {
        Controller controller = new Controller() {
            @Override
            public Matcher getInput(String prefix, ArrayList<String> regexArray, String input) {
                return super.getInput(prefix, regexArray, input);
            }
        };

        Assertions.assertNotEquals(controller.getInput("hello", new ArrayList<>(List.of("hi", "salam")), "hello hi salam"), null);
    }

    @Test
    public void testGetInputPermutation2() {
        Controller controller = new Controller() {
            @Override
            public Matcher getInput(String prefix, ArrayList<String> regexArray, String input) {
                return super.getInput(prefix, regexArray, input);
            }
        };

        Assertions.assertNotEquals(controller.getInput("hello", new ArrayList<>(List.of("hi", "salam")), "hello salam hi"), null);
    }

    @Test
    public void testGetInputPermutation3() {
        Controller controller = new Controller() {
            @Override
            public Matcher getInput(String prefix, ArrayList<String> regexArray, String input) {
                return super.getInput(prefix, regexArray, input);
            }
        };

        Assertions.assertNull(controller.getInput("hello", new ArrayList<>(List.of("hi", "salam")), "hello hi hi"));
    }

    @Test
    public void testGetInputPermutation4() {
        Controller controller = new Controller() {
            @Override
            public Matcher getInput(String prefix, ArrayList<String> regexArray, String input) {
                return super.getInput(prefix, regexArray, input);
            }
        };

        Assertions.assertNull(controller.getInput("hello", new ArrayList<>(List.of("hi", "salam")), "helo hi salam"));
    }

    @Test
    public void testGetInputPermutation5() {
        Controller controller = new Controller() {
            @Override
            public Matcher getInput(String prefix, ArrayList<String> regexArray, String input) {
                return super.getInput(prefix, regexArray, input);
            }
        };

        Assertions.assertNull(controller.getInput("hello", new ArrayList<>(List.of("hi", "salam")), "hello salam hi chetori?"));
    }
}
