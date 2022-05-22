package client.controllers;

import general.element.Movie;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.Hashtable;

public class PlotTabController {
    MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    // TODO: make plot here
    @FXML private Canvas plotCanvas;

    @FXML
    private void initialize() {
        ControllersContext.getInstance().getCollectionProperty().addListener((observableValue, oldValue, newValue) -> paintCollection(newValue));
    }

    @FXML
    private void onPlotMouseClicked(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
        System.out.println(mouseEvent.getX());
        System.out.println(mouseEvent.getY());
    }

    void paintCollection(Hashtable<Integer, Movie> hashtable) {
        GraphicsContext context = plotCanvas.getGraphicsContext2D();
        for (Movie movie : hashtable.values()) {
            context.fillRect(movie.getCoordinates().getX(), movie.getCoordinates().getY(), movie.getLength(), movie.getLength());
        }
    }
}
