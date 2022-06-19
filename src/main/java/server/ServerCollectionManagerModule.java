package server;

import general.element.Movie;
import general.element.UserProfile;

import java.util.Hashtable;

public interface ServerCollectionManagerModule extends ServerBaseModule {

    void clearAllData() throws Exception;
    void printTables();

    Hashtable<String, UserProfile> getUserCollection();

    boolean isUserPresented(UserProfile userProfile);
    long registerUser(UserProfile userProfile);
    UserProfile removeUser(UserProfile userProfile);
    void removeUser(String userName);

    Hashtable<Integer,Movie> getMovieCollection();

    Movie getMovie(Integer key);
    long countElements(String userName);
    Movie putMovie(Integer key, Movie movie, UserProfile userProfile) throws IllegalAccessException;
    Movie updateMovie(Integer key, Movie movie, UserProfile userProfile) throws IllegalAccessException;
    Movie removeMovie(Integer key, UserProfile userProfile) throws IllegalAccessException;
    void removeMovie(Integer key);
    void removeAllMovies(UserProfile userProfile) throws IllegalAccessException;
    void removeAllMovies();

    int getCollectionElementsLimit();
    int getUserElementsLimit();

}
