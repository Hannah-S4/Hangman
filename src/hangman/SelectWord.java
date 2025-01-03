package hangman;

import hangman.List.StringList;
import hangman.List.StringListNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static hangman.Main.*;

public class SelectWord {

    // loads dictionary
    static public FileReader fr_A;
    static {
        try {
            fr_A = new FileReader(dictionary_A);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static public FileReader fr_F;
    static {
        try {
            fr_F = new FileReader(dictionary_F);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static public FileReader fr_E;
    static {
        try {
            fr_E = new FileReader(dictionary_E);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Calculate the amount of words from the dictionary
    static public final int amount_of_words_E;
    static {
        try {
            amount_of_words_E = countWords(dictionary_E);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static public final int amount_of_words_A;
    static {
        try {
            amount_of_words_A = countWords(dictionary_A);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static public final int amount_of_words_F;
    static {
        try {
            amount_of_words_F = countWords(dictionary_F);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** ---------------------------------------------------------------------------------------------------------------

     /**
     * Returns a random word from the loaded dictionary as a String.
     * @return String
     */
    public static String selectWord(int amount_of_words, StringList Dictionary) {

        Random randObj = new Random();
        int wordNumber = randObj.nextInt(amount_of_words);    // creates numbers from 0 to amount_of_words - 1

        StringListNode current = Dictionary.front;
        for(int i = 0; i < wordNumber; i++){
            current = current.next;
        }
        String wordWithoutUmlaut = current.value;
        return wordWithoutUmlaut;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts Number of Words of the given dictionary via text-file.
     * @param path: path from the file to be imported
     * @return int: number of words from the dictionary
     * @throws IOException when there is an error with the file to be imported
     */
    public static int countWords(String path) throws IOException {
        Path p = Paths.get(path);
        if (Files.exists(p) && Files.isReadable(p)) {
            return Files.readAllLines(p).size();
        }
        return -1;
    }

    /** ---------------------------------------------------------------------------------------------------------------

    /**
     * Initialize the array word with the amount of underscores from the guessed word
     * @return String[]
     */
    public static String[] InitializeWord(String guessedWord) {
        String[] word = new String[guessedWord.length()];
        for (int i = 0; i < word.length; i++) {
            word[i] = "_";
        }
        return word;
    }

}