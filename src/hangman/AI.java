package hangman;

import hangman.List.StringList;
import hangman.List.StringListNode;
import java.util.Random;

import static hangman.Main.*;


public class AI {
    // Represents the letters sorted by their frequency of occurrence in German. (source Wikipedia)
    private final static String[] LetterFrequencyGerman = new String[]{"E", "N", "I", "S", "R", "A", "T", "D", "H", "U", "L", "C", "G", "M", "O", "B", "W", "F", "K", "Z", "P", "V", "J", "Y", "X", "Q"};


    // --------------------------------------------------------------------------------------------------------

    public static void SolverAI() {
        int numberOfGuesses = 0;

        while (!AIPlayer.getGameWon()) {

            // Changes state of gamRunning into false, if there is only one attempt left
            AIPlayer.stillRunning();

            // Prints the currents state of the word for player
            System.out.println();
            Helper.displayCurrentWord();

            // Counts number of current unknown letters from the AI
            int num_of_unknown_letters = 0;
            for (int i = 0; i < Words.getWord().length; i++) {
                if (Words.getWord()[i].equals("_")) {
                    num_of_unknown_letters++;
                }
            }
            // AI first guesses 3 letters before starting to guess words
            if (numberOfGuesses < 4) {
                // AI selects a letter by the frequency of the letters
                Words.setLetter(LetterByAI());
                try {
                    Thread.sleep(3000); // Verzögerung von 3000 Millisekunden (3 Sekunden)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\nDie KI hat noch " + AIPlayer.getCounter() + " Versuche und hat den folgenden Buchstaben / das folgende Wort gewählt: " + Words.getLetter());
                // Calls the CheckLetter method
                AIPlayer.CheckLetter();

                // Helper, if the word has been fully guessed
                if (String.join("", Words.getWord()).equals(Words.getGuessedWord())) {
                    System.out.println("Die KI hat das Wort '" + Words.getGuessedWord() + "' erraten!!!\nDie KI hatte " + (counter_trial - AIPlayer.getCounter()) + " Fehlversuche.");
                    AIPlayer.setNumberWon(AIPlayer.getNumberWon() + 1);
                    break;
                }
            } else {

                AIPlayer.setPossibleWords(updateWords(AIPlayer.getPossibleWords())); // Löscht nicht mögliche Wörter raus

                // AI guesses a word, if only 3 letters are unknown or if there are only 3 possible words left or
                // if it's the last try in this round
                if (num_of_unknown_letters <= 2 || AIPlayer.getPossibleWords().size() <= 3 || AIPlayer.getCounter() == 1) {

                    Words.setLetter(getRandomPossibleWord(AIPlayer.getPossibleWords()));  // Wählt zufällig ein neues Wort aus der Liste der aktuell noch möglichen Wörter
                    AIPlayer.setPossibleWords(AIPlayer.getPossibleWords().deleteElement(Words.getLetter())); // Löscht das zufällige Wort aus der List der möglichen Wörter bei der AI raus

                } else {
                    // AI selects a letter by the frequency of the letters
                    Words.setLetter(LetterByAI());

                }
                try {
                    Thread.sleep(2000); // Verzögerung von 2000 Millisekunden (2 Sekunde)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (AIPlayer.getCounter() == 1) {
                    System.out.println("Das ist der letzte Versuche der KI und sie hat folgendes  Wort geraten: ");
                } else {
                    System.out.println("\nDie KI hat noch " + AIPlayer.getCounter() + " Versuche und hat den folgenden Buchstaben / das folgende Wort gewählt: " + Words.getLetter());
                }

                if (Words.getLetter().length() == 1) {

                    // Calls the CheckLetter method
                    AIPlayer.CheckLetter();

                    // Helper, if the word has been fully guessed
                    if (String.join("", Words.getWord()).equals(Words.getGuessedWord())) {
                        System.out.println("Die KI hat das Wort '" + Words.getGuessedWord() + "' erraten!!!\nDie KI hatte " + (counter_trial - AIPlayer.getCounter()) + " Fehlversuche.");
                        AIPlayer.setNumberWon(AIPlayer.getNumberWon() + 1);
                        break;
                    }
                } else {
                    // Calls the CheckString method
                    AIPlayer.CheckString();
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (AIPlayer.getGameWon()) {
                    if ((counter_trial - AIPlayer.getCounter()) == 1) {
                        System.out.println("\nDie KI hat das Wort '" + Words.getGuessedWord() + "' erraten!!!" + "\nDie KI hatte nur " + (counter_trial - AIPlayer.getCounter()) + " Fehlversuch.");
                    } else {
                        System.out.println("\nDie KI hat das Wort '" + Words.getGuessedWord() + "' erraten!!!" + "\nDie KI hatte " + (counter_trial - AIPlayer.getCounter()) + " Fehlversuche.");
                    }
                    AIPlayer.setNumberWon(AIPlayer.getNumberWon() + 1);
                    break;
                }

                if (!AIPlayer.getGameRunning()) {
                    System.out.println("\nDie Ki hat es nicht geschafft, das Wort zu erraten. Das Wort wäre '" + Words.getGuessedWord() + "' gewesen.");
                    break;
                }

            }
            numberOfGuesses++;
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------*/

    /**
     * According to the frequency of letters the most likely and frequently letter ist returned
     * @return a letter
     */
    public static String LetterByAI() {

        // Neuen Buchstaben nach Häufigkeit heraussuchen
        if(!usedCommonSuffix()) {
            Words.setLetter(LetterFrequencyGerman[AIPlayer.getIndex()]);

            // Wenn aktueller Buchstabe schon geraten wurde, nehme den Nächsten aus dem vorgegebenen Array
            while(AIPlayer.AlreadyGuessed()){
                Words.setLetter(LetterFrequencyGerman[AIPlayer.getIndex() + 1]);
                AIPlayer.setIndex(AIPlayer.getIndex() + 1);
            }
            // Ändere den Status des Buchstabens im WordState Array
            AIPlayer.setLetterState(ChangesWordState(AIPlayer.getLetterState(), ReturnsIndex(Words.getLetter())));
        }

        // Buchstaben in den Array der benutzten Buchstaben hinzufügen (bezieht sich auch auf den letter aus der usedCommonSuffix-Methoden
        AIPlayer.addLetter();

        return Words.getLetter();
    }
    // Returns if there is typically common suffix in the word like "SCH", "UNG" etc.
    // true: yes, there is a possibility for a common suffix. So a new letter is already selected
    public static Boolean usedCommonSuffix(){

        // Häufige Vorkommen checken, beruht auf der Häufigkeit der Buchstabenvorkommen.
        // Bei -UNG wird also geschaut, ob N vorkommt, da N vor U und G gecheckt wird
        // Bei ReturnsWordState() werden als Indizes immer die entsprechenden Indizes der möglichen Buchstaben im Array LetterFrequencyGerman genommen
        // -----------------------------------------------------------------------
        // "-SCH-"
        int wordlength = Words.getWord().length;
        for(int i = 0; i < wordlength - 3; i++){  // Das S kann maximal an drittletzter Stelle stehen
            // Stand: S _ _
            if(Words.getWord()[i].equals("S") && Words.getWord()[i + 1].equals("")  && Words.getWord()[i + 2].equals("") && ReturnsWordState(AIPlayer.getLetterState(), ReturnsIndex("C"))){

                Words.setLetter("C");
                AIPlayer.setLetterState(ChangesWordState(AIPlayer.getLetterState(), ReturnsIndex("C")));
//                System.out.println("SCH-DEBUG");
                return true;
            }

            // Stand: S C _
            if(Words.getWord()[i].equals("S") && Words.getWord()[i + 1].equals("C") && Words.getWord()[i + 2].equals("_") && ReturnsWordState(AIPlayer.getLetterState(), ReturnsIndex("H"))){

                Words.setLetter("H");
                AIPlayer.setLetterState(ChangesWordState(AIPlayer.getLetterState(), ReturnsIndex("H")));
//                System.out.println("SCH-DEBUG");
                return true;
            }
        }

        // -----------------------------------------------------------------------
        // "CH"
        // Guessed "C", if "H" is contained in the word
        for(int i = 0; i < wordlength - 2; i++) {  // Das C kann maximal an vorletzter Stelle stehen (H wird vor C erraten)
            if (Words.getWord()[i].equals("_") && Words.getWord()[i + 1].equals("H")  && ReturnsWordState(AIPlayer.getLetterState(), ReturnsIndex("C"))) {

                Words.setLetter("C");
                AIPlayer.setLetterState(ChangesWordState(AIPlayer.getLetterState(), ReturnsIndex("C")));
//                System.out.println("CH-DEBUG");
                return true;
            }
        }

        // -----------------------------------------------------------------------
        // "PF"
        // Guessed "P", if "F" is contained in the word
        for(int i = 0; i < wordlength - 1; i++) {
            if (Words.getWord()[i].equals("_") && Words.getWord()[i + 1].equals("F")  && ReturnsWordState(AIPlayer.getLetterState(), ReturnsIndex("P"))) {

                Words.setLetter("P");
                AIPlayer.setLetterState(ChangesWordState(AIPlayer.getLetterState(), ReturnsIndex("P")));
//                System.out.println("PF-DEBUG");
                return true;
            }
        }

        // -----------------------------------------------------------------------
        // "-UNG" (only possible at the end of a word)
        if(wordlength >= 3) {
            // Stand: _ N _
            if (Words.getWord()[wordlength - 3].equals("") && Words.getWord()[wordlength - 2].equals("N") && Words.getWord()[wordlength - 1].equals("") && ReturnsWordState(AIPlayer.getLetterState(), ReturnsIndex("U"))) {

                Words.setLetter("U");
                AIPlayer.setLetterState(ChangesWordState(AIPlayer.getLetterState(), ReturnsIndex("U")));
//                System.out.println("UNG-DEBUG");
                return true;
            }

            // Stand: U N _
            if (Words.getWord()[wordlength - 3].equals("U") && Words.getWord()[wordlength - 2].equals("N") && Words.getWord()[wordlength - 1].equals("_") && ReturnsWordState(AIPlayer.getLetterState(), ReturnsIndex("G"))) {

                Words.setLetter("G");
                AIPlayer.setLetterState(ChangesWordState(AIPlayer.getLetterState(), ReturnsIndex("G")));
//                System.out.println("UNG-DEBUG");
                return true;
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------

    /**
     * Returns all currently possible words from dictionary in a list
     * @return a list with all currently possible words
     */
// ABGEÄNDERTE FUNKTION: NIMMT JETZT EINE LISTE ALS ARGUMENT UND LÖSCHT WÖRTER, WENN DIESE NICHT MEHR MÖGLICH SIND

    public static StringList updateWords(StringList wordList) {
        StringListNode current = wordList.front;

        // Über die gesamte Liste iterieren; Ein Wort aus wordList pro Schleife
        while (current != null) {
            // Aktuelles Wort aus der wordList als String
            String word = current.value;
            boolean wordIsValid = true;

            // Compare current word of the player character by character with word of dictionary
            for (int j = 0; j < Words.getWord().length; j++) {
                // Skip this iteration of loop, when current entry in Words.getWord() is an underscore
                if (Words.getWord()[j].equals("_")) {
                    continue;
                }
                // If characters are not the same, delete this word from wordList
                if (Words.getWord()[j].charAt(0) != word.charAt(j)) {
                    wordIsValid = false;
                    break;
                }
            }

            // Wenn das Wort nicht gültig ist, lösche es aus der wordList
            if (!wordIsValid) {
                wordList.deleteElement(word);
            }

            // Zum nächsten Knoten übergehen
            current = current.next;
        }

        return wordList;
    }

    // -----------------------------------------------------------------------------------------------------------------

    // NEUE Funktion für ganz Anfang: Vergleicht nur die Länge der Wörter
    public static StringList possibleWordsSize(StringList dictionary) {
        StringList possibleWords = new StringList();

        // über die gesamte Liste iterieren
        for (int i = 0; i < dictionary.size(); i++) {

            StringListNode current = dictionary.front;
            for (int j = 0; j < i; j++) {
                current = current.next;
            }
            // aktuelles Element i aus der Liste als String
            String wordDic = current.value;

            // If length of dictionary word has same letter length as word that is guessed, that word from dictionary is added to possibleWords
            if (wordDic.length() == Words.getWord().length) {
                possibleWords.addBack(wordDic.toUpperCase());
            }

        }
        return possibleWords;
    }

    // -----------------------------------------------------------------------------------------------------------------


    /**
     * Gets one random word of given list
     * @param possibleWords List of possible words that match with the current state of word
     * @return one random word of list
     */
    public static String getRandomPossibleWord(StringList possibleWords) {
        Random randObj = new Random();
        int numberOfWords = possibleWords.size();

        if (numberOfWords > 0) {
            int randomIndex = randObj.nextInt(numberOfWords);
            StringListNode current = possibleWords.front;

            // Durchlaufe die Liste, um das Element am zufälligen Index zu finden
            for (int i = 0; i < randomIndex; i++) {
                if (current == null) {
                    return ""; // Wenn current null wird, sollte hier eine sinnvolle Rückgabe erfolgen
                }
                current = current.next;
            }

            if (current != null) {
                return current.value; // Gib das gefundene Element als String zurück
            }
        }
        return ""; // Rückgabe eines leeren Strings, wenn numberOfWords <= 0
    }

    // -----------------------------------------------------------------------------------------------------------------

    // Changes the given Boolean Array at index to false
    public static Boolean[] ChangesWordState(Boolean[] list, int index) {
        list[index] = true;
        return list;
    }

    // -----------------------------------------------------------------------------------------------------------------

    // Returns the Boolean at given index
    // true = darf noch verwendet werden
    public static Boolean ReturnsWordState(Boolean[] list, int index){
        return list[index];
    }

    // -----------------------------------------------------------------------------------------------------------------

    // Returns index at Array LetterFrequencyGerman from given letter
    public static int ReturnsIndex(String letter){
        for(int i = 0; i < LetterFrequencyGerman.length; i++){
            if(LetterFrequencyGerman[i].equalsIgnoreCase(letter)){
                return i;
            }
        }
        return 100;
    }

}