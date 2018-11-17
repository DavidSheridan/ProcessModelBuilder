package pmc.lang.action.element;

import pmc.util.Position;

public class StringActionElement extends ActionElement<String> {

    /**
     * Constructs a new instance of a {@code StringActionElement} with the specified
     * {@code String} value.
     *
     * @param value The {@code String} value this {@code StringActionElement} represents.
     */
    public StringActionElement(String value){
        super(value);
    }

    /**
     * Constructs a new instance of a {@code StringActionElement} with the specified
     * {@code String} value and {@code Position}.
     *
     * @param value The {@code String} value this {@code StringActionElement} represents.
     * @param position The {@code Position} of this {@code StringActionElement} in an input stream.
     */
    public StringActionElement(String value, Position position){
        super(value, position);
    }

}