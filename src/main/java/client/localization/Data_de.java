package client.localization;

import java.util.ListResourceBundle;

public class Data_de extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = new Object[][] {

            {"Connection with server was successful", "Verbindung mit Server war erfolgreich"},


            // AUTH SCENE
            {"Authorization page", "Autorisierungsseite"},
            {"Type here your login", "Geben Sie hier Ihr Login ein"},
            {"Type here your pass", "Geben Sie hier Ihren Pass ein"},
            {"Sign in", "Anmelden"},
            {"User already authorized (multi-session is not supported)", "Benutzer bereits autorisiert (Multi-Session wird nicht unterstützt)"},
            {"User successfully logged in", "Benutzer erfolgreich angemeldet"},
            {"Incorrect login or password", "Falscher Login oder falsches Passwort"},
            {"Don't have an account yet?", "Sie haben noch kein Konto?"},
            {"Go to the registration page", "Gehen Sie zur Registrierungsseite"},


            // REG SCENE
            {"Registration page", "Registrierungsseite"},
            {"New user login", "Login für neue Benutzer"},
            {"New user pass", "Neuer Benutzerpass"},
            {"Sign up", "Anmelden"},
            {"User is already registered", "Benutzer ist bereits registriert"},
            {"New user successfully registered", "Neuer Benutzer erfolgreich registriert"},
            {"Already have an account?", "Haben Sie bereits ein Konto?"},
            {"Go to the authorization page", "Gehen Sie zur Autorisierungsseite"},


            // AUTH-REG SCENE
            {"Login", "Login"},
            {"Login must have at least 3 characters", "Login muss mindestens 3 Zeichen lang sein"},
            {"Login must have less than 20 characters", "Login muss weniger als 20 Zeichen haben"},
            {"Login must contains of only alphabetic characters", "Login muss nur alphabetische Zeichen enthalten"},
            {"Password", "Passwort"},
            {"Password must have at least 3 characters", "Passwort muss mindestens 3 Zeichen lang sein"},
            {"Password must have less than 20 characters", "Passwort muss weniger als 20 Zeichen lang sein"},


            // MAIN SCENE
            {"Console", "Konsole"},
            {"Table", "Tabelle"},
            {"Plot", "Grundstück"},


            // USER TAB
            {"User", "Benutzer"},
            {"Username", "Benutzernamen"},
            {"Sign out", "Abmelden"},
            {"Are you sure to sign out? (all unsaved data will be deleted)", "Sind Sie sicher, dass Sie sich abmelden? (alle nicht gespeicherten Daten werden gelöscht)"},
            {"Settings", "Einstellung"},
            {"Language", "Sprachlich"},
            {"Are you sure to exit? (all unsaved data will be deleted)", "Bist du sicher, dass du gehst? (alle nicht gespeicherten Daten werden gelöscht)"},
            {"Exit", "Ausfahrt"},


            // CONSOLE TAB
            {"Choose the command you want to send", "Wählen Sie den Befehl aus, den Sie senden möchten"},
            {"Send", "Senden"},
            {"Choose command", "Befehl auswählen"},
            {"You should choose command to send!", "Sie sollten Befehl zum Senden wählen!"},


            // COMMAND
            {"Are you sure?", "Bist du dir sicher?"},
            {"Are you sure to send command", "Sind Sie sicher, Befehl zu senden"},
            {"with given argument?", "mit gegebenem Argument?"},
            {"with given arguments?", "mit gegebenen Argumenten?"},
            {"Enter length", "Länge eingeben"},
            {"Enter script filename", "Geben Sie den Dateinamen des Skripts ein"},
            {"Enter mpaa rating", "MPAA-Bewertung eingeben"},
            {"Enter key to remove", "Enter-Taste zum Entfernen"},
            {"Movie key to insert", "Filmtaste zum Einfügen"},
            {"Movie key to replace", "Filmschlüssel zu ersetzen"},
            {"Movie key to update", "Filmschlüssel zum Aktualisieren"},
            {"Return", "Zurückgeben"},
            {"Confirm", "Bestätigen"},


            // CHECK INDEX ERRORS
            {"ERROR: Server is not responding, try later or choose another server :(", "FEHLER: Server antwortet nicht, versuchen Sie es später oder wählen Sie einen anderen Server :("},
            {"ERROR: User isn't logged in yet (or connection support time is out)", "FEHLER: Benutzer ist noch nicht angemeldet (oder die Zeit für den Verbindungssupport ist abgelaufen)"},
            {"ERROR: Your elements count limit exceeded", "FEHLER: Die Anzahl Ihrer Elemente wurde überschritten"},
            {"ERROR: Movie with given key doesn't exist", "FEHLER: Film mit angegebenem Schlüssel existiert nicht"},
            {"ERROR: You don't have permission to update movie with given key", "FEHLER: Sie haben keine Berechtigung, den Film mit dem angegebenen Schlüssel zu aktualisieren"},
            {"ERROR: You have permission to update movie with given key", "FEHLER: Sie haben die Berechtigung, den Film mit dem angegebenen Schlüssel zu aktualisieren"},
            {"ERROR: Movie with given key already exists", "FEHLER: Film mit angegebenem Schlüssel existiert bereits"},
            {"ERROR: You can't replace movie with given key", "FEHLER: Sie können den Film nicht durch den angegebenen Schlüssel ersetzen"},
            {"ERROR: You can't update movie with given key", "FEHLER: Sie können den Film mit dem angegebenen Schlüssel nicht aktualisieren"},
            {"OK", "Okay"},


            // ONE PARAM ERRORS
            {"ERROR: method \"setGUIArgs\" is not overridden", "FEHLER: Methode \"setGUIArgs\" wird nicht überschrieben"},
            {"ERROR: value must be integer", "FEHLER: Wert muss Ganzzahl sein"},
            {"ERROR: current script doesn't exists", "FEHLER: aktuelles Skript existiert nicht"},
            // ERROR: value must be one of: [G, PG, PG_13, R, NC_17]
            // ERROR: value must be integer


            // MOVIE FIELD ERRORS
            {"ERROR: value must be in range [-200,200]", "FEHLER: Wert muss im Bereich [-200,200] liegen"},
            {"ERROR: value must be float", "FEHLER: Wert muss float sein"},
            {"ERROR: field can't be null, String can't be empty", "FEHLER: Feld darf nicht null sein, Zeichenfolge darf nicht leer sein"},
            {"ERROR: name must have less than 20 characters", "FEHLER: Name muss weniger als 20 Zeichen haben"},
            {"ERROR: value must be more than 0", "FEHLER: Wert muss größer als 0 sein"},
            {"ERROR: value must be long", "FEHLER: Wert muss lang sein"},
            {"ERROR: value must be more than 60 and less than 300", "FEHLER: Wert muss größer als 60 und kleiner als 300 sein"},
            {"ERROR: value must be integer", "FEHLER: Wert muss Ganzzahl sein"},
            {"ERROR: value must be one of: [G, PG, PG_13, R, NC_17]", "FEHLER: Der Wert muss einer der folgenden Werte sein: [G, PG, PG_13, R, NC_17]"},
            {"ERROR: name must have less than 20 characters", "FEHLER: Name muss weniger als 20 Zeichen haben"},
            {"ERROR: year must be at least 1900 (vampire-screenwriters is not supported)", "FEHLER: Jahr muss mindestens 1900 sein (Vampir-Drehbuchautoren werden nicht unterstützt)"},
            {"ERROR: field must have \"DD.MM.YYYY\" format", "FEHLER: Feld muss das Format \"TT.MM.JJJJ\" haben"},



            // TABLE TAB
            {"Filter by", "Filtern nach"},
            {"with value contains", "mit Wert enthält"},
            {"anything", "alles"},
            {"Do you think here is an old collection?", "Glaubst du, hier ist eine alte Sammlung?"},
            {"Update", "Update"},
            {"or set", "oder einstellen"},
            {"Auto-update", "Automatische Aktualisierung"},


            // PLOT TAB
            {"Selected movie", "Ausgewählter Film"},
            {"nothing selected", "nichts ausgewählt"},


            // TABS
            {"Edit", "Bearbeiten"},
            {"Remove", "Entfernen"},



            // ERRORS
            {"Error", "Fehlermeldung"},
            {"Something wrong", "Irgendwas stimmt nicht"},
            {"Oops... Seems like evil goblins cut some wires... Try again later", "Ups... Es scheint, als hätten böse Kobolde einige Drähte durchtrennt... Versuchen Sie es später erneut"},
            {"Wrong request format", "Falsches Anforderungsformat"},
            {"Server has wrong logic: unexpected", "Server hat falsche Logik: unerwartet"},
            {"FATAL", "TÖDLICH"},
            {"FATAL ERROR: don't peek", "SCHWERWIEGENDER FEHLER: nicht spähen"}
    };
}
