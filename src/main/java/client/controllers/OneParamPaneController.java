package client.controllers;

import client.ClientExecutorImpl;
import general.commands.BadArgumentsException;
import general.commands.Command;
import general.element.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class OneParamPaneController {
    private ConsoleTabController consoleTabController;
    void setLogic(ConsoleTabController consoleTabController) {
        this.consoleTabController = consoleTabController;
    }
    private final ControllersContext context = ControllersContext.getInstance();

    @FXML private Label headerLabel;

    @FXML private Label oneParamLabel;
    @FXML private TextField oneParamTextField;
    @FXML private Label oneParamErrLabel;

    @FXML private Button returnButton;
    @FXML private Button confirmButton;

    @FXML
    private void initialize() {
        context.getCurrentCommandProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.getCommandType() == Command.CommandType.ONE_PARAM) {
                oneParamLabel.setText(context.getString("Enter " + newValue.getParamName()));
            }
        });
        context.localizer().resourceBundleProperty().addListener((obs, o, n) -> localize(n));
    }

    private void localize(ResourceBundle resourceBundle) {
        headerLabel.setText(resourceBundle.getString("Param filling page"));
        ClientExecutorImpl.CommandContainer command = context.getCurrentCommand();
        if (command != null && command.getCommandType() == Command.CommandType.ONE_PARAM) {
            oneParamLabel.setText(context.getString("Enter " + context.getCurrentCommand().getParamName()));
        }
        oneParamErrLabel.setText("");
        returnButton.setText(resourceBundle.getString("Return"));
        confirmButton.setText(resourceBundle.getString("Confirm"));
    }

    @FXML
    private void oneParamTextFieldKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            oneParamConfirm();
        } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            returnToTheMainPane();
        }
    }

    @FXML
    private void returnToTheMainPaneKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            returnToTheMainPane();
        }
    }

    @FXML
    private void returnToTheMainPaneMouseClicked(MouseEvent mouseEvent) {
        returnToTheMainPane();
    }

    private void returnToTheMainPane() {
        consoleTabController.returnToTheMainPane();
    }

    @FXML
    private void oneParamConfirmKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            oneParamConfirm();
        }
    }

    @FXML
    private void oneParamConfirmMouseClicked(MouseEvent mouseEvent) {
        oneParamConfirm();
    }

    private void oneParamConfirm() {
        getClientContext().setParam(oneParamTextField.getText()).setMovie(null).setMovieKey(null);
        try {
            ClientExecutorImpl.CommandContainer currentCommand = context.getCurrentCommand();
            currentCommand.getCommand().setGUIArgs(getClientContext());
            Optional<ButtonType> buttonType = context.showConfirmWindow(
                    context.getString("Are you sure?"),
                    context.getString("Are you sure to send command") + "\" " +
                            currentCommand.getCommandName() + "\"" + context.getString("with given argument?")
            );
            if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                consoleTabController.createRequestAndReceiveResponse();
                returnToTheMainPane();
            }
        } catch (BadArgumentsException e) {
            oneParamErrLabel.setText(context.getString(e.getMessage()));
        }
    }

    void setToRemove(Map.Entry<Integer, Movie> entry) {
        oneParamTextField.setText(entry.getKey().toString());
    }

    void clearPane() {
        oneParamTextField.clear();
        oneParamErrLabel.setText("");
    }

    private ConsoleTabController.ClientContextImpl getClientContext() {
        return consoleTabController.getClientContext();
    }
}
