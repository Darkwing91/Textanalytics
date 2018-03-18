# Textanalytics
Textanalytics project


*Vorstellung des Projekts:*

Unser Projekt „Hashtagsource“ des Informatik Praxisprojekts WS 17/18 „Textanalysewerkzeuge“ ist eine Anwendung,
die Anhand von Nachrichten von Twitternutzern einschätzt, um welches Geschlecht es sich bei dem Nutzer handelt und in welcher Altersgruppe dieser sich befindet.
Der Nutzer des Programms gibt einen Hashtag in das Suchfeld ein, den er gerne analysieren möchte. Das Programm sammelt daraufhin die Nachrichten der Nutzer,
die unter dem entsprechenden Hashtag zuletzt etwas gepostet haben. Ein Klassifizierungsalgorithmus entscheidet dann Anhand dieser Nachichten, um welches Geschlecht es sich bei jedem Nutzer handelt und in welcher Altersgruppe er sich befindet.
Danach werden die ausgewerteten Daten in einer übersichtlichen Statistik angezeigt.

*Einrichtung:*
Es liegt eine einfache .exe Datei vor, das Projekt kann aber über Eclipse auf einem lokalen Server gestartet werden.
Import des Programms in Eclipse:
Zum Importieren des Programms in Eclipse sind folgende Anweisungen zu befolgen:
Starte Eclipse -> File -> Import -> Existing Maven Porject -> Pfad auswählen -> Select all -> Finish

Zum Hinzufügen eines Servers sind folgende Anweisungen zu befolgen:
Rechtsklick auf Projekt "Hashtagsource_v0.3" -> New -> Other... -> Server -> Tomcat v7.0 Server -> Select host -> Select name -> Select runtime environment: Apache Tomcat v7.0 -> Next -> Select Hashtagsource -> Add -> Finish

*Konfiguration:*
Bevor ein Test des Programms gelingen kann, müssen die Twitter Inizialisirungsdaten in der Klasse 'TwitterStuff' im Package 'MainStuff' gegen zulässige Twitter Zugangsdaten ausgetauscht werden.
Dazu benötigt man einen Twitter Account und von diesem Twitter Account einen zulässigen ConsumerKey, ein ConsumerSecret, ein AccesToken, sowie ein AccessTokenSecretm, diese erhalten sie auf ihrem Twitter Account.
Der zu verändernde Code sieht folgendermaßen aus:

cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("*********************")
		  .setOAuthConsumerSecret("******************************************")
		  .setOAuthAccessToken("**************************************************")
		  .setOAuthAccessTokenSecret("******************************************");
     
Er befindet sich im Konstruktor der Klasse.

*Verwendung:*
Ist alles Vorbereitet und Konfiguriert, kann man den Server starten.
Das Programm ist dann unter der Adresse:
localhost:8080/Hashtagsource_v0.3/
zu finden.
Hier gibt es zwei Auswahlmöglichkeiten:
/MainPage und /AltPage 
Unter MainPage findet man eine lauffähige, jedoch abgespeckte Version des Programms.
Unter AltPage findet man eine alternative Webseite, die schöner aussieht, auf der jedoch die Daten für den Hashtag nicht richtig abgerufen werden können.
Um von der Statistik auf die Eingabeseite zurückzukehren, reicht ein Drücken von Zurück.

*Output:*
Hat man einen Hashtag auf /MainPage eingegeben und das Programm zuende laden lassen,
erhält man die Ergebnisse der Analyse in Form eines Balkendiagramms.
Auf der linken Seite wird das Vorkommen der einzelnen Geschlechter angezeigt, wobei bot hier für Nachichtenkanäle und einfache Spambots steht.
Auf der rechten Seite wird das Vorkommen der einzelnen Altersgruppen angezeigt unter der abgegriffenen Nutzern anzeigt. 'unclassified' steht hier für bots.
Die Überschrift gibt an, welcher Hashtag analysiert wurde und wie viele Nutzer analysiert wurden.

*Evaluation:*
Im Folgenden wird die Evaluation gewisser Funktionen erläutert.

Der Classifier, der den einzelnen Nutzern anhand ihrer Nachichten ein Geschlecht und eine Altersklasse zuordnet. 
Er befindet sich in der Klasse 'SimpleClassifier' im Package 'DataStuff'.
Der Classifier ist ein einfacher Naive-Bayes-Classifier der auf Github unter folgender Adresse zu finden ist: com.github.ptnplanet(siehe Maven dependencies)
Er lernt Anhand eines Trainingssets aus ca. 150  unterschiedlichen Nutzern, von denen das Alter und das Geschlecht bereits bekannt sind.
Diese Nutzerdaten kann man im Projekt in der Klasse 'GenerateUserSet' einsehen, diese befindet sich im Package 'MainStuff'.
Die Klasse 'SimpleClassifier' ist außerdem in der Lage den Classifier zu testen, 
indem er Nutzer aus einem vorliegenden Testset klassifiziert und dieses Ergebnis dann mit der vorliegenden echten, bereits vorliegenden, Klassifizierung des Nutzers gegenüber stellt.
Daraus ergibt sich eine Klassifizierungsgenauigkeit, diese wird am Ende auf der Konsole ausgegeben.
Um diesen Test selber durchzuführen, starten sie die Klasse 'Model' als Java Application. Die einzelnen Ergebnisse sowie die letztendliche Genauigkeit werden dann auf der Konsole angezeigt.


Die Genauigkeit des Classifiers gibt an, wie gut er einzelne Nutzer gewissen Geschlechtern zuordnen kann.
Sie ist Abhängig von der Größe und der Diversität des Trainingssets. Hier könnte man also durch eine größere Menge an Daten bessere Ergebnisse erzielen.
Die Genauigkeit der Zuordnung des Alters ist schwer zu testen, denn hier sind genaue Angaben äußerst schwierig. Außerdem sind selbst Abstandszuordnungen schwierig,
denn welcher Abstand würde als genau genug zählen? Wenn ein Nutzer 25 Jahre alt ist und der Classifier ihn auf 29 schätzt, ist dieses Ergebnis dann gut oder schlecht?
In unserem Test haben wir solch ein Ergebnis für gut befunden. Unsere Range für diesen Test haben wir auf 10 festgelegt.

Der Classifier hat außerdem Probleme damit Fremdsprachen, also Sprachen die nicht Englisch oder Deutsch sind, zuzuordnen. 
Sprachen wie Französisch oder Japanisch werden vom Classifier fast ausnahmslos als 'female' erkannt.
Ein zusätzliches Problem sind Retweets, einerseits sind auch sie aussagekräftig, wenn es um den Nutzer geht, sollten also nicht ignoriert werden. 
Andererseits hat der Classifier Probleme damit, z.B. männlichen Nutzern die häufiger Tweets weiblicher Nutzer Retweeten, das richtige Geschlecht zuzuordnen.
Sollte man diese Tweets nun außer Acht lassen oder nicht? Auf diese Frage konnten wir keine zufriedenstellende Antwort finden.



