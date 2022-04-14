import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static WordHandler wordHandler = new WordHandler();

    public static void main(String[] args) {
        System.out.println("Enter wordle guess: ");
        String word = scanner.nextLine();
        for (int i = 0; i < 6; i++) {
            String pattern = "";
            boolean invalidGuess = true;
            while (invalidGuess) {
                try {
                    pattern = patternUI();
                    invalidGuess = false;
                } catch (NumberFormatException e) {
                    System.out.println("(0 for nothing, 1 for yellow, 2 for green)");
                }
            }
            wordHandler.setCurrentGuess(word, pattern);
            word = wordHandler.getNextLikelyAnswer();
            System.out.println("Next likely word is: " + word);
        }
    }

    public static String patternUI() {
        System.out.println("Enter correctness pattern\n(0 for nothing, 1 for yellow, 2 for green):\n" +
                "Example: 01021");
        String pattern = scanner.nextLine();
        for (char c : pattern.toCharArray()) {
            if (c < '0' || c > '2' || pattern.length() != 5) {
                throw new NumberFormatException();
            }
        }
        return pattern;
    }
}
