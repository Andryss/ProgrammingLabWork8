package client.controllers;

import client.RequestBuilder;
import general.Request;
import general.commands.BadArgumentsException;
import general.element.Movie;
import general.element.Person;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.Instant;
import java.util.*;

public class TableTabController {
    MainSceneController mainSceneController;
    void setLogic(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
    private final ControllersContext context = ControllersContext.getInstance();

    @FXML private Label filterLabelFirst;
    @FXML private ChoiceBox<TableColumn<Map.Entry<Integer,Movie>,?>> filterChoiceBox;
    @FXML private Label filterLabelSecond;
    @FXML private TextField filterTextField;

    @FXML private TableView<Map.Entry<Integer, Movie>> tableMovieTable;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> movieKeyColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> movieOwnerColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> movieNameColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> movieIdColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> coordinatesXColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> coordinatesYColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> movieCreationDateColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> movieOscarsCountColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> movieLengthColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Movie.MovieGenre> movieGenreColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Movie.MpaaRating> movieMpaaRatingColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> screenwriterNameColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, String> screenwriterBirthdayColumn;
    @FXML private TableColumn<Map.Entry<Integer,Movie>, Person.Color> screenwriterHairColorColumn;

    @FXML private Label updateLabelFirst;
    @FXML private Button updateButton;
    @FXML private Label updateLabelSecond;
    @FXML private CheckBox autoUpdateCheckBox;
    @FXML private Label updateErrLabel;

    private FilteredList<Map.Entry<Integer,Movie>> filteredList;
    private final LinkedHashMap<String, TableColumn<Map.Entry<Integer,Movie>, ?>> columns = new LinkedHashMap<>();

    Timeline autoUpdate = new Timeline(new KeyFrame(Duration.seconds(5), e -> updateCollection()));
    {
        autoUpdate.setCycleCount(Animation.INDEFINITE);
        autoUpdate.setAutoReverse(false);
    }

    @FXML
    private void initialize() {
        setColumnsProperties();

        initColumns(tableMovieTable.getColumns());

        setSortingAndFiltering();

        tableMovieTable.setRowFactory(entryTableView -> {
            TableRow<Map.Entry<Integer, Movie>> tableRow = new TableRow<>();
            MenuItem editItem = new MenuItem(context.getString("Edit"));
            editItem.setOnAction(e -> mainSceneController.setToUpdate(tableRow.getItem()));
            MenuItem removeItem = new MenuItem(context.getString("Remove"));
            removeItem.setOnAction(e -> mainSceneController.setToRemove(tableRow.getItem()));
            ContextMenu contextMenu = new ContextMenu(editItem, removeItem);

            SimpleBooleanProperty isCurrentUser = new SimpleBooleanProperty(false);
            tableRow.itemProperty().addListener((observableValue, oldValue, newValue) -> isCurrentUser.set(newValue != null && newValue.getValue().getOwner().equals(context.getUserName())));

            tableRow.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(tableRow.itemProperty()).and(isCurrentUser))
                            .then(contextMenu)
                            .otherwise((ContextMenu) null)
            );

            return tableRow;
        });

        autoUpdateCheckBox.selectedProperty().addListener((obs, o, n) -> {
            if (n) {
                autoUpdate.play();
            } else {
                autoUpdate.pause();
            }
        });

        context.localizer().resourceBundleProperty().addListener((obs, o, n) -> localize(n));
    }

    private void localize(ResourceBundle resourceBundle) {
        filterLabelFirst.setText(resourceBundle.getString("Filter by"));
        filterLabelSecond.setText(resourceBundle.getString("with value contains") + ":");
        filterTextField.setPromptText(resourceBundle.getString("anything"));
        updateLabelFirst.setText(resourceBundle.getString("Do you think here is an old collection?"));
        updateButton.setText(resourceBundle.getString("Update"));
        updateLabelSecond.setText(resourceBundle.getString("or set"));
        autoUpdateCheckBox.setText(resourceBundle.getString("Auto-update"));
        updateErrLabel.setText("");
    }

    private void setColumnsProperties() {
        movieKeyColumn.setCellValueFactory(e -> new SimpleStringProperty(context.localizer().getNumberFormat().format(e.getValue().getKey())));
        movieOwnerColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getOwner()));
        movieNameColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getName()));
        movieIdColumn.setCellValueFactory(e -> new SimpleStringProperty(context.localizer().getNumberFormat().format(e.getValue().getValue().getId())));
        coordinatesXColumn.setCellValueFactory(e -> new SimpleStringProperty(context.localizer().getNumberFormat().format(e.getValue().getValue().getCoordinates().getX())));
        coordinatesYColumn.setCellValueFactory(e -> new SimpleStringProperty(context.localizer().getNumberFormat().format(e.getValue().getValue().getCoordinates().getY())));
        movieCreationDateColumn.setCellValueFactory(e -> new SimpleStringProperty(context.localizer().getLongDateFormat().format(Date.from(Instant.from(e.getValue().getValue().getCreationDate())))));
        movieOscarsCountColumn.setCellValueFactory(e -> new SimpleStringProperty(context.localizer().getNumberFormat().format(e.getValue().getValue().getOscarsCount())));
        movieLengthColumn.setCellValueFactory(e -> new SimpleStringProperty(context.localizer().getNumberFormat().format(e.getValue().getValue().getLength())));
        movieGenreColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getValue().getGenre()));
        movieMpaaRatingColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getValue().getMpaaRating()));
        screenwriterNameColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getScreenwriter().getName()));
        screenwriterBirthdayColumn.setCellValueFactory(e -> {
            if (e.getValue().getValue().getScreenwriter().getBirthday() == null) {
                return new SimpleStringProperty(null);
            }
            return new SimpleStringProperty(context.localizer().getShortDateFormat().format(e.getValue().getValue().getScreenwriter().getBirthday()));
        });
        screenwriterHairColorColumn.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getValue().getScreenwriter().getHairColor()));

        context.localizer().availableLocaleProperty().addListener((obs, o, n) -> tableMovieTable.refresh());
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

    private void setSortingAndFiltering() {
        filteredList = new FilteredList<>(context.getCollectionList(), m -> true);
        SortedList<Map.Entry<Integer,Movie>> sortedList = new SortedList<>(filteredList);
        tableMovieTable.setItems(sortedList);
        sortedList.comparatorProperty().bind(tableMovieTable.comparatorProperty());

        // FOR STREAM FILTERING
        //context.getCollectionList().addListener((ListChangeListener<Map.Entry<Integer, Movie>>) change -> filterCollection());

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
    }

    private void filterCollection() {
        if (filterTextField.getText().equals("")) {
            filteredList.setPredicate(m -> true);
        } else {
            filteredList.setPredicate(m -> {
                if (filterChoiceBox.getValue().getCellData(m) == null) {
                    return false;
                }
                return filterChoiceBox.getValue().getCellData(m).toString().toLowerCase().contains(filterTextField.getText());
            });
        }
    }

    // FOR STREAM FILTERING
//    private void filterCollection() {
//        ObservableList<Map.Entry<Integer,Movie>> collectionList = context.getCollectionList();
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

    private void updateCollection() {
        try {
            context.sendToServer(
                    RequestBuilder.createNewRequest()
                            .setRequestType(Request.RequestType.UPDATE_COLLECTION)
                            .build()
            );
        } catch (SocketTimeoutException e) {
            context.showErrorWindow(
                    context.getString("Something wrong"),
                    context.getString(new BadArgumentsException("Server is not responding, try later or choose another server :(").getMessage())
            );
            autoUpdateCheckBox.setSelected(false);
        } catch (IOException | ClassNotFoundException e) {
            context.showUserError(e);
            autoUpdateCheckBox.setSelected(false);
        }
    }
}
