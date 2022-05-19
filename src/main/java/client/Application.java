package client;

import client.controllers.AuthorizationSceneController;
import client.controllers.MainSceneController;
import client.controllers.RegistrationSceneController;
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
import java.util.Properties;

public class Application extends javafx.application.Application {
    private Stage stage;
    private final EnumMap<AppScene,Scene> sceneMap = new EnumMap<>(AppScene.class);
    private MainSceneController mainController;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {

            this.stage = stage;
            stage.setTitle("Cum");

            preInitializations();

            FXMLLoader loader = new FXMLLoader(Application.class.getResource("AuthorizationScene.fxml"));
            sceneMap.put(AppScene.AUTHORIZATION_SCENE, new Scene(loader.load()));
            ((AuthorizationSceneController) loader.getController()).setLogic(this);

            loader = new FXMLLoader(Application.class.getResource("RegistrationScene.fxml"));
            sceneMap.put(AppScene.REGISTRATION_SCENE, new Scene(loader.load()));
            ((RegistrationSceneController) loader.getController()).setLogic(this);

            loader = new FXMLLoader(Application.class.getResource("MainScene.fxml"));
            sceneMap.put(AppScene.MAIN_SCENE, new Scene(loader.load()));
            mainController = loader.getController();
            mainController.setLogic(this);

            postInitializations();

            setScene(AppScene.AUTHORIZATION_SCENE);
            stage.show();
        } catch (Throwable e) {
            // TODO: delete trash below
            e.printStackTrace();
            // TODO: delete trash above
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something went wrong");
            alert.setHeaderText(null);
            alert.setContentText("Error: " + e.getMessage());

            alert.show();
        }
    }

    private void preInitializations() throws IOException, CommandException {
        Properties properties = readProperties();

        ClientConnector.getInstance().setProperties(properties);
        ClientExecutor.getInstance().initialize();
    }

    private void postInitializations() throws IOException, ClassNotFoundException {
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

    public MainSceneController getMainController() {
        return mainController;
    }

    public enum AppScene {
        AUTHORIZATION_SCENE,
        REGISTRATION_SCENE,
        MAIN_SCENE
    }
}