package hangman;

import hangman.List.StringList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static hangman.Main.Words;

public class Helper {

    // Prints the currents state of the word for player
    public static void displayCurrentWord() {
        for (String s : Words.getWord()) {
            System.out.print(s + " ");
        }
    }

    // ------------------------------------------------------------------------------------------------------

    // converts the words from the text file into a list of Strings
    public static StringList FileintoList(String dictionary) {
        try {
            // Lese alle Zeilen der Datei in eine List<String>
            List<String> lines = Files.readAllLines(Paths.get(dictionary));

            // Erzeuge eine neue StringList
            StringList stringList = new StringList();

            // Füge jede Zeile zur StringList hinzu
            for (String line : lines) {
                String lineWithoutUmlauts = replaceUmlaut(line);
                stringList.addBack(lineWithoutUmlauts);
            }

            return stringList;

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
            return new StringList(); // Leere StringList zurückgeben im Fehlerfall
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    // replaces ä, ö, ü and ß and returns adapted String
    // If Umlaut is at the first position of the word, it can't be adapted. Because of UNICODE representation ana so on...
    public static String replaceUmlaut(String input) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            char currentChar = input.charAt(i);

            // Check if the current character is an Umlaut and replace accordingly
            switch (currentChar) {
                case 'ä':
                    sb.append("ae");
                    break;
                case 'ö':
                    sb.append("oe");
                    break;
                case 'ü':
                    sb.append("ue");
                    break;
                case 'ß':
                    sb.append("ss");
                    break;
                default:
                    // If the character is not an Umlaut, append it as is
                    sb.append(currentChar);
                    break;
            }

            i++;
        }

        return sb.toString();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static void Motivation(int counter) {

        switch (counter){
            case 10 -> System.out.println("\nWunderbar, bisher hattest du keine Fehlversuche. :)\n");
            case 9 -> {
                System.out.println();
                System.out.println("Oh Mist, der erste Fehlversuch!");
            }
            case 8 -> {
                System.out.println();
                System.out.println("Oh oh, es geht bergab...");
            }
            case 7 -> {
                System.out.println();
                System.out.println("Was für ein Schlamassel, schon 3 Fehlversuche!");
            }
            case 6 -> {
                System.out.println();
                System.out.println("Der Galgen wird immer größer...");
            }
            case 5 -> {
                System.out.println();
                System.out.println("Schon die Hälfte der Versuche ist aufgebraucht!");
            }
            case 4-> {
                System.out.println();
                System.out.println("Der Galgen steht schon komplett!");
            }
            case 3-> {
                System.out.println();
                System.out.println("Oh Mist, der Kopf hängt auch schon!");
            }
            case 2 -> {
                System.out.println();
                System.out.println("Jetzt kam auch schon der Körper dazu!");
            }
            case 1 -> {
                System.out.println();
                System.out.println("Oh, nur noch die Beine fehlen, streng dich an!");
            }
            case 0 -> {
                System.out.println();
                System.out.println("Oh nein... Der Hangman ist gestorben. :(");
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static void HangmanGraphic(int counter) {

        switch (counter){
            case 10 -> System.out.println("\nWunderbar, bisher hattest du keine Fehlversuche. :)\n");
            case 9 -> {
                System.out.println();
                System.out.println("|");
                System.out.println("|");
                System.out.println();
            }
            case 8 -> {
                System.out.println();
                System.out.println("|    ");
                System.out.println("|   ");
                System.out.println("|");
                System.out.println("|");
                System.out.println();
            }
            case 7 -> {
                System.out.println();
                System.out.println("|    ");
                System.out.println("|    ");
                System.out.println("|    ");
                System.out.println("|   ");
                System.out.println("|");
                System.out.println("|");
            }
            case 6 -> {
                System.out.println();
                System.out.println("+--");
                System.out.println("|    ");
                System.out.println("|    ");
                System.out.println("|    ");
                System.out.println("|   ");
                System.out.println("|");
                System.out.println("|");
                System.out.println();
            }
            case 5 -> {
                System.out.println();
                System.out.println("+----+");
                System.out.println("|    ");
                System.out.println("|    ");
                System.out.println("|    ");
                System.out.println("|   ");
                System.out.println("|");
                System.out.println("|");
                System.out.println();
            }
            case 4-> {
                System.out.println();
                System.out.println("+----+");
                System.out.println("|    |");
                System.out.println("|    ");
                System.out.println("|    ");
                System.out.println("|   ");
                System.out.println("|");
                System.out.println("|");
                System.out.println();
            }
            case 3-> {
                System.out.println();
                System.out.println("+----+");
                System.out.println("|    |");
                System.out.println("|    o");
                System.out.println("|    ");
                System.out.println("|   ");
                System.out.println("|");
                System.out.println("|");
                System.out.println();
            }
            case 2 -> {
                System.out.println();
                System.out.println("+----+");
                System.out.println("|    |");
                System.out.println("|    o");
                System.out.println("|    |");
                System.out.println("|   ");
                System.out.println("|");
                System.out.println("|");
                System.out.println();
            }
            case 1 -> {
                System.out.println();
                System.out.println("+----+");
                System.out.println("|    |");
                System.out.println("|    o");
                System.out.println("|   /|\\");
                System.out.println("|   ");
                System.out.println("|");
                System.out.println("|");
                System.out.println();
            }
            case 0 -> {
                System.out.println();
                System.out.println("+----+");
                System.out.println("|    |");
                System.out.println("|    o");
                System.out.println("|   /|\\");
                System.out.println("|   / \\");
                System.out.println("|");
                System.out.println("|");
                System.out.println();
            }
        }
    }
}





