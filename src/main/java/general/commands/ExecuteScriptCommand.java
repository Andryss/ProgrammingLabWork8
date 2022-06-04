package general.commands;

import general.ClientContext;
import general.ScriptContext;
import client.file.FileExecutor;
import client.file.FileManager;
import general.Request;
import general.ServerContext;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Command, which reads and executes script from file
 * @see NameableCommand
 * @see FileManager
 */
public class ExecuteScriptCommand extends NameableCommand {
    private File file;
    private FileExecutor caller;

    @ParseCommand(name = "execute_script", type = CommandType.ONE_PARAM, paramName = "script filename", example = "execute_script someScript")
    public ExecuteScriptCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute(ServerContext server) throws CommandException {
        throw new CommandException(getCommandName(), "\"" + getCommandName() + "\" can't be executed");
    }

    @Override
    public void setScriptArgs(ScriptContext script, String... args) throws BadArgumentsException {
        if (args.length != 1) {
            throw new BadArgumentsCountException(getCommandName(), 1);
        }
        file = new File(args[0]);
        if (!file.exists() || !file.isFile()) {
            throw new BadArgumentsException(getCommandName(), "script with name \"" + args[0] + "\" doesn't exists");
        }
        caller = script.getCaller();
        for (FileExecutor curCaller = caller; curCaller != null; curCaller = curCaller.getCaller()) {
            if (curCaller.getFileName().equals(args[0])) {
                throw new BadArgumentsException(getCommandName(), "recursion is not supported");
            }
        }
    }

    @Override
    public void setGUIArgs(ClientContext client) throws BadArgumentsException {
        file = new File(client.getParam());
        if (!file.exists() || !file.isFile()) {
            throw new BadArgumentsException("current script doesn't exists");
        }
    }

    @Override
    public void buildRequest(Request request) throws CommandException {
        try {
            FileManager fileManager = new FileManager(file, caller, request);
            fileManager.buildRequest();
        } catch (FileNotFoundException ignore) {
            //ignore
        }
    }
}
