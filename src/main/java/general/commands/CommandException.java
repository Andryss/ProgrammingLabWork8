package general.commands;

/**
 * Class of all command exceptions with name of command and reason
 */
public class CommandException extends Exception {
    /**
     * Name of command, when the exception is
     */
    private final String command;
    /**
     * Reason of exception
     */
    private final String reason;

    /**
     * Constructor with name and reason
     * @param command name of command
     * @param reason string with reason of exception
     */
    public CommandException(String command, String reason) {
        assert command != null | reason != null : "You must set command or reason";
        this.command = command;
        this.reason = reason;
    }

    public CommandException(String reason) {
        this(null, reason);
    }

    public String getCommand() {
        return command;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String getMessage() {
        if (command != null) {
            if (reason != null) {
                return "ERROR: problem with command \"" + command + "\" (" + reason + ")";
            } else {
                return "ERROR: some problems with command \"" + command + "\"";
            }
        } else {
            return "ERROR: " + reason;
        }
    }
}
