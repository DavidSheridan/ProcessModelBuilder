package pmc.lang.definition;

import pmc.lang.process.Process;
import pmc.util.Position;
import pmc.util.SyntacticElement;

public class LocalProcessDefinition extends SyntacticElement {

    // fields
    private String identifier;
    private Process process;

    /**
     * Constructs a new instance of a {@code LocalProcessDefinition} object with the specified
     * {@code String} identifier and {@code Process}.
     *
     * @param identifier The identifier.
     * @param process The process.
     */
    public LocalProcessDefinition(String identifier, Process process){
        this.identifier = identifier;
        this.process = process;
    }

    /**
     * Constructs a new instance of a {@code LocalProcessDefinition} object with the specified
     * {@code String} identifier, {@code Process} and {@code Position}.
     *
     * @param identifier The identifier.
     * @param process The process.
     * @param position The position of this local process definition in an input stream.
     */
    public LocalProcessDefinition(String identifier, Process process, Position position){
        super(position);
        this.identifier = identifier;
        this.process = process;
    }

    /**
     * Returns the {@code String} identifier associated with this {@code LocalProcessDefinition}.
     *
     * @return The identifier.
     */
    public String getIdentifier(){
        return identifier;
    }

    /**
     * Returns the {@code Process} associated with this {@codee LocalProcessDefinition}.
     *
     * @return The process.
     */
    public Process getProcess(){
        return process;
    }

    /**
     * Compares this {@code LocalProcessDefinition} to the specified object. Returns {@code true}
     * if and only if the argument is not {@code null} and is a {@code LocalProcessDefinition} object
     * with the same {@code String} identifier and {@code Process}.
     *
     * @param obj The object to compare this {@code LccalProcessDefinition} against.
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
        if(obj instanceof LocalProcessDefinition){
            LocalProcessDefinition localProcess = (LocalProcessDefinition)obj;
            if(!identifier.equals(localProcess.identifier)){
                return false;
            }

            return process.equals(localProcess.process);
        }

        return false;
    }

    /**
     * Returns a {@code String} representation of this {@code LocalProcessDefinition}.
     *
     * @return A {@code String} representation of this {@code LocalProcessDefinition}.
     */
    @Override
    public String toString(){
        return identifier + " = " + process;
    }

}