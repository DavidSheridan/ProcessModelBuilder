package pmc.lang;

/**
 * An enum representing the terminal symbols found in the Process Modeller grammar.
 */
public enum TerminalSymbol implements Terminal {
    ASSIGN("="),
    CLOSE_PAREN(")"),
    DOT("."),
    OPEN_PAREN("("),
    SEQUENCE("->");

    // field
    private String value;

    /**
     * Constructs a new instance of a {@code TerminalSymbol} constant with the specified
     * {@code String} value representing how the terminal symbol is defined in the Process
     * Modeller grammar.
     *
     * @param value The value of this {@code TerminalSymbol}.
     */
    TerminalSymbol(String value){
        this.value = value;
    }

    /**
     * Returns the {@code String} representation of this {@code TerminalSymbol} as it is
     * defined in the Process Modeller grammar.
     *
     * @return The {@code String} representation.
     */
    public String getValue(){
        return value;
    }
}