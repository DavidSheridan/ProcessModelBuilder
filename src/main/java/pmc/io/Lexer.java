package pmc.io;

import pmc.util.Position;
import pmc.util.SyntacticElement;

public class Lexer {

    /**
     * The {@code Token} class represents an element from the Process Modeller grammar within
     * an input stream.
     *
     * @param <E> The type of the value the {@code Token} represents.
     */
    public static abstract class Token<E> extends SyntacticElement {

        // field
        private E value;

        /**
         * Constructs a new instance of a {@code Token} object with the specified
         * {@code E} value.
         *
         * @param value The {@code E} value this {@code Token} represents.
         */
        public Token(E value){
            this.value = value;
        }

        /**
         * Constructs a new instance of a {@code Token} object with the specified
         * {@code E} value and {@code Position}.
         *
         * @param value The {@code E} value this {@code Token} represents.
         * @param position The position of the value in its input stream.
         */
        public Token(E value, Position position){
            super(position);
            this.value = value;
        }

        /**
         * Returns the {@code E} value associated with this {@code Token}.
         *
         * @return The {@code E} value of this {@code Token}.
         */
        public E getValue(){
            return value;
        }

        /**
         * Compares this {@code Token} to the specified object. Returns {@code true} if
         * and only if the argument is not {@code null} and is a {@code Token} object with
         * the same {@code E} value.
         *
         * @param obj The object to compare this {@code Token} against.
         * @return {@code true} if the specified object is equivalent to this one, otherwise
         * {@code false}.
         */
        @Override
        public boolean equals(Object obj){
            if(obj == this){
                return true;
            }
            if(obj == null){
                return false;
            }
            if(obj instanceof Token){
                return value.equals(((Token)obj).value);
            }

            return false;
        }

    }

}