package server;

import general.element.FieldException;
import org.apache.logging.log4j.core.jmx.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ServerManager initialize server and start Thread which receiving requests and Thread which reading console commands
 */
public class ServerManager {
    private static final ServerManager instance = new ServerManager(); // Follow "Singleton" pattern

    private final ServerModuleHolder moduleHolder = ServerModuleHolder.getInstance();
    private final ServerControllerModule controllerModule = moduleHolder.getControllerModule();

    private ServerManager() {}

    public static ServerManager getInstance() {
        return instance;
    }

    public void run(Properties properties) throws Exception {
        controllerModule.info("Initializations start");
        initializations(properties);
        controllerModule.info("Initializations completed");
        controllerModule.info("Server started at: " + InetAddress.getLocalHost());

        new Thread(() -> {
            try {
                moduleHolder.getConnectorModule().run();
            } catch (IOException e) {
                controllerModule.error(e.getMessage());
            }
        }, "ReceivingThread").start();
        new Thread(controllerModule::run, "SeConsoleThread").start();
    }

    private void initializations(Properties properties) throws Exception {
        moduleHolder.setPropertiesAll(properties);
        moduleHolder.initializeAll();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            moduleHolder.closeAll();
            controllerModule.info("All services closed");
        }, "SeClosingThread"));
    }
}
