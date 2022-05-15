package general.commands;

import general.Request;
import general.Response;
import general.ServerContext;

/**
 * Command, which replaces an element by key if the new value is greater than the old one
 * @see NameableCommand
 */
public class ReplaceIfGreaterCommand extends ElementCommand {

    @ParseCommand(name = "replace_if_greater", type = CommandType.MOVIE_KEY_PARAM, paramName = "key to replace", example = "replace_if_greater 600500")
    public ReplaceIfGreaterCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute(ServerContext server) throws CommandException {
        if (readMovie.compareTo(server.getMovieCollection().get(key)) > 0) {
            try {
                server.updateMovie(key, readMovie);
            } catch (IllegalAccessException e) {
                throw new CommandException(getCommandName(), e.getMessage());
            }
            server.getResponse().addMessage("Element greater than the old one has been inserted");
        } else {
            server.getResponse().addMessage("Nothing was happened");
        }
    }

    @Override
    public void checkElement(Response response) throws BadArgumentsException {
        if (response.getResponseType() != Response.ResponseType.CHECKING_SUCCESSFUL) {
            throw new BadArgumentsException(getCommandName(), response.getMessage());
        }
    }

    @Override
    public void buildRequest(Request request) throws CommandException {
        ReplaceIfGreaterCommand command = new ReplaceIfGreaterCommand(getCommandName());
        command.key = key; command.readMovie = readMovie;
        request.addCommand(command);
    }
}
