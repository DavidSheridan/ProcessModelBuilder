package pmc.lang.action;

import pmc.lang.action.element.ActionElement;
import pmc.util.SyntacticElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActionElementList extends SyntacticElement implements Action, Iterable<ActionElement> {

    // field
    private List<ActionElement> elements;

    /**
     * Constructs a new instance of an {@code ActionElementList} with the specified
     * {@code List} of {@code ActionElement}s.
     *
     * @param elements The {@code List} of {@code ActionElement}s.
     */
    public ActionElementList(List<ActionElement> elements){
        this.elements = new ArrayList<ActionElement>(elements);
    }

    /**
     * Returns a {@code String} representation of this {@code ActionElementList} that
     * is a valid representation of an {@code Action} within the Process Modeller grammar.
     *
     * @return A valid {@code String} representation of this {@code ActionElementList}.
     */
    @Override
    public String toActionString(){
        StringBuilder builder = new StringBuilder();
        for(ActionElement element : elements){
            builder.append(element);
        }

        return builder.toString();
    }

    /**
     * Returns an iterator over the {@code ActionElement} objects in this {@code ActionElementList} in
     * proper sequence.
     *
     * @return An iterator over the {@code ActionElement} objects in this {@code ActionElementList}.
     */
    @Override
    public Iterator<ActionElement> iterator(){
        return elements.iterator();
    }

    /**
     * Compares this {@code ActionElementList} to the specified object. Returns {@code true}
     * if and only if the argument is not {@code null} and is a {@code ActionElementList} object
     * with an equivalent {@code List} of {@code ActionElement} objects.
     *
     * @param obj The object to compare this {@code ActionElementList} against.
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
        if(obj instanceof ActionElementList){
            return elements.equals(((ActionElementList)obj).elements);
        }

        return false;
    }

    /**
     * Returns a {@code String} representation of this {@code ActionElementList}.
     *
     * @return A {@code String} representation of this {@code ActionElementList}.
     */
    @Override
    public String toString(){
        return elements.toString();
    }

}
