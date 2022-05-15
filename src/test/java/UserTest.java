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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    User user = new User("a", "a", "aa");
    User user2 = new User("c", "c", "cc");

    @Test
    public void testFindUserFalse() {
        Assertions.assertNull(User.findUser("arefe"));
    }

    @Test
    public void testFindUserTrue() {
        Assertions.assertNotEquals(User.findUser("a"), null);
    }

    @Test
    public void testFindUserFalsePassword1() {
        Assertions.assertNull(User.findUser("a", "b"));
    }

    @Test
    public void testFindUserFalsePassword2() {
        Assertions.assertNull(User.findUser("a", "aa"));
    }

    @Test
    public void testFindUserFalseUsername1() {
        Assertions.assertNull(User.findUser("b", "a"));
    }

    @Test
    public void testFindUserFalseUsername2() {
        Assertions.assertNull(User.findUser("aa", "a"));
    }

    @Test
    public void testFindUserFalse2Argument() {
        Assertions.assertNull(User.findUser("b", "b"));
    }

    @Test
    public void testFindUser2ArgumentTrue() {
        Assertions.assertNotEquals(User.findUser("a", "a"), null);
    }

    @Test
    public void checkIsPasswordCorrectFalse() {
        Assertions.assertFalse(user.isPasswordCorrect("b"));
    }

    @Test
    public void checkIsPasswordCorrectTrue() {
        Assertions.assertTrue(user.isPasswordCorrect("a"));
    }

    @Test
    public void testChangeNicknameFalse() {
        Assertions.assertFalse(user.changeNickname("cc"));
    }

    @Test
    public void testChangeNicknameFalse2() {
        Assertions.assertFalse(user.changeNickname("aa"));
    }

    @Test
    public void testChangeNicknameTrue() {
        Assertions.assertTrue(user.changeNickname("bb"));
    }

    @Test
    public void testChangePasswordFalse() {
        Assertions.assertFalse(user2.changePassword("c"));
    }

    @Test
    public void testChangePasswordTrue() {
        Assertions.assertTrue(user2.changePassword("ccc"));
    }

    @Test
    public void testArraylist() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("a", "a", "a"));

        User.setListOfUsers(list);
        Assertions.assertEquals(list, User.getListOfUsers());
    }

    @Test
    public void getUserName() {
        User user = new User("arefe", "123", "pargol");

        Assertions.assertEquals("arefe", user.getUsername());
    }

    @Test
    public void increaseScoreTest() {
        User user = new User("..", ",", ".");
        user.increaseScore(10);
        Assertions.assertEquals(10, user.getScore());
    }
}
