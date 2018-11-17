package pmc.util;

/**
 * <p>
 * The {@code SyntaxError} class represents a syntactic error that can occur during
 * the scanning or parsing of an input stream. A {@code SyntaxError} is thrown when
 * an input stream does not match the expected syntax.
 * </p>
 */
public class SyntaxError extends RuntimeException {

    // field
    private Position position;

    /**
     * Constructs a new instance of a {@code SyntaxError} with the specified
     * detail message and the {@code Position} where the error occurred
     * in a particular input stream.
     *
     * @param message The detail message explaining why the error occurred.
     * @param position The {@code Position} in a input stream where the error occurred.
     */
    public SyntaxError(String message, Position position){
        super(message);
        this.position = position;
    }

    /**
     * Returns the {@code Position} in the input stream where this {@code SyntaxError}
     * occurred.
     *
     * @return The {@code Position} in the input stream where the error occurred.
     */
    public Position getPosition(){
        return position;
    }

}