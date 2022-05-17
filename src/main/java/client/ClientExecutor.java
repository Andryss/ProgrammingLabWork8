package client;

import general.ScriptContext;
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
public class ClientExecutor {
    private static final ClientExecutor instance = new ClientExecutor();
    private final HashMap<String, CommandContainer> commandMap = new HashMap<>();
    private final CommandContainer emptyContainer = new CommandContainer();
    private Request request;

    private ClientExecutor() {}

    public static ClientExecutor getInstance() {
        return instance;
    }

    void initialize() throws IOException, CommandException {
        fillCommandMap();
    }

    private void fillCommandMap() throws IOException, CommandException {


        CommandFiller.fillCommandMap();


    }

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

    public HashMap<String,CommandContainer> getCommandMap() {
        return commandMap;
    }
    public boolean hasCommand(String commandName) {
        return commandMap.containsKey(commandName);
    }
    public CommandContainer getCommandContainer(String commandName) {
        return commandMap.getOrDefault(commandName, emptyContainer);
    }
    public Command.CommandType getCommandType(String commandName) {
        return commandMap.getOrDefault(commandName, emptyContainer).getCommandType();
    }
    public String getExample(String commandName) {
        return commandMap.getOrDefault(commandName, emptyContainer).getExample();
    }
    public Request getRequest() {
        return request;
    }


    public static class CommandContainer {
        private String commandName;
        private Command command;
        private Command.CommandType commandType;
        private String paramName;
        private String example;

        private CommandContainer() {}

        public CommandContainer(String commandName, Command command, Command.CommandType commandType, String paramName, String example) {
            this.commandName = commandName;
            this.command = command;
            this.commandType = commandType;
            this.paramName = paramName;
            this.example = example;
        }

        public String getCommandName() {
            return commandName;
        }
        public Command getCommand() {
            return command;
        }
        public Command.CommandType getCommandType() {
            return commandType;
        }
        public String getParamName() {
            return paramName;
        }
        public String getExample() {
            return example;
        }
    }
}
