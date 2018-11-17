package pmc.lang.definition;

import pmc.lang.terminal.ProcessType;
import pmc.util.Position;
import pmc.util.SyntacticElement;

public class ProcessDefinition extends SyntacticElement implements Definition {

    // fields
    private ProcessType processType;
    private String identifier;
    private Process process;

    /**
     * Constructs a new instance of a {@code ProcessDefinition} object with the specified
     * {@code ProcessType}, {@code String} identifier and {@code Process}.
     *
     * @param processType The process type.
     * @param identifier The process identifier.
     * @param process The process.
     */
    public ProcessDefinition(ProcessType processType, String identifier, Process process){
        this.processType = processType;
        this.identifier = identifier;
        this.process = process;
    }

    /**
     * Constructs a new instance of a {@code ProcessDefinition} object with the specified
     * {@code ProcessType}, {@code String} identifier, {@code Process} and {@code Position}.
     *
     * @param processType The process type.
     * @param identifier The process identifier.
     * @param process The process.
     * @param position The position of the process definition in an input stream.
     */
    public ProcessDefinition(ProcessType processType, String identifier, Process process, Position position){
        super(position);
        this.processType = processType;
        this.identifier = identifier;
        this.process = process;
    }

    /**
     * Returns the {@code ProcessType} associated with this {@code ProcessDefinition}.
     *
     * @return The process type.
     */
    public ProcessType getProcessType(){
        return processType;
    }

    /**
     * Returns the identifier associated with this {@code ProcessDefinition}.
     * @return
     */
    public String getIdentifier(){
        return identifier;
    }

    /**
     * Returns the {@code Process} associated with this {@code ProcessDefinition}.
     * @return
     */
    public Process getProcess(){
        return process;
    }

    /**
     * Compares this {@code ProcessDefinition} to the specified object. Returns {@code true}
     * if and only if the argument is not {@code null} and is a {@code ProcessDefinition} object
     * with the same {@code ProcessType}, {@code String} identifier and {@code Process}.
     *
     * @param obj The object to compare this {@code ProcessDefinition} against.
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
        if(obj instanceof ProcessDefinition){
            ProcessDefinition definition = (ProcessDefinition)obj;

            if(processType != definition.processType){
                return false;
            }
            if(!identifier.equals(definition.identifier)){
                return false;
            }

            return process.equals(definition.process);
        }

        return false;
    }

    /**
     * Returns a {@code String} representation of this {@code ProcessDefinition}.
     *
     * @return a {@code String} representation of this {@code ProcessDefinition}.
     */
    @Override
    public String toString(){
        return processType + " " + identifier + " = " + process + ".";
    }

}