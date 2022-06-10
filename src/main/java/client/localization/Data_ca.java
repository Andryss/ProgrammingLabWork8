package client.localization;

import java.util.ListResourceBundle;

public class Data_ca extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    // TODO: sync with default
    private final Object[][] contents = new Object[][] {

            {"Connection with server was successful", "La connexió amb el servidor ha tingut èxit"},


            // AUTH SCENE
            {"Authorization page", "Pàgina d'autorització"},
            {"Type here your login", "Escriu aquí la teva entrada"},
            {"Type here your pass", "Escriviu aquí el vostre passi"},
            {"Sign in", "Inicia sessió"},
            {"Login failed", "Ha fallat la connexió"},
            {"User already authorized (multi-session is not supported)", "Usuari ja autoritzat (multi-sessió no és compatible)"},
            {"User successfully logged in", "Usuari registrat amb èxit"},
            {"Incorrect login or password", "Accés o contrasenya incorrectes"},
            {"Don't have an account yet?", "Encara no tens un compte?"},
            {"Go to the registration page", "Anar a la pàgina d'inscripció"},


            // REG SCENE
            {"Registration page", "Pàgina d'inscripció"},
            {"New user login", "Nou accés d'usuari"},
            {"New user pass", "Nova passada d'usuari"},
            {"Sign up", "Inscriure's"},
            {"User is already registered", "L'usuari ja està registrat"},
            {"New user successfully registered", "L'usuari ja està registrat"},
            {"Already have an account?", "Ja tens un compte?"},
            {"Go to the authorization page", "Anar a la pàgina d'autorització"},


            // AUTH-REG SCENE
            {"Login", "Iniciar sessió"},
            {"Login must have at least 3 characters", "L'accés ha de tenir com a mínim 3 caràcters"},
            {"Login must have less than 20 characters", "L'accés ha de tenir menys de 20 caràcters"},
            {"Login must contains of only alphabetic characters", "Login ha de contenir només caràcters alfabètics"},
            {"Password", "Contrasenya"},
            {"Password must have at least 3 characters", "La contrasenya ha de tenir com a mínim 3 caràcters"},
            {"Password must have less than 20 characters", "La contrasenya ha de tenir menys de 20 caràcters"},


            // MAIN SCENE
            {"Console", "Consola"},
            {"Table", "Taula"},
            {"Plot", "Argument"},


            // USER TAB
            {"User", "Usuari"},
            {"Username", "Nom d ' usuari"},
            {"Sign out", "Tancar sessió"},
            {"Are you sure to sign out? (all unsaved data will be deleted)", "Estàs segur de signar? (totes les dades no desades seran eliminades)"},
            {"Settings", "Arranjament"},
            {"Language", "Idioma"},
            {"Are you sure to exit? (all unsaved data will be deleted)", "Estàs segur de sortir? (totes les dades no desades seran eliminades)"},
            {"Exit", "Sortida"},


            // CONSOLE TAB
            {"Choose the command you want to send", "Trieu l'ordre que voleu enviar"},
            {"Send", "Enviar"},
            {"Choose command", "Escolliu ordre"},
            {"You should choose command to send!", "Heu de triar l'ordre a enviar!"},


            // COMMAND
            {"Are you sure?", "N'estàs segur?"},
            {"Are you sure to send command", "Estàs segur d'enviar ordre"},
            {"with given argument?", "amb algun argument?"},
            {"with given arguments?", "amb arguments?"},
            {"Enter length", "Introduïu longitud"},
            {"Enter script filename", "Introduïu el nom del fitxer d ' script"},
            {"Enter mpaa rating", "Introduïu la qualificació mpaa"},
            {"Enter key to remove", "Introduïu la clau a eliminar"},
            {"Movie key to insert", "Clau de cinema per inserir"},
            {"Movie key to replace", "Clau de cinema per substituir"},
            {"Movie key to update", "Clau de cinema per actualitzar"},
            {"Return", "Devolucions"},
            {"Confirm", "Confirmar"},


            // CHECK INDEX ERRORS
            {"ERROR: Server is not responding, try later or choose another server :(", "ERROR: El Servidor No respon, proveu-ho més tard o trieu un altre servidor :("},
            {"ERROR: User isn't logged in yet (or connection support time is out)", "ERROR: L'Usuari encara no ha iniciat la sessió (o el temps de suport de la connexió ha finalitzat)"},
            {"ERROR: Your elements count limit exceeded", "ERROR: s 'ha excedit el límit del recompte d' elements"},
            {"ERROR: Movie with given key doesn't exist", "ERROR: El Cinema amb clau donada no existeix"},
            {"ERROR: You don't have permission to update movie with given key", "ERROR: no teniu permís per actualitzar el vídeo amb la clau indicada"},
            {"ERROR: You have permission to update movie with given key", "ERROR: teniu permís per actualitzar el vídeo amb la clau indicada"},
            {"ERROR: Movie with given key already exists", "ERROR: La Pel. Lícula amb clau donada ja existeix"},
            {"ERROR: You can't replace movie with given key", "ERROR: no podeu substituir el film per la clau donada"},
            {"ERROR: You can't update movie with given key", "ERROR: no es pot actualitzar la pel. lícula amb la clau indicada"},
            {"OK", "BÉ"},


            // ONE PARAM ERRORS
            {"ERROR: method \"setGUIArgs\" is not overridden", "ERROR: el mètode \"setGUIArgs\" no està sobreescrit"},
            {"ERROR: value must be integer", "ERROR: el valor ha de ser sencer"},
            {"ERROR: current script doesn't exists", "ERROR: l ' script actual no existeix"},
            // ERROR: value must be one of: [G, PG, PG_13, R, NC_17]
            // ERROR: value must be integer


            // MOVIE FIELD ERRORS
            {"ERROR: value must be in range [-200,200]", "ERROR: el valor ha d'estar en l ' interval [-200,200]"},
            {"ERROR: value must be float", "ERROR: el valor ha de ser flotant"},
            {"ERROR: field can't be null, String can't be empty", "ERROR: el camp no pot ser nul, La Cadena no pot estar buida"},
            {"ERROR: name must have less than 20 characters", "ERROR: el nom ha de tenir menys de 20 caràcters"},
            {"ERROR: value must be more than 0", "ERROR: el valor ha de ser superior a 0"},
            {"ERROR: value must be long", "ERROR: el valor ha de ser llarg"},
            {"ERROR: value must be more than 60 and less than 300", "ERROR: el valor ha de ser superior a 60 i inferior a 300"},
            {"ERROR: value must be integer", "ERROR: el valor ha de ser sencer"},
            {"ERROR: value must be one of: [G, PG, PG_13, R, NC_17]", "ERROR: el valor ha de ser un de: [G, PG, PG_13, R, NC_17]"},
            {"ERROR: name must have less than 20 characters", "ERROR: el nom ha de tenir menys de 20 caràcters"},
            {"ERROR: year must be at least 1900 (vampire-screenwriters is not supported)", "ERROR: l'any ha de ser com a mínim 1900 (els guionistes de vampirs no estan permesos)"},
            {"ERROR: field must have \"DD.MM.YYYY\" format", "ERROR: el camp ha de tenir format\" dd.MM. AYYY\""},



            // TABLE TAB
            {"Filter by", "Filtrar per"},
            {"with value contains", "el valor conté"},
            {"anything", "qualsevol cosa"},
            {"Do you think here is an old collection?", "Creus que aquí hi ha una recollida selectiva?"},
            {"Update", "Actualitza"},
            {"or set", "o set"},
            {"Auto-update", "Actualització automàtica"},


            // PLOT TAB
            {"Selected movie", "Film seleccionat"},
            {"nothing selected", "res seleccionat"},


            // TABS
            {"Edit", "Edita"},
            {"Remove", "Elimina"},



            // ERRORS
            {"Error", "Error"},
            {"Something wrong", "Alguna cosa no va bé"},
            {"Oops... Seems like evil goblins cut some wires... Try again later", "Oops... Sembla que els malvats goblins tallen alguns cables... Torna a intentar-ho més tard"},
            {"Wrong request format", "Format de petició incorrecte"},
            {"Server has wrong logic: unexpected", "El servidor té una lògica incorrecta: inesperada"},
            {"FATAL", "FATAL"},
            {"FATAL ERROR: don't peek", "ERROR FATAL: no mireu"}
    };
}
