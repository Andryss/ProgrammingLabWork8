package client.controllers;

import client.ClientConnector;
import client.ClientController;
import client.ClientExecutor;
import general.ClientContext;
import general.Response;
import general.commands.Command;
import general.commands.CommandException;
import general.element.Movie;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Optional;

public class ConsoleTabController {
    private MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML private OneParamPaneController oneParamPaneController;
    @FXML private MovieKeyParamPaneController movieKeyParamPaneController;

    @FXML private StackPane consoleStackPane;
    @FXML private BorderPane consoleMainPane;
    @FXML private ScrollPane oneParamPane;
    @FXML private ScrollPane movieKeyParamPane;

    @FXML private ScrollPane consoleScrollPane;
    @FXML private TextFlow consoleTextFlow;
    @FXML private ComboBox<String> commandComboBox;


    private String currentUserName;
    private ClientExecutor.CommandContainer currentCommand;
    ClientExecutor.CommandContainer getCurrentCommand() {
        return currentCommand;
    }
    private final ClientContextImpl clientContext = new ClientContextImpl();

    // TODO: add user name setting
    void setCurrentUserName (String currentUserName) {
        this.currentUserName = currentUserName;
        movieKeyParamPaneController.setCurrentUserName(currentUserName);
    }

    @FXML
    private void initialize() {
        oneParamPaneController.setLogic(this);
        movieKeyParamPaneController.setLogic(this);
        ClientController.getInstance().setTextFlow(consoleTextFlow);

        consoleScrollPane.vvalueProperty().bind(consoleTextFlow.heightProperty());
        commandComboBox.setItems(FXCollections.observableList(
                new ArrayList<>(ClientExecutor.getInstance().getCommandMap().keySet())
        ));

        selectMainPane();
    }

    @FXML
    private void sendCommandMouseClicked(MouseEvent mouseEvent) {
        sendCommand();
    }

    private void sendCommand() {
        String commandName = commandComboBox.getValue();
        if (!ClientExecutor.getInstance().hasCommand(commandName)) {
            mainSceneController.showWarningWindow("Choose command", "You should choose command to send!");
            return;
        }
        currentCommand = ClientExecutor.getInstance().getCommandContainer(commandName);
        Command.CommandType currentCommandType = currentCommand.getCommandType();
        if (currentCommandType == Command.CommandType.NO_PARAMS) {
            noParamCommand();
        } else if (currentCommandType == Command.CommandType.ONE_PARAM) {
            oneParamCommand();
        } else if (currentCommandType == Command.CommandType.MOVIE_KEY_PARAM) {
            movieKeyParamCommand();
        } else {
            mainSceneController.showWarningWindow("Undefined command type", "Undefined type: " + currentCommandType);
        }
    }

    private void noParamCommand() {
        Optional<ButtonType> buttonType = showConfirmWindow("Are you sure?", "Are you sure to send command \"" + currentCommand.getCommandName() + "\"?");

        if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {

            createRequestAndReceiveResponse();

        }
    }

    private void oneParamCommand() {
        oneParamPaneController.prepareToSelect();
        selectOneParamPane();
    }

    private void movieKeyParamCommand() {
        movieKeyParamPaneController.prepareToSelect();
        selectMovieKeyPane();
    }

    void createRequestAndReceiveResponse() {
        try {
            ClientController.getInstance().println(currentCommand.getCommandName());
            ClientExecutor.getInstance().executeCommand(currentCommand.getCommandName());
            Response response = ClientConnector.getInstance().sendToServer(ClientExecutor.getInstance().getRequest());
            mainSceneController.updateMovieTable(response.getHashtable());
            if (response.getResponseType() == Response.ResponseType.EXECUTION_SUCCESSFUL) {
                ClientController.getInstance().println(response.getMessage());
            } else if (response.getResponseType() == Response.ResponseType.EXECUTION_FAILED) {
                ClientController.getInstance().printlnErr(response.getMessage());
            } else {
                ClientController.getInstance().printlnErr("Server has wrong logic: unexpected \"" +
                        response.getResponseType() +
                        "\"");
            }
        } catch (SocketTimeoutException e) {
            ClientController.getInstance().printlnErr("Server isn't responding (try again later)");
        } catch (IOException | ClassNotFoundException | CommandException e) {
            ClientController.getInstance().printlnErr(e.getMessage());
        }
    }

    Optional<ButtonType> showConfirmWindow(String title, String contextText) {
        return mainSceneController.showConfirmWindow(title, contextText);
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
        consoleStackPane.getChildren().remove(e);
        consoleStackPane.getChildren().add(e);
        consoleStackPane.getChildren().forEach(n -> n.setDisable(true));
        e.setDisable(false);
    }

    public TextFlow getConsoleTextArea() {
        return consoleTextFlow;
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
