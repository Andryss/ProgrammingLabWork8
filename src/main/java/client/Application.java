package client;

import client.controllers.AuthorizationController;
import client.controllers.MainController;
import client.controllers.RegistrationController;
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

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            initializations();

            this.stage = stage;
            stage.setTitle("Cum");

            FXMLLoader loader = new FXMLLoader(Application.class.getResource("client-authorization-scene.fxml"));
            sceneMap.put(AppScene.AUTHORIZATION_SCENE, new Scene(loader.load()));
            ((AuthorizationController) loader.getController()).setLogic(this);

            loader = new FXMLLoader(Application.class.getResource("client-registration-scene.fxml"));
            sceneMap.put(AppScene.REGISTRATION_SCENE, new Scene(loader.load()));
            ((RegistrationController) loader.getController()).setLogic(this);

            loader = new FXMLLoader(Application.class.getResource("client-main-scene.fxml"));
            sceneMap.put(AppScene.MAIN_SCENE, new Scene(loader.load()));
            ((MainController) loader.getController()).setLogic(this);

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

    private void initializations() throws IOException, CommandException, ClassNotFoundException {
        Properties properties = readProperties();

        ClientConnector.getInstance().setProperties(properties);
        ClientExecutor.getInstance().initialize();

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
}