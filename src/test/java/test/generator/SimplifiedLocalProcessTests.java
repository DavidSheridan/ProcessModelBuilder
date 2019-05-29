package test.generator;

import test.processor.TestProcessor;

public abstract class SimplifiedLocalProcessTests<ExpectedType> extends LocalProcessTests<ExpectedType> {

    /**
     * Constructs a new instance of a {@code SimplifiedLocalProcessTests} object with the specified
     * {@code TestProcessor}.
     *
     * @param processor The {@code TestProcessor} to be used in tests.
     */
    public SimplifiedLocalProcessTests(TestProcessor<ExpectedType> processor) {
        super(processor);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified {@code TestData}.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType expected(SequentialLocalProcessTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified sequential {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType sequentialExpectedWithoutParentheses(SequentialLocalProcessTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified sequential {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType sequentialExpectedWithParentheses(SequentialLocalProcessTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified {@code TestData}.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType expected(LoopedLocalProcessTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified looped {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType loopedExpectedWithoutParentheses(LoopedLocalProcessTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the looped looped {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType loopedExpectedWithParentheses(LoopedLocalProcessTests.TestData data){
        return expected(data);
    }

}