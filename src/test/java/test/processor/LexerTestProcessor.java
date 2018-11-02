package test.processor;

import pmc.io.Lexer;
import pmc.io.Lexer.Token;

import java.util.List;

public class LexerTestProcessor implements TestProcessor<List<Token>> {

    @Override
    public List<Token> process(String input){
        return new Lexer(input).scan();
    }

}