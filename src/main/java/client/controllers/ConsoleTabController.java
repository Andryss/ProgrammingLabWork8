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


    private final ClientContextImpl clientContext = new ClientContextImpl();

    @FXML
    private void initialize() {
        oneParamPaneController.setLogic(this);
        movieKeyParamPaneController.setLogic(this);
        ControllersContext.getInstance().setConsoleTextFlow(consoleTextFlow);

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
            ControllersContext.getInstance().showWarningWindow("Choose command", "You should choose command to send!");
            return;
        }
        ControllersContext.getInstance().setCurrentCommand(ClientExecutor.getInstance().getCommandContainer(commandName));
        Command.CommandType currentCommandType = ControllersContext.getInstance().getCurrentCommand().getCommandType();
        if (currentCommandType == Command.CommandType.NO_PARAMS) {
            noParamCommand();
        } else if (currentCommandType == Command.CommandType.ONE_PARAM) {
            oneParamCommand();
        } else if (currentCommandType == Command.CommandType.MOVIE_KEY_PARAM) {
            movieKeyParamCommand();
        } else {
            ControllersContext.getInstance().showWarningWindow("Undefined command type", "Undefined type: " + currentCommandType);
        }
    }

    private void noParamCommand() {
        Optional<ButtonType> buttonType = ControllersContext.getInstance().showConfirmWindow(
                "Are you sure?", "Are you sure to send command \"" + ControllersContext.getInstance().getCurrentCommand().getCommandName() + "\"?"
        );

        if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {

            createRequestAndReceiveResponse();

        }
    }

    private void oneParamCommand() {
        selectOneParamPane();
    }

    private void movieKeyParamCommand() {
        selectMovieKeyPane();
    }

    void createRequestAndReceiveResponse() {
        try {
            ClientController.getInstance().println(ControllersContext.getInstance().getCurrentCommand().getCommandName());
            ClientExecutor.getInstance().executeCommand(ControllersContext.getInstance().getCurrentCommand().getCommandName());
            Response response = ControllersContext.getInstance().sendToServer(ClientExecutor.getInstance().getRequest());
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
