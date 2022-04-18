package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Controller {
    public static Matcher findMatcherFromString(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
