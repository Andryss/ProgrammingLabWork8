package client.controllers;

import client.Application;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class RegistrationController {
    private Application application;
    public void setLogic(Application application) {
        this.application = application;
    }

    @FXML
    private void goToAuthPage(MouseEvent mouseEvent) {
        application.setScene(Application.AppScene.AUTHORIZATION_SCENE);
    }
}
