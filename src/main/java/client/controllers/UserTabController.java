package client.controllers;

import client.Application;
import client.RequestBuilder;
import client.localization.LocalizedData;
import general.Request;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserTabController {
    private MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML private Label userLabel;
    @FXML private Label iconLabel;
    @FXML private Canvas iconCanvas;
    @FXML private Label usernameLabel;
    @FXML private TextField usernameTextField;
    @FXML private Label passwordLabel;
    @FXML private TextField passwordTextField;
    @FXML private Button signOutButton;
    @FXML private Label settingsLabel;
    @FXML private Label languageLabel;
    @FXML private ChoiceBox<LocalizedData.AvailableLocale> languageChoiceBox;
    @FXML private Button exitButton;

    @FXML
    private void initialize() {
        drawFace();

        ControllersContext.getInstance().getUserNameProperty().addListener((obs, o, n) -> {
            mainSceneController.getUserTab().textProperty().setValue(n);
            usernameTextField.setText(n);
        });

        languageChoiceBox.setItems(FXCollections.observableArrayList(LocalizedData.AvailableLocale.values()));
        languageChoiceBox.valueProperty().bindBidirectional(ControllersContext.getInstance().localizedData().availableLocaleProperty());
        ControllersContext.getInstance().localizedData().resourceBundleProperty().addListener((obs, o, n) -> localize(n));
    }

    private void localize(ResourceBundle resourceBundle) {
        userLabel.setText(resourceBundle.getString("User"));
        iconLabel.setText(resourceBundle.getString("Icon") + ":");
        usernameLabel.setText(resourceBundle.getString("Username") + ":");
        passwordLabel.setText(resourceBundle.getString("Password") + ":");
        signOutButton.setText(resourceBundle.getString("Sign out"));
        settingsLabel.setText(resourceBundle.getString("Settings"));
        languageLabel.setText(resourceBundle.getString("Language") + ":");
        exitButton.setText(resourceBundle.getString("Exit"));
    }

    private void drawFace() {
        // TODO: create random generating algo
        GraphicsContext context = iconCanvas.getGraphicsContext2D();
        context.strokeOval(20, 20, 110, 110); // FACE
        context.strokeOval(40, 50, 20, 20); // LEFT EYE
        context.fillOval(45, 55, 10, 10);
        context.strokeOval(90, 50, 20, 20); // RIGHT EYE
        context.fillOval(95, 55, 10, 10);
        context.strokeOval(50, 90, 50, 20); // MOUTH
        context.strokeRect(70, 70, 10, 10); // NOSE
    }

    @FXML
    private void showPasswordMouseClicked(MouseEvent mouseEvent) {
        showPassword();
    }

    private void showPassword() {
        // Haha, joke
        ControllersContext.getInstance().showErrorWindow(
                ControllersContext.getInstance().getString("FATAL"),
                ControllersContext.getInstance().getString("FATAL ERROR: don't peek")
        );
        // Sadness :(
    }

    @FXML
    private void signOutMouseClicked(MouseEvent mouseEvent) {
        signOut();
    }

    private void signOut() {
        Optional<ButtonType> answer = ControllersContext.getInstance().showConfirmWindow(
                ControllersContext.getInstance().getString("Are you sure?"),
                ControllersContext.getInstance().getString("Are you sure to sign out? (all unsaved data will be deleted)")
        );
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            try {
                ControllersContext.getInstance().sendRequest(
                        RequestBuilder.createNewRequest().setRequestType(Request.RequestType.LOGOUT_USER).build()
                );
                ControllersContext.getInstance().getApplication().setScene(Application.AppScene.AUTHORIZATION_SCENE);
            } catch (IOException e) {
                ControllersContext.getInstance().showUserError(e);
            }
        }
    }

    @FXML
    private void exitMouseClicked(MouseEvent mouseEvent) {
        exit();
    }

    private void exit() {
        Optional<ButtonType> answer = ControllersContext.getInstance().showConfirmWindow(
                ControllersContext.getInstance().getString("Are you sure?"),
                ControllersContext.getInstance().getString("Are you sure to exit? (all unsaved data will be deleted)")
        );
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
