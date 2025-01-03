# Hangman
This is a implementation of the common game  "Hangman" in java, which I created for a university course.
Consider that the game currently supports only German words and the interaction between the player(s) and the laptop is in German, too.
The interaction and game control take place on the console for simplicity. 

------

That is the instruction for the game in German:
## Instruction

### Wie startet man das Spiel?
Um das Spiel zu starten, führen Sie die Main-Methode aus. 
Die gesamte Interaktion erfolgt anschließend über die Konsole.

### Generelle Funktionsweise:
Ein Wort wird zufällig aus einer importierten Textdatei ausgewählt. Der/die Spieler müssen durch Buchstaben-/Worteingaben
in der Konsole dieses unter der Einhaltung einer maximaen Anzahl an Fehlversuchen schnellst möglich erraten. 
Des Weiteren gibt es eine KI, die durch einen selbst implementierten Algorithmus ebenfalls die Wörter errät.
Die gesamte Steuerung und Bedienung des Spiels erfolgt über die Konsole mittels Tasteneingabe.


### Es gibt zwei verschiedene Spielmodi:
1. Singlemodus: Nachdem ein Spieler versucht hat das Wort zu erraten, sieht er die Vorgehensweise der KI Schritt für Schritt.
   Beide erraten das gleiche Wort, sodass sich der Spieler im direkten Vergleich mit der KI messen kann. 
   Derjenige, der weniger Fehlversuche gebraucht hat, hat gewonnen.
2. Partnermodus: Zwei Spieler versuchen gleichzeitig das gleiche Wort zu erraten. 
   Derjenige, der das Wort zuerst erraten hat, hat gewonnen.


### Beide Modi unterstützen:
- Schwierigkeitsauswahl: Auswahl der Schwierigkeit der Wörter (realisiert durch verschiedene Textdateien)
- Rundenauswahl: Auswahl der zu spielenden Rundenanzahl
- Anleitung: Zu Beginn des Spiels wird das Spielprinzip verständlich gemacht.
  Im Singlemodus besteht die Möglichkeit, sich Informationen über die Vorgehensweise des KI-Algorithmus anzeigen zu lassen.
- Hangman-Zeichnen: Zeichnen des Hangmans durch ein "Strichmännchen"
- Motivationssprüche: Ausgabe von verschiedenen Motivationssprüchen abhängig von der bisherigen Anzahl an Fehlversuchen.
- Buchstabenvalidierung: Bereits geratene Buchstaben können nicht erneut geraten werden. Der Spieler wird zu einer erneuten Eingabe aufgefordert.
- Worteingaben-Validierung: Worteingaben einer Länge, die nicht mit dem zu erraten Wort übereinstimmen, führen ebenfalls zu einer erneuten Eingabeaufforderung.
- Spielstatistik: Auswertung der Spielrunden durch eine keien Statistik / Gegenüberstellung der Spieler
- Ansprechende und übersichtliche Darstellung des aktuellen Spielstands unterstützt durch zeitlich verzögerte Ausgaben der Kommentare.


### Beschreibung des KI-Algorithmus:
- Buchstabenhäufigkeit: Die KI rät Buchstaben nach einer festgelegten Reihenfolge, basierend auf der Häufigkeit des Vorkommens in deutschen Wörtern.
- Wörterbuch-Reduktion: Der Algorithmus hat Zugriff auf alle möglichen Wörter aus dem importierten Wörterbuch und verringert nach und nach die Anzahl dieser, 
  indem er sie mit den bisher schon richtig geratenen Buchstaben vergleicht.
- Restwörter-Raten: Wenn die KI nur noch drei oder weniger mögliche Wörter zur Verfügung hat, rät sie mit uniformer Wahrscheinlichkeit eines davon. 
  Auch bei nur drei oder weniger verbleibenden zu erratenden Buchstaben rät sie ein Wort, besonders wenn es ihr letzter Versuch ist, da sie durch Zufall 
  das richtige Wort raten könnte.

### Individuelle Anpassungen
- Neue/andere Wörter können ganz leicht in das Spiel integriert werden, indem sie in die aktuellen Textdateien eingfügt werden.
- Auch durch das Hinzufügen einer neuen Textdatei in den geichen Projektordner könnnen ganz leicht neue Wörterbücher hinzugefügt werden.
