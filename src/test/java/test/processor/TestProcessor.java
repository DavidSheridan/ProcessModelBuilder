package test.processor;

public interface TestProcessor<ExpectedType> {

    LexerTestProcessor LEXER = new LexerTestProcessor();

    ExpectedType process(String input);

}