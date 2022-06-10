package client;

import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>ClientController implements (1) and (4) steps in ClientManager</p>
 * <p>The main idea of this class is "to speak" with the user</p>
 */
public class ClientController {
    private static final ClientController instance = new ClientController();
    private final Scanner reader = new Scanner(System.in);
    private TextFlow textFlow;
    private final ArrayList<String> baseText = new ArrayList<>();
    public void setTextFlow(TextFlow textFlow) {this.textFlow = textFlow;}

    private ClientController() {}

    public static ClientController getInstance() {
        return instance;
    }

    public void initialize() {
        textFlow.getChildren().clear();
        for (String line : baseText) {
            println(line);
        }
        println("Hi! This is a simple client-server program for working with collection.\n" +
                "I'm waiting for your commands (type \"help\" for list of available commands).");
    }

    void addBaseText(String... text) {
        baseText.addAll(Arrays.asList(text));
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
    public void printlnCommand(String line) {
        println(" >>>>> " + line);
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

    public void saveHistoryToFile(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (Node node : textFlow.getChildren()) {
                if (node instanceof Text) {
                    Text text = (Text) node;
                    writer.write(text.getText());
                }  // else sadness :(
            }
            writer.flush();
        }
    }
}
