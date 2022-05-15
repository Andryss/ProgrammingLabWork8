package general.commands;

import general.ScriptContext;
import general.ServerContext;

import java.util.LinkedList;

/**
 * Command, which prints last 13 commands (without arguments)
 * @see NameableCommand
 */
public class HistoryCommand extends NameableCommand {

    @ParseCommand(name = "history", type = CommandType.NO_PARAMS, paramName = "", example = "history")
    public HistoryCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute(ServerContext server) {
        LinkedList<String> history = server.getUserHistory();
        for (int i = Math.max(0, history.size() - 13); i < history.size(); i++) {
            server.getResponse().addMessage(history.get(i));
        }
    }

    @Override
    public void setScriptArgs(ScriptContext script, String... args) throws BadArgumentsException {
        if (args.length > 0) {
            throw new BadArgumentsCountException(getCommandName());
        }
    }
}
