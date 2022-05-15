package general.commands;

import general.ScriptContext;
import general.Request;
import general.ServerContext;
import general.ClientContext;

import java.io.Serializable;

/**
 * <p>interface Command represents all required command methods</p>
 * <p>Follow "Command" pattern</p>
 */
public interface Command extends Serializable {
    /**
     * Makes command executing
     */
    void execute(ServerContext server) throws CommandException;

    /**
     * Validate and set arguments for command
     * @param args String array with arguments
     * @throws BadArgumentsException if arguments are incorrect
     */
    void setScriptArgs(ScriptContext script, String... args) throws BadArgumentsException;

    /**
     * Set arguments from gui input (if command type != no_params)
     * @param client Object fot getting arguments
     */
    default void setGUIArgs(ClientContext client) throws BadArgumentsException {
        throw new BadArgumentsException("command", "method \"setGUIArgs\" is not overridden");
    }

    /**
     * Build Request depending on command type
     * @throws CommandException if something wrong with building Request
     */
    default void buildRequest(Request request) throws CommandException {
        request.addCommand(this);
    }

    enum CommandType {
        NO_PARAMS,
        ONE_PARAM,
        MOVIE_KEY_PARAM
    }
}
