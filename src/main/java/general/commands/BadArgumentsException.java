package general.commands;

import client.ClientExecutor;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Exception, when command arguments are incorrect
 */
public class BadArgumentsException extends CommandException {

    /**
     * Constructor without reason
     * @param command name of command
     */
    public BadArgumentsException(String command) {
        this(command, null);
    }

    /**
     * Constructor with reason
     * @param command name of command
     * @param reason reason of exception
     * @see CommandException
     */
    public BadArgumentsException(String command, String reason) {
        super(command, reason);
    }

    public static String getExample(String command) {
        return ClientExecutor.getInstance().getExample(command);
    }

    @Override
    public String getMessage() {
        if (getReason() != null) {
            return "ERROR: bad arguments command \"" + getCommand() + "\" (" + getReason() + ")";
        }
        String example = getExample(getCommand());
        if (example != null) {
            return "ERROR: bad arguments command \"" + getCommand() + "\" (example: \"" + example + "\")";
        } else {
            return "ERROR: bad arguments command \"" + getCommand() + "\" (try another variations)";
        }
    }
}
