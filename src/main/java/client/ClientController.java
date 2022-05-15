package client;

import client.controllers.MainController;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * <p>ClientController implements (1) and (4) steps in ClientManager</p>
 * <p>The main idea of this class is "to speak" with the user</p>
 */
public class ClientController {
    private static final ClientController instance = new ClientController();
    private final Scanner reader = new Scanner(System.in);
    private TextFlow textFlow;
    void setTextFlow(TextFlow textFlow) {this.textFlow = textFlow;}

    private ClientController() {}

    public static ClientController getInstance() {
        return instance;
    }

    void initialize() {
        Text text = new Text("Hi! This is a simple client-server program for working with collection.\n" +
                "I'm waiting for your commands (type \"help\" for list of available commands).\n");
        text.setStyle("-fx-fill: CYAN");
        print0(text);
    }

    public String readLine() {
        return reader.nextLine();
    }

    private String readPrivateLine() {
        return String.valueOf(System.console().readPassword());
    }

    private void print0(Text text) {
        textFlow.getChildren().add(text);
    }
    public void println(String line) {
        print0(new Text(line + "\n"));
    }
    public void print(String line) {
        print0(new Text(line));
    }
    public void printlnGood(String line) {
        Text text = new Text(line + "\n");
        text.setStyle("-fx-fill: GREEN");
        print0(text);
    }
    public void printlnErr(String line) {
        Text text = new Text(line + "\n");
        text.setStyle("-fx-fill: RED");
        print0(text);
    }

    String readLogin() {
        print("Enter user login: ");
        return readLine().trim();
    }

    String readPassword() {
        print("Enter user password: ");
        return readPrivateLine().trim();
    }
}
