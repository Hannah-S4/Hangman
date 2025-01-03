package hangman;

import java.util.Scanner;
import static hangman.Main.*;

public class Singlemode {

    /**
     * Plays the single mode
     */
    public static void Singlemode() {
        System.out.println("\nVersuche nun, das folgende Wort zu erraten:");
        Scanner read = new Scanner(System.in);

        // Game loop
        while (!SinglePlayer.getGameWon()) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Changes state of gameRunning into false, if there are no attempts left
            SinglePlayer.stillRunning();

            //aktueller Stand der Versuche
            int trials = SinglePlayer.getCounter();


            // Prints the currents state of the word for player
            Helper.displayCurrentWord();

            // user input for player (distinguishes the cases 1 = Versuch and else = Versuch*e*
            if(SinglePlayer.getCounter() == 1){
                System.out.print("\nDu hast noch " + SinglePlayer.getCounter() + " Versuch. Dein letzter Versuch!.\nGib einen Buchstaben ein oder versuche das Wort zu erraten: ");
            }
            else {
                System.out.print("\nDu hast noch " + SinglePlayer.getCounter() + " Versuche. \nGib einen Buchstaben ein oder versuche das Wort zu erraten: ");
            }

            Words.setLetter(read.next());

            while(SinglePlayer.ValidInput()){
                System.out.print("Bitte gib einen Buchstaben und keine Zahl oder ein Sonderzeichen ein: ");
                Words.setLetter(read.next());
            }

            // Checks if you already guessed the letter. If yes, you have to enter a new one.
            while(SinglePlayer.AlreadyGuessed()){
                System.out.print("Du hast den Buchstaben '" + Words.getLetter() + "' schon versucht. Bitte gib einen anderen Buchstaben ein: ");
                Words.setLetter(read.next());
            }
            while(Words.getLetter().length() > 1 && Words.getLetter().length() != Words.getGuessedWord().length()){
                System.out.print("Das eingegebene Wort hat nicht die richtige Länge. \n" +
                        "Bitte gib ein Wort der richtigen Länge oder einen Buchstaben ein: ");
                Words.setLetter(read.next());
            }


            if (Words.getLetter().length() == 1) {

                // Calls the CheckLetter method
                SinglePlayer.CheckLetter();

                // Checks if the word has been fully guessed
                if (String.join("", Words.getWord()).equals(Words.getGuessedWord())) {
                    if((counter_trial - SinglePlayer.getCounter()) == 1) {
                        System.out.println("Glückwunsch! Du hast das Wort '" + Words.getGuessedWord() + "' erraten!!! \nDu hattest " + (counter_trial - SinglePlayer.getCounter()) + " Fehlversuch.");
                        SinglePlayer.setNumberWon(SinglePlayer.getNumberWon() + 1);
                    }
                    else{
                        System.out.println("Glückwunsch! Du hast das Wort '" + Words.getGuessedWord() + "' erraten!!! \nDu hattest " + (counter_trial - SinglePlayer.getCounter()) + " Fehlversuche.");
                        SinglePlayer.setNumberWon(SinglePlayer.getNumberWon() + 1);
                    }
                    SinglePlayer.setGameWon(true);
                    break;
                }

                // Adds letter to Array with already Used letters
                SinglePlayer.addLetter();

            } else {
                // Calls the CheckString method
               SinglePlayer.CheckString();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (SinglePlayer.getGameWon()) {
                // Prints Finishing message, because player won the game
                if((counter_trial - SinglePlayer.getCounter()) == 1) {
                    System.out.println("\nGlückwunsch! Du hast das Wort '" + Words.getGuessedWord() + "' erraten!!! \nDu hattest " + (counter_trial - SinglePlayer.getCounter()) + " Fehlversuch.");
                    SinglePlayer.setNumberWon(SinglePlayer.getNumberWon() + 1);
                }
                else{
                    System.out.println("\nGlückwunsch! Du hast das Wort '" + Words.getGuessedWord() + "' erraten!!! \nDu hattest " + (counter_trial - SinglePlayer.getCounter()) + " Fehlversuche.");
                    SinglePlayer.setNumberWon(SinglePlayer.getNumberWon() + 1);
                }
                break;
            }

            // if the counter was decreased, a motivational statement is printetd aditionally to the hangman graphic
            if(trials != SinglePlayer.getCounter()){
                Helper.Motivation(SinglePlayer.getCounter());
            }
            else{
                if(SinglePlayer.getCounter() != 10) {
                    System.out.println("\nDer aktuelle Stand deines Hangmans:");
                }
            }

            Helper.HangmanGraphic(SinglePlayer.getCounter());

            if (!SinglePlayer.getGameRunning()) {
                // Prints Finishing message, because player don't have any guessing attempts left
                System.out.println("Du hast keine Versuche mehr! Das richtige Wort wäre '" + Words.getGuessedWord() + "' gewesen, schade.");
                break;
            }

            System.out.println("-----------------------------------------------------------------------------------------");

        }
    }
}
