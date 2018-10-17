package pmc.lang.definition;

import pmc.lang.terminal.ProcessType;
import pmc.util.Position;
import pmc.util.SyntacticElement;

public class ProcessDefinition extends SyntacticElement implements Definition {

    // fields
    private ProcessType processType;
    private String identifier;
    private Process process;

    public ProcessDefinition(ProcessType processType, String identifier, Process process){
        this.processType = processType;
        this.identifier = identifier;
        this.process = process;
    }

    public ProcessDefinition(ProcessType processType, String identifier, Process process, Position position){
        super(position);
        this.processType = processType;
        this.identifier = identifier;
        this.process = process;
    }

    public ProcessType getProcessType(){
        return processType;
    }

    public String getIdentifier(){
        return identifier;
    }

    public Process getProcess(){
        return process;
    }

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

    @Override
    public String toString(){
        return processType + " " + identifier + " = " + process + ".";
    }

}