package hangman;

import hangman.List.StringList;

import java.util.Scanner;

import static hangman.Main.*;

public class Player {
    private int playerID;             // For Partnermode to identify teh players
    private int counter;              // counts number of guessing trials of a player
    private Boolean gameRunning;      // says, if the game is still Running = true/
    private Boolean gameWon;          // says, if the player has won the game, so he guesses the word correctly = true
    private String[] UsedLetters;     // Array of used letters
    private int NumberUsedLetters;    // Number of guessed letters by a player
    public int NumberWon = 0;         // counts number of won games
    private int index;                // For AI to make sure the right index for UsedLetters
    private StringList possibleWords; // For AI: List of possible words from loaded dictionary
    private Boolean[] letterState;    // true = darf noch verwendet werden

    // Getter & Setter
    public int getPlayerID() {
        return this.playerID;
    }

    public int getCounter() {
        return this.counter;
    }

    public Boolean getGameRunning() {
        return this.gameRunning;
    }

    public Boolean getGameWon() {
        return this.gameWon;
    }

    public int getNumberUsedLetters() {
        return NumberUsedLetters;
    }

    public String[] getUsedLetters() {
        return UsedLetters;
    }

    public int getIndex() {
        return index;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setGameRunning(Boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public void setGameWon(Boolean gameWon) {
        this.gameWon = gameWon;
    }

    public void setUsedLetters(String[] usedLetters) {
        UsedLetters = usedLetters;
    }

    public void setNumberUsedLetters(int numberUsedLetters) {
        NumberUsedLetters = numberUsedLetters;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPossibleWords(StringList possibleWords) {
        this.possibleWords = possibleWords;
    }

    public StringList getPossibleWords() {
        return possibleWords;
    }

    public void setLetterState(Boolean[] letterState) {
        this.letterState = letterState;
    }

    public Boolean[] getLetterState() {
        return letterState;
    }

    public int getNumberWon() {
        return NumberWon;
    }

    public void setNumberWon(int numberWon) {
        NumberWon = numberWon;
    }

    String AllowedLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    //-----------------------------------------------------------------------------------------------------------
    // Methods
    //-----------------------------------------------------------------------------------------------------------

    /*
     * Checks if an entered letter by a player is contained in the guessed word and adapts all necessary variables
     */
    public void CheckLetter() {
        // returns first occurrence of the entered letter
        int index = Words.getGuessedWord().indexOf(Words.getLetter());
        if (index != -1) {
            System.out.println("Super, du hast einen Buchstaben erraten.");
            // Update all occurrences of the letter in the word
            for (int i = 0; i < Words.getGuessedWord().length(); i++) {
                if (Words.getGuessedWord().charAt(i) == Words.getLetter().charAt(0)) {
                    Words.word[i] = Words.getLetter();
                }
            }
        } else {
            setCounter(getCounter() - 1);
            System.out.println("Der eingegebene Buchstabe '" + Words.getLetter() + "' ist nicht im Wort enthalten.");
        }
    }

    /**
     * --------------------------------------------------------------------------------------------------------
     * <p>
     * /**
     * Checks if the entered word equals the guessed word and adapts all necessary variables
     */
    public void CheckString() {

        if (Words.getLetter().length() != Words.getGuessedWord().length()) {
            System.out.println("Das eingegebene Wort hat nicht die richtige Anzahl an Buchstaben.");
        } else if (String.join("", Words.getLetter()).equals(Words.getGuessedWord())) {
            setGameWon(true);
        } else {
            setCounter(getCounter() - 1);
            System.out.println("Das eingegebene Wort '" + Words.getLetter() + "' ist nicht korrekt.");
        }
    }

    /**
     * --------------------------------------------------------------------------------------------------------
     * <p>
     * /**
     * Calls all necessary methods for this mode and returns true, if player is still allowed to go on
     *
     * @param read that input can be read
     * @return boolean if player is allowed to go on with guessing
     */
    public boolean playerTurn(Scanner read) {

        stillRunning();

        // Prints the currents state of the word for player
        Helper.displayCurrentWord();
        System.out.println();

        //aktueller Stand der Versuche
        int trials = getCounter();

        // Prints user input for player
        if (getCounter() == 1) {
            System.out.print("Spieler " + getPlayerID() + ", du hast noch " + getCounter() + " Versuch. Dein letzter, auf geht's.\nGib einen Buchstaben oder ein Wort ein: ");
        } else {
            System.out.print("Spieler " + getPlayerID() + ", du hast noch " + getCounter() + " Versuche. \nGib einen Buchstaben oder ein Wort ein: ");
        }
        Words.setLetter(read.next());

        while (ValidInput()) {
            System.out.println("Bitte gib nur Buchstaben und keine Zahl oder ein Sonderzeichen ein.");
            Words.setLetter(read.next());
        }

        // Checks if you already guessed the letter. If yes, you have to enter a new one.
        while (AlreadyGuessed()) {
            System.out.print("Du hast den Buchstaben '" + Words.getLetter() + "' schon versucht. Bitte gib einen anderen Buchstaben odr ein Wort ein: ");
            Words.setLetter(read.next());
        }

        while (Words.getLetter().length() > 1 && Words.getLetter().length() != Words.getGuessedWord().length()) {
            System.out.print("Das eingegebene Wort hat nicht die richtige Länge. \n" +
                    "Bitte gib ein Wort der richtigen Länge oder einen Buchstaben ein: ");
            Words.setLetter(read.next());
        }

        if (Words.getLetter().length() == 1) {

            // Checks if you already guessed the letter. If yes, you have to enter a new one.
            while (AlreadyGuessed()) {
                System.out.print("Du hast den Buchstaben '" + Words.getLetter() + "' schon eingegeben. Bitte gib einen neuen ein oder ein Wort: ");
                Words.setLetter(read.next());
            }

            // Calls the CheckLetter method with attributes from player 1 or player 2 depending on given argument playerNumber
            CheckLetter();

            // Checks if the word has been fully guessed
            if (String.join("", Words.getWord()).equals(Words.getGuessedWord())) {
                if ((counter_trial - getCounter()) == 1) {
                    System.out.println("\nHerzlichen Glückwunsch Spieler " + getPlayerID() + ", du hast das Wort '" + Words.getGuessedWord() +
                            "' erraten!!! \nDu hattest nur 1 Fehlversuch.");
                } else {
                    System.out.println("\nHerzlichen Glückwunsch Spieler " + getPlayerID() + ", du hast das Wort '" + Words.getGuessedWord() +
                            "' erraten!!! \nDu hattest " + (counter_trial - getCounter()) + " Fehlversuche.");
                }
                setNumberWon(getNumberWon() + 1);
                return false;
            }

            // Adds letter to Array with already Used letters for both Players
            Player1.addLetter();
            Player2.addLetter();

        } else {
            // Calls the CheckString method with attributes from player 1 or player 2 depending on given argument playerNumber
            CheckString();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (gameWon) {
            if ((counter_trial - getCounter()) == 1) {
                System.out.println("\nHerzlichen Glückwunsch Spieler " + getPlayerID() + ", du hast das Wort '" + Words.getGuessedWord() +
                        "' erraten!!! \nDu hattest nur 1 Fehlversuch.");
            } else {
                System.out.println("\nHerzlichen Glückwunsch Spieler " + getPlayerID() + ", du hast das Wort '" + Words.getGuessedWord() +
                        "' erraten!!! \nDu hattest " + (counter_trial - getCounter()) + " Fehlversuche.");
            }
            setNumberWon(getNumberWon() + 1);
            return false;
        }

        if (!gameRunning) {
            System.out.println("Spieler " + getPlayerID() + ", du hast die maximale Anzahl an Versuchen erreicht.!!!\n");
            return false;
        }

        // if the counter was decreased, a motivational statement is printetd aditionally to the hangman graphic
        if (trials != getCounter()) {
            Helper.Motivation(getCounter());
        } else {
            if (getCounter() != 10) {
                System.out.println("\nDer aktuelle Stand deines Hangmans:");
            }
        }

        Helper.HangmanGraphic(getCounter());

        System.out.println("--------------------------------------------------------------------------------");
        return true;
    }

    /**
     * --------------------------------------------------------------------------------------------------------
     * <p>
     * /*
     * Changes state of gameRunning into false, if there are no trials left
     */
    public void stillRunning() {
        if (getCounter() <= 1) {
            setGameRunning(false);
        }
    }

    /**
     * --------------------------------------------------------------------------------------------------------
     */

    // Buchstaben in den Array der benutzten Buchstaben hinzufügen
    public void addLetter() {
        if (getNumberUsedLetters() < getUsedLetters().length) {
            UsedLetters[getNumberUsedLetters()] = Words.getLetter();
            setNumberUsedLetters(getNumberUsedLetters() + 1);
        }
    }

    // Returns true, if the letter is already guessed
    public Boolean AlreadyGuessed() {
        for (int i = 0; i < getUsedLetters().length; i++) {
            if (getUsedLetters()[i] != null && Words.getLetter().equals(getUsedLetters()[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean ValidInput() {
        //for (int i = 0; i < LetterArray.length; i++) {
        if (Words.getLetter().matches("[" + AllowedLetters + "]+")) {
            return false;
        }
        return true;
    }
}
