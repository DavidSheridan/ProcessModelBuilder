package pmc.lang.action;

public interface Action {

    /**
     * Returns a {@code String} representation of this {@code Action} that
     * is a valid representation of an {@code Action} within the Process Modeller grammar.
     *
     * @return A valid {@code String} representation of this {@code Action}.
     */
    String toActionString();

}