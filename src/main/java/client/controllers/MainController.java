package client.controllers;

import client.Application;
import client.ClientConnector;
import client.ClientController;
import client.ClientExecutor;
import general.Response;
import general.commands.Command;
import general.commands.CommandException;
import general.element.Coordinates;
import general.element.FieldException;
import general.element.Movie;
import general.element.Person;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.ZonedDateTime;
import java.util.*;

public class MainController {
    private Application application;
    public void setLogic(Application application) {
        this.application = application;
    }

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
    }

    void updateMovieTable(Hashtable<Integer,Movie> movieHashtable) {
        tableMovieTable.setItems(FXCollections.observableArrayList(movieHashtable.values()));
    }

    private void sendCommand() {
        ///
    }

    private void noParamCommand(String commandName) {
        Alert confirmWindow = new Alert(Alert.AlertType.CONFIRMATION);
        confirmWindow.setTitle("Are you sure?");
        confirmWindow.setHeaderText(null);
        confirmWindow.setContentText("Are you sure to send command \"" + commandName + "\"");

        Optional<ButtonType> buttonType = confirmWindow.showAndWait();
        if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
            try {
                ClientController.getInstance().println(commandName);
                ClientExecutor.getInstance().executeCommand(commandName, new String[0]);
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

//    @FXML
//    private void execInputMouseClicked(MouseEvent mouseEvent) {
//        execInput();
//    }
//
//    @FXML
//    private void execInputKeyReleased(KeyEvent keyEvent) {
//        if (keyEvent.getCode() == KeyCode.ENTER) {
//            execInput();
//        }
//    }
//
//    private void execInput() {
//        try {
//            ClientController.getInstance().println(consoleTextField.getText());
//            ClientExecutor.getInstance().parseCommand(consoleTextField.getText());
//            consoleTextField.clear();
//            Response response = ClientConnector.getInstance().sendToServer(ClientExecutor.getInstance().getRequest());
//            if (response.getResponseType() == Response.ResponseType.EXECUTION_SUCCESSFUL) {
//                ClientController.getInstance().println(response.getMessage());
//            } else if (response.getResponseType() == Response.ResponseType.EXECUTION_FAILED) {
//                ClientController.getInstance().printlnErr(response.getMessage());
//            } else {
//                ClientController.getInstance().printlnErr("Server has wrong logic: unexpected \"" +
//                        response.getResponseType() +
//                        "\"");
//            }
//        } catch (SocketTimeoutException e) {
//            ClientController.getInstance().printlnErr("Server isn't responding (try again later)");
//        } catch (IOException | ClassNotFoundException | CommandException e) {
//            ClientController.getInstance().printlnErr(e.getMessage());
//        }
//    }

    public TextFlow getConsoleTextArea() {
        return consoleTextFlow;
    }
}
