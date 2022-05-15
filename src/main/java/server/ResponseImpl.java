package server;

import general.Response;
import general.element.Movie;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @see Response
 */
public class ResponseImpl implements Response {
    private ResponseType responseType;
    private ArrayList<String> message;
    private Hashtable<Integer,Movie> hashtable;
    private static final Response emptyResponse = ResponseBuilder.createNewResponse()
            .setResponseType(ResponseType.CONNECTION_SUCCESSFUL)
            .addMessage("*empty response*")
            .build();

    public ResponseImpl() {}

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
    public void setHashtable(Hashtable<Integer, Movie> hashtable) {
        this.hashtable = hashtable;
    }

    @Override
    public Response addMessage(String message) {
        if (this.message == null) {
            this.message = new ArrayList<>();
        } else {
            this.message.add("\n");
        }
        this.message.add(message.intern());
        return this;
    }

    @Override
    public ResponseType getResponseType() {
        return responseType;
    }
    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        message.forEach(builder::append);
        return builder.toString();
    }
    @Override
    public Hashtable<Integer, Movie> getHashtable() {
        return hashtable;
    }

    public static Response getEmptyResponse() {
        return emptyResponse;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseType=" + responseType +
                ", message=" + message +
                '}';
    }
}
