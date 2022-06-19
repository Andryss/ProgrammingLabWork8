package general.commands;

import client.ClientExecutorImpl;
import client.ClientExecutorModule;
import client.ClientModuleHolder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

/**
 * This class has only one method, which walk through general.commands directory and fill command map with commands
 */
public abstract class CommandFiller {

    public static void fillCommandMap() throws IOException, CommandException {
        HashMap<String, ClientExecutorImpl.CommandContainer> commandMap = ClientModuleHolder.getInstance().getClientExecutorModule().getCommandMap();
        try {
            List<String> classNames;
            try {
                // Try if we are in JAR file
                classNames = new JarFile(new File(ClientExecutorImpl.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())).stream()
                        .map(ZipEntry::toString)
                        .filter(s -> s.startsWith("general/commands/") && s.endsWith(".class"))
                        .map(s -> s.substring(0, s.length() - 6).replaceAll("/", "."))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                // But maybe we are in .class file
                Path path = new File(ClientExecutorImpl.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toPath();
                classNames = Files.walk(new File(path + "\\general\\commands\\").toPath())
                        .map(Path::toString)
                        .filter(s -> s.endsWith(".class"))
                        .map(s -> s.substring(path.toString().length() + 1, s.length() - 6).replaceAll("\\\\", "."))
                        .collect(Collectors.toList());
            }
            for (String className : classNames) {
                try {
                    Class<?> cls = Class.forName(className);
                    if (!isImplementCommand(cls)) {
                        continue;
                    }
                    Constructor<?> constructor = cls.getDeclaredConstructor(String.class);
                    ParseCommand command = constructor.getDeclaredAnnotation(ParseCommand.class);
                    if (command == null) {
                        continue;
                    }
                    if (commandMap.containsKey(command.name())) {
                        throw new CommandException(command.name(), "found at least 2 commands in general/commands/ with the same name, what is forbidden");
                    }
                    ClientExecutorModule.CommandContainer container = new ClientExecutorImpl.CommandContainerImpl(
                            command.name(),
                            (Command) constructor.newInstance(command.name()),
                            command.type(),
                            command.paramName(),
                            command.example()
                    );
                    commandMap.put(command.name(), container);
                } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                    // ignore
                }
            }
        } catch (IOException e) {
            throw new IOException("Some io problems: " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new IOException("Some syntax problems: " + e.getMessage());
        }
    }

    private static boolean isImplementCommand(Class<?> cls) {
        for (Class<?> curCls = cls; curCls != Object.class; curCls = curCls.getSuperclass()) {
            if (curCls == null) {
                return false;
            }
            if (Arrays.stream(curCls.getInterfaces()).anyMatch(c -> c == Command.class)) {
                return true;
            }
        }
        return false;
    }

}
