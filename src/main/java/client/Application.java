package client;

import client.controllers.ControllersContext;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.util.EnumMap;
import java.util.Objects;
import java.util.Properties;

public class Application extends javafx.application.Application {
    private Stage stage;
    private final EnumMap<AppScene,Scene> sceneMap = new EnumMap<>(AppScene.class);

    private final ClientModuleHolder moduleHolder = ClientModuleHolder.getInstance();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {

            this.stage = stage;
            stage.setTitle("Free trial version for 3 minutes (then 1 BARS point for minute)");
            try (InputStream stream = getClass().getResourceAsStream("movie.png")){
                if (stream != null) {
                    stage.getIcons().add(new Image(stream));
                }
            }

            preInitializations();

            ControllersContext.getInstance().setApplication(this);

            loadScene("AuthorizationScene.fxml", AppScene.AUTHORIZATION_SCENE);
            loadScene("RegistrationScene.fxml", AppScene.REGISTRATION_SCENE);
            loadScene("MainScene.fxml", AppScene.MAIN_SCENE);

            postInitializations();

            setScene(AppScene.AUTHORIZATION_SCENE);
            stage.show();
        } catch (Throwable e) {
            e.printStackTrace();
            ControllersContext.getInstance().showUserError(e);
        }
    }

    private void loadScene(String name, AppScene sceneType) throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource(name));
        Scene scene = new Scene(loader.load());
        sceneMap.put(sceneType, scene);
    }

    private void preInitializations() throws Exception {
        moduleHolder.setPropertiesAll(readProperties());

        moduleHolder.getClientExecutorModule().initialize();
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

    private void postInitializations() throws Exception {
        setStyle(AppStyle.DEFAULT);

        try {
            moduleHolder.getClientControllerModule().setWritableObject(
                    ControllersContext.getInstance().getConsoleTextFlow()
            );
        } catch (Exception e) {
            // never
        }

        moduleHolder.getClientConnectorModule().initialize();
    }

    public Stage getStage() {
        return stage;
    }


    private final ObjectProperty<AppScene> sceneProperty = new SimpleObjectProperty<>();
    public void setScene(AppScene appScene) {
        stage.setScene(sceneMap.get(appScene));
        sceneProperty.set(appScene);
    }
    public ReadOnlyObjectProperty<AppScene> sceneProperty() {
        return sceneProperty;
    }
    public AppScene getScene() {
        return sceneProperty.get();
    }

    public enum AppScene {
        AUTHORIZATION_SCENE,
        REGISTRATION_SCENE,
        MAIN_SCENE
    }


    private final ObjectProperty<AppStyle> styleProperty = new SimpleObjectProperty<>();
    public void setStyle(AppStyle appStyle) {
        for (Scene scene : sceneMap.values()) {
            scene.getStylesheets().setAll(appStyle.getPath());
        }
        styleProperty.set(appStyle);
    }
    public ReadOnlyObjectProperty<AppStyle> styleProperty() {
        return styleProperty;
    }
    public AppStyle getStyle() {
        return styleProperty.get();
    }

    public enum AppStyle {
        DEFAULT("defaultTheme.css", "Default"),
        DARK("darkTheme.css", "Dark");

        private final String path;
        private final String name;

        AppStyle(String path, String name) {
            this.path = Objects.requireNonNull(getClass().getResource(path)).toExternalForm();
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public String getName() {
            return name;
        }
    }
}