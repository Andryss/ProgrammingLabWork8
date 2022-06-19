package client;

import general.Request;
import general.Response;

public interface ClientConnectorModule extends ClientBaseModule {

    Response sendToServer(Request request) throws Exception;

    void sendRequest(Request request) throws Exception;

}
