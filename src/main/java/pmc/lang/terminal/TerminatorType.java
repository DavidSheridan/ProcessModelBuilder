package pmc.lang.terminal;

import pmc.lang.terminal.Terminal;

/**
 * An enum representing the terminator types found in the Process Modeller grammar.
 */
public enum TerminatorType implements Terminal {
    STOP;

    /**
     * Returns the {@code String} representation of this {@code TerminatorType} as it is
     * defined in the Process Modeller grammar.
     *
     * @return The {@code String} representation.
     */
    public String getValue(){
        return this.name();
    }
}