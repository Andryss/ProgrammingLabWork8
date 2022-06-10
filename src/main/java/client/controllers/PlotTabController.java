package client.controllers;

import general.element.Movie;
import general.element.Person;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.*;

public class PlotTabController {
    MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
    private final ControllersContext context = ControllersContext.getInstance();

    @FXML private BubbleChart<Float,Float> plotChart;

    @FXML private Label selectedMovieLabel;
    @FXML private Label nothingSelectedLabel;
    @FXML private VBox selectedMoviePane;

    @FXML private TextField movieKeyTextField;
    @FXML private TextField movieOwnerTextField;
    @FXML private TextField movieIdTextField;
    @FXML private TextField movieNameTextField;
    @FXML private TextField coordinatesXTextField;
    @FXML private TextField coordinatesYTextField;
    @FXML private TextField movieCreationDateTextField;
    @FXML private TextField movieOscarsCountTextField;
    @FXML private TextField movieLengthTextField;
    @FXML private ChoiceBox<Movie.MovieGenre> movieGenreChoiceBox;
    @FXML private ChoiceBox<Movie.MpaaRating> movieMpaaRatingChoiceBox;
    @FXML private TextField screenwriterNameTextField;
    @FXML private TextField screenwriterBirthdayTextField;
    @FXML private ChoiceBox<Person.Color> screenwriterHairColorChoiceBox;

    @FXML private Button editSelectedMovieButton;
    @FXML private Button removeSelectedMovieButton;

    private HashMap<XYChart.Data<Float,Float>, Map.Entry<Integer,Movie>> movieData;
    private final ObjectProperty<Map.Entry<Integer,Movie>> selectedMovie = new SimpleObjectProperty<>();

    @FXML
    private void initialize() {
        context.getCollectionProperty().addListener((observableValue, oldValue, newValue) -> paintCollection(newValue));
        selectedMovie.addListener(((observableValue, oldValue, newValue) -> {
            if (newValue == null) {
                clearSelectedMovieFields();
            } else {
                setSelectedMovieFields(newValue);
                boolean disable = !newValue.getValue().getOwner().equals(context.getUserName());
                editSelectedMovieButton.setDisable(disable);
                removeSelectedMovieButton.setDisable(disable);
            }
            movieData.forEach((key, value) -> {
                if (value == newValue) {
                    key.getNode().setScaleX(1.2);
                    key.getNode().setScaleY(1.2);
                } else if (value == oldValue) {
                    key.getNode().setScaleX(1);
                    key.getNode().setScaleY(1);
                }
            });
        }));
        editSelectedMovieButton.setOnAction(e -> mainSceneController.setToUpdate(selectedMovie.getValue()));
        removeSelectedMovieButton.setOnAction(e -> mainSceneController.setToRemove(selectedMovie.getValue()));

        context.localizedData().resourceBundleProperty().addListener((obs, o, n) -> localize(n));
    }

    private void localize(ResourceBundle resourceBundle) {
        selectedMovieLabel.setText(resourceBundle.getString("Selected movie"));
        nothingSelectedLabel.setText("*" + resourceBundle.getString("nothing selected") + "*");
        editSelectedMovieButton.setText(resourceBundle.getString("Edit"));
        removeSelectedMovieButton.setText(resourceBundle.getString("Remove"));
    }

    void paintCollection() {
        paintCollection(context.getCollection());
    }

    private void paintCollection(Hashtable<Integer, Movie> hashtable) {
        HashMap<String, XYChart.Series<Float,Float>> seriesHashMap = new HashMap<>();
        Timeline paintAnimation = new Timeline();
        movieData = new HashMap<>();
        hashtable.forEach((key, value) -> {
            XYChart.Data<Float,Float> data = new XYChart.Data<>(
                    value.getCoordinates().getX(),
                    value.getCoordinates().getY(),
                    0
            );
            movieData.put(data, new AbstractMap.SimpleEntry<>(key,value));
            paintAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(500 + Math.random() * 500), new KeyValue(data.extraValueProperty(), radiusFunction(value.getLength()), Interpolator.EASE_BOTH)));
            if (seriesHashMap.containsKey(value.getOwner())) {
                seriesHashMap.get(value.getOwner()).getData().add(data);
            } else {
                //noinspection unchecked
                XYChart.Series<Float,Float> series = new XYChart.Series<>(value.getOwner(), FXCollections.observableArrayList(data));
                seriesHashMap.put(value.getOwner(), series);
            }
        });
        plotChart.setData(FXCollections.observableArrayList(seriesHashMap.values()));
        paintAnimation.play();
        setOnMouseClicked();
        unsetSelectedMovie();
    }

    private void setOnMouseClicked() {
        for (XYChart.Series<Float,Float> series : plotChart.getData()) {
            for (XYChart.Data<Float,Float> data : series.getData()) {
                data.getNode().setOnMouseClicked(e -> setSelectedMovie(movieData.get(data)));
            }
        }
    }

    @FXML
    private void onCanvasMouseClicked(MouseEvent mouseEvent) {
        if (!(mouseEvent.getPickResult().getIntersectedNode().getClass().getName().equals("javafx.scene.chart.BubbleChart$1"))) {
            unsetSelectedMovie();
        }
    }

    private void setSelectedMovie(Map.Entry<Integer,Movie> entry) {
        selectedMovie.set(entry);
        nothingSelectedLabel.setVisible(false);
        selectedMoviePane.setVisible(true);
    }

    private void unsetSelectedMovie() {
        selectedMovie.set(null);
        selectedMoviePane.setVisible(false);
        nothingSelectedLabel.setVisible(true);
    }

    private void setSelectedMovieFields(Map.Entry<Integer,Movie> entry) {
        movieKeyTextField.setText(String.valueOf(entry.getKey()));
        movieOwnerTextField.setText(entry.getValue().getOwner());
        movieIdTextField.setText(String.valueOf(entry.getValue().getId()));
        movieNameTextField.setText(entry.getValue().getName());
        coordinatesXTextField.setText(String.valueOf(entry.getValue().getCoordinates().getX()));
        coordinatesYTextField.setText(String.valueOf(entry.getValue().getCoordinates().getY()));
        movieCreationDateTextField.setText(String.valueOf(entry.getValue().getCreationDate()));
        movieOscarsCountTextField.setText(String.valueOf(entry.getValue().getOscarsCount()));
        movieLengthTextField.setText(String.valueOf(entry.getValue().getLength()));
        movieGenreChoiceBox.setValue(entry.getValue().getGenre());
        movieMpaaRatingChoiceBox.setValue(entry.getValue().getMpaaRating());
        screenwriterNameTextField.setText(entry.getValue().getScreenwriter().getName());
        screenwriterBirthdayTextField.setText(entry.getValue().getScreenwriter().getBirthdayString());
        screenwriterHairColorChoiceBox.setValue(entry.getValue().getScreenwriter().getHairColor());
    }

    private void clearSelectedMovieFields() {
        movieKeyTextField.clear();
        movieOwnerTextField.clear();
        movieIdTextField.clear();
        movieNameTextField.clear();
        coordinatesXTextField.clear();
        coordinatesYTextField.clear();
        movieCreationDateTextField.clear();
        movieOscarsCountTextField.clear();
        movieLengthTextField.clear();
        movieGenreChoiceBox.setValue(null);
        movieMpaaRatingChoiceBox.setValue(null);
        screenwriterNameTextField.clear();
        screenwriterBirthdayTextField.clear();
        screenwriterHairColorChoiceBox.setValue(null);
    }

    private double radiusFunction(int value) {
        return Math.sqrt(value);
    }
}
