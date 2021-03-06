package general.commands;

import general.ClientContext;
import general.ScriptContext;
import general.Request;
import general.element.Movie;
import general.ServerContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Command, which prints an elements whose "mpaaRating" is equal to the given
 * @see NameableCommand
 */
public class FilterByMpaaRatingCommand extends NameableCommand {
    private Movie.MpaaRating mpaaRating;

    @ParseCommand(name = "filter_by_mpaa_rating", type = CommandType.ONE_PARAM, paramName = "mpaa rating", example = "filter_by_mpaa_rating G")
    public FilterByMpaaRatingCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute(ServerContext server) throws CommandException {
        server.getResponse().addMessage("Found movies with \"" + mpaaRating + "\" mpaa rating:");
        List<Map.Entry<Integer,Movie>> found = server.getMovieCollection().entrySet().stream()
                .filter(entry -> entry.getValue().getMpaaRating() == mpaaRating)
                .collect(Collectors.toList());
        if (found.size() == 0) {
            server.getResponse().addMessage("*nothing*");
        } else {
            found.forEach(entry -> server.getResponse().addMessage(entry.getKey() + " - " + entry.getValue()));
        }
    }

    @Override
    public void setScriptArgs(ScriptContext script, String... args) throws BadArgumentsException {
        if (args.length != 1) {
            throw new BadArgumentsCountException(getCommandName(), 1);
        }
        try {
            mpaaRating = Movie.MpaaRating.valueOf(args[0]);
        } catch (IllegalArgumentException e) {
            throw new BadArgumentsFormatException(getCommandName(), "one of: " + Arrays.toString(Movie.MpaaRating.values()));
        }
    }

    @Override
    public void setGUIArgs(ClientContext client) throws BadArgumentsException {
        try {
            mpaaRating = Movie.MpaaRating.valueOf(client.getParam());
        } catch (IllegalArgumentException e) {
            throw new BadArgumentsException("value must be one of: " + Arrays.toString(Movie.MpaaRating.values()));
        }
    }

    @Override
    public void buildRequest(Request request) throws CommandException {
        FilterByMpaaRatingCommand command = new FilterByMpaaRatingCommand(getCommandName());
        command.mpaaRating = mpaaRating;
        request.addCommand(command);
    }
}
