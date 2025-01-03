package hangman;

import java.util.Scanner;

import static hangman.Main.*;

public class Partnermode {

    // Distinguish between the different game scenarios and call the playerTurn method
    public static void GameScenarios() {

        Scanner read = new Scanner(System.in);
        System.out.println("\nVersucht nun, das Wort zu erraten.");

        // Nobody has won until now: Both players are guessing
        while (!Player1.getGameWon() && !Player2.getGameWon()) {
            if (!Player1.playerTurn(read)) break;
            if (!Player2.playerTurn(read)) break;
        }

        // Player 1 has no trials left, so only player 2 is allowed to guess
        while (!Player1.getGameRunning() && Player2.getGameRunning()) {
            System.out.println("Also kann nur Spieler 2 weiterraten.\n");
            if (!Player2.playerTurn(read)) break;
        }

        // Player 2 has no trials left, so only player 1 is allowed to guess
        while (!Player2.getGameRunning() && Player1.getGameRunning()) {
            System.out.println("Also kann nur Spieler 1 weiterraten.\n");
            if (!Player1.playerTurn(read)) break;
        }

        // Nobody has guessed the right word and nobody has guessing trials left.
        // Prints a message, what the right word was.
        if (!Player1.getGameWon() && !Player2.getGameWon() && !Player1.getGameRunning() && !Player2.getGameRunning()) {
            System.out.println("Oh nein, niemand hat das Wort erraten! Das korrekte Wort wäre '" + Words.getGuessedWord() + "' gewesen.");
        }
    }
}