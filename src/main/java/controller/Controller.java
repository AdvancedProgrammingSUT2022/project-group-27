package controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Controller {
    public Matcher findMatcherFromString(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) return matcher;
        return null;
    }

    public Matcher getInput(String prefix, ArrayList<String> regexArray, String input){
        long powerOfArraySize = 1;
        for (int i = 0;i < regexArray.size(); i++) powerOfArraySize *= regexArray.size();

        long copyOfNumber;
        long tool;
        for (int i = 0; i < powerOfArraySize; i++){
            ArrayList<Integer> bits = new ArrayList<>();
            copyOfNumber = i;
            tool = regexArray.size();
            while(copyOfNumber > 0){
                bits.add((int)(copyOfNumber % tool));
                copyOfNumber /= tool;
            }

            while(bits.size() < tool) bits.add(0);
            boolean checkItsPermutation = false;
            for (int j = 0; j < tool; j++){
                for (int k = 0; k < tool; k++){
                    if (j != k && bits.get(j) == bits.get(k)) checkItsPermutation = true;
                }
            }
            if (!checkItsPermutation){
                String regex;
                regex="";
                for (int j = 0; j < prefix.length(); j++) regex += prefix.charAt(j);
                for (Integer bit : bits) {
                    regex += "\\s+";
                    regex += regexArray.get(bit);
                }

                if (findMatcherFromString(input,regex) != null) return findMatcherFromString(input,regex);
            }
        }
        return null;
    }
}
