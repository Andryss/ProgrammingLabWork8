package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServerModuleHolder {

    private final ServerCollectionManagerModule collectionManagerModule;
    private final ServerConnectorModule connectorModule;
    private final ServerControllerModule controllerModule;
    private final ServerExecutorModule executorModule;
    private final ServerHistoryManagerModule historyManagerModule;

    private final List<ServerBaseModule> serverModules = new ArrayList<>();

    {
        // Change it here to replace modules to your own
        collectionManagerModule = ServerCollectionManagerImpl.getInstance();
        connectorModule = ServerConnectorImpl.getInstance();
        controllerModule = ServerControllerImpl.getInstance();
        executorModule = ServerExecutorImpl.getInstance();
        historyManagerModule = ServerHistoryManagerImpl.getInstance();
    }

    {
        serverModules.add(collectionManagerModule);
        serverModules.add(connectorModule);
        serverModules.add(controllerModule);
        serverModules.add(executorModule);
        serverModules.add(historyManagerModule);
    }

    private static final ServerModuleHolder instance = new ServerModuleHolder();
    public static ServerModuleHolder getInstance() {
        return instance;
    }

    private ServerModuleHolder() {}

    public void initializeAll() throws Exception {
        for (ServerBaseModule module : serverModules) {
            module.initialize();
        }
    }

    public void setPropertiesAll(Properties properties) throws Exception {
        for (ServerBaseModule module : serverModules) {
            module.setProperties(properties);
        }
    }

    public void closeAll() {
        for (ServerBaseModule module : serverModules) {
            module.close();
        }
    }

    public ServerCollectionManagerModule getCollectionManagerModule() {
        return collectionManagerModule;
    }

    public ServerConnectorModule getConnectorModule() {
        return connectorModule;
    }

    public ServerControllerModule getControllerModule() {
        return controllerModule;
    }

    public ServerExecutorModule getExecutorModule() {
        return executorModule;
    }

    public ServerHistoryManagerModule getHistoryManagerModule() {
        return historyManagerModule;
    }
}
