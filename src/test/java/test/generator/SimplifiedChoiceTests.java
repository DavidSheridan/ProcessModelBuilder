package test.generator;

import test.processor.TestProcessor;

public abstract class SimplifiedChoiceTests<ExpectedType> extends ChoiceTests<ExpectedType> {

    /**
     * Constructs a new instance of a {@code SimplifiedChoiceTests} object with the specified
     * {@code TestProcessor}.
     *
     * @param processor The {@code TestProcessor} to be used in tests.
     */
    public SimplifiedChoiceTests(TestProcessor<ExpectedType> processor){
        super(processor);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified two branch (@code TestData}.
     *
     * @param data The test data required to generate the expected result.
     *
     * @return The expected result.
     */
    public abstract ExpectedType expected(TwoBranchChoiceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified two branch {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     *
     * @return The expected result.
     */
    public ExpectedType twoBranchExpectedWithoutParentheses(TwoBranchChoiceTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified two branch {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     *
     * @return The expected result.
     */
    public ExpectedType twoBranchExpectedWithParentheses(TwoBranchChoiceTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified two branch {@code TestData}
     * with nesting.
     *
     * @param data The test data required to generate the expected result.
     *
     * @return The expected result.
     */
    public ExpectedType twoBranchExpectedWithNesting(TwoBranchChoiceTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified multi branch (@code TestData}.
     *
     * @param data The test data required to generate the expected result.
     *
     * @return The expected result.
     */
    public abstract ExpectedType expected(MultiBranchChoiceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified multi branch {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     *
     * @return The expected result.
     */
    public ExpectedType multiBranchExpectedWithoutParentheses(MultiBranchChoiceTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified multi branch {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     *
     * @return The expected result.
     */
    public ExpectedType multiBranchExpectedWithParentheses(MultiBranchChoiceTests.TestData data){
        return expected(data);
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified multi branch {@code TestData}
     * with nesting.
     *
     * @param data The test data required to generate the expected result.
     *
     * @return The expected result.
     */
    public ExpectedType multiBranchExpectedWithNesting(MultiBranchChoiceTests.TestData data){
        return expected(data);
    }

}