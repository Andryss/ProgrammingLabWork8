package client.controllers;

import client.Application;
import client.RequestBuilder;
import client.localization.Localizer;
import general.Request;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserTabController {
    private MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
    private final ControllersContext context = ControllersContext.getInstance();

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
    @FXML private ChoiceBox<Localizer.AvailableLocale> languageChoiceBox;
    @FXML private Label themeLabel;
    @FXML private ChoiceBox<Application.AppStyle> themeChoiceBox;
    @FXML private Button exitButton;

    @FXML
    private void initialize() {
        context.getUserNameProperty().addListener((obs, o, n) -> {
            mainSceneController.getUserTab().textProperty().setValue(n);
            usernameTextField.setText(n);
            drawFace();
        });

        languageChoiceBox.setItems(FXCollections.observableArrayList(Localizer.AvailableLocale.values()));
        languageChoiceBox.valueProperty().bindBidirectional(context.localizer().availableLocaleProperty());
        context.localizer().resourceBundleProperty().addListener((obs, o, n) -> localize(n));
        
        themeChoiceBox.setItems(FXCollections.observableArrayList(Application.AppStyle.values()));
        themeChoiceBox.setValue(Application.AppStyle.DEFAULT);
        themeChoiceBox.valueProperty().addListener((obs, o, n) -> context.setSccStyle(n));
    }

    private void localize(ResourceBundle resourceBundle) {
        userLabel.setText(resourceBundle.getString("User"));
        iconLabel.setText(resourceBundle.getString("Icon") + ":");
        usernameLabel.setText(resourceBundle.getString("Username") + ":");
        passwordLabel.setText(resourceBundle.getString("Password") + ":");
        signOutButton.setText(resourceBundle.getString("Sign out"));
        settingsLabel.setText(resourceBundle.getString("Settings"));
        languageLabel.setText(resourceBundle.getString("Language") + ":");
        themeLabel.setText(resourceBundle.getString("Theme") + ":");
        exitButton.setText(resourceBundle.getString("Exit"));
    }

    @FXML
    private void showPasswordMouseClicked(MouseEvent mouseEvent) {
        showPassword();
    }

    private void showPassword() {
        // Haha, joke
        context.showErrorWindow(
                context.getString("FATAL"),
                context.getString("FATAL ERROR: don't peek")
        );
        // Sadness :(
    }

    @FXML
    private void signOutMouseClicked(MouseEvent mouseEvent) {
        signOut();
    }

    private void signOut() {
        Optional<ButtonType> answer = context.showConfirmWindow(
                context.getString("Are you sure?"),
                context.getString("Are you sure to sign out? (all unsaved data will be deleted)")
        );
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            try {
                context.sendRequest(
                        RequestBuilder.createNewRequest().setRequestType(Request.RequestType.LOGOUT_USER).build()
                );
                context.getApplication().setScene(Application.AppScene.AUTHORIZATION_SCENE);
            } catch (IOException e) {
                context.showUserError(e);
            }
        }
    }

    @FXML
    private void exitMouseClicked(MouseEvent mouseEvent) {
        exit();
    }

    private void exit() {
        Optional<ButtonType> answer = context.showConfirmWindow(
                context.getString("Are you sure?"),
                context.getString("Are you sure to exit? (all unsaved data will be deleted)")
        );
        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void refreshMouseClicked(MouseEvent mouseEvent) {
        drawFace();
    }

    /**
     * tg: @ray_1024
     * @author Ray_1024
     */
    private void drawFace() {
        GraphicsContext context = iconCanvas.getGraphicsContext2D();
        context.clearRect(0, 0, iconCanvas.getWidth(), iconCanvas.getHeight());

        // DRAW FACE
        switch ((int) (Math.random() * 5.0d)) {
            case 0:
                context.strokeOval(20, 20, 110, 110);
                break;
            case 1:
                context.strokeRect(20, 20, 110, 110);
                break;
            case 2:
                context.strokePolygon(new double[]{47, 103, 130, 20}, new double[]{20, 20, 130, 130}, 4);
                break;
            case 3:
                context.strokePolygon(new double[]{47, 103, 130, 20}, new double[]{130, 130, 20, 20}, 4);
                break;
            case 4:
                int n = (int) (Math.random() * 10.0d) + 5;
                double angle = Math.random() * Math.PI * 2.0d, da = Math.PI * 2.0d / ((double) n);
                double[] x = new double[n], y = new double[n];
                for (int i = 0; i < n; ++i, angle += da) {
                    x[i] = Math.cos(angle) * 55.0d + 75.0d;
                    y[i] = Math.sin(angle) * 55.0d + 75.0d;
                }
                context.strokePolygon(x, y, n);
                break;
        }

        // LEFT EYE
        context.strokeOval(40, 50, 20, 20);
        switch ((int) (Math.random() * 5.0d)) {
            case 0:
                context.fillOval(40, 55, 10, 10);
                break;
            case 1:
                context.fillOval(45, 50, 10, 10);
                break;
            case 2:
                context.fillOval(50, 55, 10, 10);
                break;
            case 3:
                context.fillOval(45, 60, 10, 10);
                break;
            case 4:
                context.fillOval(45, 55, 10, 10);
                break;
        }

        // RIGHT EYE
        context.strokeOval(90, 50, 20, 20);
        switch ((int) (Math.random() * 5.0d)) {
            case 0:
                context.fillOval(90, 55, 10, 10);
                break;
            case 1:
                context.fillOval(95, 50, 10, 10);
                break;
            case 2:
                context.fillOval(100, 55, 10, 10);
                break;
            case 3:
                context.fillOval(95, 60, 10, 10);
                break;
            case 4:
                context.fillOval(95, 55, 10, 10);
                break;
        }

        // MOUTH
        int w = (int) (Math.random() * 40.0d) + 10, h = (int) (Math.random() * 10.0d) + 10;
        context.strokeOval(75 - w / 2d, 108 - w / 2d, w, h);

        // NOSE
        switch ((int) (Math.random() * 4.0d)) {
            case 0:
                context.fillRect(70, 70, 10, 10);
                break;
            case 1:
                context.fillOval(70, 70, 10, 10);
                break;
            case 2:
                context.fillPolygon(new double[]{75, 70, 80}, new double[]{70, 80, 80}, 3);
                break;
            case 3:
                context.fillPolygon(new double[]{75, 70, 80}, new double[]{80, 70, 70}, 3);
                break;
        }
    }
}
