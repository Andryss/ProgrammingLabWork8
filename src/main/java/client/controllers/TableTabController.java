package client.controllers;

import general.element.Movie;
import general.element.Person;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Hashtable;

public class TableTabController {
    MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML
    private TableView<Movie> tableMovieTable;
    @FXML private TableColumn<Movie,String> movieOwnerColumn;
    @FXML private TableColumn<Movie,String> movieNameColumn;
    @FXML private TableColumn<Movie,Long> movieIdColumn;
    @FXML private TableColumn<Movie,Float> coordinatesXColumn;
    @FXML private TableColumn<Movie,Float> coordinatesYColumn;
    @FXML private TableColumn<Movie, ZonedDateTime> movieCreationDateColumn;
    @FXML private TableColumn<Movie,Long> movieOscarsCountColumn;
    @FXML private TableColumn<Movie,Integer> movieLengthColumn;
    @FXML private TableColumn<Movie,Movie.MovieGenre> movieGenreColumn;
    @FXML private TableColumn<Movie,Movie.MpaaRating> movieMpaaRatingColumn;
    @FXML private TableColumn<Movie,String> screenwriterNameColumn;
    @FXML private TableColumn<Movie, Date> screenwriterBirthdayColumn;
    @FXML private TableColumn<Movie, Person.Color> screenwriterHairColorColumn;

    @FXML
    private void initialize() {
        // TODO: maybe connect it with @FieldSetter
        movieOwnerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        movieIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        coordinatesXColumn.setCellValueFactory(e -> new SimpleFloatProperty(e.getValue().getCoordinates().getX()).asObject());
        coordinatesYColumn.setCellValueFactory(e -> new SimpleFloatProperty(e.getValue().getCoordinates().getY()).asObject());
        movieCreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        movieOscarsCountColumn.setCellValueFactory(new PropertyValueFactory<>("oscarsCount"));
        movieLengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        movieGenreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        movieMpaaRatingColumn.setCellValueFactory(new PropertyValueFactory<>("mpaaRating"));
        screenwriterNameColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getScreenwriter().getName()));
        screenwriterBirthdayColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getScreenwriter().getBirthday()));
        screenwriterHairColorColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getScreenwriter().getHairColor()));
    }

    void updateMovieTable(Hashtable<Integer,Movie> movieHashtable) {
        tableMovieTable.setItems(FXCollections.observableArrayList(movieHashtable.values()));
    }

}
