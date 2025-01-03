package hangman;

import hangman.List.StringList;
import java.util.Scanner;

public class Main {

    // Name of the used dictionary as text file
    public static final String dictionary_A = "wordlist_A.txt";
    public static final String dictionary_F = "wordlist_F.txt";
    public static final String dictionary_E = "wordlist_E.txt";
    public static final String dictionary_KI = "wordlist_KI.txt";

    // Loads Dictionary from imported text file in a list of Strings
    public static StringList Dictionary_A = Helper.FileintoList(dictionary_A);
    public static StringList Dictionary_F = Helper.FileintoList(dictionary_F);
    public static StringList Dictionary_E = Helper.FileintoList(dictionary_E);
    public static StringList Dictionary_KI = Helper.FileintoList(dictionary_KI);

    // Initialize the number of guessing attempts for the player
    public static final int counter_trial = 10;

    // Create the Players
    public static Player SinglePlayer = new Player();
    public static Player AIPlayer = new Player();
    public static Player Player1 = new Player();
    public static Player Player2 = new Player();

    // Create the Words
    public static Words Words = new Words();

    // Welcome message
    public static String WelcomeWords = "\nHallo und herzlich willkommen zu Hangman. \nBitte wähle einen Spielmodus.\nDrücke 'S', um im Singlemodus oder 'P', um im Partnermodus zu spielen: ";

    /** -------------------------------------------------------------------------------------------------------------*/

    public static void main(String[] args) {

        System.out.print(WelcomeWords);

        Scanner read = new Scanner(System.in);

        SelectMode.Greeting(read.next().toUpperCase());

    }
}


