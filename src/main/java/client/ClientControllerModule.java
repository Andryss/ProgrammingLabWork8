package client;

import java.io.File;
import java.io.IOException;

public interface ClientControllerModule extends ClientBaseModule {

    void setWritableObject(Object object) throws Exception;

    void addBaseText(String... text);

    void print(String line);
    void printGood(String line);
    void printErr(String line);

    default void println(String line) {
        print(line + "\n");
    }
    default void printlnCommand(String line) {
        println(" >>>>> " + line);
    }
    default void printlnGood(String line) {
        printGood(line + "\n");
    }
    default void printlnErr(String line) {
        printErr(line + "\n");
    }

    void saveHistoryToFile(File file) throws IOException;

}
