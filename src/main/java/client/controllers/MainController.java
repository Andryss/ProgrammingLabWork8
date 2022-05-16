package client.controllers;

import client.Application;
import client.ClientConnector;
import client.ClientController;
import client.ClientExecutor;
import general.Response;
import general.commands.Command;
import general.commands.CommandException;
import general.element.Movie;
import general.element.Person;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Optional;

public class MainController {
    private Application application;
    public void setLogic(Application application) {
        this.application = application;
    }

    @FXML private StackPane consoleStackPane;
    @FXML private BorderPane consoleMainPane;
    @FXML private ScrollPane consoleOneParamPane;
    @FXML private ScrollPane consoleMovieKeyParamPane;

    @FXML private ScrollPane consoleScrollPane;
    @FXML private TextFlow consoleTextFlow;
    @FXML private ComboBox<String> commandComboBox;

    @FXML private TableView<Movie> tableMovieTable;
    @FXML private TableColumn<Movie,String> movieOwnerColumn;
    @FXML private TableColumn<Movie,String> movieNameColumn;
    @FXML private TableColumn<Movie,Long> movieIdColumn;
    @FXML private TableColumn<Movie,Float> coordinatesXColumn;
    @FXML private TableColumn<Movie,Float> coordinatesYColumn;
    @FXML private TableColumn<Movie,ZonedDateTime> movieCreationDateColumn;
    @FXML private TableColumn<Movie,Long> movieOscarsCountColumn;
    @FXML private TableColumn<Movie,Integer> movieLengthColumn;
    @FXML private TableColumn<Movie,Movie.MovieGenre> movieGenreColumn;
    @FXML private TableColumn<Movie,Movie.MpaaRating> movieMpaaRatingColumn;
    @FXML private TableColumn<Movie,String> screenwriterNameColumn;
    @FXML private TableColumn<Movie,Date> screenwriterBirthdayColumn;
    @FXML private TableColumn<Movie,Person.Color> screenwriterHairColorColumn;

    private String currentCommand;

    public void initialize() {
        consoleScrollPane.vvalueProperty().bind(consoleTextFlow.heightProperty());

        commandComboBox.setItems(FXCollections.observableList(
                new ArrayList<>(ClientExecutor.getInstance().getCommandMap().keySet())
        ));

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

        selectMainPane();
    }

    void updateMovieTable(Hashtable<Integer,Movie> movieHashtable) {
        tableMovieTable.setItems(FXCollections.observableArrayList(movieHashtable.values()));
    }

    @FXML
    private void sendCommandMouseClicked(MouseEvent mouseEvent) {
        sendCommand();
    }

    private void sendCommand() {
        currentCommand = commandComboBox.getValue();
        if (!ClientExecutor.getInstance().hasCommand(currentCommand)) {
            showWarningWindow("Choose command", "You should choose command to send!");
            return;
        }
        Command.CommandType currentCommandType = ClientExecutor.getInstance().getCommandType(currentCommand);
        if (currentCommandType == Command.CommandType.NO_PARAMS) {
            noParamCommand();
        } else if (currentCommandType == Command.CommandType.ONE_PARAM) {
            selectOneParamPane();
        } else if (currentCommandType == Command.CommandType.MOVIE_KEY_PARAM) {
            selectMovieKeyPane();
        } else {
            showWarningWindow("Undefined command type", "Undefined type: " + currentCommandType);
        }
    }

    private void noParamCommand() {
        Optional<ButtonType> buttonType = showConfirmWindow("Are you sure?", "Are you sure to send command \"" + currentCommand + "\"");

        if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
            try {
                ClientController.getInstance().println(currentCommand);
                ClientExecutor.getInstance().executeCommand(currentCommand, new String[0]);
                Response response = ClientConnector.getInstance().sendToServer(ClientExecutor.getInstance().getRequest());
                if (response.getResponseType() == Response.ResponseType.EXECUTION_SUCCESSFUL) {
                    ClientController.getInstance().println(response.getMessage());
                } else if (response.getResponseType() == Response.ResponseType.EXECUTION_FAILED) {
                    ClientController.getInstance().printlnErr(response.getMessage());
                } else {
                    ClientController.getInstance().printlnErr("Server has wrong logic: unexpected \"" +
                            response.getResponseType() +
                            "\"");
                }
            } catch (SocketTimeoutException e) {
                ClientController.getInstance().printlnErr("Server isn't responding (try again later)");
            } catch (IOException | ClassNotFoundException | CommandException e) {
                ClientController.getInstance().printlnErr(e.getMessage());
            }
        }
    }

    @FXML
    private void returnToTheMainPaneMouseClicked(MouseEvent mouseEvent) {
        returnToTheMainPane();
    }

    private void returnToTheMainPane() {
        clearOneParamPane();
        clearMovieKeyParamPane();
        selectMainPane();
    }

    @FXML private TextField oneParamTextField;
    private void clearOneParamPane() {
        oneParamTextField.clear();
    }

    @FXML private TextField movieKeyTextField;
    @FXML private TextField movieNameTextField;
    @FXML private TextField coordinatesXTextField;
    @FXML private TextField coordinatesYTextField;
    @FXML private TextField movieOscarsCountTextField;
    @FXML private TextField movieLengthTextField;
    @FXML private ChoiceBox<String> movieGenreChoiceBox;
    @FXML private ChoiceBox<String> movieMpaaRatingChoiceBox;
    @FXML private TextField screenwriterNameTextField;
    @FXML private TextField screenwriterBirthdayTextField;
    @FXML private ChoiceBox<String> screenwriterHairColorChoiceBox;
    private void clearMovieKeyParamPane() {
        movieKeyTextField.clear();
        movieNameTextField.clear();
        coordinatesXTextField.clear();
        coordinatesYTextField.clear();
        movieOscarsCountTextField.clear();
        movieLengthTextField.clear();
        movieGenreChoiceBox.setValue("");
        movieMpaaRatingChoiceBox.setValue("");
        screenwriterNameTextField.clear();
        screenwriterBirthdayTextField.clear();
        screenwriterHairColorChoiceBox.setValue("");
    }

    private void selectMainPane() {
        popupPane(consoleMainPane);
    }

    private void selectOneParamPane() {
        popupPane(consoleOneParamPane);
    }

    private void selectMovieKeyPane() {
        popupPane(consoleMovieKeyParamPane);
    }

    private void popupPane(Node e) {
        consoleStackPane.getChildren().remove(e);
        consoleStackPane.getChildren().add(e);
        consoleStackPane.getChildren().forEach(n -> n.setDisable(true));
        e.setDisable(false);
    }

    private final Alert confirmWindow = new Alert(Alert.AlertType.CONFIRMATION);
    private Optional<ButtonType> showConfirmWindow(String title, String contextText) {
        confirmWindow.setTitle(title);
        confirmWindow.setHeaderText(null);
        confirmWindow.setContentText(contextText);
        return confirmWindow.showAndWait();
    }

    private final Alert warningWindow = new Alert(Alert.AlertType.WARNING);
    private void showWarningWindow(String title, String contextText) {
        warningWindow.setTitle(title);
        warningWindow.setHeaderText(null);
        warningWindow.setContentText(contextText);
        warningWindow.show();
    }

    public TextFlow getConsoleTextArea() {
        return consoleTextFlow;
    }
}
