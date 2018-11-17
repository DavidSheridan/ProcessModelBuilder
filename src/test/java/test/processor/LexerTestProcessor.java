package test.processor;

import pmc.io.Lexer;
import pmc.io.Lexer.Token;

import java.util.List;

public class LexerTestProcessor implements TestProcessor<List<Token>> {

    /**
     * Takes the specified {@code String} input and returns the expected {@code List}
     * of {@code Token}s as an output.
     *
     * @param input The input to be processed.
     * @return The resulting {@code List} of {@code Token}s.
     */
    @Override
    public List<Token> process(String input){
        return new Lexer(input).scan();
    }

}