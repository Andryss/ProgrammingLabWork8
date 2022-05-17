package general;

import general.element.Movie;

public interface ClientContext {

    String getParam();

    Integer getMovieKey();

    Movie getMovie();

}
