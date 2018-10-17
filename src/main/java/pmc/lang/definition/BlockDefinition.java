package pmc.lang.definition;

import pmc.util.Position;
import pmc.util.SyntacticElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BlockDefinition extends SyntacticElement implements Definition, Iterable<Definition> {

    // field
    private List<Definition> definitions;

    public BlockDefinition(Definition...definitions){
        this.definitions = Arrays.stream(definitions).collect(Collectors.toList());
    }

    public BlockDefinition(List<Definition> definitions){
        this.definitions = new ArrayList<Definition>(definitions);
    }

    public BlockDefinition(List<Definition> definitions, Position position){
        super(position);
        this.definitions = new ArrayList<Definition>(definitions);
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
        if(obj instanceof BlockDefinition){
            return definitions.equals(((BlockDefinition)obj).definitions);
        }

        return false;
    }

    @Override
    public String toString(){
        return definitions.toString();
    }

}