package client.controllers;

import client.Application;
import client.RequestBuilder;
import client.localization.LocalizedData;
import general.Request;
import general.Response;
import general.element.UserProfile;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ResourceBundle;

public class AuthorizationSceneController {

    @FXML private ProgressBar authProgressBar;
    @FXML private Label authLabel;
    @FXML private Label loginLabel;
    @FXML private TextField loginTextField;
    @FXML private Label loginErrLabel;
    @FXML private Label passwordLabel;
    @FXML private PasswordField passwordField;
    @FXML private Label passwordErrLabel;
    @FXML private Button signInButton;
    @FXML private Label successfulLabel;
    @FXML private Label goToRegLabel;
    @FXML private Hyperlink goToRegLink;
    @FXML private ChoiceBox<LocalizedData.AvailableLocale> languageChoiceBox;

    @FXML
    private void initialize() {
        languageChoiceBox.setItems(FXCollections.observableArrayList(LocalizedData.AvailableLocale.values()));
        languageChoiceBox.valueProperty().bindBidirectional(ControllersContext.getInstance().localizedData().availableLocaleProperty());
        ControllersContext.getInstance().localizedData().resourceBundleProperty().addListener((obs, o, n) -> localize(n));
    }

    private void localize(ResourceBundle resourceBundle) {
        authLabel.setText(resourceBundle.getString("Authorization page"));
        loginLabel.setText(resourceBundle.getString("Login") + ":");
        loginTextField.setPromptText(resourceBundle.getString("Type here your login"));
        loginErrLabel.setText("");
        passwordLabel.setText(resourceBundle.getString("Password") + ":");
        passwordField.setPromptText(resourceBundle.getString("Type here your pass"));
        passwordErrLabel.setText("");
        signInButton.setText(resourceBundle.getString("Sign in"));
        successfulLabel.setText("");
        goToRegLabel.setText(resourceBundle.getString("Don't have an account yet?"));
        goToRegLink.setText(resourceBundle.getString("Go to the registration page"));
    }

    @FXML
    private void goToRegPageMouseClicked(MouseEvent mouseEvent) {
        goToRegPage();
    }

    private void goToRegPage() {
        clear();
        ControllersContext.getInstance().setScene(Application.AppScene.REGISTRATION_SCENE);
    }

    private void goToMainPage() {
        clear();
        ControllersContext.getInstance().setScene(Application.AppScene.MAIN_SCENE);
    }

    @FXML
    private void signInUserKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            signInUser();
        }
    }

    @FXML
    private void signInUserMouseClicked(MouseEvent mouseEvent) {
        signInUser();
    }

    private void signInUser() {
        try {
            UserProfile.checkLogin(loginTextField.getText());
            loginErrLabel.setText("");
        } catch (IllegalArgumentException e) {
            loginErrLabel.setText(ControllersContext.getInstance().getString(e.getMessage()));
        }
        try {
            UserProfile.checkPassword(passwordField.getText());
            passwordErrLabel.setText("");
        } catch (IllegalArgumentException e) {
            passwordErrLabel.setText(ControllersContext.getInstance().getString(e.getMessage()));
        }

        UserProfile userProfile;
        try {
            userProfile = new UserProfile(loginTextField.getText(),passwordField.getText());
        } catch (IllegalArgumentException e) {
            return;
        }
        RequestBuilder.setUserProfile(userProfile);
        try {
            Response response = ControllersContext.getInstance().sendToServer(
                    RequestBuilder.createNewRequest().setRequestType(Request.RequestType.LOGIN_USER).build()
            );
            if (response.getResponseType() == Response.ResponseType.LOGIN_FAILED) {
                ControllersContext.getInstance().showErrorWindow(
                        ControllersContext.getInstance().getString("Login failed"),
                        ControllersContext.getInstance().getString(response.getMessage())
                );
            } else if (response.getResponseType() == Response.ResponseType.LOGIN_SUCCESSFUL) {
                addLogoutHook();
                ControllersContext.getInstance().setUserName(userProfile.getName());
                successfulLabel.setText(ControllersContext.getInstance().getString(response.getMessage()));
                authProgressBar.setVisible(true);
                Timeline authProgress = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(authProgressBar.progressProperty(), 1, ControllersContext.getInstance().getProgressInterpolator())));
                authProgress.setOnFinished(e -> goToMainPage());
                authProgress.play();
            } else {
                throw new IOException("Server has wrong logic: unexpected \"" +
                        response.getResponseType() +
                        "\"");
            }
        } catch (IOException | ClassNotFoundException e) {
            ControllersContext.getInstance().showUserError(e);
        }
    }

    private void clear() {
        authProgressBar.setProgress(0);
        authProgressBar.setVisible(false);
        loginTextField.clear();
        loginErrLabel.setText("");
        passwordField.clear();
        passwordErrLabel.setText("");
        successfulLabel.setText("");
    }

    private void addLogoutHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ControllersContext.getInstance().sendRequest(
                        RequestBuilder.createNewRequest().setRequestType(Request.RequestType.LOGOUT_USER).build()
                );
            } catch (IOException e) {
                //ignore
            }
        }));
    }
}