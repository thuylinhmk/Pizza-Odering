package exceptions;

/**
 * Exception when trying to add more than 5 toppings on a pizza
 */
public class TooManyToppingsException extends Exception {
    /**
     * TooManyToppingsException constructor specifying detailed message to print out
     *
     * @param message The message to show
     */
    public TooManyToppingsException(String message) {
        super(message);
    }

    /**
     * TooManyToppingsException constructor specifying detailed message to print out with line
     * number where error occurs
     *
     * @param message The message to show
     * @param lineNum Line number of error
     */
    public TooManyToppingsException(String message, int lineNum) {
        super(message + " at " + lineNum);
    }

    /**
     * TooManyToppingsException constructor stores the underlying cause of the exception.
     *
     * @param cause The cause of exception
     */
    public TooManyToppingsException(Throwable cause) {
        super(cause);
    }

    /**
     * TooManyToppingException constructor contains a helpful detail message
     * explaining why the exception occurred and the underlying cause of the exception.
     *
     * @param message   The message to show
     * @param cause     The cause of exception
     */
    public TooManyToppingsException(String message, Throwable cause) {
        super(message, cause);
    }

}
