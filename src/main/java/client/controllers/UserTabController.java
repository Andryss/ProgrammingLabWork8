package client.controllers;

import client.Application;
import client.RequestBuilder;
import general.Request;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class UserTabController {
    private MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML private TextField usernameTextField;

    @FXML
    private void initialize() {
        ControllersContext.getInstance().getUserNameProperty().addListener((obs, o, n) -> {
            mainSceneController.getUserTab().textProperty().setValue(n);
            usernameTextField.setText(n);
        });
    }

    @FXML
    private void signOutMouseClicked(MouseEvent mouseEvent) {
        signOut();
    }

    private void signOut() {
        Optional<ButtonType> answer = ControllersContext.getInstance().showConfirmWindow("Are you sure?", "Are you sure to sign out? (all unsaved data will be deleted)");
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            try {
                ControllersContext.getInstance().sendRequest(
                        RequestBuilder.createNewRequest().setRequestType(Request.RequestType.LOGOUT_USER).build()
                );
                ControllersContext.getInstance().getApplication().setScene(Application.AppScene.AUTHORIZATION_SCENE);
            } catch (IOException e) {
                ControllersContext.getInstance().showErrorWindow("Error", "Something went wrong");
            }
        }
    }

    @FXML
    private void exitMouseClicked(MouseEvent mouseEvent) {
        exit();
    }

    private void exit() {
        Optional<ButtonType> answer = ControllersContext.getInstance().showConfirmWindow("Are you sure?", "Are you sure to exit? (all unsaved data will be deleted)");
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
