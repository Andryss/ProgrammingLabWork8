package server;

import general.Request;
import general.commands.Command;
import general.commands.CommandException;
import general.element.Movie;
import general.element.UserProfile;
import general.Response;
import general.ServerContext;

import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ServerExecutor executing Request depending on RequestType and starting Thread which sending server Response
 *
 * Also, ServerExecutor monitor authorized users
 */
public class ServerExecutorImpl implements ServerExecutorModule {
    private static final ServerExecutorImpl instance = new ServerExecutorImpl();

    private ServerCollectionManagerModule collectionManagerModule;
    private ServerConnectorModule connectorModule;
    private ServerControllerModule controllerModule;
    private ServerHistoryManagerModule historyManagerModule;

    private ExecutorService executorService;     // Follow "Object pool" pattern
    private final List<UserProfile> authorizedUsers = new CopyOnWriteArrayList<>();

    private ServerExecutorImpl() {}

    static ServerExecutorImpl getInstance() {
        return instance;
    }

    @Override
    public void initialize() {
        ServerModuleHolder moduleHolder = ServerModuleHolder.getInstance();
        collectionManagerModule = moduleHolder.getCollectionManagerModule();
        connectorModule = moduleHolder.getConnectorModule();
        controllerModule = moduleHolder.getControllerModule();
        historyManagerModule = moduleHolder.getHistoryManagerModule();

        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void setProperties(Properties properties) throws Exception {
        // nothing
    }

    @Override
    public void close() {
        try {
            executorService.shutdown();
        } catch (Throwable e) {
            // ignore
        }
    }

    @Override
    public void executeRequest(SocketAddress client, Request request) {
        executorService.submit(() -> new ServerExecutorWorker(client, request).executeRequest());
    }

    @Override
    public void logoutUser(String userName) {
        authorizedUsers.stream().filter(u -> u.getName().equals(userName))
                .forEach(u -> {
                    authorizedUsers.remove(u);
                    historyManagerModule.deleteUser(u);
                });
    }

    @Override
    public void printUsers() {
        controllerModule.info("Authorized users: " + authorizedUsers);
    }

    @Override
    public void clearAuthorizedUsers() {
        authorizedUsers.clear();
    }


    private class ServerExecutorWorker {
        private final SocketAddress client;
        private final Request request;
        private ServerContext serverINFO;

        private ServerExecutorWorker(SocketAddress client, Request request) {
            this.client = client;
            this.request = request;
        }

        void executeRequest() {
            controllerModule.info("Request starts executing");

            if (request.getRequestType() == null) {
                controllerModule.info("Found null request type");

                Response response = ResponseBuilder.createNewResponse()
                        .setResponseType(Response.ResponseType.WRONG_REQUEST_FORMAT)
                        .addMessage("Request type is null")
                        .build();
                new Thread(() -> connectorModule.sendToClient(client, response), "SendingWFThread").start();

                return;
            }

            try {
                if (request.getRequestType() == Request.RequestType.CHECK_CONNECTION) {
                    checkConnectionRequest();
                } else if (request.getRequestType() == Request.RequestType.UPDATE_COLLECTION) {
                    updateCollectionRequest();
                } else if (request.getRequestType() == Request.RequestType.LOGIN_USER) {
                    loginUserRequest();
                } else if (request.getRequestType() == Request.RequestType.LOGOUT_USER) {
                    logoutUserRequest();
                } else if (request.getRequestType() == Request.RequestType.REGISTER_USER) {
                    registerUserRequest();
                } else if (request.getRequestType() == Request.RequestType.DELETE_USER) {
                    deleteUserRequest();
                } else if (request.getRequestType() == Request.RequestType.CHECK_ELEMENT) {
                    checkElementRequest();
                } else if (request.getRequestType() == Request.RequestType.EXECUTE_COMMAND) {
                    executeCommandRequest();
                } else {
                    controllerModule.info("Unexpected request type: " + request.getRequestType());
                }
            } catch (AssertionError e) {
                controllerModule.info("Found \"" + e.getMessage() + "\"");

                Response response = ResponseBuilder.createNewResponse()
                        .setResponseType(Response.ResponseType.WRONG_REQUEST_FORMAT)
                        .addMessage("Wrong request format")
                        .build();
                new Thread(() -> connectorModule.sendToClient(client, response), "SendingWFThread").start();
            } catch (NullPointerException e) {
                controllerModule.info("Found NullPointerException somewhere");
            }

            controllerModule.info("Request executed");

            printUsers();
            collectionManagerModule.printTables();
        }

        private void checkConnectionRequest() {
            // BAD-CODE ALERT
            // TimeUnit.SECONDS.sleep(2); // Emulate work
            new Thread(() -> connectorModule.sendToClient(client,
                    ResponseBuilder.createNewResponse()
                            .setResponseType(Response.ResponseType.CONNECTION_SUCCESSFUL)
                            .addMessage("Connection with server was successful")
                            .build()),
                    "SendingCCThread").start();
        }

        private void updateCollectionRequest() {
            new Thread(() -> connectorModule.sendToClient(client,
                    ResponseBuilder.createNewResponse()
                            .setResponseType(Response.ResponseType.COLLECTION_UPDATED_SUCCESSFUL)
                            .setHashtable(collectionManagerModule.getMovieCollection())
                            .build()),
                    "SendingUCThread").start();
        }

        private void loginUserRequest() {
            assert request.getUserProfile() != null : "User profile is null";
            assert request.getUserProfile().getName() != null : "Username is null";
            assert request.getUserProfile().getPassword() != null : "User password is null";

            Response response;
            if (collectionManagerModule.isUserPresented(request.getUserProfile())) {
                if (authorizedUsers.contains(request.getUserProfile())) {
                    response = ResponseBuilder.createNewResponse()
                            .setResponseType(Response.ResponseType.LOGIN_FAILED)
                            .addMessage("User already authorized (multi-session is not supported)")
                            .build();
                } else {
                    authorizedUsers.add(request.getUserProfile());
                    response = ResponseBuilder.createNewResponse()
                            .setResponseType(Response.ResponseType.LOGIN_SUCCESSFUL)
                            .addMessage("User successfully logged in")
                            .setHashtable(collectionManagerModule.getMovieCollection())
                            .build();
                    historyManagerModule.updateUser(request.getUserProfile());
                }
            } else {
                response = ResponseBuilder.createNewResponse()
                        .setResponseType(Response.ResponseType.LOGIN_FAILED)
                        .addMessage("Incorrect login or password")
                        .build();
            }
            new Thread(() -> connectorModule.sendToClient(client, response), "SendingLUThread").start();
        }

        private void logoutUserRequest() {
            assert request.getUserProfile() != null : "User profile is null";
            assert request.getUserProfile().getName() != null : "Username is null";
            assert request.getUserProfile().getPassword() != null : "User password is null";

            authorizedUsers.remove(request.getUserProfile());
            historyManagerModule.deleteUser(request.getUserProfile());
        }

        private void checkElementRequest() {
            assert request.getUserProfile() != null : "User profile is null";
            assert request.getUserProfile().getName() != null : "Username is null";
            assert request.getUserProfile().getPassword() != null : "User password is null";
            assert request.getCheckingIndex() != null : "Checking index is null";

            Response response;
            if (authorizedUsers.stream().noneMatch((u) -> u.equals(request.getUserProfile()))) {
                response = ResponseBuilder.createNewResponse()
                        .setResponseType(Response.ResponseType.CHECKING_FAILED)
                        .addMessage("User isn't logged in yet (or connection support time is out)")
                        .build();
            } else {
                Movie movie = collectionManagerModule.getMovie(request.getCheckingIndex());
                if (movie == null) {
                    if (collectionManagerModule.countElements(request.getUserProfile().getName()) >= collectionManagerModule.getUserElementsLimit()) {
                        response = ResponseBuilder.createNewResponse()
                                .setResponseType(Response.ResponseType.USER_LIMIT_EXCEEDED)
                                .addMessage("Your elements count limit exceeded")
                                .build();
                    } else {
                        response = ResponseBuilder.createNewResponse()
                                .setResponseType(Response.ResponseType.ELEMENT_NOT_PRESENTED)
                                .addMessage("Movie with given key doesn't exist")
                                .build();
                    }
                } else {
                    if (!movie.getOwner().equals(request.getUserProfile().getName())) {
                        response = ResponseBuilder.createNewResponse()
                                .setResponseType(Response.ResponseType.PERMISSION_DENIED)
                                .addMessage("You don't have permission to update movie with given key")
                                .build();
                    } else {
                        response = ResponseBuilder.createNewResponse()
                                .setResponseType(Response.ResponseType.CHECKING_SUCCESSFUL)
                                .addMessage("You have permission to update movie with given key")
                                .build();
                    }
                }
                response.setHashtable(collectionManagerModule.getMovieCollection());
                historyManagerModule.updateUser(request.getUserProfile());
            }
            new Thread(() -> connectorModule.sendToClient(client, response), "SendingCEThread").start();
        }

        private void registerUserRequest() {
            assert request.getUserProfile() != null : "User profile is null";
            assert request.getUserProfile().getName() != null : "Username is null";
            assert request.getUserProfile().getPassword() != null : "User password is null";

            Response response;
            long newUserID = collectionManagerModule.registerUser(request.getUserProfile());
            if (newUserID == -1) {
                response = ResponseBuilder.createNewResponse()
                        .setResponseType(Response.ResponseType.REGISTER_FAILED)
                        .addMessage("User is already registered")
                        .build();
            } else {
                response = ResponseBuilder.createNewResponse()
                        .setResponseType(Response.ResponseType.REGISTER_SUCCESSFUL)
                        .addMessage("New user successfully registered")
                        .build();
            }
            new Thread(() -> connectorModule.sendToClient(client, response), "SendingRUThread").start();
        }

        private void deleteUserRequest() {
            assert request.getUserProfile() != null : "User profile is null";
            assert request.getUserProfile().getName() != null : "Username is null";
            assert request.getUserProfile().getPassword() != null : "User password is null";

            Response response;
            UserProfile deletedProfile = collectionManagerModule.removeUser(request.getUserProfile());
            if (deletedProfile != null) {
                authorizedUsers.remove(request.getUserProfile());
                historyManagerModule.clearUserHistory(request.getUserProfile().getName());
                response = ResponseBuilder.createNewResponse()
                        .setResponseType(Response.ResponseType.DELETE_SUCCESSFUL)
                        .addMessage("User successfully deleted")
                        .build();
            } else {
                response = ResponseBuilder.createNewResponse()
                        .setResponseType(Response.ResponseType.DELETE_FAILED)
                        .addMessage("Can't delete current user")
                        .build();
            }
            new Thread(() -> connectorModule.sendToClient(client, response), "SendingDUThread").start();
        }

        private void executeCommandRequest() {
            assert request.getUserProfile() != null : "User profile is null";
            assert request.getUserProfile().getName() != null : "Username is null";
            assert request.getUserProfile().getPassword() != null : "User password is null";
            assert request.getCommandName() != null : "Command name is null";
            assert request.getCommandQueue() != null : "Command queue is null";

            Response response;
            if (authorizedUsers.stream().noneMatch((u) -> u.equals(request.getUserProfile()))) {
                response = ResponseBuilder.createNewResponse()
                        .setResponseType(Response.ResponseType.EXECUTION_FAILED)
                        .addMessage("User isn't logged in yet (or connection support time is out)")
                        .build();
            } else {
                response = ResponseBuilder.createNewResponse()
                        .setResponseType(Response.ResponseType.EXECUTION_SUCCESSFUL)
                        .build();
                historyManagerModule.updateUser(request.getUserProfile());
                serverINFO = new ServerContextImpl(request.getUserProfile(), response);

                Queue<Command> commandQueue = request.getCommandQueue();
                try {
                    response.addMessage("START: command \"" + request.getCommandName() + "\" start executing");
                    if (commandQueue.size() > 1) {
                        validateCommands();
                    }
                    for (Command command : commandQueue) {
                        command.execute(serverINFO);
                    }
                    response.addMessage("SUCCESS: command \"" + request.getCommandName() + "\" successfully completed");
                    historyManagerModule.addUserHistory(request.getUserProfile(), request.getCommandName());
                } catch (CommandException e) {
                    response = ResponseBuilder.createNewResponse()
                            .setResponseType(Response.ResponseType.EXECUTION_FAILED)
                            .addMessage(e.getMessage())
                            .build();
                }
                response.setHashtable(collectionManagerModule.getMovieCollection());
            }
            Response finalResponse = response;
            new Thread(() -> connectorModule.sendToClient(client, finalResponse), "SendingECThread").start();
        }

        private void validateCommands() throws CommandException {
            ServerContext copiedServerINFO = serverINFO.validationClone();

            for (Command command : request.getCommandQueue()) {
                try {
                    command.execute(copiedServerINFO);
                } catch (CommandException e) {
                    throw new CommandException(e.getCommand(), "Error in validation: " + e.getMessage());
                }
            }
        }
    }
}
