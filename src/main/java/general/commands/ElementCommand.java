package general.commands;

import general.ClientContext;
import general.ScriptContext;
import general.Request;
import general.element.Coordinates;
import general.element.FieldSetter;
import general.element.Movie;
import general.element.Person;
import general.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * Type of command, which can read elements
 */
public abstract class ElementCommand extends NameableCommand {
    private static final Map<String, String> fieldExamples = new HashMap<>();
    private static final Map<String, Method> methodsSetters = new HashMap<>();
    private static final Map<Integer, String> order = new HashMap<>();
    protected Integer key;
    protected Movie readMovie;
    private transient ScriptContext client;

    static {
        fillMethodsSetters(methodsSetters, Movie.class);
    }

    /**
     * Here we analyze class and fill Maps: fieldExamples, methodsSetters, order
     * @param emptyMethodsSetters not fully filled methodsSetters
     * @param cls class we need to analyze
     * @see FieldSetter
     * @see Movie
     * @see Coordinates
     * @see Person
     */
    private static void fillMethodsSetters(Map<String, Method> emptyMethodsSetters, Class<?> cls) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getName().startsWith("set") && method.getAnnotation(FieldSetter.class) != null) {
                if (method.getParameters()[0].getType().equals(String.class)) {
                    FieldSetter annotation = method.getAnnotation(FieldSetter.class);
                    String fieldName = cls.getSimpleName() + " " + annotation.fieldName();
                    emptyMethodsSetters.put(fieldName, method);
                    fieldExamples.put(fieldName, annotation.example());
                    order.put(annotation.index(), fieldName);
                } else {
                    fillMethodsSetters(emptyMethodsSetters, method.getParameters()[0].getType());
                }
            }
        }
    }

    /**
     * Constructor with name, Hashtable, Scanner and boolean
     */
    public ElementCommand(String commandName) {
        super(commandName);
    }

    /**
     * Method, which read one field (line)
     * @return string - user input or null
     */
    protected String readOneField() {
        String command = client.nextLine().trim();
        return (command.equals("") ? null : command);
    }

    /**
     * Method, which tries to read and set only one field
     * @param object object, which field we set
     * @param method setter we invoke
     */
    protected void setOneField(Object object, Method method) throws Throwable {
        try {
            method.invoke(object, readOneField());
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    /**
     * Main method. Read one element from reader
     * @return reader Movie element
     */
    protected Movie readMovie() throws Throwable {
        Movie newMovie = new Movie();
        Coordinates newCoordinates = new Coordinates();
        Person newScreenwriter = new Person();
        for (Integer step : order.keySet()) {
            Method method = methodsSetters.get(order.get(step));
            if (method.getDeclaringClass() == Movie.class) {
                setOneField(newMovie, method);
            } else if (method.getDeclaringClass() == Coordinates.class) {
                setOneField(newCoordinates, method);
            } else if (method.getDeclaringClass() == Person.class) {
                setOneField(newScreenwriter, method);
            } else {
                throw new RuntimeException("Some problems with \"methodsSetters\" (find new class?)");
            }
        }
        newMovie.setCoordinates(newCoordinates);
        newMovie.setScreenwriter(newScreenwriter);
        return newMovie;
    }

    /**
     * @see Command
     */
    @Override
    public void setScriptArgs(ScriptContext script, String... args) throws BadArgumentsException {
        if (args.length != 1) {
            throw new BadArgumentsCountException(getCommandName(), 1);
        }
        try {
            key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "integer");
        }

        this.client = script;
        sendRequestAndCheckElement();

        try {
            this.readMovie = readMovie();
        } catch (NoSuchElementException e) {
            throw new BadArgumentsException(getCommandName(), "the script ended before the Movie was read");
        } catch (Throwable e) {
            throw new BadArgumentsException(getCommandName(), e.getMessage());
        }
    }

    private void sendRequestAndCheckElement() throws BadArgumentsException {
        try {
            Response response = client.sendToServer(client.createNewRequest(
                    Request.RequestType.CHECK_ELEMENT,
                    key
            ));
            checkElement(response);
        } catch (SocketTimeoutException e) {
            throw new BadArgumentsException(getCommandName(), "Server is not responding, try later or choose another server");
        } catch (Exception e) {
            throw new BadArgumentsException(getCommandName(), e.getMessage());
        }
    }

    public abstract void checkElement(Response response) throws BadArgumentsException;

    @Override
    public void setGUIArgs(ClientContext client) {
        key = client.getMovieKey();
        readMovie = client.getMovie();
    }
}
