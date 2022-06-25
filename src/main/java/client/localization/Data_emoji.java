package client.localization;

import java.util.ListResourceBundle;

public class Data_emoji extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = new Object[][] {

            // AUTH SCENE
            {"Authorization page", "✔  \uD83D\uDCC3"},
            {"Type here your login", "\uD83C\uDD8E  \uD83C\uDE01  \uD83D\uDC46  \uD83D\uDCB3"},
            {"Type here your pass", "\uD83C\uDD8E  \uD83C\uDE01  \uD83D\uDC46  \uD83C\uDE34"},
            {"Sign in", "\uD83D\uDED1 "},
            {"Login failed", "\uD83D\uDCB3  ❌"},
            {"User already authorized (multi-session is not supported)", "\uD83D\uDC69\u200D\uD83D\uDCBB  ⏪  ✔ ( \uD83D\uDC41 - \uD83D\uDCAC  \uD83D\uDEAB  \uD83C\uDFE5 )"},
            {"User successfully logged in", "\uD83D\uDC69\u200D\uD83D\uDCBB  \uD83D\uDD1C  \uD83D\uDC68 \uD83D\uDCDB "},
            {"Incorrect login or password", "❌  \uD83D\uDCB3  ⚖️  \uD83D\uDD13"},
            {"Don't have an account yet?", "\uD83D\uDEAB  ✔️  \uD83C\uDFE7  ❓"},
            {"Go to the registration page", "\uD83D\uDEB6  ®  \uD83D\uDCC3"},


            // REG SCENE
            {"Registration page", "®  \uD83D\uDCC3"},
            {"New user login", "\uD83C\uDD95  \uD83D\uDC69\u200D\uD83D\uDCBB  \uD83D\uDCB3"},
            {"New user pass", "\uD83C\uDD95  \uD83D\uDC69\u200D\uD83D\uDCBB  \uD83C\uDE34"},
            {"Sign up", "\uD83D\uDED1  \uD83C\uDD99"},
            {"User is already registered", "\uD83D\uDC69\u200D\uD83D\uDCBB  ⏪  ®"},
            {"New user successfully registered", "\uD83C\uDD95  \uD83D\uDC69\u200D\uD83D\uDCBB  \uD83D\uDD1C  ®"},
            {"Already have an account?", "⏪  ✔️  \uD83C\uDFE7  ❓"},
            {"Go to the authorization page", "\uD83D\uDEB6  ✔  \uD83D\uDCC3"},


            // AUTH-REG SCENE
            {"Login", "\uD83D\uDCB3"},
            {"Login must have at least 3 characters", "\uD83D\uDCB3  \uD83D\uDD1C  ✔️  \uD83D\uDC25  3⃣  \uD83D\uDC32"},
            {"Login must have less than 20 characters", "\uD83D\uDCB3  \uD83D\uDD1C  ✔️  \uD83C\uDF18  \uD83D\uDE42 \uD83D\uDCDB  2⃣ 0⃣  \uD83D\uDC32"},
            {"Login must contains of only alphabetic characters", "\uD83D\uDCB3  \uD83D\uDD1C  \uD83D\uDD0C  \uD83D\uDD74  \uD83D\uDD20  \uD83D\uDC32"},
            {"Password", "\uD83D\uDD13"},
            {"Password must have at least 3 characters", "\uD83D\uDD13  \uD83D\uDD1C  ✔️  \uD83D\uDC25  3⃣  \uD83D\uDC32"},
            {"Password must have less than 20 characters", "\uD83D\uDD13  \uD83D\uDD1C  ✔️  \uD83C\uDF18  \uD83D\uDE42 \uD83D\uDCDB  2⃣ 0⃣  \uD83D\uDC32"},


            // MAIN SCENE
            {"Console", "\uD83D\uDD79"},
            {"Table", "\uD83C\uDFD3"},
            {"Plot", "\uD83D\uDCD6"},


            // USER TAB
            {"User", "\uD83D\uDC69\u200D\uD83D\uDCBB"},
            {"Icon", "\uD83D\uDEC4"},
            {"Username", "\uD83D\uDCDB"},
            {"Sign out", "\uD83D\uDED1  \uD83D\uDE31"},
            {"Are you sure to sign out? (all unsaved data will be deleted)", "\uD83D\uDC46  \uD83D\uDE0A  \uD83D\uDED1  \uD83D\uDE31  ❓ ( \uD83C\uDF10  \uD83D\uDCDC  \uD83D\uDCD0  \uD83D\uDD1C  ❎ )"},
            {"Delete user", "❎  \uD83D\uDC69\u200D\uD83D\uDCBB"},
            {"Are you sure to delete user? (all your elements will be deleted)", "\uD83D\uDC46  \uD83D\uDE0A  ❎  \uD83D\uDC69\u200D\uD83D\uDCBB  ❓ ( \uD83C\uDF10  \uD83D\uDC46  \uD83D\uDD23  \uD83D\uDD1C  ❎ )"},
            {"Are you really sure?", "\uD83D\uDC46  \uD83E\uDD19  \uD83D\uDE0A  ❓"},
            {"Are you REALLY sure to DELETE USER? (ALL your elements WILL BE DELETED)", "\uD83D\uDC46  \uD83E\uDD19  \uD83D\uDE0A  ❎  \uD83D\uDC69\u200D\uD83D\uDCBB  ❓ ( \uD83C\uDF10  \uD83D\uDC46  \uD83D\uDD23  \uD83D\uDD1C  ❎ )"},
            {"Are you really really really sure?", "\uD83D\uDC46  \uD83E\uDD19  \uD83E\uDD19  \uD83E\uDD19  \uD83D\uDE0A  ❓"},
            {"ARE YOU REALLY REALLY REALLY SURE TO DELETE USER? (ALL YOUR ELEMENTS WILL BE DELETED)", "\uD83D\uDC46  \uD83E\uDD19  \uD83E\uDD19  \uD83E\uDD19  \uD83D\uDE0A  ❎  \uD83D\uDC69\u200D\uD83D\uDCBB  ❓ ( \uD83C\uDF10  \uD83D\uDC46  \uD83D\uDD23  \uD83D\uDD1C  ❎ )"},
            {"Settings", "\uD83D\uDD06"},
            {"Language", "\uD83C\uDDEC\uD83C\uDDE7"},
            {"Theme", "\uD83C\uDFB6"},
            {"Default", "\uD83D\uDD22"},
            {"Dark", "⚫"},
            {"Are you sure to exit? (all unsaved data will be deleted)", "\uD83D\uDC46  \uD83D\uDE0A  \uD83D\uDEAA  ❓ ( \uD83C\uDF10  \uD83D\uDCDC  \uD83D\uDCD0  \uD83D\uDD1C  ❎ )"},
            {"Exit", "\uD83D\uDEAA"},


            // CONSOLE TAB
            {"Save history to file", "\uD83D\uDCBE  \uD83D\uDCD6  \uD83D\uDCC2"},
            {"Saved console history", "\uD83D\uDCBE  \uD83D\uDD79  \uD83D\uDCD6"},
            {"Choose the command you want to send", "⛏  \uD83D\uDD79  \uD83D\uDC46  \uD83D\uDC9A  \uD83D\uDCE8"},
            {"Send", "\uD83D\uDCE8"},
            {"Choose command", "⛏  \uD83D\uDD79"},
            {"You should choose command to send!", "\uD83D\uDC46  \uD83D\uDD1C  ⛏  \uD83D\uDD79  \uD83D\uDCE8  ❗"},


            // ONE PARAM PANE
            {"Param filling page", "= \uD83C\uDF77  \uD83D\uDCC3"},


            // MOVIE KEY PARAM PANE
            {"Element filling page", "\uD83D\uDD23  \uD83C\uDF77  \uD83D\uDCC3"},


            // COMMAND
            {"Are you sure?", "\uD83D\uDC46  \uD83D\uDE0A  ❓"},
            {"Are you sure to send command", "\uD83D\uDC46  \uD83D\uDE0A  \uD83D\uDCE8  \uD83D\uDD79"},
            {"with given argument?", "⏮  \uD83D\uDC50  ❌  ❓"},
            {"with given arguments?", "⏮  \uD83D\uDC50  ❌  ❓"},
            {"Enter length", "⛔  \uD83D\uDCD0"},
            {"Enter script filename", "⛔  \uD83D\uDC68 \uD83D\uDCDB  \uD83D\uDCC1"},
            {"Enter mpaa rating", "⛔  \uD83C\uDFAC  \uD83D\uDCAF"},
            {"Enter key to remove", "⛔  \uD83D\uDD11  ❎"},
            {"Movie key to insert", "\uD83C\uDF9E  \uD83D\uDD11  \uD83D\uDCE9"},
            {"Movie key to replace", "\uD83C\uDF9E  \uD83D\uDD11  ❎"},
            {"Movie key to update", "\uD83C\uDF9E  \uD83D\uDD11  \uD83D\uDD27"},
            {"Return", "↪"},
            {"Confirm", "✔"},


            // CHECK INDEX ERRORS
            {"ERROR: Server is not responding, try later or choose another server :(", "❌ : \uD83D\uDCBD  \uD83D\uDEAB  \uD83D\uDE47 , \uD83D\uDD04  \uD83D\uDD56  ⚖️  ⛏  ➕ 1⃣  \uD83D\uDCBD :("},
            {"ERROR: User isn't logged in yet (or connection support time is out)", "❌ : \uD83D\uDC69\u200D\uD83D\uDCBB  ➖ \uD83D\uDEAB  \uD83D\uDEAB  \uD83D\uDC68 \uD83D\uDCDB ( ⚖️  \uD83D\uDD17  \uD83C\uDFE5  \uD83D\uDD70  \uD83D\uDE31 )"},
            {"ERROR: Your elements count limit exceeded", "❌ : \uD83D\uDC46  \uD83D\uDD23  \uD83D\uDD1F  \uD83D\uDCC9  \uD83D\uDCC9"},
            {"ERROR: Movie with given key doesn't exist", "❌ : \uD83C\uDF9E  ⏮  \uD83D\uDC50  \uD83D\uDD11  \uD83D\uDEAB  \uD83D\uDC95"},
            {"ERROR: You don't have permission to update movie with given key", "❌ : \uD83D\uDC46  \uD83D\uDEAB  ✔️  ✔  \uD83D\uDD27  \uD83C\uDF9E  ⏮  \uD83D\uDC50  \uD83D\uDD11"},
            {"ERROR: You have permission to update movie with given key", "❌ : \uD83D\uDC46  ✔️  ✔  \uD83D\uDD27  \uD83C\uDF9E  ⏮  \uD83D\uDC50  \uD83D\uDD11"},
            {"ERROR: Movie with given key already exists", "❌ : \uD83C\uDF9E  ⏮  \uD83D\uDC50  \uD83D\uDD11  ⏪  \uD83D\uDC95"},
            {"ERROR: You can't replace movie with given key", "❌ : \uD83D\uDC46  \uD83D\uDCAA  \uD83D\uDEAB  ❎  \uD83C\uDF9E  ⏮  \uD83D\uDC50  \uD83D\uDD11"},
            {"ERROR: You can't update movie with given key", "❌ : \uD83D\uDC46  \uD83D\uDCAA  \uD83D\uDEAB  \uD83D\uDD27  \uD83C\uDF9E  ⏮  \uD83D\uDC50  \uD83D\uDD11"},
            {"OK", "\uD83C\uDD97"},


            // ONE PARAM ERRORS
            {"ERROR: method \"setGUIArgs\" is not overridden", "❌ : \uD83D\uDC69\u200D\uD83D\uDD2C \"setGUIArgs\" \uD83D\uDEAB  ⏸"},
            {"ERROR: value must be integer", "❌ : \uD83D\uDCB2  \uD83D\uDD1C  \uD83D\uDD22"},
            {"ERROR: current script doesn't exists", "❌ : ⚡  \uD83D\uDC68 \uD83D\uDCDB  \uD83D\uDEAB  \uD83D\uDC95"},
            // ERROR: value must be one of: [G, PG, PG_13, R, NC_17]
            // ERROR: value must be integer


            // MOVIE FIELD ERRORS
            {"ERROR: value must be in range [-200,200]", "❌ : \uD83D\uDCB2  \uD83D\uDD1C  ↔ [- 2⃣ 0⃣ 0⃣ , 2⃣ 0⃣ 0⃣ ]"},
            {"ERROR: value must be float", "❌ : \uD83D\uDCB2  \uD83D\uDD1C  \uD83C\uDF88"},
            {"ERROR: field can't be null, String can't be empty", "❌ : \uD83C\uDFD1  \uD83D\uDCAA  \uD83D\uDEAB =, \uD83C\uDFBB  \uD83D\uDCAA  \uD83D\uDEAB  \uD83C\uDE33"},
            {"ERROR: name must have less than 20 characters", "❌ : \uD83D\uDCDB  \uD83D\uDD1C  ✔️  \uD83C\uDF18  \uD83D\uDE42 \uD83D\uDCDB  2⃣ 0⃣  \uD83D\uDC32"},
            {"ERROR: value must be more than 0", "❌ : \uD83D\uDCB2  \uD83D\uDD1C  \uD83D\uDC97  \uD83D\uDE42 \uD83D\uDCDB  0⃣"},
            {"ERROR: value must be long", "❌ : \uD83D\uDCB2  \uD83D\uDD1C  \uD83D\uDCCF"},
            {"ERROR: value must be more than 60 and less than 300", "❌ : \uD83D\uDCB2  \uD83D\uDD1C  \uD83D\uDC97  \uD83D\uDE42 \uD83D\uDCDB  6⃣ 0⃣ & \uD83C\uDF18  \uD83D\uDE42 \uD83D\uDCDB  3⃣ 0⃣ 0⃣"},
            {"ERROR: value must be integer", "❌ : \uD83D\uDCB2  \uD83D\uDD1C  \uD83D\uDD22"},
            {"ERROR: value must be one of: [G, PG, PG_13, R, NC_17]", "❌ : \uD83D\uDCB2  \uD83D\uDD1C  1⃣ : [G, PG, PG_13, R, NC_17]"},
            {"ERROR: name must have less than 20 characters", "❌ : \uD83D\uDCDB  \uD83D\uDD1C  ✔️  \uD83C\uDF18  \uD83D\uDE42 \uD83D\uDCDB  2⃣ 0⃣  \uD83D\uDC32"},
            {"ERROR: year must be at least 1900 (vampire-screenwriters is not supported)", "❌ : 1⃣ 2⃣ \uD83D\uDDD3  \uD83D\uDD1C  \uD83C\uDF18  1⃣ 9⃣ 0⃣ 0⃣ ( \uD83E\uDDDB \u200D ♂ - \uD83D\uDC68\u200D\uD83C\uDFA8  \uD83D\uDEAB  \uD83C\uDFE5 )"},
            {"ERROR: field must have \"DD.MM.YYYY\" format", "❌ : \uD83C\uDFD1  \uD83D\uDD1C  ✔️ \"DD.MM.YYYY\" \uD83D\uDCC1"},


            // TABLE TAB
            {"Filter by", "\uD83D\uDEB0 "},
            {"with value contains", "⏮  \uD83D\uDCB2  \uD83D\uDD0C"},
            {"anything", "\uD83D\uDE0D"},
            {"Do you think here is an old collection?", "\uD83D\uDC46  \uD83D\uDCAD  \uD83C\uDE01  \uD83D\uDC74  \uD83D\uDDC3  ❓"},
            {"Update", "\uD83D\uDD27"},
            {"or set", "⚖️  \uD83D\uDD06"},
            {"Auto-update", "\uD83D\uDE99 - \uD83D\uDD27"},


            // PLOT TAB
            {"Selected movie", "\uD83D\uDD8A  \uD83C\uDF9E"},
            {"nothing selected", "\uD83D\uDD73  \uD83D\uDD8A"},


            // TABS
            {"Edit", "✍"},
            {"Remove", "❎"},


            // ERRORS
            {"Error", "❌"},
            {"Something wrong", "\uD83D\uDE0D  ❌"},
            {"Oops... Seems like evil goblins cut some wires... Try again later", "\uD83D\uDE2B ... \uD83D\uDE11  \uD83D\uDC96  \uD83D\uDC7F  \uD83D\uDC7F  ✂  \uD83D\uDD87 ... \uD83D\uDD04  \uD83D\uDD04  \uD83D\uDD56"},
            {"FATAL", "☠"},
            {"FATAL ERROR: don't peek", "☠  ❌ : \uD83D\uDEAB  \uD83D\uDC40"},
            {"not extends ElementCommand", "\uD83D\uDEAB  ↔ ElementCommand"}
    };
}
