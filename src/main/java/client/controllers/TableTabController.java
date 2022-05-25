package client.controllers;

import client.RequestBuilder;
import general.Request;
import general.element.Movie;
import general.element.Person;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TableTabController {
    MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML private Label filterLabelFirst;
    @FXML private ChoiceBox<TableColumn<Map.Entry<Integer,Movie>,?>> filterChoiceBox;
    @FXML private Label filterLabelSecond;
    @FXML private TextField filterTextField;

    @FXML private TableView<Map.Entry<Integer, Movie>> tableMovieTable;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Integer> movieKeyColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> movieOwnerColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> movieNameColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Long> movieIdColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Float> coordinatesXColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Float> coordinatesYColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, ZonedDateTime> movieCreationDateColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Long> movieOscarsCountColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Integer> movieLengthColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Movie.MovieGenre> movieGenreColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Movie.MpaaRating> movieMpaaRatingColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> screenwriterNameColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Date> screenwriterBirthdayColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Person.Color> screenwriterHairColorColumn;

    @FXML private Button updateButton;
    @FXML private CheckBox autoUpdateCheckBox;
    @FXML private Label updateErrLabel;

    private FilteredList<Map.Entry<Integer,Movie>> filteredList;
    private final LinkedHashMap<String, TableColumn<Map.Entry<Integer,Movie>, ?>> columns = new LinkedHashMap<>();

    @FXML
    private void initialize() {
        // TODO: maybe connect it with @FieldSetter
        movieKeyColumn.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getKey()).asObject());
        movieOwnerColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getOwner()));
        movieNameColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getName()));
        movieIdColumn.setCellValueFactory(e -> new SimpleLongProperty(e.getValue().getValue().getId()).asObject());
        coordinatesXColumn.setCellValueFactory(e -> new SimpleFloatProperty(e.getValue().getValue().getCoordinates().getX()).asObject());
        coordinatesYColumn.setCellValueFactory(e -> new SimpleFloatProperty(e.getValue().getValue().getCoordinates().getY()).asObject());
        movieCreationDateColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getValue().getCreationDate()));
        movieOscarsCountColumn.setCellValueFactory(e -> new SimpleLongProperty(e.getValue().getValue().getOscarsCount()).asObject());
        movieLengthColumn.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getValue().getLength()).asObject());
        movieGenreColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getValue().getGenre()));
        movieMpaaRatingColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getValue().getMpaaRating()));
        screenwriterNameColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getScreenwriter().getName()));
        screenwriterBirthdayColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getValue().getScreenwriter().getBirthday()));
        screenwriterHairColorColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getValue().getScreenwriter().getHairColor()));

        initColumns(tableMovieTable.getColumns());

        filteredList = new FilteredList<>(ControllersContext.getInstance().getCollectionList(), m -> true);
        SortedList<Map.Entry<Integer,Movie>> sortedList = new SortedList<>(filteredList);
        tableMovieTable.setItems(sortedList);
        sortedList.comparatorProperty().bind(tableMovieTable.comparatorProperty());

        // FOR STREAM FILTERING
        //ControllersContext.getInstance().getCollectionList().addListener((ListChangeListener<Map.Entry<Integer, Movie>>) change -> filterCollection());

        filterChoiceBox.setItems(FXCollections.observableArrayList(columns.values()));
        filterChoiceBox.setConverter(new StringConverter<TableColumn<Map.Entry<Integer,Movie>, ?>>() {
            @Override
            public String toString(TableColumn<Map.Entry<Integer,Movie>, ?> movieTableColumn) {
                //noinspection OptionalGetWithoutIsPresent
                return columns.entrySet().stream().filter(e -> e.getValue() == movieTableColumn).findAny().get().getKey();
            }
            @Override
            public TableColumn<Map.Entry<Integer,Movie>, ?> fromString(String s) {
                return columns.get(s);
            }
        });
        //noinspection OptionalGetWithoutIsPresent
        filterChoiceBox.setValue(columns.values().stream().findFirst().get());

        filterTextField.textProperty().addListener((observableValue, oldValue, newValue) -> filterCollection());

        tableMovieTable.setRowFactory(entryTableView -> {
            TableRow<Map.Entry<Integer, Movie>> tableRow = new TableRow<>();
            MenuItem editItem = new MenuItem("Edit");
            editItem.setOnAction(e -> mainSceneController.setToUpdate(tableRow.getItem()));
            MenuItem removeItem = new MenuItem("Remove");
            removeItem.setOnAction(e -> mainSceneController.setToRemove(tableRow.getItem()));
            ContextMenu contextMenu = new ContextMenu(editItem, removeItem);

            SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(false);
            tableRow.itemProperty().addListener((observableValue, oldValue, newValue) -> booleanProperty.set(newValue != null && newValue.getValue().getOwner().equals(ControllersContext.getInstance().getUserName())));

            tableRow.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(tableRow.itemProperty()).and(booleanProperty))
                            .then(contextMenu)
                            .otherwise((ContextMenu) null)
            );
            return tableRow;
        });

    }

    private void initColumns(ObservableList<TableColumn<Map.Entry<Integer,Movie>, ?>> list) {
        initColumns("", list);
    }

    private void initColumns(String prefix, ObservableList<TableColumn<Map.Entry<Integer,Movie>, ?>> list) {
        for (TableColumn<Map.Entry<Integer,Movie>, ?> column : list) {
            if (column.getColumns().size() != 0) {
                initColumns(prefix + column.getText(), column.getColumns());
            } else {
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

    // FOR STREAM FILTERING
//    private void filterCollection() {
//        ObservableList<Map.Entry<Integer,Movie>> collectionList = ControllersContext.getInstance().getCollectionList();
//        if (filterTextField.getText().equals("")) {
//            tableMovieTable.setItems(collectionList);
//        } else {
//            tableMovieTable.setItems(FXCollections.observableList(collectionList.stream()
//                    .filter(m -> filterChoiceBox.getValue().getCellData(new AbstractMap.SimpleEntry<>(m.getKey(), m.getValue())).toString().toLowerCase().contains(filterTextField.getText()))
//                    .collect(Collectors.toList())));
//        }
//    }

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
