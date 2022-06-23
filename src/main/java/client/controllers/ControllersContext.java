package client.controllers;

import client.*;
import client.localization.Localizer;
import general.Request;
import general.Response;
import general.element.Movie;
import javafx.animation.Interpolator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

public class ControllersContext {
    private static final ControllersContext instance = new ControllersContext();

    private ControllersContext() {}

    public static ControllersContext getInstance() {
        return instance;
    }

    private final Alert confirmWindow = new Alert(Alert.AlertType.CONFIRMATION);
    public Optional<ButtonType> showConfirmWindow(String title, String contextText) {
        confirmWindow.setTitle(title);
        confirmWindow.setContentText(contextText);
        return confirmWindow.showAndWait();
    }

    private final Alert warningWindow = new Alert(Alert.AlertType.WARNING);
    public void showWarningWindow(String title, String contextText) {
        warningWindow.setTitle(title);
        warningWindow.setContentText(contextText);
        warningWindow.show();
    }

    private final Alert errorWindow = new Alert(Alert.AlertType.ERROR);
    public void showErrorWindow(String title, String contextText) {
        errorWindow.setTitle(title);
        errorWindow.setContentText(contextText);
        errorWindow.show();
    }

    {
        confirmWindow.setHeaderText(null);
        warningWindow.setHeaderText(null);
        errorWindow.setHeaderText(null);
    }

    private final FileChooser fileChooser = new FileChooser();
    public File chooseFile() {
        return chooseFile(getApplication().getStage());
    }
    public File chooseFile(Window window) {
        fileChooser.setInitialFileName(
                getString("Saved console history") + " " +
                        localizer().getShortDateTimeFormat().format(System.currentTimeMillis())
        );
        return fileChooser.showSaveDialog(window);
    }

    {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
    }

    private final SimpleStringProperty userName = new SimpleStringProperty("");
    void setUserName(String username) {
        this.userName.set(username);
    }
    SimpleStringProperty getUserNameProperty() {
        return userName;
    }
    String getUserName() {
        return userName.get();
    }

    private final SimpleObjectProperty<Hashtable<Integer, Movie>> collection = new SimpleObjectProperty<>(new Hashtable<>());
    void setCollection(Hashtable<Integer,Movie> collection) {
        collectionList.setAll(collection.entrySet());
        this.collection.set(collection);
    }
    SimpleObjectProperty<Hashtable<Integer, Movie>> getCollectionProperty() {
        return collection;
    }
    Hashtable<Integer,Movie> getCollection() {
        return collection.get();
    }

    private final ObservableList<Map.Entry<Integer, Movie>> collectionList = FXCollections.observableArrayList();
    ObservableList<Map.Entry<Integer, Movie>> getCollectionList() {
        return collectionList;
    }


    private final SimpleObjectProperty<ClientExecutorImpl.CommandContainer> currentCommand = new SimpleObjectProperty<>(null);
    void setCurrentCommand(ClientExecutorImpl.CommandContainer currentCommand) {
        this.currentCommand.set(currentCommand);
    }
    SimpleObjectProperty<ClientExecutorImpl.CommandContainer> getCurrentCommandProperty() {
        return currentCommand;
    }
    ClientExecutorImpl.CommandContainer getCurrentCommand() {
        return currentCommand.get();
    }

    private TextFlow consoleTextFlow;
    void setConsoleTextFlow(TextFlow textFlow) {
        this.consoleTextFlow = textFlow;
    }
    public TextFlow getConsoleTextFlow() {
        return consoleTextFlow;
    }

    private final ClientModuleHolder moduleHolder = ClientModuleHolder.getInstance();
    private final ClientConnectorModule connectorModule = moduleHolder.getClientConnectorModule();
    ClientConnectorModule getConnectorModule() {
        return connectorModule;
    }
    private final ClientControllerModule controllerModule = moduleHolder.getClientControllerModule();
    ClientControllerModule getControllerModule() {
        return controllerModule;
    }
    private final ClientExecutorModule executorModule = moduleHolder.getClientExecutorModule();
    ClientExecutorModule getExecutorModule() {
        return executorModule;
    }

    Response sendToServer(Request request) throws Exception {
        Response response = connectorModule.sendToServer(request);
        if (response.getHashtable() != null) {
            setCollection(response.getHashtable());
        }
        return response;
    }
    void sendRequest(Request request) throws Exception {
        connectorModule.sendRequest(request);
    }

    private Application application;
    public void setApplication(Application application) {
        this.application = application;
        application.styleProperty().addListener((obs, o, n) -> {
            confirmWindow.getDialogPane().getStylesheets().setAll(n.getPath());
            warningWindow.getDialogPane().getStylesheets().setAll(n.getPath());
            errorWindow.getDialogPane().getStylesheets().setAll(n.getPath());
        });
    }
    Application getApplication() {
        return application;
    }

    Localizer localizer() {
        return Localizer.getInstance();
    }
    String getString(String key) {
        return localizer().getResourceBundle().getString(key);
    }

    private final Interpolator progressInterpolator = new Interpolator() {
        @Override
        protected double curve(double v) {
            return (v < 0.2) ? (-22.5*v*v + 9*v) : (v < 0.6) ? (0.225*v + 0.855) : (v < 0.9) ? (-2./15*v +1.07) : (5*v*v -9*v + 5);
        }
    };
    Interpolator getProgressInterpolator() {
        return progressInterpolator;
    }

    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    public void showUserError(Throwable throwable) {
        showErrorWindow(
                getString("Error"),
                getString("Oops... Seems like evil goblins cut some wires... Try again later")
        );
        try {
            File file = new File("errStackTrace" + formatter.format(System.currentTimeMillis()));
            if (file.createNewFile()) {
                try (PrintStream stream = new PrintStream(file)) {
                    // And now we can send stackTrace to the server
                    // COMING SOON ... (no, I'm lazy)
                    // Let's just print stackTrace into a file
                    throwable.printStackTrace(stream);
                }
            } // Else sadness :(
        } catch (IOException e) {
            // Also, sadness :(
        }
    }
}
