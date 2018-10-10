package pmc.lang;

/**
 * The {@code Terminal} interface is a superclass for all terminals in the Process
 * Modeller grammar.
 */
public interface Terminal {

    /**
     * Returns a {@code String} representation of this {@code Terminal} as it
     * is defined by the Process Modeller grammar.
     *
     * @return The {@code String} representation.
     */
    String getValue();

}