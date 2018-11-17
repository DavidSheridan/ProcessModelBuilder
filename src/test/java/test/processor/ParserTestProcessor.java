package test.processor;

import pmc.io.Lexer;
import pmc.io.Lexer.Token;
import pmc.io.Parser;
import pmc.lang.AST;

import java.util.List;

public class ParserTestProcessor implements TestProcessor<AST> {

    /**
     * Takes the specified {@code String} input and returns the expected {@code AST}
     * as an output.
     *
     * @param input The input to be processed.
     * @return The resulting {@code AST}.
     */
    @Override
    public AST process(String input){
        List<Token> tokens =  new Lexer(input).scan();
        return new Parser(tokens).scan();
    }

}