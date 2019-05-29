package test.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestFactory;
import pmc.lang.terminal.ProcessType;
import pmc.lang.terminal.TerminatorType;
import test.processor.TestProcessor;
import util.TestUtil;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class LocalProcessTests<ExpectedType> {

    /**
     * Enum representing the types of reference tests there are.
     */
    public enum TestType {
        WITHOUT_PAREN,
        WITH_PAREN
    }

    /**
     * The action strings to be used when generating tests.
     */
    private static final String[] ACTIONS = new String[]{"a", "b", "c", "d", "e"};

    // field
    private TestProcessor<ExpectedType> processor;

    /**
     * Constructs a new instance of a {@code LocalProcessTests} object with the specified
     * {@code TestProcessor}.
     *
     * @param processor The {@code TestProcessor} to be used in tests.
     */
    public LocalProcessTests(TestProcessor<ExpectedType> processor){
        this.processor = processor;
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified sequential {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType sequentialExpectedWithoutParentheses(SequentialLocalProcessTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified sequential {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType sequentialExpectedWithParentheses(SequentialLocalProcessTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified looped {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType loopedExpectedWithoutParentheses(LoopedLocalProcessTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the looped looped {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType loopedExpectedWithParentheses(LoopedLocalProcessTests.TestData data);

    @Nested
    @DisplayName("sequential tests")
    public class SequentialLocalProcessTests {

        /**
         * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
         * containing test cases for sequential local process definitions without parentheses.
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
         * containing test cases for sequential local process definitions with parentheses.
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
            return TestUtil.permute(ProcessType.values(), TestUtil.series(ACTIONS, 2), TerminatorType.values()).stream()
                    .map(tuple -> new TestData((ProcessType)tuple[0], (String[])tuple[1], (TerminatorType)tuple[2]));
        }

        /**
         * Generates and returns the a valid process definition {@code String} input based
         * on the specified {@code TestType} and {@code TestData}.
         *
         * @param type The type of input to be generated.
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String input(TestType type, TestData data){
            switch(type){
                case WITHOUT_PAREN:
                    return inputWithoutParentheses(data);
                case WITH_PAREN:
                    return inputWithParentheses(data);
            }

            throw new IllegalArgumentException("invalid test type: " + type);
        }

        /**
         * Generates and returns a valid {@code String} input with a series of process
         * definitions containing sequential local processes based on the specified {@code TestData}
         * without parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithoutParentheses(TestData data){
            StringBuilder builder = new StringBuilder();

            return builder.toString();
        }

        /**
         * Generates and returns a valid {@code String} input with a series of process
         * definitions containing sequential local processes based on the specified {@code TestData}
         * with parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithParentheses(TestData data){
            StringBuilder builder = new StringBuilder();

            return builder.toString();
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
                    return sequentialExpectedWithoutParentheses(data);
                case WITH_PAREN:
                    return sequentialExpectedWithParentheses(data);
            }

            throw new IllegalArgumentException("invalid test type: " + type);
        }

        /**
         * Class representing the data required for a sequential local process test. This consists of a
         * {@code ProcessType}, a series of actions and {@code TerminatorType}.
         */
        public class TestData {

            // fields
            public final ProcessType processType;
            public final String[] actions;
            public final TerminatorType terminator;

            /**
             * Constructs a new instance of a {@code TestData} object with the specified
             * {@code ProcessType}, array of action {@code String}s and {@code TerminatorType}.
             *
             * @param processType The process type.
             * @param actions The array of action strings.
             * @param terminator The terminator type.
             */
            public TestData(ProcessType processType, String[] actions, TerminatorType terminator){
                this.processType = processType;
                this.actions = actions;
                this.terminator = terminator;
            }

        }

    }

    @Nested
    @DisplayName("looped tests")
    public class LoopedLocalProcessTests {

        /**
         * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
         * containing test cases for looped local process definitions without parentheses.
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
         * containing test cases for lopped local process definitions with parentheses.
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
            return TestUtil.permute(ProcessType.values(), TestUtil.series(ACTIONS, 2)).stream()
                    .map(tuple -> new TestData((ProcessType)tuple[0], (String[])tuple[1]));
        }

        /**
         * Generates and returns the a valid process definition {@code String} input based
         * on the specified {@code TestType} and {@code TestData}.
         *
         * @param type The type of input to be generated.
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String input(TestType type, TestData data){
            switch(type){
                case WITHOUT_PAREN:
                    return inputWithoutParentheses(data);
                case WITH_PAREN:
                    return inputWithParentheses(data);
            }

            throw new IllegalArgumentException("invalid test type: " + type);
        }

        /**
         * Generates and returns a valid {@code String} input with a series of process
         * definitions containing looped local processes based on the specified {@code TestData}
         * without parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithoutParentheses(TestData data){
            StringBuilder builder = new StringBuilder();

            return builder.toString();
        }

        /**
         * Generates and returns a valid {@code String} input with a series of process
         * definitions containing looped local processes based on the specified {@code TestData}
         * with parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithParentheses(TestData data){
            StringBuilder builder = new StringBuilder();

            return builder.toString();
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
                    return loopedExpectedWithoutParentheses(data);
                case WITH_PAREN:
                    return loopedExpectedWithParentheses(data);
            }

            throw new IllegalArgumentException("invalid test type: " + type);
        }

        /**
         * Class representing the data required for a looped local process test. This consists of a
         * {@code ProcessType} and a series of actions.
         */
        public class TestData {

            // fields
            public final ProcessType processType;
            public final String[] actions;


            /**
             * Constructs a new instance of a {@code TestData} object with the specified
             * {@code ProcessType} and array of actions {@code String}s.
             *
             * @param processType The process type.
             * @param actions The array of action strings.
             */
            public TestData(ProcessType processType, String[] actions){
                this.processType = processType;
                this.actions = actions;
            }

        }

    }

}