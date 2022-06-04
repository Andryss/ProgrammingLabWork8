package client.controllers;

import client.ClientExecutor;
import general.commands.BadArgumentsException;
import general.commands.Command;
import general.element.Movie;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class OneParamPaneController {
    private ConsoleTabController consoleTabController;
    void setLogic(ConsoleTabController consoleTabController) {
        this.consoleTabController = consoleTabController;
    }

    @FXML private Label oneParamLabel;
    @FXML private TextField oneParamTextField;
    @FXML private Label oneParamErrLabel;
    @FXML private Button returnButton;
    @FXML private Button confirmButton;

    @FXML
    private void initialize() {
        ControllersContext.getInstance().getCurrentCommandProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.getCommandType() == Command.CommandType.ONE_PARAM) {
                oneParamLabel.setText(ControllersContext.getInstance().getString("Enter " + newValue.getParamName()));
            }
        });
        ControllersContext.getInstance().localizedData().resourceBundleProperty().addListener((obs, o, n) -> localize(n));
    }

    private void localize(ResourceBundle resourceBundle) {
        ClientExecutor.CommandContainer command = ControllersContext.getInstance().getCurrentCommand();
        if (command != null && command.getCommandType() == Command.CommandType.ONE_PARAM) {
            oneParamLabel.setText(ControllersContext.getInstance().getString("Enter " + ControllersContext.getInstance().getCurrentCommand().getParamName()));
        }
        oneParamErrLabel.setText("");
        returnButton.setText(resourceBundle.getString("Return"));
        confirmButton.setText(resourceBundle.getString("Confirm"));
    }

    @FXML
    private void returnToTheMainPaneMouseClicked(MouseEvent mouseEvent) {
        returnToTheMainPane();
    }

    private void returnToTheMainPane() {
        consoleTabController.returnToTheMainPane();
    }

    @FXML
    private void oneParamConfirmMouseClicked(MouseEvent mouseEvent) {
        oneParamConfirm();
    }

    private void oneParamConfirm() {
        getClientContext().setParam(oneParamTextField.getText()).setMovie(null).setMovieKey(null);
        try {
            ClientExecutor.CommandContainer currentCommand = ControllersContext.getInstance().getCurrentCommand();
            currentCommand.getCommand().setGUIArgs(getClientContext());
            Optional<ButtonType> buttonType = ControllersContext.getInstance().showConfirmWindow(
                    ControllersContext.getInstance().getString("Are you sure?"),
                    ControllersContext.getInstance().getString("Are you sure to send command") + "\" " +
                            currentCommand.getCommandName() + "\"" + ControllersContext.getInstance().getString("with given argument?")
            );
            if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                consoleTabController.createRequestAndReceiveResponse();
                returnToTheMainPane();
            }
        } catch (BadArgumentsException e) {
            oneParamErrLabel.setText(ControllersContext.getInstance().getString(e.getMessage()));
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
