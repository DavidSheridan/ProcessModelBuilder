package pmc.util;

/**
 * The {@code SyntacticElement} class represents the position of a particular element
 * in an input stream.
 */
public abstract class SyntacticElement {

    // field
    private Position position;

    /**
     * Constructs a new instance of a {@code SyntacticElement} object with the
     * position pointing to the beginning of an input stream.
     */
    public SyntacticElement(){
        position = new Position(0, 0, 0, 0);
    }

    /**
     * Constructs a new instance of a {@code SyntacticElement} object with the
     * specified {@code Position}.
     *
     * @param position The position of this {@code SyntacticElement}.
     */
    public SyntacticElement(Position position){
        this.position = position;
    }

    /**
     * Returns the position of this {@code SyntacticElement}.
     *
     * @return The position of this {@code SyntacticElement}.
     */
    public Position getPosition(){
        return position;
    }

}