package server;

import general.element.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Properties;

/**
 * ServerController logging into file and terminal, implements simple console commands for server
 */
public class ServerControllerImpl implements ServerControllerModule {
    private static final ServerControllerImpl instance = new ServerControllerImpl();

    private ServerCollectionManagerModule collectionManagerModule;
    private ServerExecutorModule executorModule;
    private ServerHistoryManagerModule historyManagerModule;

    private final Logger logger = LogManager.getLogger();

    private ServerControllerImpl() {}

    public static ServerControllerImpl getInstance() {
        return instance;
    }

    @Override
    public void initialize() throws Exception {
        ServerModuleHolder moduleHolder = ServerModuleHolder.getInstance();
        collectionManagerModule = moduleHolder.getCollectionManagerModule();
        executorModule = moduleHolder.getExecutorModule();
        historyManagerModule = moduleHolder.getHistoryManagerModule();
    }
    @Override
    public void setProperties(Properties properties) throws Exception {
        // nothing
    }
    @Override
    public void close() {
        // nothing
    }

    public void info(String message) {
        logger.info(message);
    }

    public void error(String message) {
        logger.error("ERROR: " + message);
    }

    public void run() {
        info("Ready for commands (type \"?\" or \"help\" for list of commands)");

        while (true) {
            try {
                String[] args = System.console().readLine().split("\\s");
                info(Arrays.toString(args) + " command");

                switch (args[0]) {
                    case "?":
                    case "help":
                        info("Available commands:\n" +
                                "exit - shut down server\n" +
                                "usl[ogout] <name> - logout user\n" +
                                "usb[an] <name> - ban and logout user\n" +
                                "tbs[how] - print authorized users and collections\n" +
                                "usr[eg] <name> <pass> - register new user\n" +
                                "elr[emove] <key> - remove movie\n" +
                                "elc[lear] - clear movie collection\n" +
                                "usclh[istory] <name> - clear user history\n" +
                                "tbdropcreate - drop and create tables");
                        break;

                    case "exit":
                        System.exit(0);

                    case "usl":
                    case "uslogout":
                        executorModule.logoutUser(args[1]);
                        break;

                    case "usb":
                    case "usban":
                        executorModule.logoutUser(args[1]);
                        collectionManagerModule.removeUser(args[1]);
                        break;

                    case "tbs":
                    case "tbshow":
                        executorModule.printUsers();
                        collectionManagerModule.printTables();
                        break;

                    case "usr":
                    case "usreg":
                        collectionManagerModule.registerUser(new UserProfile(args[1], args[2]));
                        break;

                    case "elr":
                    case "elremove":
                        try {
                            collectionManagerModule.removeMovie(Integer.parseInt(args[1]));
                        } catch (NumberFormatException e) {
                            error(e.getMessage());
                        }
                        break;

                    case "elc":
                    case "elclear":
                        collectionManagerModule.removeAllMovies();
                        break;

                    case "usclh":
                    case "usclhistory":
                        historyManagerModule.clearUserHistory(args[1]);
                        break;

                    case "tbdropcreate":
                        try {
                            executorModule.clearAuthorizedUsers();
                            collectionManagerModule.clearAllData();
                        } catch (Exception e) {
                            error(e.getMessage());
                        }
                        break;

                    default:
                        info("Undefined console command \"" + args[0] + "\"");
                }
            } catch (IndexOutOfBoundsException e) {
                error("Incorrect amount of arguments: " + e.getMessage());
            } catch (NullPointerException e) {
                error("Found EOF");
                break;
            }
        }
    }
}
