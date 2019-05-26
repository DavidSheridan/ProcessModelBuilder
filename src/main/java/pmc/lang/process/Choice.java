package pmc.lang.process;

import pmc.util.Position;
import pmc.util.SyntacticElement;

public class Choice extends SyntacticElement implements Process {

    // fields
    private Process firstBranch;
    private Process secondBranch;

    /**
     * Constructs a new instance of a {@code Choice} object with the two specified
     * {@code Process} branches.
     *
     * @param firstBranch The first branch.
     * @param secondBranch The second branch.
     */
    public Choice(Process firstBranch, Process secondBranch){
        this.firstBranch = firstBranch;
        this.secondBranch = secondBranch;
    }

    /**
     * Constructs a new instance of a {@code Choice} object with the two specified
     * {@code Process} branches and {@code Position}.
     *
     * @param firstBranch The first branch.
     * @param secondBranch The second branch.
     * @param position The position of this {@code Choice} in an input stream/
     */
    public Choice(Process firstBranch, Process secondBranch, Position position){
        super(position);
        this.firstBranch = firstBranch;
        this.secondBranch = secondBranch;
    }

    /**
     * Returns the first {@code Process} branch associated with this {@code Choice} object.
     *
     * @return The first branch.
     */
    public Process getFirstBranch(){
        return firstBranch;
    }

    /**
     * Returns the second {@code Process} branch associated with this {@code Choice} object.
     *
     * @return The second branch.
     */
    public Process getSecondBranch(){
        return secondBranch;
    }

    /**
     * Compares this {@code Choice} object to the specified object. Returns {@code true}
     * if and only if the argument is not {@code null} and is a {@code Choice} object
     * with the same {@code Process} branches in the same order.
     *
     * @param obj The object to compare this {@code Choice} object against.
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
        if(obj instanceof Choice){
            Choice choice = (Choice)obj;
            if(!firstBranch.equals(choice.firstBranch)){
                return false;
            }

            return secondBranch.equals(choice.secondBranch);
        }

        return false;
    }

    /**
     * Returns a {@code String} representation of this {@code Choice} object.
     *
     * @return A {@code String} representation of this {@code Choice} object.
     */
    @Override
    public String toString(){
        return firstBranch + " | " + secondBranch;
    }

}