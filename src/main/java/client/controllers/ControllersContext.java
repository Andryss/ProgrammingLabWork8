package client.controllers;

import client.Application;
import client.ClientConnector;
import client.ClientController;
import client.ClientExecutor;
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


    private final SimpleObjectProperty<ClientExecutor.CommandContainer> currentCommand = new SimpleObjectProperty<>(null);
    void setCurrentCommand(ClientExecutor.CommandContainer currentCommand) {
        this.currentCommand.set(currentCommand);
    }
    SimpleObjectProperty<ClientExecutor.CommandContainer> getCurrentCommandProperty() {
        return currentCommand;
    }
    ClientExecutor.CommandContainer getCurrentCommand() {
        return currentCommand.get();
    }

    private TextFlow consoleTextFlow;
    void setConsoleTextFlow(TextFlow textFlow) {
        this.consoleTextFlow = textFlow;
    }
    public TextFlow getConsoleTextFlow() {
        return consoleTextFlow;
    }

    ClientController getClientController() {
        return ClientController.getInstance();
    }

    private Application application;
    public void setApplication(Application application) {
        this.application = application;
    }
    Application getApplication() {
        return application;
    }
    void setScene(Application.AppScene scene) {
        application.setScene(scene);
    }
    void setSccStyle(Application.AppStyle style) {
        application.setCssStyle(style);
    }

    Response sendToServer(Request request) throws IOException, ClassNotFoundException {
        Response response = ClientConnector.getInstance().sendToServer(request);
        if (response.getHashtable() != null) {
            setCollection(response.getHashtable());
        }
        return response;
    }
    void sendRequest(Request request) throws IOException {
        ClientConnector.getInstance().sendRequest(request);
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

    public void showUserError(Throwable throwable) {
        showErrorWindow(
                getString("Error"),
                getString("Oops... Seems like evil goblins cut some wires... Try again later")
        );
        try {
            File file = new File("errStackTrace" + (Math.random() * Long.MAX_VALUE));
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
