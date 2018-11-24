package pmc.lang.process;

import pmc.util.Position;
import pmc.util.SyntacticElement;

public class Reference extends SyntacticElement implements Process {

    // field
    private String reference;

    /**
     * Constructs a new instance of a {@code Reference} object with the specified
     * {@code String} reference.
     *
     * @param reference The reference.
     */
    public Reference(String reference){
        this.reference = reference;
    }

    /**
     * Constructs a new instance of a {@code Reference} object with the specified
     * {@code String} reference and {@code Position}.
     *
     * @param reference The reference.
     * @param position The position of this {@code Reference} in an input stream.
     */
    public Reference(String reference, Position position){
        super(position);
        this.reference = reference;
    }

    /**
     * Returns the reference associated with this {@code Reference} object.
     *
     * @return The reference.
     */
    public String getReference(){
        return reference;
    }

    /**
     * Compares this {@code Reference} to the specified object. Returns {@code true}
     * if and only if the argument is not {@code null} and is a {@code Reference} object
     * with the same {@code String} refernece value.
     *
     * @param obj The object to compare this {@code Reference} against.
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
        if(obj instanceof Reference){
            return reference.equals(((Reference)obj).reference);
        }

        return false;
    }

    /**
     * Returns a {@code String} representation of this {@code Reference} object.
     *
     * @return A {@code String} representation of this {@code Reference} object.
     */
    @Override
    public String toString(){
        return reference;
    }

}