package pmc.io;

import pmc.lang.Terminal;
import pmc.util.Position;
import pmc.util.SyntacticElement;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    // field
    private String input;

    /**
     * Constructs a new instance of a {@code Lexer} with the specified
     * {@code String} input.
     *
     * @param input The input {@code String}.
     */
    public Lexer(String input){
        this.input = input;
    }

    /**
     * Scans through the input and constructs the corresponding {@code List} of
     * {@code Token}s.
     *
     * @return The corresponding {@code List} of {@code Token}s.
     */
    public List<Token> scan(){
        List<Token> tokens = new ArrayList<Token>();

        return tokens;
    }

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

    /**
     * The {@code Terminal} class represents any terminal element defined in the Process
     * Modeller grammar within an input stream.
     */
    public static class TerminalToken extends Token<Terminal> {

        /**
         * Constructs a new instance of a {@code TerminalToken} with the specified
         * {@code Terminal} value.
         *
         * @param value The {@code Terminal} value this {@code TerminalToken} represents.
         */
        public TerminalToken(Terminal value){
            super(value);
        }

        /**
         * Constructs a new instance of a {@code TerminalToken} object with the specified
         * {@code Terminal} value and {@code Position}.
         *
         * @param value The {@code Terminal} value this {@code TerminalToken} represents.
         * @param position The position of the value in its input stream.
         */
        public TerminalToken(Terminal value, Position position){
            super(value, position);
        }

    }

}