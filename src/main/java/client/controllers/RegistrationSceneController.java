package client.controllers;

import client.Application;
import client.ClientConnector;
import client.RequestBuilder;
import client.localization.LocalizedData;
import general.Request;
import general.Response;
import general.element.UserProfile;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ResourceBundle;

public class RegistrationSceneController {

    @FXML private ProgressBar regProgressBar;
    @FXML private Label regLabel;
    @FXML private Label loginLabel;
    @FXML private TextField loginTextField;
    @FXML private Label loginErrLabel;
    @FXML private Label passwordLabel;
    @FXML private PasswordField passwordField;
    @FXML private Label passwordErrLabel;
    @FXML private Button signUpButton;
    @FXML private Label successfulLabel;
    @FXML private Label goToAuthLabel;
    @FXML private Hyperlink goToAuthLink;
    @FXML private ChoiceBox<LocalizedData.AvailableLocale> languageChoiceBox;

    @FXML
    private void initialize() {
        languageChoiceBox.setItems(FXCollections.observableArrayList(LocalizedData.AvailableLocale.values()));
        languageChoiceBox.valueProperty().bindBidirectional(ControllersContext.getInstance().localizedData().availableLocaleProperty());
        ControllersContext.getInstance().localizedData().resourceBundleProperty().addListener((obs, o, n) -> localize(n));
    }

    private void localize(ResourceBundle resourceBundle) {
        regLabel.setText(resourceBundle.getString("Registration page"));
        loginLabel.setText(resourceBundle.getString("Login") + ":");
        loginTextField.setPromptText(resourceBundle.getString("New user login"));
        loginErrLabel.setText("");
        passwordLabel.setText(resourceBundle.getString("Password") + ":");
        passwordField.setPromptText(resourceBundle.getString("New user pass"));
        passwordErrLabel.setText("");
        signUpButton.setText(resourceBundle.getString("Sign up"));
        successfulLabel.setText("");
        goToAuthLabel.setText(resourceBundle.getString("Already have an account?"));
        goToAuthLink.setText(resourceBundle.getString("Go to the authorization page"));
    }

    @FXML
    private void goToAuthPageMouseClicked(MouseEvent mouseEvent) {
        goToAuthPage();
    }

    private void goToAuthPage() {
        ControllersContext.getInstance().setScene(Application.AppScene.AUTHORIZATION_SCENE);
        clear();
    }

    @FXML
    private void signUpUserMouseClicked(MouseEvent mouseEvent) {
        signUpUser();
    }

    private void signUpUser() {
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
            Response response = ControllersContext.getInstance().sendToServer(
                    RequestBuilder.createNewRequest().setRequestType(Request.RequestType.REGISTER_USER).build()
            );
            if (response.getResponseType() == Response.ResponseType.REGISTER_FAILED) {
                ControllersContext.getInstance().showErrorWindow(
                        ControllersContext.getInstance().getString("Registration failed"),
                        ControllersContext.getInstance().getString(response.getMessage())
                );
            } else if (response.getResponseType() == Response.ResponseType.REGISTER_SUCCESSFUL) {
                successfulLabel.setText(response.getMessage());
                regProgressBar.setVisible(true);
                Timeline authProgress = new Timeline(new KeyFrame(Duration.seconds(10), new KeyValue(regProgressBar.progressProperty(), 1, ControllersContext.getInstance().getProgressInterpolator())));
                authProgress.setOnFinished(e -> goToAuthPage());
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
        regProgressBar.setProgress(0);
        regProgressBar.setVisible(false);
        loginTextField.clear();
        loginErrLabel.setText("");
        passwordField.clear();
        passwordErrLabel.setText("");
        successfulLabel.setText("");
    }
}
