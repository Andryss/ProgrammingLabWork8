package server;

import general.Response;

import java.io.IOException;
import java.net.SocketAddress;

public interface ServerConnectorModule extends ServerBaseModule {

    void run() throws IOException;

    void sendToClient(SocketAddress client, Response response);

}
