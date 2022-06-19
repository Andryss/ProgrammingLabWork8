package client;

import general.commands.*;
import general.Request;

import java.io.IOException;
import java.util.*;

/**
 * <p>ClientExecutor implements (2) step in ClientManager:</p>
 * <p>1) Split input into command name and args</p>
 * <p>2) Check if command name is valid</p>
 * <p>3) Check if args for this command is valid</p>
 * <p>4) Make command build Request</p>
 */
public class ClientExecutorImpl implements ClientExecutorModule {
    private static final ClientExecutorImpl instance = new ClientExecutorImpl();
    private final HashMap<String, CommandContainer> commandMap = new HashMap<>();
    private Request request;

    private ClientExecutorImpl() {}

    public static ClientExecutorImpl getInstance() {
        return instance;
    }

    @Override
    public void initialize() throws IOException, CommandException {
        fillCommandMap();
    }

    private void fillCommandMap() throws IOException, CommandException {

        CommandFiller.fillCommandMap();

    }

    @Override
    public void setProperties(Properties properties) throws Exception {
        // NOTHING HERE
    }

    @Override
    public void executeCommand(String commandName) throws CommandException {
        Command command = commandMap.get(commandName).getCommand();
        if (command == null) {
            throw new UndefinedCommandException(commandName);
        }
        request = RequestBuilder.createNewRequest()
                .setRequestType(Request.RequestType.EXECUTE_COMMAND)
                .setCommandName(commandName)
                .build();
        command.buildRequest(request);
    }

    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    public HashMap<String,CommandContainer> getCommandMap() {
        return commandMap;
    }


    public static class CommandContainerImpl implements CommandContainer {
        private final String commandName;
        private final Command command;
        private final Command.CommandType commandType;
        private final String paramName;
        private final String example;

        public CommandContainerImpl(String commandName, Command command, Command.CommandType commandType, String paramName, String example) {
            this.commandName = commandName;
            this.command = command;
            this.commandType = commandType;
            this.paramName = paramName;
            this.example = example;
        }

        @Override
        public String getCommandName() {
            return commandName;
        }
        @Override
        public Command getCommand() {
            return command;
        }
        @Override
        public Command.CommandType getCommandType() {
            return commandType;
        }
        @Override
        public String getParamName() {
            return paramName;
        }
        @Override
        public String getExample() {
            return example;
        }
    }
}
