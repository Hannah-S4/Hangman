package hangman;

import java.util.Scanner;

import static hangman.AI.SolverAI;
import static hangman.AI.possibleWordsSize;
import static hangman.Main.*;
import static hangman.SelectWord.*;

public class SelectMode {

    private static String dividingLine = "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

    /**
     * Prints a message which mode had been selected and starts the according game
     * @param greet entered letters in the console by player
     */
    public static void Greeting(String greet) {
        Scanner read = new Scanner(System.in);

        switch (greet.toLowerCase()) {
            case "s" -> {
                System.out.println("Du hast den Singlemodus gewählt.");
                startsSinglemode();
            }

            case "p" -> {
                System.out.println("Du hast den Partnermodus gewählt.");
                startsPartnermode();
            }

            default -> {
                System.out.println("Du hast keinen Spielmodus ausgewählt, bitte gib 's' ein, um im Singlemodus, oder 'p' um im Partnermodus zu spielen: ");
                Greeting(read.next());
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /*
     * Selects Level by player.
     */
    public static String Level(String level) {
        Scanner read = new Scanner(System.in);
        String res = " ";

        switch (level.toLowerCase()) {
            case "a" -> {
                System.out.println("Du hast das Anfänger-Level ausgewählt. Ok, jeder fängt schließlich mal klein an.");
                res = "a";
            }
            case "f" -> {
                System.out.println("Du hast das Fortgeschritten-Level ausgewählt. Nicht schlecht.");
                res = "f";
            }
            case "e" -> {
                System.out.println("Du hast das Experten-Level ausgewählt. Du willst also ein echter Experte sein?");
                res = "e";
            }
            default -> {
                System.out.print("Du hast kein Level ausgewählt.\nBitte wähle 'A' für Anfänger, 'F' für Fortgeschrittene und 'E' für Experten: ");
                Level(read.next());
            }
        }
        return res;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /*
     * Selects number of Rounds by player.
     */
    public static int SelectRounds() {
        Scanner read = new Scanner(System.in);
        boolean valid = false;
        String input;
        int number = -1;
        while (!valid) {
            try {
                input = read.next();
                number = Integer.parseInt(input);
                if (number > 0 && number < 11) {
                    System.out.println("Ok, du hast " + number + " Runden gewählt. Los geht's mit dem Spiel.");
                    valid = true;
                    return number;
                } else {
                    System.out.print("Du hast eine nicht mögliche Rundenanzahl eingegeben. Bitte gib eine Zahl zwischen 1 und 10 ein: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Du hast eine nicht mögliche Rundenanzahl eingegeben. Bitte gib eine Zahl zwischen 1 und 10 ein: ");
            }
        }
        return number;
    }

    /* ----------------------------------------------------------------------------------------------------------------------------------------------*/

    public static void startsSinglemode() {
        System.out.println(dividingLine);
        Scanner read = new Scanner(System.in);

        // Select level
        System.out.print("Auf welchem Level möchtest du spielen?\nWähle 'A' für Anfänger, 'F' für Fortgeschrittene und 'E' für Experten: ");
        String level = read.next();
        Level(level);

        System.out.println(dividingLine);

        // Select amount of rounds
        System.out.print("Wie viele Runden möchtest du spielen? Gib eine Zahl zwischen 1 und 10 ein: ");
        int rounds = SelectRounds();

        System.out.println(dividingLine);

        System.out.print("Kurze Anleitung: \nDer PC gibt dir ein Wort vor und du musst versuchen, es mit möglichst wenigen " +
                "Fehlversuchen zu erraten, du hast maximal jedoch 10 Fehlversuche. \nDu kannst einen einzelnen Buchstaben oder ein ganzes Wort eingeben. \n" +
                "Es gilt: 'ä' = 'ae', 'ö' = 'oe', 'ü' = 'ue', 'ß' = 'ss'.\n" +
                "Groß- und Kleinschreibung kann ignoriert werden.\n" +
                "Im Anschluss an deine Runde kannst du dich mit der KI direkt vergleichen. Sie muss das gleiche Wort erraten wie du. Sei gespannt.\n" +
                "Und jetzt viel Spaß. :)\nWenn du mehr über den Algorithmus der KI erfahren möchtest, drücke 'K'. Ansonsten drücke 'S', um mit Hangman zu starten: ");

        String input = read.next();

        while (!(input.equalsIgnoreCase("s") || input.equalsIgnoreCase("k"))) {
            System.out.print("Du musst 'K' drücken, um mehr über die KI zu erfahren oder 'S', wenn du mit Hangman starten möchtest: ");
            input = read.next();
        }

        if (input.equalsIgnoreCase("K")) {
            System.out.print("So funktioniert der Algorithmus der KI:\nSie rät zunächst Buchstaben nach der Häufigkeit des Vorkommens im Deutschen. \n" +
                    "Außerdem hat sie Zugriff auf alle potenziell möglichen Wörter aus dem Wörterbuch.\n" +
                    "Um das Spiel fair zu gestalten, rät sie in den ersten 3 Versuchen auf jeden Fall Buchstaben." +
                    "Mit jedem Buchstaben, den sie mehr errät, verringert sie die Anzahl der aktuell noch möglichen Wörter.\n" +
                    "Wenn ihr nur noch weniger als 2 Buchstaben fehlen, es nur noch weniger als 3 mögliche Wörter gibt oder es ihr allerletzter Rateversuch ist,\n" +
                    "wählt sie mit uniformer Wahrscheinlichkeitsverteilung ein Wort aus.\n" +
                    "Des Weiteren rät sie, wenn es zulässig ist, priorisiert häufig vorkommende Buchstabenkombination wie 'sch', 'ch', 'pf' ...\n" +
                    "So, und jetzt bist du dran. Drücke 'S', um endlich Hangman zu spielen: ");
            input = read.next();
        }

        while (!input.equalsIgnoreCase("s")) {
            System.out.print("Du musst 'S' drücken, wenn du mit Hangman starten möchtest: ");
            input = read.next();
        }

        if (input.equalsIgnoreCase("S")) {

            // Counter für Spielauswertung (S = Spieler, K = KI)
            double numberFailsS = 0;
            double numberFailsK = 0;
            int numberWonRoundsS = 0;  // für direkten Vergleich
            int numberWonRoundsK = 0;


            for (int i = 0; i < rounds; i++) {
                // set variables for a new a game
                if (level.equals("a")) {
                    hangman.Words.setGuessedWord(selectWord(amount_of_words_A, Dictionary_A));
                }
                if (level.equals("f")) {
                    hangman.Words.setGuessedWord(selectWord(amount_of_words_F, Dictionary_F));
                }
                if (level.equals("e")) {
                    hangman.Words.setGuessedWord(selectWord(amount_of_words_E, Dictionary_E));
                }

                // Initialize the attributes of player and words
                Words.setWord(InitializeWord(hangman.Words.getGuessedWord()));
                Words.setLetter("");
                SinglePlayer.setCounter(counter_trial);
                SinglePlayer.setGameRunning(true);
                SinglePlayer.setGameWon(false);
                SinglePlayer.setUsedLetters(new String[26]);
                SinglePlayer.setNumberUsedLetters(0);

                // Prints dividing line for next game
                System.out.println("\n------------------------------------------------------- Runde " + (i + 1) + " " + dividingLine);

                // starts Singlemode
                Singlemode.Singlemode();

                // AI guesses the same word as singleplayer
                System.out.println(dividingLine);
                System.out.print("\nMal schauen, ob du besser als die KI bist. Drücke 'a', um die KI zu starten. ");

                // Reads the next line
                input = read.next();

                // If entered letter is not 'A', player have to enter again
                while (!input.equalsIgnoreCase("A")) {
                    System.out.println("Du musst 'a' drücken, um die KI zu starten. ");
                    input = read.next();
                }

                // sets variables for the AI
                hangman.Words.setWord(InitializeWord(hangman.Words.getGuessedWord()));
                hangman.Words.setLetter("");
                AIPlayer.setCounter(counter_trial);
                AIPlayer.setGameRunning(true);
                AIPlayer.setGameWon(false);
                AIPlayer.setUsedLetters(new String[26]);
                AIPlayer.setNumberUsedLetters(0);
                AIPlayer.setIndex(0);
                AIPlayer.setLetterState(new Boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true});
                AIPlayer.setPossibleWords(possibleWordsSize(Dictionary_KI));

                // starts AI
                SolverAI();

                // ----------------------------------------------------------------------------------------------

                // Auswertung von dieser Runde

                try {
                    Thread.sleep(4000); // Verzögerung von 2000 Millisekunden (2 Sekunde)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String SingleGewonnen = " nicht";
                if (SinglePlayer.getGameWon()) {
                    SingleGewonnen = "";
                }

                String KIGewonnen = " nicht";
                if (AIPlayer.getGameWon()) {
                    KIGewonnen = "";
                }

                System.out.println(dividingLine);

                System.out.println("Auswertung von dieser Runde: \n" +
                        "Du hast es" + SingleGewonnen + " geschafft, das Wort zu erraten. Anzahl Fehlversuche: " + (counter_trial - SinglePlayer.getCounter()) + "\n" +
                        "Die KI hat es" + KIGewonnen + " geschafft, das Wort zu erraten. Anzahl Fehlversuche: " + (counter_trial - AIPlayer.getCounter()));

                if ((counter_trial - SinglePlayer.getCounter()) < counter_trial - AIPlayer.getCounter()) {
                    System.out.println("Du warst also besser als die KI. Wow, sehr gut!!!");
                    numberWonRoundsS++;
                } else if (counter_trial - AIPlayer.getCounter() < counter_trial - SinglePlayer.getCounter()) {
                    System.out.println("Die KI hat dich geschlagen. Schade, vielleicht schaffst du es ja beim nächsten Mal.");
                    numberWonRoundsK++;
                } else {
                    System.out.println("Oh, da gab es wohl ein Unentschieden. Das schreit nach einer Revanche.");
                }

                numberFailsS = numberFailsS + (counter_trial - SinglePlayer.getCounter());
                numberFailsK = numberFailsK + (counter_trial - AIPlayer.getCounter());

                try {
                    Thread.sleep(3000); // Verzögerung von 3000 Millisekunden
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // In letzter Runde soll das nicht ausgegeben werden
                if (i < rounds - 1) {
                    System.out.print("\nBereit für die nächste Runde? Dann drücke 'S': ");
                    while (!read.next().equalsIgnoreCase("S")) {
                        System.out.print("Du musst 'S' drücken, um die nächste Runde zu starten.");
                    }
                }

            }

            try {
                Thread.sleep(3000); // Verzögerung von 3000 Millisekunden
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Spielauswertung
            System.out.println(dividingLine);
            System.out.println("\nKommen wir jetzt zur gesamten Spielauswertung:\n");
            System.out.println("Anzahl an gespielten Runden: " + rounds + "\n");
            System.out.println("Anzahl Runden, bei dem das Wort erraten wurde: \nDu: " + SinglePlayer.getNumberWon() + "\nKI: " + AIPlayer.getNumberWon() + "\n");
            System.out.println("Durchschnittliche Anzahl an Fehlversuchen pro Runde (Je weniger, desto besser): \nDu: " + String.format("%.2f", numberFailsS / rounds) + "\nKI: " + String.format("%.2f", numberFailsK / rounds) + "\n");
            System.out.println("Direkter Vergleich zwischen dir und KI (Wie oft war man besser als der Gegner.): \nDu: " + numberWonRoundsS + "\nKI: " + numberWonRoundsK);
        }
        read.close();
    }



    // -----------------------------------------------------------------------------------------------------------------



    public static void startsPartnermode() {
        // Sets Players IDs
        Player1.setPlayerID(1);
        Player2.setPlayerID(2);

        System.out.println(dividingLine);
        Scanner read = new Scanner(System.in);

        // Select level
        System.out.print("Auf welchem Level möchtet ihr spielen?\nWählt 'A' für Anfänger, 'F' für Fortgeschrittene und 'E' für Experten: ");
        String level = read.next();
        Level(level);

        System.out.println(dividingLine);

        // Select amount of rounds
        System.out.print("Wie viele Runden möchtet ihr spielen? Gebt eine Zahl zwischen 1 und 10 ein: ");
        int rounds = SelectRounds();

        System.out.println(dividingLine);

        System.out.print("\nKurze Anleitung: \nDer PC gibt euch beiden das gleiche Wort vor. Abwechselnd könnt ihr einen einzelnen Buchstaben oder ein ganzes Wort eingeben. \n" +
                "Wer von euch das Wort zuerst errät, hat gewonnen. Jeder hat maximal 10 Fehlversuche.\n" +
                "Es gilt: 'ä' = 'ae', 'ö' = 'oe', 'ü' = 'ue', 'ß' = 'ss'\n" +
                "Sonderzeichen und Zahlen sind genauso wie Worteingaben, die nicht der jeweiligen Wortlänge entsprechen, nicht möglich.\n" +
                "Sollte ein Spieler seine Rateversuche vor dem anderen Spieler aufgebraucht haben, darf der andere natürlich noch weiter raten.\n" +
                "Für jeden Spieler wird ein separates Galgenmännchen gezeichnet, immer nach seinem jeweiligen Rateversuch.\n" +
                "Und jetzt viel Spaß. :)\nDrückt 'P', um endlich Hangman zu spielen: ");

        String input = read.next();

        while (!input.equalsIgnoreCase("P")) {
            System.out.print("Ihr müsst 'P' drücken, wenn ihr mit Hangman starten möchtet: ");
            input = read.next();
        }

        if (input.equalsIgnoreCase("P")) {

            // Counter für Spielauswertung (1 = Spieler 1; 2 = Spieler)
            double numberFails1 = 0;
            double numberFails2 = 0;

            for (int i = 0; i < rounds; i++) {
                // set variables for a new a game
                //Words.setGuessedWord(selectWord());
                if (level.equals("a")) {
                    hangman.Words.setGuessedWord(selectWord(amount_of_words_A, Dictionary_A));
                }
                if (level.equals("f")) {
                    hangman.Words.setGuessedWord(selectWord(amount_of_words_F, Dictionary_F));
                }
                if (level.equals("e")) {
                    hangman.Words.setGuessedWord(selectWord(amount_of_words_E, Dictionary_E));
                }

                // Initialize attribute sof players and words
                Words.setWord(InitializeWord(hangman.Words.getGuessedWord()));
                Words.setLetter("");
                Player1.setCounter(counter_trial);
                Player1.setGameRunning(true);
                Player1.setGameWon(false);
                Player1.setUsedLetters(new String[26]);
                Player1.setNumberUsedLetters(0);
                Player1.setIndex(0);
                Player2.setCounter(counter_trial);
                Player2.setGameRunning(true);
                Player2.setGameWon(false);
                Player2.setUsedLetters(new String[26]);
                Player2.setNumberUsedLetters(0);
                Player2.setIndex(0);

                // Prints dividing line for next game
                System.out.println("\n------------------------------------------------ Runde " + (i + 1) + " " + dividingLine);

                // starts Partner mode
                Partnermode.GameScenarios();

                // Variables for game avaluation
                numberFails1 = numberFails1 + (counter_trial - Player1.getCounter());
                numberFails2 = numberFails2 + (counter_trial - Player2.getCounter());

                try {
                    Thread.sleep(2000); // Verzögerung von 2000 Millisekunden (2 Sekunde)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // In letzter Runde soll das nicht ausgegeben werden
                if (i < rounds - 1) {
                    System.out.print("\nBereit für die nächste Runde? Dann drückt 'P': ");
                    while (!read.next().equalsIgnoreCase("P")) {
                        System.out.print("Du musst 'P' drücken, um die nächste Runde zu starten.");
                    }
                }
            }
            read.close();

            // Gesamtauswertung

            System.out.println(dividingLine);
            System.out.println("\nKommen wir jetzt zur gesamten Spielauswertung:\n");
            System.out.println("Anzahl an gespielten Runden: " + rounds + "\n");
            System.out.println("Anzahl gewonnener Spiele: \nSpieler 1: " + Player1.getNumberWon() + "\nSpieler 2: " + Player2.getNumberWon() + "\n");
            System.out.println("Durchschnittliche Anzahl an Fehlversuchen pro Runde (Je weniger, desto besser): \nSpieler 1: " + String.format("%.2f", numberFails1 / rounds) + "\nSpieler 2: " + String.format("%.2f", numberFails2 / rounds) + "\n");

        }
    }
}


