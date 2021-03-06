package general.commands;

import general.Request;
import general.Response;
import general.ServerContext;

/**
 * Command, which updates an element with given id
 * @see NameableCommand
 */
public class UpdateCommand extends ElementCommand {

    @ParseCommand(name = "update", type = CommandType.MOVIE_KEY_PARAM, paramName = "key to update", example = "update 30")
    public UpdateCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute(ServerContext server) throws CommandException {
        try {
            server.updateMovie(key, readMovie);
        } catch (IllegalAccessException e) {
            throw new CommandException(getCommandName(), e.getMessage());
        }
        server.getResponse().addMessage("The movie has been updated");
    }

    @Override
    public void checkElement(Response response) throws BadArgumentsException {
        if (response.getResponseType() == Response.ResponseType.CHECKING_FAILED ||
                response.getResponseType() == Response.ResponseType.ELEMENT_NOT_PRESENTED ||
                response.getResponseType() == Response.ResponseType.PERMISSION_DENIED) {
            throw new BadArgumentsException(response.getMessage());
        }
        if (response.getResponseType() != Response.ResponseType.CHECKING_SUCCESSFUL) {
            throw new BadArgumentsException("You can't update movie with given key");
        }
    }

    @Override
    public void buildRequest(Request request) throws CommandException {
        UpdateCommand command = new UpdateCommand(getCommandName());
        command.key = key; command.readMovie = readMovie;
        request.addCommand(command);
    }
}
