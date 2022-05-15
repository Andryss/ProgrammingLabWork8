package general;

import general.element.Movie;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Interface of server response to the client
 */
public interface Response extends Serializable {

    Response addMessage(String line);

    ResponseType getResponseType();

    String getMessage();

    Hashtable<Integer, Movie> getHashtable();

    void setHashtable(Hashtable<Integer, Movie> hashtable);

    /**
     * Represent type of possible server responses
     */
    enum ResponseType {
        WRONG_REQUEST_FORMAT,

        CONNECTION_SUCCESSFUL,

        LOGIN_SUCCESSFUL,
        LOGIN_FAILED,

        REGISTER_SUCCESSFUL,
        REGISTER_FAILED,

        ELEMENT_NOT_PRESENTED,
        USER_LIMIT_EXCEEDED,
        PERMISSION_DENIED,
        CHECKING_SUCCESSFUL,
        CHECKING_FAILED,

        EXECUTION_SUCCESSFUL,
        EXECUTION_FAILED
    }
}
