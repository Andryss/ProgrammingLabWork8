package server;

public interface ServerControllerModule extends ServerBaseModule {

    void info(String message);

    void error(String message);

    void run();

}
