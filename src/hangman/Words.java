package hangman;

public class Words {

    public static String guessedWord;  // Represents the guessedWord, that is randomly selected from a dictionary
    public static String[] word;       // Represents the current state of the word for the player
    public static String letter = " "; // represents the entered letter by the player

    // Getter & Setter
    public static void setGuessedWord(String guessedWord) { Words.guessedWord = guessedWord.toUpperCase(); }
    public static void setWord(String[] word) { Words.word = word; }
    public static void setLetter(String letter) { Words.letter = letter.toUpperCase() ;}
    public static String getGuessedWord() { return guessedWord; }
    public static String[] getWord() { return word; }
    public static String getLetter() { return letter; }
}
