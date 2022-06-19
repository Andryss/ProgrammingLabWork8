package server;

import general.element.Movie;
import general.element.UserProfile;
import general.Response;
import general.ServerContext;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

/**
 * @see ServerContext
 */
public class ServerContextImpl implements ServerContext {
    private final ServerModuleHolder moduleHolder = ServerModuleHolder.getInstance();
    private final ServerCollectionManagerModule collectionManagerModule = moduleHolder.getCollectionManagerModule();
    private final ServerHistoryManagerModule historyManagerModule = moduleHolder.getHistoryManagerModule();
    
    protected final UserProfile userProfile;
    private final Response response;


    public ServerContextImpl(UserProfile userProfile, Response response) {
        this.userProfile = userProfile;
        this.response = response;
    }

    @Override
    public Movie getMovie(Integer key) {
        return collectionManagerModule.getMovie(key);
    }

    @Override
    public Movie putMovie(Integer key, Movie movie) throws IllegalAccessException {
        return collectionManagerModule.putMovie(key, movie, userProfile);
    }

    @Override
    public Movie updateMovie(Integer key, Movie movie) throws IllegalAccessException {
        return collectionManagerModule.updateMovie(key, movie, userProfile);
    }

    @Override
    public Movie removeMovie(Integer key) throws IllegalAccessException {
        return collectionManagerModule.removeMovie(key, userProfile);
    }

    @Override
    public void removeAllMovies() throws IllegalAccessException {
        collectionManagerModule.removeAllMovies(userProfile);
    }

    @Override
    public Hashtable<Integer,Movie> getMovieCollection() {
        return collectionManagerModule.getMovieCollection();
    }

    @Override
    public LinkedList<String> getUserHistory() {
        return historyManagerModule.getUserHistory(userProfile);
    }

    @Override
    public Response getResponse() {
        return response;
    }

    @Override
    public ServerContext validationClone() {
        return new ServerContextClone(userProfile);
    }


    private class ServerContextClone extends ServerContextImpl {
        private final Hashtable<Integer, Movie> movieCollection = collectionManagerModule.getMovieCollection();

        public ServerContextClone(UserProfile userProfile) {
            super(userProfile, ResponseImpl.getEmptyResponse());
        }

        @Override
        public Movie getMovie(Integer key) {
            return movieCollection.get(key);
        }
        @Override
        public Movie putMovie(Integer key, Movie movie) throws IllegalAccessException {
            if (movieCollection.containsKey(key)) {
                throw new IllegalAccessException("Movie already exists");
            } else if (movieCollection.size() >= collectionManagerModule.getCollectionElementsLimit()) {
                throw new IllegalAccessException("Collection limit (" + collectionManagerModule.getCollectionElementsLimit() + ") exceeded");
            } else if (movieCollection.values().stream()
                    .filter(m -> m.getOwner().equals(userProfile.getName()))
                    .count() >= collectionManagerModule.getUserElementsLimit()) {
                throw new IllegalAccessException(userProfile.getName() + "'s elements count limit (" + collectionManagerModule.getUserElementsLimit() + ") exceeded");
            }
            movie.setOwner(userProfile.getName());
            return movieCollection.put(key, movie);
        }
        @Override
        public Movie updateMovie(Integer key, Movie movie) throws IllegalAccessException {
            if (movieCollection.get(key) != null) {
                throw new IllegalAccessException("Movie with key \"" + key + "\" doesn't exist");
            }
            if (movieCollection.get(key).getOwner().equals(userProfile.getName())) {
                throw new IllegalAccessException("User \"" + userProfile.getName() + "\" doesn't have permission to update movie with key \"" + key + "\"");
            }
            return putMovie(key, movie);
        }
        @Override
        public Movie removeMovie(Integer key) throws IllegalAccessException {
            if (movieCollection.get(key) != null) {
                throw new IllegalAccessException("Movie with key \"" + key + "\" doesn't exist");
            }
            if (movieCollection.get(key).getOwner().equals(userProfile.getName())) {
                throw new IllegalAccessException("User \"" + userProfile.getName() + "\" doesn't have permission to remove movie with key \"" + key + "\"");
            }
            return movieCollection.remove(key);
        }
        @Override
        public void removeAllMovies() {
            //noinspection unchecked
            ((Hashtable<Integer,Movie>) movieCollection.clone()).entrySet().stream()
                    .filter(e -> e.getValue().getOwner().equals(userProfile.getName()))
                    .map(Map.Entry::getKey)
                    .forEach(movieCollection::remove);
        }
        @Override
        public Hashtable<Integer, Movie> getMovieCollection() {
            return movieCollection;
        }
    }
}
