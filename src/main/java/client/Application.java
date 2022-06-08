package client;

import client.controllers.ControllersContext;
import general.commands.CommandException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Objects;
import java.util.Properties;

public class Application extends javafx.application.Application {
    private Stage stage;
    private final EnumMap<AppScene,Scene> sceneMap = new EnumMap<>(AppScene.class);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {

            this.stage = stage;
            stage.setTitle("Free trial version for 3 minutes (then 1 BARS point for minute)");

            preInitializations();

            loadScene("AuthorizationScene.fxml", AppScene.AUTHORIZATION_SCENE);
            loadScene("RegistrationScene.fxml", AppScene.REGISTRATION_SCENE);
            loadScene("MainScene.fxml", AppScene.MAIN_SCENE);

            ControllersContext.getInstance().setApplication(this);

            postInitializations();

            setScene(AppScene.AUTHORIZATION_SCENE);
            stage.show();
        } catch (Throwable e) {
            // TODO: delete trash below
            e.printStackTrace();
            // TODO: delete trash above
            ControllersContext.getInstance().showUserError(e);
        }
    }

    private String cssStyle;
    private void loadSccStyle() throws FileNotFoundException {
        try {
            cssStyle = Objects.requireNonNull(this.getClass().getResource("defaultTheme.css")).toExternalForm();
        } catch (NullPointerException e) {
            throw new FileNotFoundException("Can't find defaultTheme.css file");
        }
    }
    private void loadScene(String name, AppScene sceneType) throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource(name));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(cssStyle);
        sceneMap.put(sceneType, scene);
    }

    private void preInitializations() throws IOException, CommandException {
        loadSccStyle();

        Properties properties = readProperties();

        ClientConnector.getInstance().setProperties(properties);
        ClientExecutor.getInstance().initialize();
    }

    private void postInitializations() throws IOException, ClassNotFoundException {
        ClientController.getInstance().setTextFlow(ControllersContext.getInstance().getConsoleTextFlow());

        ClientConnector.getInstance().initialize();

        ClientController.getInstance().initialize();
    }

    private Properties readProperties() throws IOException {
        Properties properties = new Properties();
        try {
            File props = new File("client.properties");
            if (!props.createNewFile()) {
                if (!props.isFile() || !props.canRead()) {
                    throw new IOException("Can't read anything from \"client.properties\" file");
                }
            }
            properties.load(new FileReader(props));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File \"client.properties\" with properties not found");
        }
        return properties;
    }

    public void setScene(AppScene appScene) {
        stage.setScene(sceneMap.get(appScene));
    }

    public enum AppScene {
        AUTHORIZATION_SCENE,
        REGISTRATION_SCENE,
        MAIN_SCENE
    }
}