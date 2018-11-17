package pmc.lang.process;

import pmc.lang.terminal.TerminatorType;
import pmc.util.Position;
import pmc.util.SyntacticElement;

public class Terminator extends SyntacticElement implements Process {

    // field
    private TerminatorType terminator;

    /**
     * Constructs a new instance of a {@code Terminator} with the specified {@code TerminatorType}.
     *
     * @param terminator The terminator type.
     */
    public Terminator(TerminatorType terminator){
        this.terminator = terminator;
    }

    /**
     * Constructs a new instance of a {@code Terminator} with the specified {@code TerminatorType}
     * and {@code Position}.
     *
     * @param terminator
     * @param position
     */
    public Terminator(TerminatorType terminator, Position position){
        super(position);
        this.terminator = terminator;
    }

    /**
     * Returns the {@code TerminatorType} associated with this {@code Terminator}.
     *
     * @return The terminator type.
     */
    public TerminatorType getTerminator(){
        return terminator;
    }

    /**
     * Compares this {@code Terminator} to the specified object. Returns {@code true}
     * if and only if the argument is not {@code null} and is a {@code Terminator} object
     * with the same {@code TerminatorType} value.
     *
     * @param obj The object to compare this {@code Terminator} against.
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
        if(obj instanceof Terminator){
            return terminator == ((Terminator)obj).terminator;
        }

        return false;
    }

    /**
     * Returns a {@code String} representation of this {@code Terminator}.
     *
     * @return a {@code String} representation of this {@code Terminator}.
     */
    @Override
    public String toString(){
        return terminator.toString();
    }

}