package general;

import client.file.FileExecutor;

import java.io.IOException;

/**
 * Represents all methods which cat be used by commands in script before sending it to the server
 */
public interface ScriptContext {

    Response sendToServer(Request request) throws IOException, ClassNotFoundException;

    Request createNewRequest(Request.RequestType requestType, Integer checkingIndex);

    FileExecutor getCaller();

    String nextLine();

}
