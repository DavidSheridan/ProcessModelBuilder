package test.generator;

import test.processor.TestProcessor;

public abstract class SimplifiedSequenceTests<ExpectedType> extends SequenceTests<ExpectedType> {

    /**
     * Constructs a new instance of a {@code SimplifiedSequenceTests} object with the specified
     * {@code TestProcessor}.
     *
     * @param processor The {@code TestProcessor} to be used in tests.
     */
    public SimplifiedSequenceTests(TestProcessor<ExpectedType> processor) {
        super(processor);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified {@code TestData}.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType expected(SequenceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType expectedWithoutParentheses(TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType expectedWithParentheses(TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified {@code TestData}
     * with nested sequences.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType expectedWithNesting(TestData data){
        return expected(data);
    }

}