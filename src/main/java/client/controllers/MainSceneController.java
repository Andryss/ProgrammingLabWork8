package client.controllers;

import client.*;
import general.ClientContext;
import general.Request;
import general.Response;
import general.commands.BadArgumentsException;
import general.commands.Command;
import general.commands.CommandException;
import general.commands.ElementCommand;
import general.element.Coordinates;
import general.element.FieldException;
import general.element.Movie;
import general.element.Person;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.ZonedDateTime;
import java.util.*;

public class MainSceneController {
    private Application application;
    public void setLogic(Application application) {
        this.application = application;
    }

    @FXML private ConsoleTabController consoleTabController;
    @FXML private TableTabController tableTabController;
    @FXML private PlotTabController plotTabController;

    @FXML
    private void initialize() {
        consoleTabController.setLogic(this);
        tableTabController.setLogic(this);
        plotTabController.setLogic(this);
    }
    public ConsoleTabController getConsoleTabController() {
        return consoleTabController;
    }
    public TableTabController getTableTabController() {
        return tableTabController;
    }
    public PlotTabController getPlotTabController() {
        return plotTabController;
    }


    public TextFlow getConsoleTextArea() {
        return consoleTabController.getConsoleTextArea();
    }

    public void updateMovieTable(Hashtable<Integer, Movie> hashtable) {
        tableTabController.updateMovieTable(hashtable);
    }

    private String currentUserName;
    public void setCurrentUserName(String name) {
        this.currentUserName = name;
        consoleTabController.setCurrentUserName(name);
    }
    public String getCurrentUserName() {
        return currentUserName;
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
}
