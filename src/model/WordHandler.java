import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordHandler {

    List<String> listOfAllWords;
    Pair<String, String> currentGuess;
    List<String> alreadyUsedWords;
    Map<Character, Integer> greenCharsAndIndexes;
    List<Character> yellowChars;
    List<Character> greyChars;

    public WordHandler() {
        WordleFileReader reader = new WordleFileReader();
        listOfAllWords = reader.getList();
        alreadyUsedWords = new ArrayList<>();
        greenCharsAndIndexes = new HashMap<>();
        yellowChars = new ArrayList<>();
        greyChars = new ArrayList<>();
    }

    public void setCurrentGuess(String guess, String pattern) {
        currentGuess = new Pair<>(guess, pattern);
    }

    private void appendLists() {
        List<Character> tempYellow = getYellowChars();
        for (Character c : tempYellow) {
            yellowChars.add(c);
        }
        Map<Character, Integer> tempGreen = getGreenCharsAndIndexes();
        for (Map.Entry<Character, Integer> instance : tempGreen.entrySet()) {
            greenCharsAndIndexes.put(instance.getKey(), instance.getValue());
        }
        List<Character> tempGrey = getGreyChars();
        for (Character c : tempGrey) {
            greyChars.add(c);
        }
    }
    public String getNextLikelyAnswer() {
        appendLists();
        List<String> potentialAnswers = new ArrayList<>();
        for (String word : listOfAllWords) {
            boolean validWord = true;
            for (int i = 0; i < word.length(); i++) {
                for (Map.Entry<Character, Integer> instance : greenCharsAndIndexes.entrySet()) {
                    if (instance.getValue() == i && instance.getKey() != word.charAt(i)) {
                        validWord = false;
                        break;
                    }
                }
                for (char c : yellowChars) {
                    if (!word.contains(c + "")) {
                        validWord = false;
                        break;
                    }
                }
                for (char c : greyChars) {
                    if (word.contains(c + "")) {
                        validWord = false;
                        break;
                    }
                }
                if (!validWord) {
                    break;
                }
            }
            if (validWord) {
                potentialAnswers.add(word);
                break;
            }
        }
        String answer = (potentialAnswers.size() > 0) ? potentialAnswers.get(0) : null;
        while (alreadyUsedWords.contains(answer)) {
            potentialAnswers.remove(answer);
            answer = (potentialAnswers.size() > 0) ? potentialAnswers.get(0) : null;
        }
        alreadyUsedWords.add(answer);
        return answer;
    }

    private List<Character> getYellowChars() {
        List<Character> list = new ArrayList<>();
        int index = 0;
        for (char c : currentGuess.getValue().toCharArray()) {
            if (c == '1') {
                list.add(currentGuess.getKey().charAt(index));
            }
            index++;
        }
        return list;
    }

    private List<Character> getGreyChars() {
        List<Character> list = new ArrayList<>();
        int index = 0;
        for (char c : currentGuess.getValue().toCharArray()) {
            if (c == '0' && !yellowChars.contains(currentGuess.getKey().charAt(index)) &&
                    !greenCharsAndIndexes.containsKey(currentGuess.getKey().charAt(index))) {
                list.add(currentGuess.getKey().charAt(index));
            }
            index++;
        }
        return list;
    }

    private Map<Character, Integer> getGreenCharsAndIndexes() {
        Map<Character, Integer> greenGuessIndexes = new HashMap<>();
        int index = 0;
        for (char c : currentGuess.getValue().toCharArray()) {
            if (c == '2') {
                greenGuessIndexes.put(currentGuess.getKey().charAt(index), index);
            }
            index++;
        }
        return greenGuessIndexes;
    }
}
