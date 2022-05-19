package client.controllers;

import client.ClientExecutor;
import general.commands.BadArgumentsException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

public class OneParamPaneController {
    private ConsoleTabController consoleTabController;
    void setLogic(ConsoleTabController consoleTabController) {
        this.consoleTabController = consoleTabController;
    }

    @FXML private Label oneParamLabel;
    @FXML private TextField oneParamTextField;
    @FXML private Label oneParamErrLabel;
    @FXML private Button oneParamConfirmButton;

    void prepareToSelect() {
        oneParamLabel.setText("Enter " + getCurrentCommand().getParamName() + ":");
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
            getCurrentCommand().getCommand().setGUIArgs(getClientContext());
            Optional<ButtonType> buttonType = showConfirmWindow("Are you sure?", "Are you sure to send command \"" + getCurrentCommand().getCommandName() + "\" with given argument?");
            if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                createRequestAndReceiveResponse();
                returnToTheMainPane();
            }
        } catch (BadArgumentsException e) {
            oneParamErrLabel.setText(e.getMessage());
        }
    }

    void clearPane() {
        oneParamTextField.clear();
        oneParamErrLabel.setText("");
    }

    private ClientExecutor.CommandContainer getCurrentCommand() {
        return consoleTabController.getCurrentCommand();
    }
    private ConsoleTabController.ClientContextImpl getClientContext() {
        return consoleTabController.getClientContext();
    }
    private Optional<ButtonType> showConfirmWindow(String title, String contextText) {
        return consoleTabController.showConfirmWindow(title, contextText);
    }
    private void createRequestAndReceiveResponse() {
        consoleTabController.createRequestAndReceiveResponse();
    }
}
