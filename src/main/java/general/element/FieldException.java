package general.element;

/**
 * Exception, when field is incorrect
 */
public class FieldException extends Exception {
    /**
     * Required value for field
     */
    private final String required;

    /**
     * Constructor with value and required value
     * @param required required value
     */
    public FieldException(String required) {
        this.required = required;
    }

    @Override
    public String getMessage() {
        return "ERROR: " + required;
    }
}
