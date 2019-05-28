package test.generator;

import test.processor.TestProcessor;

public abstract class SimplifiedReferenceTests<ExpectedType> extends ReferenceTests<ExpectedType> {

    /**
     * Constructs a new instance of a {@code ReferenceTests} object with the specified
     * {@code TestProcessor}.
     *
     * @param processor The {@code TestProcessor} to be used in tests.
     */
    public SimplifiedReferenceTests(TestProcessor<ExpectedType> processor){
        super(processor);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified sequential {@code TestData}.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType expected(SequentialReferenceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified sequential {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType sequentialExpectedWithoutParentheses(SequentialReferenceTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified sequential {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType sequentialExpectedWithParentheses(SequentialReferenceTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified self reference {@code TestData}.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType expected(SelfReferenceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified self reference {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType selfReferenceExpectedWithoutParentheses(SelfReferenceTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified self reference {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType selfReferenceExpectedWithParentheses(SelfReferenceTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified self reference {@code TestData}
     * with nesting parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public ExpectedType selfReferenceExpectedWithNesting(SelfReferenceTests.TestData data){
        return expected(data);
    }

}