package pmc.lang.process;

import pmc.lang.action.Action;
import pmc.util.Position;
import pmc.util.SyntacticElement;

public class Sequence extends SyntacticElement implements Process {

    // fields
    private Action action;
    private Process next;

    /**
     * Constructs a new instance of a {@code Sequence} object with the specified
     * {@code Action} and the next {@code Process} in the sequence.
     *
     * @param action The {@code Action} that transitions to the next {@code Process}.
     * @param next The mext {@code Process} in the sequence.
     */
    public Sequence(Action action, Process next){
        this.action = action;
        this.next = next;
    }

    /**
     * Constructs a new instance of a {@code Sequence} object with the specified
     * {@code Action}, the next {@code Process} in the sequence and {@code Position}.
     *
     * @param action The {@code Action} that transitions to the next {@code Process}.
     * @param next The mext {@code Process} in the sequence.
     * @param position The position of this {@code Sequence} in an input stream.
     */
    public Sequence(Action action, Process next, Position position){
        super(position);
        this.action = action;
        this.next = next;
    }

    /**
     * Returns the {@code Action} associated with this {@code Sequence} object.
     *
     * @return The {@code Action} that transitions to the next {@code Process}.
     */
    public Action getAction(){
        return action;
    }

    /**
     * Returns the next {@code Process} in the sequence associated with this
     * {@code Sequence} object.
     *
     * @return The next {@code Process} in the sequence.
     */
    public Process getNext(){
        return next;
    }

    /**
     * Compares this {@code Sequence} to the specified object. Returns {@code true}
     * if and only if the argument is not {@code null} and is a {@code Sequence} object
     * with the same {@code Action} and next {@code Process} values.
     *
     * @param obj The object to compare this {@code Sequence} against.
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
        if(obj instanceof Sequence){
            Sequence sequence = (Sequence)obj;
            if(!action.equals(sequence.action)){
                return false;
            }

            return next.equals(sequence.next);
        }

        return false;
    }

    /**
     * Returns a {@code String} representation of this {@code Sequence} object.
     *
     * @return A {@code String} representation of this {@code Sequence} object.
     */
    @Override
    public String toString(){
        return action + " -> " + next;
    }

}