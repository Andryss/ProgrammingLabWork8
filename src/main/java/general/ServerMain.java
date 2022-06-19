package general;

import general.element.FieldException;
import server.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServerMain {

    public static void main(String[] args) {
        ServerModuleHolder moduleHolder = ServerModuleHolder.getInstance();
        ServerControllerModule controllerModule = moduleHolder.getControllerModule();

        Properties properties = new Properties();
        try {
            File props = new File("server.properties");
            if (!props.createNewFile()) {
                if (!props.isFile() || !props.canRead()) {
                    throw new IOException("Can't read anything from \"server.properties\" file");
                }
            }
            properties.load(new FileReader(props));
        } catch (FileNotFoundException e) {
            controllerModule.error("File \"server.properties\" with properties not found");
            return;
        } catch (IOException e) {
            controllerModule.error(e.getMessage());
            return;
        }


        try {
            ServerManager.getInstance().run(properties);
        } catch (FieldException e) {
            controllerModule.error("Problems with Movie File: " + e.getMessage());
            System.exit(0);
        } catch (Throwable e) {
            controllerModule.error(e.getMessage());
            System.exit(0);
        }
    }
}
