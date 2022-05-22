package client.controllers;

import javafx.fxml.FXML;

public class MainSceneController {

    @FXML private ConsoleTabController consoleTabController;
    @FXML private TableTabController tableTabController;
    @FXML private PlotTabController plotTabController;

    @FXML
    private void initialize() {
        consoleTabController.setLogic(this);
        tableTabController.setLogic(this);
        plotTabController.setLogic(this);
    }
}
