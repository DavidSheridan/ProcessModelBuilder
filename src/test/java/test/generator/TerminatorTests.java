package test.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import pmc.lang.terminal.ProcessType;
import pmc.lang.terminal.TerminatorType;
import test.processor.TestProcessor;
import util.TestUtil;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TerminatorTests<ExpectedType> {

    /**
     * Enum representing the types of terminator tests there are.
     */
    private enum TestType{
        WITHOUT_PAREN,
        WITH_PAREN
    }

    // field
    private TestProcessor<ExpectedType> processor;

    /**
     * Constructs a new instance of a {@code TerminatorTests} object with the specified
     * {@code TestProcessor}.
     *
     * @param processor - The {@code TestProcessor} to be used in tests.
     */
    public TerminatorTests(TestProcessor<ExpectedType> processor){
        this.processor = processor;
    }

    /**
     * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
     * containing test cases for terminator process definitions without parentheses.
     *
     * @return {@code Stream} of {@code DynamicTest}s.
     */
    @TestFactory
    @DisplayName("non parenthesised tests")
    public Stream<DynamicTest> testsWithoutParentheses(){
        return generateTestDataStream().map(data -> generateTestCase(TestType.WITHOUT_PAREN, data));
    }

    /**
     * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
     * containing test cases for terminator process definitions with parentheses.
     *
     * @return {@code Stream} of {@code DynamicTest}s.
     */
    @TestFactory
    @DisplayName("parenthesised tests")
    public Stream<DynamicTest> testsWithParentheses(){
        return generateTestDataStream().map(data -> generateTestCase(TestType.WITH_PAREN, data));
    }

    /**
     * Generates a {@code DynamicTest} to be executed by a {@code TestFactory}.
     * Asserts that result received from processing the test input matches
     * the expected result.
     *
     * @param type The type of test to be executed.
     * @param data The test data required to generate the test.
     * @return The {@code DynamicTest} to execute.
     */
    private DynamicTest generateTestCase(TestType type, TestData data){
        String input = input(type, data);
        return DynamicTest.dynamicTest(input, () -> {
            ExpectedType expected = expected(type, data);
            ExpectedType result = processor.process(input);
            assertEquals(expected, result);
        });
    }

    private Stream<TestData> generateTestDataStream(){
        return TestUtil.permute(ProcessType.values(), TerminatorType.values()).stream()
                .map(tuple -> new TestData((ProcessType)tuple[0], (TerminatorType)tuple[1]));
    }

    /**
     * Generates and returns the a valid process definition {@code String} input based
     * on the specified {@code TestType} and {@code TestData}.
     *
     * @param type The type of input to be generated.
     * @param data The test data required to generate the input.
     * @return A valid process definition input.
     */
    private String input(TestType type, TestData data){
        switch(type){
            case WITHOUT_PAREN:
                return inputWithoutParentheses(data);
            case WITH_PAREN:
                return inputWithParentheses(data);
            default:
                throw new IllegalArgumentException("invalid terminator test type: " + type);
        }
    }

    /**
     * Generates and returns a valid terminator process definition {@code String} input
     * from the specified {@code TestData} without parentheses.
     *
     * @param data The test data required to generate the input.
     * @return A valid process definition input.
     */
    private String inputWithoutParentheses(TestData data){
        return data.processType + " " + data.identifier + " = " + data.terminator + ".";
    }

    /**
     * Constructs and returns a valid terminator process definition {@code String} input
     * from the specified {@code TestData} with parentheses.
     * @param data
     * @return
     */
    private String inputWithParentheses(TestData data){
        return data.processType + " " + data.identifier + " = (" + data.terminator + ").";
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified {@code TestData}
     * based on the specified {@code TestType}.
     *
     * @param type The type of input to be generated.
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    private ExpectedType expected(TestType type, TestData data){
        switch(type){
            case WITHOUT_PAREN:
                return expectedWithoutParentheses(data);
            case WITH_PAREN:
                return expectedWithParentheses(data);
            default:
                throw new IllegalArgumentException("invalid terminator test type: " + type);
        }
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType expectedWithoutParentheses(TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType expectedWithParentheses(TestData data);

    /**
     * Class representing the data required for a terminator test. This consists of a
     * {@code ProcessType} and {@code TerminatorType}. The process identifier is defined
     * in the class, however this value can be changed to any valid identifier {@code String}.
     */
    public static class TestData {

        // fields
        public final ProcessType processType;
        public final String identifier = "Test";
        public final TerminatorType terminator;

        /**
         * Constructs a new instance of a {@code TestData} object with the specified
         * {@code ProcessType} and {@code TerminatorType}.
         *
         * @param processType The process type.
         * @param terminator The terminator type.
         */
        public TestData(ProcessType processType, TerminatorType terminator){
            this.processType = processType;
            this.terminator = terminator;
        }

    }

}