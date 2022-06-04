package client.controllers;

import general.commands.Command;
import general.element.Movie;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.Map;
import java.util.ResourceBundle;

public class MainSceneController {

    @FXML private UserTabController userTabController;
    @FXML private ConsoleTabController consoleTabController;
    @FXML private TableTabController tableTabController;
    @FXML private PlotTabController plotTabController;

    @FXML private TabPane mainTabPane;
    @FXML private Tab userTabTab;
    @FXML private Tab consoleTabTab;
    @FXML private Tab tableTabTab;
    @FXML private Tab plotTabTab;

    @FXML
    private void initialize() {
        userTabController.setLogic(this);
        consoleTabController.setLogic(this);
        tableTabController.setLogic(this);
        plotTabController.setLogic(this);

        mainTabPane.getSelectionModel().select(consoleTabTab);

        plotTabTab.selectedProperty().addListener((obs, o, n) -> {
            if (n) plotTabController.paintCollection();
        });

        ControllersContext.getInstance().localizedData().resourceBundleProperty().addListener((obs, o, n) -> localize(n));
    }

    private void localize(ResourceBundle resourceBundle) {
        consoleTabTab.setText(resourceBundle.getString("Console"));
        tableTabTab.setText(resourceBundle.getString("Table"));
        plotTabTab.setText(resourceBundle.getString("Plot"));
    }

    void setToUpdate(Map.Entry<Integer, Movie> entry) {
        consoleTabController.setToUpdate(entry);
        selectTab(consoleTabTab);
    }

    void setToRemove(Map.Entry<Integer, Movie> entry) {
        consoleTabController.setToRemove(entry);
        selectTab(consoleTabTab);
    }

    private void selectTab(Tab tab) {
        mainTabPane.getSelectionModel().select(tab);
    }

    Tab getUserTab() {
        return userTabTab;
    }
    Tab getPlotTab() {
        return plotTabTab;
    }
}
