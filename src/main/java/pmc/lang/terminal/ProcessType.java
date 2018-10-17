package pmc.lang.terminal;

/**
 * An enum representing the process type terminals found in the Process Modeller grammar.
 */
public enum ProcessType implements Terminal {
    AUTOMATA("automata");

    // field
    private String value;

    /**
     * Constructs a new instance of a {@code ProcessType} constant with the specified
     * {@code String} value representing how the process type is defined in the Process
     * Modeller grammar.
     *
     * @param value The value of this {@code ProcessType}.
     */
    ProcessType(String value){
        this.value = value;
    }

    /**
     * Returns the {@code String} representation of this {@code ProcessType} as it is
     * defined in the Process Modeller grammar.
     *
     * @return The {@code String} representation.
     */
    public String getValue(){
        return value;
    }
}