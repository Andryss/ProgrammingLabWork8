package client;

import client.file.FileController;
import client.file.FileExecutor;
import general.ScriptContext;
import general.Request;
import general.Response;

import java.io.IOException;

/**
 * @see ScriptContext
 */
public class ScriptContextImpl implements ScriptContext {
    protected FileExecutor caller;

    @Override
    public Response sendToServer(Request request) throws IOException, ClassNotFoundException {
        return ClientConnector.getInstance().sendToServer(request);
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
        return ClientController.getInstance().readLine();
    }


    public static class ClientINFOFromFileImpl extends ScriptContextImpl {
        private final FileController controller;

        public ClientINFOFromFileImpl(FileController controller, FileExecutor caller) {
            this.controller = controller;
            this.caller = caller;
        }

        @Override
        public String nextLine() {
            return controller.readLine();
        }
    }
}
