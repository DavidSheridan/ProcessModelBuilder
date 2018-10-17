package pmc.lang.process;

import pmc.lang.TerminatorType;
import pmc.util.Position;
import pmc.util.SyntacticElement;

public class Terminator extends SyntacticElement implements Process {

    // field
    private TerminatorType terminator;

    public Terminator(TerminatorType terminator){
        this.terminator = terminator;
    }

    public Terminator(TerminatorType terminator, Position position){
        super(position);
        this.terminator = terminator;
    }

    public TerminatorType getTerminator(){
        return terminator;
    }

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

    @Override
    public String toString(){
        return terminator.toString();
    }

}