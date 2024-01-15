package exceptions;

/**
 * Exception thrown when an error is thrown while parsing the text file using MenuLoader
 */
public class PizzaFormatException extends Exception {
    /**
     * PizzaFormatException constructor specifying detailed message to print out and line
     * where error occur
     *
     * @param message The message to show
     * @param lineNum Line number where error occurs
     *
     */
    public PizzaFormatException(String message, int lineNum) {
        super(message + " at " + lineNum);
    }

    /**
     * PizzaFormatException constructor specifying detailed message to print out
     * helpful detail message explaining why the exception occurred.
     *
     * @param message   The message to show
     * @param lineNum   Line number where error occurs
     * @param cause     Cause of this exception
     */
    public PizzaFormatException(String message, int lineNum, Throwable cause) {
        super(message + " at " + lineNum, cause);
    }

}
