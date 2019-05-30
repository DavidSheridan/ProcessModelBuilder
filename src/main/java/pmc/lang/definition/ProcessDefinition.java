package pmc.lang.definition;

import pmc.lang.process.Process;
import pmc.lang.terminal.ProcessType;
import pmc.util.Position;
import pmc.util.SyntacticElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProcessDefinition extends SyntacticElement implements Definition, Iterable<LocalProcessDefinition> {

    // fields
    private ProcessType processType;
    private String identifier;
    private Process process;
    private List<LocalProcessDefinition> localProcesses;

    /**
     * Constructs an empty instance of a {@code ProcessDefinition} object.
     */
    private ProcessDefinition(){
        localProcesses = new ArrayList<LocalProcessDefinition>();
    }

    /**
     * Constructs a new instance of a {@code ProcessDefinition} object with the specified
     * {@code ProcessType}, {@code String} identifier, {@code Process} and {@code List} of
     * local processes.
     *
     * @param processType The process type.
     * @param identifier The process identifier.
     * @param process The process.
     * @param localProcesses The list of local processes.
     * @param position The position of the process definition in an input stream.
     */
    private ProcessDefinition(ProcessType processType, String identifier, Process process, List<LocalProcessDefinition> localProcesses, Position position){
        super(position);
        this.processType = processType;
        this.identifier = identifier;
        this.process = process;
        this.localProcesses = new ArrayList<LocalProcessDefinition>(localProcesses);
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
     * Returns an iterator over the {@code LocalProcessDefinition} objects in this
     * {@code ProcessDefinition} in proper sequence.
     *
     * @return An iterator over the {@code LocalProcessDefinition} objects in this {@code ProcessDefinition}.
     */
    @Override
    public Iterator<LocalProcessDefinition> iterator(){
        return localProcesses.iterator();
    }

    /**
     * Compares this {@code ProcessDefinition} to the specified object. Returns {@code true}
     * if and only if the argument is not {@code null} and is a {@code ProcessDefinition} object
     * with the same {@code ProcessType}, {@code String} identifier, {@code Process} and {@code List}
     * of {@code LocalProcessDefinition}s.
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
            if(!process.equals(definition.process)){
                return false;
            }

            return localProcesses.equals(definition.localProcesses);
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

    public static class Builder {

        // field
        private ProcessDefinition definition;
        private Position position;

        /**
         * Constructs a new instance of a {@code Builder} object.
         */
        public Builder(){
            definition = new ProcessDefinition();
        }

        /**
         * Constructs a new instance of a {@code Builder} object with the specified
         * {@code ProcessType}, {@code String} identifier and {@code Process}.
         *
         * @param processType The process type.
         * @param identifier The identifier.
         * @param process The process.
         */
        public Builder(ProcessType processType, String identifier, Process process){
            definition = new ProcessDefinition();
            definition.processType = processType;
            definition.identifier = identifier;
            definition.process = process;
        }

        /**
         * Sets the process type to the specified {@code ProcessType}.
         *
         * @param processType The process type.
         */
        public void setProcessType(ProcessType processType){
            definition.processType = processType;
        }

        /**
         * Sets the identifier to the specified {@code String} identifier.
         *
         * @param identifier The identifier.
         */
        public void setIdentifier(String identifier){
            definition.identifier = identifier;
        }

        /**
         * Sets the process to the specified {@code Process}.
         *
         * @param process The process.
         */
        public void setProcess(Process process){
            definition.process = process;
        }

        /**
         * Adds the specified {@code LocalProcessDefinition} to the {@code ProcessDefinition}
         * being built.
         *
         * @param localProcess The local process to add.
         */
        public void addLocalProcess(LocalProcessDefinition localProcess){
            definition.localProcesses.add(localProcess);
        }

        public void setPosition(Position position){
            this.position = position;
        }

        /**
         * Constructs and returns a new {@code ProcessDefinition} object based on the current
         * state of the {@code Builder}.
         *
         * @return The process definition.
         */
        public ProcessDefinition build(){
            if(definition.processType == null){
                throw new IllegalStateException("process type has not been defined");
            }
            if(definition.identifier == null){
                throw new IllegalStateException("identifier has not been defined");
            }
            if(definition.process == null){
                throw new IllegalStateException("process has not been defined");
            }

            return new ProcessDefinition(definition.processType, definition.identifier, definition.process, definition.localProcesses, position);
        }

    }

}