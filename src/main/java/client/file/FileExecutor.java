package client.file;

import client.ClientModuleHolder;
import general.ScriptContext;
import general.Request;
import general.commands.*;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * <p>FileExecutor implements (2) step in FileManager:</p>
 * <p>*** do same things as ClientExecutor but with some changes in ClientINFO (more sensitive to any Exceptions) ***</p>
 */
public class FileExecutor {
    @SuppressWarnings("unchecked")
    private final HashMap<String, Command> commandMap = (HashMap<String, Command>) ClientModuleHolder.getInstance().getClientExecutorModule().getCommandMap().clone();
    private final FileController controller;
    private final ScriptContext clientINFO;
    private final FileExecutor caller;
    private final Request request;
    private int commandNumber = 1;

    public FileExecutor(FileController fileController, FileExecutor caller, Request request) {
        this.caller = caller;
        this.controller = fileController;
        this.clientINFO = new ScriptContextImpl(this.controller, caller);
        this.request = request;
    }

    void parseCommand(String inputLine) throws CommandException {
        String[] operands = inputLine.trim().split("\\s+", 2);
        if (operands.length == 0) {
            throw new UndefinedCommandException("");
        } else if (operands.length == 1) {
            executeCommand(operands[0], new String[0]);
        } else {
            String[] args = operands[1].split("\\s+");
            executeCommand(operands[0], args);
        }
        commandNumber++;
    }

    private void executeCommand(String commandName, String[] args) throws CommandException {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new UndefinedCommandException(commandName);
        }
        try {
            command.setScriptArgs(clientINFO, args);
        } catch (NoSuchElementException e) {
            throw new CommandException(commandName, "File ended before command \"" + commandName + "\" completed");
        }
        try {
            command.buildRequest(request);
        } catch (CommandException e) {
            throw new CommandException(commandName, e.getReason());
        }
    }

    public FileExecutor getCaller() {
        return caller;
    }
    public String getFileName() {
        return controller.getFileName();
    }
    public int getCommandNumber() {
        return commandNumber;
    }
}
