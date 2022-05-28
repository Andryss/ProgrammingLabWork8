package client.controllers;

import client.ClientExecutor;
import client.RequestBuilder;
import general.Request;
import general.Response;
import general.commands.BadArgumentsException;
import general.commands.Command;
import general.commands.ElementCommand;
import general.element.Coordinates;
import general.element.FieldException;
import general.element.Movie;
import general.element.Person;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class MovieKeyParamPaneController {
    private ConsoleTabController consoleTabController;
    void setLogic(ConsoleTabController consoleTabController) {
        this.consoleTabController = consoleTabController;
    }

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
    @FXML private ChoiceBox<Movie.MpaaRating> movieMpaaRatingChoiceBox;
    @FXML private Label movieMpaaRatingErrLabel;
    @FXML private TextField screenwriterNameTextField;
    @FXML private Label screenwriterNameErrLabel;
    @FXML private TextField screenwriterBirthdayTextField;
    @FXML private Label screenwriterBirthdayErrLabel;
    @FXML private ChoiceBox<Person.Color> screenwriterHairColorChoiceBox;
    @FXML private Button movieKeyParamConfirmButton;
    @FXML private Label movieKeyParamConfirmErrLabel;

    private Integer currentKey;
    private Movie currentMovie;

    @FXML
    private void initialize() {
        ControllersContext.getInstance().getCurrentCommandProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.getCommandType() == Command.CommandType.MOVIE_KEY_PARAM) {
                currentMovie = new Movie();
                currentMovie.setCoordinates(new Coordinates());
                currentMovie.setScreenwriter(new Person());
                movieKeyLabel.setText("Movie " + newValue.getParamName() + ":");
                movieCreationDateTextField.setText(currentMovie.getCreationDate().toString());
            }
        }));
        ControllersContext.getInstance().getUserNameProperty().addListener((observableValue, oldValue, newValue) -> movieOwnerTextField.setText(newValue));

        movieGenreChoiceBox.setItems(FXCollections.observableList(Arrays.asList(Movie.MovieGenre.values())));
        movieMpaaRatingChoiceBox.setItems(FXCollections.observableList(Arrays.asList(Movie.MpaaRating.values())));
        screenwriterHairColorChoiceBox.setItems(FXCollections.observableList(Arrays.asList(Person.Color.values())));
    }

    @FXML
    private void checkMovieKeyMouseClicked(MouseEvent mouseEvent) {
        checkMovieKey();
    }

    private void checkMovieKey() {
        if (checkMovieKeyByType()) {
            if (ControllersContext.getInstance().getCurrentCommand().getCommand() instanceof ElementCommand) {
                try {
                    Response response = ControllersContext.getInstance().sendToServer(RequestBuilder.createNewRequest()
                            .setRequestType(Request.RequestType.CHECK_ELEMENT)
                            .setCheckingIndex(currentKey)
                            .build()
                    );
                    ElementCommand elementCommand = (ElementCommand) ControllersContext.getInstance().getCurrentCommand().getCommand();
                    elementCommand.checkElement(response);
                    movieKeyErrLabel.setText("OK");
                } catch (IOException | ClassNotFoundException | BadArgumentsException e) {
                    movieKeyErrLabel.setText(e.getMessage());
                }
            } else {
                movieKeyErrLabel.setText(ControllersContext.getInstance().getCurrentCommand().getCommandName() + " not extends ElementCommand");
            }
        }
    }

    @FXML
    private void returnToTheMainPaneMouseClicked(MouseEvent mouseEvent) {
        returnToTheMainPane();
    }

    private void returnToTheMainPane() {
        consoleTabController.returnToTheMainPane();
    }

    @FXML
    private void movieKeyConfirmMouseClicked(MouseEvent mouseEvent) {
        movieKeyConfirm();
    }

    private void movieKeyConfirm() {
        if (checkMovie()) {
            getClientContext().setParam(null).setMovie(currentMovie).setMovieKey(currentKey);
            try {
                ClientExecutor.CommandContainer currentCommand = ControllersContext.getInstance().getCurrentCommand();
                currentCommand.getCommand().setGUIArgs(getClientContext());
                Optional<ButtonType> buttonType = ControllersContext.getInstance().showConfirmWindow(
                        "Are you sure?", "Are you sure to send command \"" + currentCommand.getCommandName() + "\" with given arguments?"
                );
                if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                    consoleTabController.createRequestAndReceiveResponse();
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
        } catch (FieldException e) {
            // never
        }
        try {
            currentMovie.setMpaaRating((movieMpaaRatingChoiceBox.getValue() == null) ?
                    null : movieMpaaRatingChoiceBox.getValue().toString());
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
        } catch (FieldException e) {
            // never
        }
        return allOk;
    }

    void setToUpdate(Map.Entry<Integer, Movie> entry) {
        movieKeyTextField.setText(entry.getKey().toString());
        movieNameTextField.setText(entry.getValue().getName());
        coordinatesXTextField.setText(String.valueOf(entry.getValue().getCoordinates().getX()));
        coordinatesYTextField.setText(String.valueOf(entry.getValue().getCoordinates().getY()));
        movieCreationDateTextField.setText(entry.getValue().getCreationDate().toString());
        movieOscarsCountTextField.setText(String.valueOf(entry.getValue().getOscarsCount()));
        movieLengthTextField.setText(String.valueOf(entry.getValue().getLength()));
        movieGenreChoiceBox.setValue(entry.getValue().getGenre());
        movieMpaaRatingChoiceBox.setValue(entry.getValue().getMpaaRating());
        screenwriterNameTextField.setText(entry.getValue().getScreenwriter().getName());
        screenwriterBirthdayTextField.setText(entry.getValue().getScreenwriter().getBirthdayString());
        screenwriterHairColorChoiceBox.setValue(entry.getValue().getScreenwriter().getHairColor());
    }

    void clearPane() {
        movieKeyTextField.clear();
        movieKeyErrLabel.setText("");
        movieNameTextField.clear();
        movieNameErrLabel.setText("");
        coordinatesXTextField.clear();
        coordinatesXErrLabel.setText("");
        coordinatesYTextField.clear();
        coordinatesYErrLabel.setText("");
        movieOscarsCountTextField.clear();
        movieOscarsCountErrLabel.setText("");
        movieLengthTextField.clear();
        movieLengthErrLabel.setText("");
        movieGenreChoiceBox.setValue(null);
        movieMpaaRatingChoiceBox.setValue(null);
        screenwriterNameTextField.clear();
        screenwriterNameErrLabel.setText("");
        screenwriterBirthdayTextField.clear();
        screenwriterBirthdayErrLabel.setText("");
        screenwriterHairColorChoiceBox.setValue(null);
    }

    private ConsoleTabController.ClientContextImpl getClientContext() {
        return consoleTabController.getClientContext();
    }
}
