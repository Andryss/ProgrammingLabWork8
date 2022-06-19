package client;

import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

/**
 * <p>ClientController implements (1) and (4) steps in ClientManager</p>
 * <p>The main idea of this class is "to speak" with the user</p>
 */
public class ClientControllerImpl implements ClientControllerModule {
    private static final ClientControllerImpl instance = new ClientControllerImpl();
    private final Scanner reader = new Scanner(System.in);
    private TextFlow textFlow;
    private final ArrayList<String> baseText = new ArrayList<>();

    private ClientControllerImpl() {}

    public static ClientControllerImpl getInstance() {
        return instance;
    }

    @Override
    public void initialize() {
        textFlow.getChildren().clear();
        for (String line : baseText) {
            println(line);
        }
        println("Hi! This is a simple client-server program for working with collection.\n" +
                "I'm waiting for your commands (type \"help\" for list of available commands).");
    }

    @Override
    public void setProperties(Properties properties) {
        // NOTHING HERE
    }

    @Override
    public void setWritableObject(Object object) throws Exception {
        if (object instanceof TextFlow) {
            textFlow = (TextFlow) object;
        } else {
            throw new Exception("WritableObject is not instance of TextFlow");
        }
    }

    @Override
    public void addBaseText(String... text) {
        baseText.addAll(Arrays.asList(text));
    }


    private void print0(Text text) {
        textFlow.getChildren().add(text);
    }
    @Override
    public void print(String line) {
        print0(new Text(line));
    }
    @Override
    public void printGood(String line) {
        Text text = new Text(line);
        text.setStyle("-fx-fill: GREEN");
        print0(text);
    }
    @Override
    public void printErr(String line) {
        Text text = new Text(line);
        text.setStyle("-fx-fill: RED");
        print0(text);
    }

    @Override
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
