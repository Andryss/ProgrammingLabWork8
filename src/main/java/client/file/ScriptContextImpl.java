package client.file;

import client.ClientConnectorModule;
import client.ClientModuleHolder;
import client.RequestBuilder;
import general.ScriptContext;
import general.Request;
import general.Response;

/**
 * @see ScriptContext
 */
public class ScriptContextImpl implements ScriptContext {
    private final ClientConnectorModule connectorModule = ClientModuleHolder.getInstance().getClientConnectorModule();

    private final FileExecutor caller;
    private final FileController controller;

    public ScriptContextImpl(FileController controller, FileExecutor caller) {
        this.controller = controller;
        this.caller = caller;
    }

    @Override
    public Response sendToServer(Request request) throws Exception {
        return connectorModule.sendToServer(request);
    }

    @Override
    public Request createNewRequest(Request.RequestType requestType, Integer checkingIndex) {
        return RequestBuilder.createNewRequest()
                .setRequestType(requestType)
                .setCheckingIndex(checkingIndex)
                .build();
    }

    @Override
    public FileExecutor getCaller() {
        return caller;
    }

    @Override
    public String nextLine() {
        return controller.readLine();
    }
}
