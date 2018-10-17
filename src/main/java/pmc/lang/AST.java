package pmc.lang;

import pmc.lang.definition.BlockDefinition;
import pmc.lang.definition.Definition;

import java.util.Iterator;

public class AST implements Iterable<Definition> {

    // field
    private BlockDefinition definitions;

    public AST(BlockDefinition definitions){
        this.definitions = definitions;
    }

    public Iterator<Definition> iterator(){
        return definitions.iterator();
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(obj instanceof AST){
            return definitions.equals(((AST)obj).definitions);
        }

        return false;
    }

    @Override
    public String toString(){
        return definitions.toString();
    }

}
