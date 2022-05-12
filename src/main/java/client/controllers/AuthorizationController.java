package client.controllers;

import client.Application;
import client.ClientConnector;
import client.RequestBuilder;
import general.Request;
import general.Response;
import general.element.UserProfile;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AuthorizationController {
    private Application application;
    public void setLogic(Application application) {
        this.application = application;
    }

    @FXML private Label authLabel;
    @FXML private Label loginLabel;
    @FXML private TextField loginTextField;
    @FXML private Label loginErrLabel;
    @FXML private Label passwordLabel;
    @FXML private PasswordField passwordField;
    @FXML private Label passwordErrLabel;
    @FXML private Button signInButton;
    @FXML private Label signInErrLabel;
    @FXML private Label goToRegLabel;
    @FXML private Hyperlink goToRegLink;

    @FXML
    private void goToRegPage(MouseEvent mouseEvent) {
        application.setScene(Application.AppScene.REGISTRATION_SCENE);
    }

    @FXML
    private void loginUser(MouseEvent mouseEvent) {
        try {
            UserProfile.checkLogin(loginTextField.getText());
            loginErrLabel.setText("");
        } catch (IllegalArgumentException e) {
            loginErrLabel.setText(e.getMessage());
        }
        try {
            UserProfile.checkPassword(passwordField.getText());
            passwordErrLabel.setText("");
        } catch (IllegalArgumentException e) {
            passwordErrLabel.setText(e.getMessage());
        }

        UserProfile userProfile;
        try {
            userProfile = new UserProfile(loginTextField.getText(),passwordField.getText());
        } catch (IllegalArgumentException e) {
            return;
        }
        RequestBuilder.setUserProfile(userProfile);
        try {
            Response response = ClientConnector.getInstance().sendToServer(
                    RequestBuilder.createNewRequest().setRequestType(Request.RequestType.LOGIN_USER).build()
            );
            if (response.getResponseType() == Response.ResponseType.LOGIN_FAILED) {
                signInErrLabel.setText(response.getMessage());
            } else if (response.getResponseType() == Response.ResponseType.LOGIN_SUCCESSFUL) {
                addLogoutHook();
                loginTextField.clear();
                passwordField.clear();
                signInErrLabel.setText("");
                application.setScene(Application.AppScene.MAIN_SCENE);
            } else {
                throw new IOException("Server has wrong logic: expected \"" +
                        Response.ResponseType.LOGIN_FAILED +
                        "\" or \"" +
                        Response.ResponseType.LOGIN_SUCCESSFUL +
                        "\", but not \"" +
                        response.getResponseType() +
                        "\"");
            }
        } catch (IOException | ClassNotFoundException e) {
            signInErrLabel.setText(e.getMessage());
        }
    }

    private void addLogoutHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ClientConnector.getInstance().sendRequest(
                        RequestBuilder.createNewRequest().setRequestType(Request.RequestType.LOGOUT_USER).build()
                );
            } catch (IOException e) {
                //ignore
            }
        }));
    }
}