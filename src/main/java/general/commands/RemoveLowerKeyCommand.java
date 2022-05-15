package general.commands;

import general.ClientContext;
import general.ScriptContext;
import general.Request;
import general.ServerContext;

/**
 * Command, which removes all elements whose key is less than given
 * @see NameableCommand
 */
public class RemoveLowerKeyCommand extends NameableCommand {
    private Integer key;

    @ParseCommand(name = "remove_lower_key", type = CommandType.ONE_PARAM, paramName = "key to remove", example = "remove_lower_key -13")
    public RemoveLowerKeyCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute(ServerContext server) throws CommandException {
        server.getMovieCollection().keySet().stream()
                .filter(key -> key < this.key)
                .forEach(key -> {
                    try {
                        server.removeMovie(key);
                    } catch (IllegalAccessException e) {
                        //ignore
                    }
                });
        server.getResponse().addMessage("All your elements with key lower than \"" + key + "\" has been removed");
    }

    @Override
    public void setScriptArgs(ScriptContext script, String... args) throws BadArgumentsException {
        if (args.length != 1) {
            throw new BadArgumentsCountException(getCommandName(), 1);
        }
        try {
            this.key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "integer");
        }
    }

    @Override
    public void setGUIArgs(ClientContext client) throws BadArgumentsException {
        try {
            this.key = Integer.parseInt(client.getParam());
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "integer");
        }
    }

    @Override
    public void buildRequest(Request request) throws CommandException {
        RemoveLowerKeyCommand command = new RemoveLowerKeyCommand(getCommandName());
        command.key = key;
        request.addCommand(command);
    }
}
