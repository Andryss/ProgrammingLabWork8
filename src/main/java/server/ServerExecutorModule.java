package server;

import general.Request;

import java.net.SocketAddress;

public interface ServerExecutorModule extends ServerBaseModule {

    void executeRequest(SocketAddress client, Request request);

    void logoutUser(String userName);

    void printUsers();

    void clearAuthorizedUsers();

}
