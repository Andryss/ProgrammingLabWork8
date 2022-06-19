package client.controllers;

import client.ClientControllerModule;
import client.ClientExecutorImpl;
import client.ClientExecutorModule;
import general.ClientContext;
import general.Response;
import general.commands.Command;
import general.commands.CommandException;
import general.element.Movie;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class ConsoleTabController {
    private MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
    private final ControllersContext context = ControllersContext.getInstance();
    private final ClientControllerModule controllerModule = context.getControllerModule();
    private final ClientExecutorModule executorModule = context.getExecutorModule();

    @FXML private OneParamPaneController oneParamPaneController;
    @FXML private MovieKeyParamPaneController movieKeyParamPaneController;

    @FXML private StackPane consoleStackPane;
    @FXML private AnchorPane consoleMainPane;
    @FXML private ScrollPane oneParamPane;
    @FXML private ScrollPane movieKeyParamPane;

    @FXML private ScrollPane consoleScrollPane;
    @FXML private TextFlow consoleTextFlow;
    @FXML private MenuItem saveHistoryMenuItem;
    @FXML private ComboBox<String> commandComboBox;
    @FXML private Button sendCommandButton;


    private final ClientContextImpl clientContext = new ClientContextImpl();

    @FXML
    private void initialize() {
        setControllersLogic();
        context.setConsoleTextFlow(consoleTextFlow);

        consoleScrollPane.vvalueProperty().bind(consoleTextFlow.heightProperty());
        saveHistoryMenuItem.setOnAction(e -> saveConsoleHistory());
        commandComboBox.setItems(FXCollections.observableList(
                new ArrayList<>(executorModule.getCommandMap().keySet())
        ));
        context.localizer().resourceBundleProperty().addListener((obs, o, n) -> localize(n));

        selectMainPane();
    }

    private void setControllersLogic() {
        oneParamPaneController.setLogic(this);
        movieKeyParamPaneController.setLogic(this);
    }

    private void localize(ResourceBundle resourceBundle) {
        saveHistoryMenuItem.setText(resourceBundle.getString("Save history to file"));
        commandComboBox.setPromptText(resourceBundle.getString("Choose the command you want to send"));
        sendCommandButton.setText(resourceBundle.getString("Send"));
    }

    private void saveConsoleHistory() {
        File file = context.chooseFile();
        if (file != null) {
            try {
                controllerModule.saveHistoryToFile(file);
            } catch (IOException e) {
                context.showUserError(e);
            }
        }
    }

    @FXML
    private void chooseCommandKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendCommand();
        }
    }

    @FXML
    private void sendCommandKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendCommand();
        }
    }

    @FXML
    private void sendCommandMouseClicked(MouseEvent mouseEvent) {
        sendCommand();
    }

    private void sendCommand() {
        String commandName = commandComboBox.getValue();
        if (!executorModule.hasCommand(commandName)) {
            context.showWarningWindow(
                    context.getString("Choose command"),
                    context.getString("You should choose command to send!")
            );
            return;
        }
        context.setCurrentCommand(executorModule.getCommandContainer(commandName));
        Command.CommandType currentCommandType = context.getCurrentCommand().getCommandType();
        if (currentCommandType == Command.CommandType.NO_PARAMS) {
            noParamCommand();
        } else if (currentCommandType == Command.CommandType.ONE_PARAM) {
            oneParamCommand();
        } else if (currentCommandType == Command.CommandType.MOVIE_KEY_PARAM) {
            movieKeyParamCommand();
        } else {
            context.showUserError(new RuntimeException("Undefined command type: " + currentCommandType));
        }
    }

    private void noParamCommand() {
        Optional<ButtonType> buttonType = context.showConfirmWindow(
                context.getString("Are you sure?"),
                context.getString("Are you sure to send command") + " \"" + context.getCurrentCommand().getCommandName() + "\"?"
        );

        if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {

            createRequestAndReceiveResponse();

        }
    }

    private void oneParamCommand() {
        selectOneParamPane();
    }

    void setToRemove(Map.Entry<Integer, Movie> entry) {
        ClientExecutorImpl.CommandContainer commandContainer = executorModule.getCommandContainer("remove_key");
        if (commandContainer == null) {
            context.showUserError(new RuntimeException("Cannot find \"remove_key\" command :("));
        } else {
            context.setCurrentCommand(commandContainer);
            Command.CommandType currentCommandType = commandContainer.getCommandType();
            if (currentCommandType == Command.CommandType.ONE_PARAM) {
                oneParamPaneController.setToRemove(entry);
                oneParamCommand();
            } else {
                context.showUserError(new RuntimeException("Undefined \"remove_key\" command type: " + currentCommandType));
            }
        }
    }

    private void movieKeyParamCommand() {
        selectMovieKeyPane();
    }

    void setToUpdate(Map.Entry<Integer, Movie> entry) {
        ClientExecutorImpl.CommandContainer commandContainer = executorModule.getCommandContainer("update");
        if (commandContainer == null) {
            context.showUserError(new RuntimeException("Cannot find \"update\" command :("));
        } else {
            context.setCurrentCommand(commandContainer);
            Command.CommandType currentCommandType = commandContainer.getCommandType();
            if (currentCommandType == Command.CommandType.MOVIE_KEY_PARAM) {
                movieKeyParamPaneController.setToUpdate(entry);
                movieKeyParamCommand();
            } else {
                context.showUserError(new RuntimeException("Undefined \"update\" command type: " + currentCommandType));
            }
        }
    }

    void createRequestAndReceiveResponse() {
        try {
            controllerModule.printlnCommand(context.getCurrentCommand().getCommandName());
            executorModule.executeCommand(context.getCurrentCommand().getCommandName());
            Response response = context.sendToServer(executorModule.getRequest());
            if (response.getResponseType() == Response.ResponseType.EXECUTION_SUCCESSFUL) {
                controllerModule.println(response.getMessage());
            } else if (response.getResponseType() == Response.ResponseType.EXECUTION_FAILED) {
                controllerModule.printlnErr(response.getMessage());
            } else {
                controllerModule.printlnErr("Server has wrong logic: unexpected \"" +
                        response.getResponseType() +
                        "\"");
            }
        } catch (SocketTimeoutException e) {
            controllerModule.printlnErr("Server isn't responding (try again later)");
        } catch (CommandException e) {
            controllerModule.printlnErr(e.getMessage());
        } catch (Exception e) {
            context.showUserError(e);
        }
    }

    void returnToTheMainPane() {
        oneParamPaneController.clearPane();
        movieKeyParamPaneController.clearPane();
        selectMainPane();
    }


    private void selectMainPane() {
        popupPane(consoleMainPane);
    }

    private void selectOneParamPane() {
        popupPane(oneParamPane);
    }

    private void selectMovieKeyPane() {
        popupPane(movieKeyParamPane);
    }

    private void popupPane(Node e) {
        consoleStackPane.getChildren().forEach(n -> n.setVisible(n == e));
    }

    public ClientContextImpl getClientContext() {
        return clientContext;
    }

    static class ClientContextImpl implements ClientContext {
        private String param;
        private Integer movieKey;
        private Movie movie;

        public ClientContextImpl() {}

        ClientContextImpl setParam(String param) {
            this.param = param;
            return this;
        }
        ClientContextImpl setMovieKey(Integer movieKey) {
            this.movieKey = movieKey;
            return this;
        }
        ClientContextImpl setMovie(Movie movie) {
            this.movie = movie;
            return this;
        }

        @Override
        public String getParam() {
            return param;
        }
        @Override
        public Integer getMovieKey() {
            return movieKey;
        }
        @Override
        public Movie getMovie() {
            return movie;
        }
    }
}
