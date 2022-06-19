package server;

import general.element.UserProfile;

import java.util.LinkedList;

public interface ServerHistoryManagerModule extends ServerBaseModule {

    void updateUser(UserProfile userProfile);
    void deleteUser(UserProfile userProfile);

    void addUserHistory(UserProfile userProfile, String command);
    void clearUserHistory(String username);

    LinkedList<String> getUserHistory(UserProfile userProfile);

}
