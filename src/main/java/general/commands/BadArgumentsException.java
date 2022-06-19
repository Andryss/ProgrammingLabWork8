package general.commands;

import client.ClientModuleHolder;

/**
 * Exception, when command arguments are incorrect
 */
public class BadArgumentsException extends CommandException {

    /**
     * Constructor with reason
     * @param command name of command
     * @param reason reason of exception
     * @see CommandException
     */
    public BadArgumentsException(String command, String reason) {
        super(command, reason);
    }

    public BadArgumentsException(String reason) {
        super(reason);
    }

    public static String getExample(String command) {
        return ClientModuleHolder.getInstance().getClientExecutorModule().getExample(command);
    }

    @Override
    public String getMessage() {
        if (getCommand() != null) {
            if (getReason() != null) {
                return "ERROR: bad arguments command \"" + getCommand() + "\" (" + getReason() + ")";
            } else {
                String example = getExample(getCommand());
                if (example != null) {
                    return "ERROR: bad arguments command \"" + getCommand() + "\" (example: \"" + example + "\")";
                } else {
                    return "ERROR: bad arguments command \"" + getCommand() + "\" (try another variations)";
                }
            }
        }
        return super.getMessage();
    }
}
