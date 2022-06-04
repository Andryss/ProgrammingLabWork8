package general.commands;

import general.ClientContext;
import general.ScriptContext;
import general.Request;
import general.ServerContext;

/**
 * Command, which prints the number of elements whose "length" less than the given
 * @see NameableCommand
 */
public class CountLessThenLengthCommand extends NameableCommand {

    private int length;

    @ParseCommand(name = "count_less_than_length", type = CommandType.ONE_PARAM, paramName = "length", example = "count_less_than_length 90")
    public CountLessThenLengthCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute(ServerContext server) throws CommandException {
        server.getResponse().addMessage("Found " +
                server.getMovieCollection().values().stream()
                        .filter(movie -> movie.getLength() < length)
                        .count()
                + " movies with length less than " + length);
    }

    @Override
    public void setScriptArgs(ScriptContext script, String... args) throws BadArgumentsException {
        if (args.length != 1) {
            throw new BadArgumentsCountException(getCommandName(), 1);
        }
        try {
            length = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "integer");
        }
    }

    @Override
    public void setGUIArgs(ClientContext client) throws BadArgumentsException {
        try {
            length = Integer.parseInt(client.getParam());
        } catch (NumberFormatException e) {
            throw new BadArgumentsException("value must be integer");
        }
    }

    @Override
    public void buildRequest(Request request) throws CommandException {
        CountLessThenLengthCommand command = new CountLessThenLengthCommand(getCommandName());
        command.length = length;
        request.addCommand(command);
    }
}
