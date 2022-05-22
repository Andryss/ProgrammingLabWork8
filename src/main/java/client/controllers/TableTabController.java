package client.controllers;

import client.RequestBuilder;
import general.Request;
import general.element.Movie;
import general.element.Person;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

public class TableTabController {
    MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML private Label filterLabelFirst;
    @FXML private ChoiceBox<TableColumn<Movie,?>> filterChoiceBox;
    @FXML private Label filterLabelSecond;
    @FXML private TextField filterTextField;

    @FXML private TableView<Movie> tableMovieTable;
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

    @FXML private Button updateButton;
    @FXML private CheckBox autoUpdateCheckBox;
    @FXML private Label updateErrLabel;

    private FilteredList<Movie> filteredList;
    private final LinkedHashMap<String, TableColumn<Movie, ?>> columns = new LinkedHashMap<>();

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

        initColumns(tableMovieTable.getColumns());

        filteredList = new FilteredList<>(ControllersContext.getInstance().getCollectionList(), m -> true);
        SortedList<Movie> sortedList = new SortedList<>(filteredList);
        tableMovieTable.setItems(sortedList);
        sortedList.comparatorProperty().bind(tableMovieTable.comparatorProperty());

        filterChoiceBox.setItems(FXCollections.observableArrayList(columns.values()));
        filterChoiceBox.setConverter(new StringConverter<TableColumn<Movie, ?>>() {
            @Override
            public String toString(TableColumn<Movie, ?> movieTableColumn) {
                //noinspection OptionalGetWithoutIsPresent
                return columns.entrySet().stream().filter(e -> e.getValue() == movieTableColumn).findAny().get().getKey();
            }
            @Override
            public TableColumn<Movie, ?> fromString(String s) {
                return columns.get(s);
            }
        });
        //noinspection OptionalGetWithoutIsPresent
        filterChoiceBox.setValue(columns.values().stream().findFirst().get());

        filterTextField.textProperty().addListener((observableValue, oldValue, newValue) -> filterCollection());

    }

    private void initColumns(ObservableList<TableColumn<Movie, ?>> list) {
        initColumns("", list);
    }

    private void initColumns(String prefix, ObservableList<TableColumn<Movie, ?>> list) {
        for (TableColumn<Movie, ?> column : list) {
            if (column.getColumns().size() != 0) {
                initColumns(prefix + column.getText(), column.getColumns());
            } else {
                System.out.println(prefix + " " + column.getText());
                columns.put(prefix.equals("") ? column.getText() : prefix + " " + column.getText(), column);
            }
        }
    }

    private void filterCollection() {
        if (filterTextField.getText().equals("")) {
            filteredList.setPredicate(m -> true);
        } else {
            filteredList.setPredicate(m -> filterChoiceBox.getValue().getCellData(m).toString().toLowerCase().contains(filterTextField.getText()));
        }
    }

    @FXML
    private void updateCollectionMouseClicked(MouseEvent mouseEvent) {
        updateCollection();
    }

    Timeline autoUpdate = new Timeline(new KeyFrame(Duration.seconds(5), e -> updateCollection()));
    {
        autoUpdate.setCycleCount(Animation.INDEFINITE);
        autoUpdate.setAutoReverse(false);
    }
    private void setAutoUpdatePlay() {
        autoUpdate.play();
    }
    private void setAutoUpdatePause() {
        autoUpdate.pause();
    }

    @FXML
    private void autoUpdateMouseClicked(MouseEvent mouseEvent) {
        autoUpdate();
    }

    private void autoUpdate() {
        if (autoUpdateCheckBox.isSelected()) {
            setAutoUpdatePlay();
        } else {
            setAutoUpdatePause();
        }
    }

    private void updateCollection() {
        try {
            ControllersContext.getInstance().sendToServer(
                    RequestBuilder.createNewRequest()
                            .setRequestType(Request.RequestType.UPDATE_COLLECTION)
                            .build()
            );
        } catch (IOException | ClassNotFoundException e) {
            updateErrLabel.setText(e.getMessage());
        }
    }
}
