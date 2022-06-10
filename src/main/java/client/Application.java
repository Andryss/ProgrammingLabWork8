package client;

import client.controllers.ControllersContext;
import general.commands.CommandException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            ControllersContext.getInstance().showUserError(e);
        }
    }

    private void loadScene(String name, AppScene sceneType) throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource(name));
        Scene scene = new Scene(loader.load());
        sceneMap.put(sceneType, scene);
    }

    private void preInitializations() throws IOException, CommandException {
        Properties properties = readProperties();

        ClientConnector.getInstance().setProperties(properties);
        ClientExecutor.getInstance().initialize();
    }

    private void postInitializations() throws IOException, ClassNotFoundException {
        setCssStyle(AppStyle.DEFAULT);

        ClientController.getInstance().setTextFlow(ControllersContext.getInstance().getConsoleTextFlow());

        ClientConnector.getInstance().initialize();
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

    public void setCssStyle(AppStyle appStyle) {
        for (Scene scene : sceneMap.values()) {
            scene.getStylesheets().setAll(appStyle.path());
        }
    }

    public enum AppStyle {
        DEFAULT("defaultTheme.css"),
        // TODO: add dark theme
        DARK("darkTheme.css");

        private final String path;

        AppStyle(String path) {
            this.path = Objects.requireNonNull(this.getClass().getResource(path)).toExternalForm();
        }

        public String path() {
            return path;
        }
    }
}