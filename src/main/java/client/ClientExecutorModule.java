package client;

import general.Request;
import general.commands.Command;
import general.commands.CommandException;

import java.util.HashMap;

public interface ClientExecutorModule extends ClientBaseModule {

    void executeCommand(String commandName) throws CommandException;

    Request getRequest();

    HashMap<String,CommandContainer> getCommandMap();

    default boolean hasCommand(String commandName) {
        return getCommandMap().containsKey(commandName);
    }
    default CommandContainer getCommandContainer(String commandName) {
        return (hasCommand(commandName) ? getCommandMap().get(commandName) : null);
    }
    default Command.CommandType getCommandType(String commandName) {
        return (hasCommand(commandName) ? getCommandMap().get(commandName).getCommandType() : null);
    }
    default String getExample(String commandName) {
        return (hasCommand(commandName) ? getCommandMap().get(commandName).getExample() : null);
    }

    interface CommandContainer {

        String getCommandName();

        Command getCommand();

        Command.CommandType getCommandType();

        String getParamName();

        String getExample();

    }
}
