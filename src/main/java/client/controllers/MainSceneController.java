package client.controllers;

import general.element.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.Map;
import java.util.ResourceBundle;

public class MainSceneController {
    private final ControllersContext context = ControllersContext.getInstance();

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
        setControllersLogic();

        selectTab(consoleTabTab);

        plotTabTab.selectedProperty().addListener((obs, o, n) -> {
            if (n) plotTabController.paintCollection();
        });

        context.localizedData().resourceBundleProperty().addListener((obs, o, n) -> localize(n));
    }

    private void setControllersLogic() {
        userTabController.setLogic(this);
        consoleTabController.setLogic(this);
        tableTabController.setLogic(this);
        plotTabController.setLogic(this);
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
}
