package test.processor;

public interface TestProcessor<ExpectedType> {

    /**
     * A static reference to an instance of a {@code LexerTestProcessor} object.
     */
    LexerTestProcessor LEXER = new LexerTestProcessor();

    /**
     * Takes the specified {@code String} input and returns the {@code ExpectedType} output.
     *
     * @param input The input to be processed.
     * @return The resulting output.
     */
    ExpectedType process(String input);

}