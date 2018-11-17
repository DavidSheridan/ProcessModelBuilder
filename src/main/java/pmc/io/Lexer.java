package pmc.io;

import pmc.lang.terminal.ProcessType;
import pmc.lang.terminal.Terminal;
import pmc.lang.terminal.TerminalSymbol;
import pmc.lang.terminal.TerminatorType;
import pmc.util.Position;
import pmc.util.SyntacticElement;
import pmc.util.SyntaxError;

import java.util.*;

public class Lexer {

    /**
     * A mapping from the {@code String} representation of terminals in the Process Modeller grammar to
     * their internal representation.
     */
    private static final Map<String, Terminal> terminalMap = new HashMap<String, Terminal>(){
        {
            Arrays.stream(ProcessType.values()).forEach(terminal -> put(terminal.getValue(), terminal));
            Arrays.stream(TerminalSymbol.values()).forEach(terminal -> put(terminal.getValue(), terminal));
            Arrays.stream(TerminatorType.values()).forEach(terminal -> put(terminal.getValue(), terminal));
        }
    };

    // fields
    private String input;
    private int index;
    private int line;
    private int column;

    /**
     * Constructs a new instance of a {@code Lexer} with the specified
     * {@code String} input.
     *
     * @param input The input {@code String}.
     */
    public Lexer(String input){
        this.input = input;
        this.index = 0;
        this.line = 0;
        this.column = 0;
    }

    /**
     * Scans through the input and constructs the corresponding {@code List} of
     * {@code Token}s.
     *
     * @return The corresponding {@code List} of {@code Token}s.
     */
    public List<Token> scan(){
        List<Token> tokens = new ArrayList<Token>();

        while(index < input.length()){
            skipWhitespace();

            int lineStart = line;
            int columnStart = column;

            if(Character.isUpperCase(input.charAt(index))){
                String value = parseString();
                if(terminalMap.containsKey(value)){
                    tokens.add(new TerminalToken(terminalMap.get(value), new Position(lineStart, columnStart, line, column)));
                }
                else{
                    tokens.add(new UpperCaseIdentifierToken(value, new Position(lineStart, columnStart, line, column)));
                }
            }
            else if(Character.isLowerCase(input.charAt(index))){
                String value = parseString();
                if(terminalMap.containsKey(value)){
                    tokens.add(new TerminalToken(terminalMap.get(value), new Position(lineStart, columnStart, line, column)));
                }
                else{
                    tokens.add(new LowerCaseIdentifierToken(value, new Position(lineStart, columnStart, line, column)));
                }
            }
            else{
                String terminal = parseTerminal();
                if(!terminalMap.containsKey(terminal)){
                    throw new SyntaxError("invalid terminal: " + terminal, new Position(lineStart, columnStart, line, column));
                }

                tokens.add(new TerminalToken(terminalMap.get(terminal), new Position(lineStart, columnStart, line, column)));
            }
        }

        return tokens;
    }

    private String parseString(){
        int start = index;
        while(index < input.length()){
            char c = input.charAt(index);
            if(Character.isLetterOrDigit(c) || c == '_'){
                index++;
                column++;
            }
            else{
                break;
            }
        }

        return input.substring(start, index);
    }

    /**
     * Parses a terminal string from the current position in the input. Once the method is complete
     * the index points to the character after the final character in the terminal string.
     *
     * @return The terminal string at the current position in the input.
     */
    private String parseTerminal(){
        int start = index;
        while(index < input.length()){
            char c = input.charAt(index);
            if(Character.isWhitespace(c) || terminalMap.containsKey(input.substring(start, index))){
                break;
            }

            index++;
        }

        return input.substring(start, index);
    }

    /**
     * Skips the whitespace at the current position of the input. Once the method is
     * executed the index points to
     */
    private void skipWhitespace(){
        while(index < input.length()){
            char c = input.charAt(index);
            if(c == ' ' || c == '\t'){
                column++;
            }
            else if(c == '\n' || c == '\r'){
                line++;
                column = 0;
            }
            else{
                break;
            }

            index++;
        }
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

    public static class UpperCaseIdentifierToken extends Token<String> {

        public UpperCaseIdentifierToken(String value){
            super(value);
        }

        public UpperCaseIdentifierToken(String value, Position position){
            super(value, position);
        }

    }

    public static class LowerCaseIdentifierToken extends Token<String> {

        public LowerCaseIdentifierToken(String value){
            super(value);
        }

        public LowerCaseIdentifierToken(String value, Position position){
            super(value, position);
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