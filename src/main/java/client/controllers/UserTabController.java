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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;

import java.util.*;

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
    @FXML private ComboBox<Localizer.AvailableLocale> languageComboBox;
    @FXML private Label themeLabel;
    @FXML private ComboBox<Application.AppStyle> themeComboBox;
    @FXML private Button exitButton;

    private final EnumMap<Application.AppStyle, Paint> drawingMap = new EnumMap<>(Application.AppStyle.class);

    @FXML
    private void initialize() {
        context.getUserNameProperty().addListener((obs, o, n) -> {
            mainSceneController.getUserTab().textProperty().setValue(n);
            usernameTextField.setText(n);
            switch (n.hashCode() % 3 + 6) {
                case 6: passwordTextField.setText("******");break;
                case 7: passwordTextField.setText("*******");break;
                case 8: passwordTextField.setText("********");break;
            }
            drawFace();
        });

        languageComboBox.setItems(FXCollections.observableArrayList(Localizer.AvailableLocale.values()));
        languageComboBox.valueProperty().bindBidirectional(context.localizer().availableLocaleProperty());
        context.localizer().resourceBundleProperty().addListener((obs, o, n) -> localize(n));

        themeComboBox.setItems(FXCollections.observableArrayList(Application.AppStyle.values()));
        themeComboBox.setValue(Application.AppStyle.DEFAULT);
        themeComboBox.setConverter(new StringConverter<Application.AppStyle>() {
            @Override
            public String toString(Application.AppStyle appStyle) {
                return context.getString(appStyle.getName());
            }

            @Override
            public Application.AppStyle fromString(String s) {
                return Arrays.stream(Application.AppStyle.values())
                        .filter(e -> e.getName().equals(s))
                        .findAny()
                        .orElse(null);
            }
        });
        themeComboBox.valueProperty().addListener((obs, o, n) -> context.getApplication().setStyle(n));

        for (Application.AppStyle style : Application.AppStyle.values()) {
            if (style == Application.AppStyle.DEFAULT) {
                drawingMap.put(style, new Color(0d, 0d, 0d, 1d));
            } else if (style == Application.AppStyle.DARK) {
                drawingMap.put(style, new Color(1d, 1d, 1d, 1d));
            } else {
                // funny :)
                drawingMap.put(style, new Color(Math.random(), Math.random(), Math.random(), Math.random()));
            }
        }
        context.getApplication().styleProperty().addListener((obs, o, n) -> {
            GraphicsContext context = iconCanvas.getGraphicsContext2D();
            Paint paint = drawingMap.get(n);
            context.setStroke(paint);
            context.setFill(paint);
            redrawFace();
        });
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
    private void signOutKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            signOut();
        }
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
            } catch (Exception e) {
                context.showUserError(e);
            }
        }
    }

    @FXML
    private void exitKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            signOut();
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
    private void refreshKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            drawFace();
        }
    }

    @FXML
    private void refreshMouseClicked(MouseEvent mouseEvent) {
        drawFace();
    }


    private final Random random = new Random();
    private long lastSeed;
    private void drawFace() {
        drawFace((long) (Math.random() * Long.MAX_VALUE));
    }
    private void redrawFace() {
        drawFace(lastSeed);
    }
    /**
     * tg: @ray_1024
     * @author Ray_1024
     */
    private void drawFace(long seed) {
        GraphicsContext context = iconCanvas.getGraphicsContext2D();
        context.clearRect(0, 0, iconCanvas.getWidth(), iconCanvas.getHeight());

        random.setSeed(seed);
        lastSeed = seed;

        // DRAW FACE
        switch ((int) (random.nextDouble() * 5.0d)) {
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
                int n = (int) (random.nextDouble() * 10.0d) + 5;
                double angle = random.nextDouble() * Math.PI * 2.0d, da = Math.PI * 2.0d / ((double) n);
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
        switch ((int) (random.nextDouble() * 5.0d)) {
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
        switch ((int) (random.nextDouble() * 5.0d)) {
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
        int w = (int) (random.nextDouble() * 40.0d) + 10, h = (int) (random.nextDouble() * 10.0d) + 10;
        context.strokeOval(75 - w / 2d, 108 - w / 2d, w, h);

        // NOSE
        switch ((int) (random.nextDouble() * 4.0d)) {
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
