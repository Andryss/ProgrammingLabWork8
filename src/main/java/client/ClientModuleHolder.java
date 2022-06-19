package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class ClientModuleHolder {

    private final ClientConnectorModule clientConnectorModule;
    private final ClientControllerModule clientControllerModule;
    private final ClientExecutorModule clientExecutorModule;

    private final List<ClientBaseModule> clientModules = new ArrayList<>();

    {
        // Change it here to replace modules to your own
        clientConnectorModule = ClientConnectorImpl.getInstance();
        clientControllerModule = ClientControllerImpl.getInstance();
        clientExecutorModule = ClientExecutorImpl.getInstance();
    }

    {
        clientModules.add(clientConnectorModule);
        clientModules.add(clientControllerModule);
        clientModules.add(clientExecutorModule);
    }

    private static final ClientModuleHolder instance = new ClientModuleHolder();
    public static ClientModuleHolder getInstance() {
        return instance;
    }

    private ClientModuleHolder() {}

    public void initializeAll() throws Exception {
        for (ClientBaseModule module : clientModules) {
            module.initialize();
        }
    }

    public void setPropertiesAll(Properties properties) throws Exception {
        for (ClientBaseModule module : clientModules) {
            module.setProperties(properties);
        }
    }

    public ClientConnectorModule getClientConnectorModule() {
        return clientConnectorModule;
    }

    public ClientControllerModule getClientControllerModule() {
        return clientControllerModule;
    }

    public ClientExecutorModule getClientExecutorModule() {
        return clientExecutorModule;
    }
}
