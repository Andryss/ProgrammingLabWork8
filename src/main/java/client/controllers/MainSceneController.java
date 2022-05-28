package client.controllers;

import general.element.Movie;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.Map;

public class MainSceneController {

    @FXML private ConsoleTabController consoleTabController;
    @FXML private TableTabController tableTabController;
    @FXML private PlotTabController plotTabController;

    @FXML private TabPane mainTabPane;
    @FXML private Tab consoleTabTab;
    @FXML private Tab tableTabTab;
    @FXML private Tab plotTabTab;

    @FXML
    private void initialize() {
        consoleTabController.setLogic(this);
        tableTabController.setLogic(this);
        plotTabController.setLogic(this);

        plotTabTab.selectedProperty().addListener((obs, o, n) -> {
            if (n) plotTabController.paintCollection();
        });
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

    Tab getPlotTab() {
        return plotTabTab;
    }
}
