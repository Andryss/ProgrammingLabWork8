package general.commands;

import general.ScriptContext;
import general.ServerContext;

/**
 * Command, which clears the collection
 * @see NameableCommand
 */
public class ClearCommand extends NameableCommand {

    @ParseCommand(name = "clear", type = CommandType.NO_PARAMS, paramName = "", example = "clear")
    public ClearCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute(ServerContext server) throws CommandException {
        try {
            server.removeAllMovies();
        } catch (IllegalAccessException e) {
            //ignore
        }
        server.getResponse().addMessage("All your elements deleted");
    }

    @Override
    public void setScriptArgs(ScriptContext script, String... args) throws BadArgumentsException {
        if (args.length > 0) {
            throw new BadArgumentsCountException(getCommandName());
        }
    }
}
