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

    /**
     * Constructs a new instance of a {@code BlockDefinition} object with the specified
     * array of {@code Definition} objects.
     *
     * @param definitions The definition array.
     */
    public BlockDefinition(Definition...definitions){
        this.definitions = Arrays.stream(definitions).collect(Collectors.toList());
    }

    /**
     * Constructs a new instance of a {@code BlockDefinition} object with the specified
     * {@code List} of {@code Definition} objects.
     *
     * @param definitions {@code List} of {@code Definition} objects.
     */
    public BlockDefinition(List<Definition> definitions){
        this.definitions = new ArrayList<Definition>(definitions);
    }

    /**
     * Constructs a new instance of a {@code BlockDefinition} object with the specified
     * {@code List} of {@code Definition} objects and {@code Position}.
     *
     * @param definitions {@code List} of {@code Definition} objects.
     * @param position The position of this {@code BlockDefinition} in an input stream.
     */
    public BlockDefinition(List<Definition> definitions, Position position){
        super(position);
        this.definitions = new ArrayList<Definition>(definitions);
    }

    /**
     * Returns an iterator over the {@code Definition} objects in this {@code BlockDefinition} in
     * proper sequence.
     *
     * @return An iterator over the {@code Definition} objects in this {@code BlockDefinition}.
     */
    @Override
    public Iterator<Definition> iterator(){
        return definitions.iterator();
    }

    /**
     * Compares this {@code BlockDefinition} to the specified object. Returns {@code true}
     * if and only if the argument is not {@code null} and is a {@code BlockDefinition} object
     * with an equivalent {@code List} of {@code Definition} objects.
     *
     * @param obj The object to compare this {@code BlockDefinition} against.
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
        if(obj instanceof BlockDefinition){
            return definitions.equals(((BlockDefinition)obj).definitions);
        }

        return false;
    }

    /**
     * Returns a {@code String} representation of this {@code BlockDefinition}.
     *
     * @return a {@code String} representation of this {@code BlockDefinition}.
     */
    @Override
    public String toString(){
        return definitions.toString();
    }

}