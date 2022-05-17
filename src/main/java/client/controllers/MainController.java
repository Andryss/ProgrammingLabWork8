package client.controllers;

import client.*;
import general.ClientContext;
import general.Request;
import general.Response;
import general.commands.BadArgumentsException;
import general.commands.Command;
import general.commands.CommandException;
import general.commands.ElementCommand;
import general.element.Coordinates;
import general.element.FieldException;
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
import java.util.*;

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

    @FXML private Label oneParamLabel;
    @FXML private TextField oneParamTextField;
    @FXML private Label oneParamErrLabel;
    @FXML private Button oneParamConfirmButton;

    @FXML private Label movieKeyLabel;
    @FXML private TextField movieKeyTextField;
    @FXML private Label movieKeyErrLabel;
    @FXML private TextField movieOwnerTextField;
    @FXML private TextField movieNameTextField;
    @FXML private Label movieNameErrLabel;
    @FXML private TextField coordinatesXTextField;
    @FXML private Label coordinatesXErrLabel;
    @FXML private TextField coordinatesYTextField;
    @FXML private Label coordinatesYErrLabel;
    @FXML private TextField movieCreationDateTextField;
    @FXML private TextField movieOscarsCountTextField;
    @FXML private Label movieOscarsCountErrLabel;
    @FXML private TextField movieLengthTextField;
    @FXML private Label movieLengthErrLabel;
    @FXML private ChoiceBox<Movie.MovieGenre> movieGenreChoiceBox;
    @FXML private Label movieGenreErrLabel;
    @FXML private ChoiceBox<Movie.MpaaRating> movieMpaaRatingChoiceBox;
    @FXML private Label movieMpaaRatingErrLabel;
    @FXML private TextField screenwriterNameTextField;
    @FXML private Label screenwriterNameErrLabel;
    @FXML private TextField screenwriterBirthdayTextField;
    @FXML private Label screenwriterBirthdayErrLabel;
    @FXML private ChoiceBox<Person.Color> screenwriterHairColorChoiceBox;
    @FXML private Label screenwriterHairColorErrLabel;
    @FXML private Button movieKeyParamConfirmButton;
    @FXML private Label movieKeyParamConfirmErrLabel;

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

    private String currentUserName;
    private ClientExecutor.CommandContainer currentCommand;
    private final ClientContextImpl clientContext = new ClientContextImpl();
    private Integer currentKey;
    private Movie currentMovie;

    // TODO: add user name setting
    void setCurrentUserName (String currentUserName) {
        this.currentUserName = currentUserName;
        movieOwnerTextField.setText(currentUserName);
    }

    public void initialize() {
        consoleScrollPane.vvalueProperty().bind(consoleTextFlow.heightProperty());
        commandComboBox.setItems(FXCollections.observableList(
                new ArrayList<>(ClientExecutor.getInstance().getCommandMap().keySet())
        ));

        movieGenreChoiceBox.setItems(FXCollections.observableList(Arrays.asList(Movie.MovieGenre.values())));
        movieMpaaRatingChoiceBox.setItems(FXCollections.observableList(Arrays.asList(Movie.MpaaRating.values())));
        screenwriterHairColorChoiceBox.setItems(FXCollections.observableList(Arrays.asList(Person.Color.values())));

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
        String commandName = commandComboBox.getValue();
        if (!ClientExecutor.getInstance().hasCommand(commandName)) {
            showWarningWindow("Choose command", "You should choose command to send!");
            return;
        }
        currentCommand = ClientExecutor.getInstance().getCommandContainer(commandName);
        Command.CommandType currentCommandType = currentCommand.getCommandType();
        if (currentCommandType == Command.CommandType.NO_PARAMS) {
            noParamCommand();
        } else if (currentCommandType == Command.CommandType.ONE_PARAM) {
            oneParamCommand();
        } else if (currentCommandType == Command.CommandType.MOVIE_KEY_PARAM) {
            movieKeyParamCommand();
        } else {
            showWarningWindow("Undefined command type", "Undefined type: " + currentCommandType);
        }
    }

    private void noParamCommand() {
        Optional<ButtonType> buttonType = showConfirmWindow("Are you sure?", "Are you sure to send command \"" + currentCommand.getCommandName() + "\"?");

        if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {

            createRequestAndReceiveResponse();

        }
    }

    private void oneParamCommand() {
        oneParamLabel.setText("Enter " + currentCommand.getParamName() + ":");
        selectOneParamPane();
    }

    @FXML
    private void oneParamConfirmMouseClicked(MouseEvent mouseEvent) {
        oneParamConfirm();
    }

    private void oneParamConfirm() {
        clientContext.setParam(oneParamTextField.getText());
        clientContext.setMovie(null);
        clientContext.setMovieKey(null);
        try {
            currentCommand.getCommand().setGUIArgs(clientContext);
            Optional<ButtonType> buttonType = showConfirmWindow("Are you sure?", "Are you sure to send command \"" + currentCommand.getCommandName() + "\" with given argument?");
            if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                createRequestAndReceiveResponse();
                returnToTheMainPane();
            }
        } catch (BadArgumentsException e) {
            oneParamErrLabel.setText(e.getMessage());
        }
    }

    private void movieKeyParamCommand() {
        currentMovie = new Movie();
        currentMovie.setCoordinates(new Coordinates());
        currentMovie.setScreenwriter(new Person());
        movieKeyLabel.setText("Movie " + currentCommand.getParamName() + ":");
        movieCreationDateTextField.setText(currentMovie.getCreationDate().toString());
        selectMovieKeyPane();
    }

    @FXML
    private void checkMovieKeyMouseClicked(MouseEvent mouseEvent) {
        checkMovieKey();
    }

    private void checkMovieKey() {
        if (checkMovieKeyByType()) {
            if (currentCommand.getCommand() instanceof ElementCommand) {
                try {
                    Response response = ClientConnector.getInstance().sendToServer(RequestBuilder.createNewRequest()
                            .setRequestType(Request.RequestType.CHECK_ELEMENT)
                            .setCheckingIndex(currentKey)
                            .build()
                    );
                    ElementCommand elementCommand = (ElementCommand) currentCommand.getCommand();
                    elementCommand.checkElement(response);
                    movieKeyErrLabel.setText("OK");
                } catch (IOException | ClassNotFoundException | BadArgumentsException e) {
                    movieKeyErrLabel.setText(e.getMessage());
                }
            } else {
                movieKeyErrLabel.setText(currentCommand.getCommandName() + " not extends ElementCommand");
            }
        }
    }

    @FXML
    private void movieKeyConfirmMouseClicked(MouseEvent mouseEvent) {
        movieKeyConfirm();
    }

    private void movieKeyConfirm() {
        if (checkMovie()) {
            clientContext.setParam(null);
            clientContext.setMovie(currentMovie);
            clientContext.setMovieKey(currentKey);
            try {
                currentCommand.getCommand().setGUIArgs(clientContext);
                Optional<ButtonType> buttonType = showConfirmWindow("Are you sure?", "Are you sure to send command \"" + currentCommand.getCommandName() + "\" with given arguments?");
                if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                    createRequestAndReceiveResponse();
                    returnToTheMainPane();
                }
            } catch (BadArgumentsException e) {
                movieKeyParamConfirmErrLabel.setText(e.getMessage());
            }
        }
    }

    private boolean checkMovieKeyByType() {
        try {
            currentKey = Integer.parseInt(movieKeyTextField.getText());
            return true;
        } catch (NumberFormatException e) {
            movieKeyErrLabel.setText("Value must be integer");
            return false;
        }
    }

    private boolean checkMovie() {
        boolean allOk = checkMovieKeyByType();
        try {
            currentMovie.setName(movieNameTextField.getText());
            movieNameErrLabel.setText("");
        } catch (FieldException e) {
            movieNameErrLabel.setText(e.getMessage()); allOk = false;
        }
        try {
            currentMovie.getCoordinates().setX(coordinatesXTextField.getText());
            coordinatesXErrLabel.setText("");
        } catch (FieldException e) {
            coordinatesXErrLabel.setText(e.getMessage()); allOk = false;
        }
        try {
            currentMovie.getCoordinates().setY(coordinatesYTextField.getText());
            coordinatesYErrLabel.setText("");
        } catch (FieldException e) {
            coordinatesYErrLabel.setText(e.getMessage()); allOk = false;
        }
        try {
            currentMovie.setOscarsCount(movieOscarsCountTextField.getText());
            movieOscarsCountErrLabel.setText("");
        } catch (FieldException e) {
            movieOscarsCountErrLabel.setText(e.getMessage()); allOk = false;
        }
        try {
            currentMovie.setLength(movieLengthTextField.getText());
            movieLengthErrLabel.setText("");
        } catch (FieldException e) {
            movieLengthErrLabel.setText(e.getMessage()); allOk = false;
        }
        try {
            currentMovie.setGenre((movieGenreChoiceBox.getValue() == null) ?
                    null : movieGenreChoiceBox.getValue().toString());
            movieGenreErrLabel.setText("");
        } catch (FieldException e) {
            movieGenreErrLabel.setText(e.getMessage()); allOk = false;
        }
        try {
            currentMovie.setMpaaRating((movieMpaaRatingChoiceBox.getValue() == null) ?
                    null : movieMpaaRatingChoiceBox.getValue().toString());
            movieMpaaRatingErrLabel.setText("");
        } catch (FieldException e) {
            movieMpaaRatingErrLabel.setText(e.getMessage()); allOk = false;
        }
        try {
            currentMovie.getScreenwriter().setName(screenwriterNameTextField.getText());
            screenwriterNameErrLabel.setText("");
        } catch (FieldException e) {
            screenwriterNameErrLabel.setText(e.getMessage()); allOk = false;
        }
        try {
            currentMovie.getScreenwriter().setBirthday(screenwriterBirthdayTextField.getText());
            screenwriterBirthdayErrLabel.setText("");
        } catch (FieldException e) {
            screenwriterBirthdayErrLabel.setText(e.getMessage()); allOk = false;
        }
        try {
            currentMovie.getScreenwriter().setHairColor((screenwriterHairColorChoiceBox.getValue() == null) ?
                    null : screenwriterHairColorChoiceBox.getValue().toString());
            screenwriterHairColorErrLabel.setText("");
        } catch (FieldException e) {
            screenwriterHairColorErrLabel.setText(e.getMessage()); allOk = false;
        }
        return allOk;
    }

    private void createRequestAndReceiveResponse() {
        try {
            ClientController.getInstance().println(currentCommand.getCommandName());
            ClientExecutor.getInstance().executeCommand(currentCommand.getCommandName());
            Response response = ClientConnector.getInstance().sendToServer(ClientExecutor.getInstance().getRequest());
            updateMovieTable(response.getHashtable());
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

    @FXML
    private void returnToTheMainPaneMouseClicked(MouseEvent mouseEvent) {
        returnToTheMainPane();
    }

    private void returnToTheMainPane() {
        clearOneParamPane();
        clearMovieKeyParamPane();
        selectMainPane();
    }

    private void clearOneParamPane() {
        oneParamTextField.clear();
    }

    private void clearMovieKeyParamPane() {
        movieKeyTextField.clear();
        movieNameTextField.clear();
        coordinatesXTextField.clear();
        coordinatesYTextField.clear();
        movieOscarsCountTextField.clear();
        movieLengthTextField.clear();
        movieGenreChoiceBox.setValue(null);
        movieMpaaRatingChoiceBox.setValue(Movie.MpaaRating.G);
        screenwriterNameTextField.clear();
        screenwriterBirthdayTextField.clear();
        screenwriterHairColorChoiceBox.setValue(null);
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

    private static class ClientContextImpl implements ClientContext {
        private String param;
        private Integer movieKey;
        private Movie movie;

        public ClientContextImpl() {}

        private void setParam(String param) {
            this.param = param;
        }
        private void setMovieKey(Integer movieKey) {
            this.movieKey = movieKey;
        }
        private void setMovie(Movie movie) {
            this.movie = movie;
        }

        @Override
        public String getParam() {
            return param;
        }
        @Override
        public Integer getMovieKey() {
            return movieKey;
        }
        @Override
        public Movie getMovie() {
            return movie;
        }
    }
}
