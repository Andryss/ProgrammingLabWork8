package general.commands;

import general.ScriptContext;
import general.ServerContext;

/**
 * Command, which ends the client program (without saving)
 * @see NameableCommand
 */
// TODO: do something with "exit" (delete maybe?)
public class ExitCommand extends NameableCommand {

    @ParseCommand(name = "exit", type = CommandType.NO_PARAMS, paramName = "", example = "exit")
    public ExitCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute(ServerContext server) throws CommandException {
        throw new CommandException(getCommandName(), "command can't be executed");
    }

    @Override
    public void setScriptArgs(ScriptContext script, String... args) throws BadArgumentsException {
        throw new BadArgumentsException(getCommandName(), "do you really want exit in script? (not today)");
    }
}
