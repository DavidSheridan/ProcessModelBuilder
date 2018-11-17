package pmc.lang.action.element;

import pmc.util.Position;
import pmc.util.SyntacticElement;

public abstract class ActionElement<E> extends SyntacticElement {

    // field
    private E value;

    /**
     * Constructs a new instance of an {@code ActionElement} with the specified
     * {@code E} value.
     *
     * @param value The value this {@code ActionElement} represents.
     */
    public ActionElement(E value){
        this.value = value;
    }

    /**
     * Constructs a new instance of an {@code ActionElement} with the specified
     * {@code E} value and {@code Position}.
     *
     * @param value The value this {@code ActionElement} represents.
     * @param position The position of this {@code ActionElement} in an input stream.
     */
    public ActionElement(E value, Position position){
        super(position);
        this.value = value;
    }

    /**
     * Returns the value this {@code ActionElement} represents.
     *
     * @return The value this {@code ActionElement} represents.
     */
    public E getValue(){
        return value;
    }

    /**
     * Compares this {@code ActionElement} to the specified object. Returns {@code true}
     * if and only if the argument is not {@code null} and is a {@code Terminator} object
     * with the same {@code E} value.
     *
     * @param obj The object to compare this {@code ActionElement} against.
     * @return {@code true} if the specified object is equivalent to this one, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(obj instanceof ActionElement){
            return value.equals(((ActionElement)obj).value);
        }

        return false;
    }

    /**
     * Returns a {@code String} representation of this {@code ActionElement}.
     *
     * @return A {@code String} representation of this {@code ActionElement}.
     */
    public String toString(){
        return value.toString();
    }

}