package client.localization;

import java.util.ListResourceBundle;

public class Data extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = new Object[][] {

            // AUTH SCENE
            {"Authorization page", "Authorization page"},
            {"Type here your login", "Type here your login"},
            {"Type here your pass", "Type here your pass"},
            {"Sign in", "Sign in"},
            {"Login failed", "Login failed"},
            {"User already authorized (multi-session is not supported)", "User already authorized (multi-session is not supported)"},
            {"User successfully logged in", "User successfully logged in"},
            {"Incorrect login or password", "Incorrect login or password"},
            {"Don't have an account yet?", "Don't have an account yet?"},
            {"Go to the registration page", "Go to the registration page"},


            // REG SCENE
            {"Registration page", "Registration page"},
            {"New user login", "New user login"},
            {"New user pass", "New user pass"},
            {"Sign up", "Sign up"},
            {"User is already registered", "User is already registered"},
            {"New user successfully registered", "New user successfully registered"},
            {"Already have an account?", "Already have an account?"},
            {"Go to the authorization page", "Go to the authorization page"},


            // AUTH-REG SCENE
            {"Login", "Login"},
            {"Login must have at least 3 characters", "Login must have at least 3 characters"},
            {"Login must have less than 20 characters", "Login must have less than 20 characters"},
            {"Login must contains of only alphabetic characters", "Login must contains of only alphabetic characters"},
            {"Password", "Password"},
            {"Password must have at least 3 characters", "Password must have at least 3 characters"},
            {"Password must have less than 20 characters", "Password must have less than 20 characters"},


            // MAIN SCENE
            {"Console", "Console"},
            {"Table", "Table"},
            {"Plot", "Plot"},


            // USER TAB
            {"User", "User"},
            {"Icon", "Icon"},
            {"Username", "Username"},
            {"Sign out", "Sign out"},
            {"Are you sure to sign out? (all unsaved data will be deleted)", "Are you sure to sign out? (all unsaved data will be deleted)"},
            {"Settings", "Settings"},
            {"Language", "Language"},
            {"Theme", "Theme"},
            {"Default", "Default"},
            {"Dark", "Dark"},
            {"Are you sure to exit? (all unsaved data will be deleted)", "Are you sure to exit? (all unsaved data will be deleted)"},
            {"Exit", "Exit"},


            // CONSOLE TAB
            {"Save history to file", "Save history to file"},
            {"Saved console history", "Saved console history"},
            {"Choose the command you want to send", "Choose the command you want to send"},
            {"Send", "Send"},
            {"Choose command", "Choose command"},
            {"You should choose command to send!", "You should choose command to send!"},


            // ONE PARAM PANE
            {"Param filling page", "Param filling page"},


            // MOVIE KEY PARAM PANE
            {"Element filling page", "Element filling page"},


            // COMMAND
            {"Are you sure?", "Are you sure?"},
            {"Are you sure to send command", "Are you sure to send command"},
            {"with given argument?", "with given argument?"},
            {"with given arguments?", "with given arguments?"},
            {"Enter length", "Enter length"},
            {"Enter script filename", "Enter script filename"},
            {"Enter mpaa rating", "Enter mpaa rating"},
            {"Enter key to remove", "Enter key to remove"},
            {"Movie key to insert", "Movie key to insert"},
            {"Movie key to replace", "Movie key to replace"},
            {"Movie key to update", "Movie key to update"},
            {"Return", "Return"},
            {"Confirm", "Confirm"},


            // CHECK INDEX ERRORS
            {"ERROR: Server is not responding, try later or choose another server :(", "ERROR: Server is not responding, try later or choose another server :("},
            {"ERROR: User isn't logged in yet (or connection support time is out)", "ERROR: User isn't logged in yet (or connection support time is out)"},
            {"ERROR: Your elements count limit exceeded", "ERROR: Your elements count limit exceeded"},
            {"ERROR: Movie with given key doesn't exist", "ERROR: Movie with given key doesn't exist"},
            {"ERROR: You don't have permission to update movie with given key", "ERROR: You don't have permission to update movie with given key"},
            {"ERROR: You have permission to update movie with given key", "ERROR: You have permission to update movie with given key"},
            {"ERROR: Movie with given key already exists", "ERROR: Movie with given key already exists"},
            {"ERROR: You can't replace movie with given key", "ERROR: You can't replace movie with given key"},
            {"ERROR: You can't update movie with given key", "ERROR: You can't update movie with given key"},
            {"OK", "OK"},


            // ONE PARAM ERRORS
            {"ERROR: method \"setGUIArgs\" is not overridden", "ERROR: method \"setGUIArgs\" is not overridden"},
            {"ERROR: value must be integer", "ERROR: value must be integer"},
            {"ERROR: current script doesn't exists", "ERROR: current script doesn't exists"},
            // ERROR: value must be one of: [G, PG, PG_13, R, NC_17]
            // ERROR: value must be integer


            // MOVIE FIELD ERRORS
            {"ERROR: value must be in range [-200,200]", "ERROR: value must be in range [-200,200]"},
            {"ERROR: value must be float", "ERROR: value must be float"},
            {"ERROR: field can't be null, String can't be empty", "ERROR: field can't be null, String can't be empty"},
            {"ERROR: name must have less than 20 characters", "ERROR: name must have less than 20 characters"},
            {"ERROR: value must be more than 0", "ERROR: value must be more than 0"},
            {"ERROR: value must be long", "ERROR: value must be long"},
            {"ERROR: value must be more than 60 and less than 300", "ERROR: value must be more than 60 and less than 300"},
            {"ERROR: value must be integer", "ERROR: value must be integer"},
            {"ERROR: value must be one of: [G, PG, PG_13, R, NC_17]", "ERROR: value must be one of: [G, PG, PG_13, R, NC_17]"},
            {"ERROR: name must have less than 20 characters", "ERROR: name must have less than 20 characters"},
            {"ERROR: year must be at least 1900 (vampire-screenwriters is not supported)", "ERROR: year must be at least 1900 (vampire-screenwriters is not supported)"},
            {"ERROR: field must have \"DD.MM.YYYY\" format", "ERROR: field must have \"DD.MM.YYYY\" format"},


            // TABLE TAB
            {"Filter by", "Filter by"},
            {"with value contains", "with value contains"},
            {"anything", "anything"},
            {"Do you think here is an old collection?", "Do you think here is an old collection?"},
            {"Update", "Update"},
            {"or set", "or set"},
            {"Auto-update", "Auto-update"},


            // PLOT TAB
            {"Selected movie", "Selected movie"},
            {"nothing selected", "nothing selected"},


            // TABS
            {"Edit", "Edit"},
            {"Remove", "Remove"},


            // ERRORS
            {"Error", "Error"},
            {"Something wrong", "Something wrong"},
            {"Oops... Seems like evil goblins cut some wires... Try again later", "Oops... Seems like evil goblins cut some wires... Try again later"},
            {"FATAL", "FATAL"},
            {"FATAL ERROR: don't peek", "FATAL ERROR: don't peek"},
            {"not extends ElementCommand", "not extends ElementCommand"}
    };
}
