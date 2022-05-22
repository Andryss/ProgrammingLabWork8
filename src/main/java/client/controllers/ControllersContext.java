package client.controllers;

import client.Application;
import client.ClientConnector;
import client.ClientExecutor;
import general.Request;
import general.Response;
import general.element.Movie;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Optional;

public class ControllersContext {
    private static final ControllersContext instance = new ControllersContext();

    private ControllersContext() {}

    public static ControllersContext getInstance() {
        return instance;
    }

    private final Alert confirmWindow = new Alert(Alert.AlertType.CONFIRMATION);
    Optional<ButtonType> showConfirmWindow(String title, String contextText) {
        confirmWindow.setTitle(title);
        confirmWindow.setHeaderText(null);
        confirmWindow.setContentText(contextText);
        return confirmWindow.showAndWait();
    }

    private final Alert warningWindow = new Alert(Alert.AlertType.WARNING);
    void showWarningWindow(String title, String contextText) {
        warningWindow.setTitle(title);
        warningWindow.setHeaderText(null);
        warningWindow.setContentText(contextText);
        warningWindow.show();
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
        collectionList.setAll(collection.values());
        this.collection.set(collection);
    }
    SimpleObjectProperty<Hashtable<Integer, Movie>> getCollectionProperty() {
        return collection;
    }
    Hashtable<Integer,Movie> getCollection() {
        return collection.get();
    }

    private final ObservableList<Movie> collectionList = FXCollections.observableArrayList();
    ObservableList<Movie> getCollectionList() {
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

}
