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

public abstract class ReferenceTests<ExpectedType> {

    /**
     * Enum representing the types of reference tests there are.
     */
    public enum TestType {
        WITHOUT_PAREN,
        WITH_PAREN,
        NESTED
    }

    /**
     * The action strings to be used when generating tests.
     */
    private static final String[] ACTIONS = new String[]{"a", "b", "c", "d", "e"};

    // field
    private TestProcessor<ExpectedType> processor;

    /**
     * Constructs a new instance of a {@code ReferenceTests} object with the specified
     * {@code TestProcessor}.
     *
     * @param processor The {@code TestProcessor} to be used in tests.
     */
    public ReferenceTests(TestProcessor<ExpectedType> processor){
        this.processor = processor;
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified sequential {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType sequentialExpectedWithoutParentheses(SequentialReferenceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified sequential {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType sequentialExpectedWithParentheses(SequentialReferenceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified self reference {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType selfReferenceExpectedWithoutParentheses(SelfReferenceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified self reference {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType selfReferenceExpectedWithParentheses(SelfReferenceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified self reference {@code TestData}
     * with nesting parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return The expected result.
     */
    public abstract ExpectedType selfReferenceExpectedWithNesting(SelfReferenceTests.TestData data);

    @Nested
    @DisplayName("sequential tests")
    public class SequentialReferenceTests {

        /**
         * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
         * containing test cases for sequential reference process definitions without parentheses.
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
         * containing test cases for sequential reference process definitions with parentheses.
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
         * definitions containing sequential referencing based on the specified {@code TestData}
         * without parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithoutParentheses(TestData data){
            StringBuilder builder = new StringBuilder();
            for(int i = data.actions.length - 1; i >= 0; i--){
                builder.append(data.processType + " " + data.actions[i].toUpperCase() + " = " + data.actions[i] + " -> ");
                if(i == data.actions.length - 1){
                    builder.append(data.terminator);
                }
                else{
                    builder.append(data.actions[i + 1].toUpperCase());
                }
                builder.append(".");
                if(i != 0){
                    builder.append("\n");
                }
            }

            return builder.toString();
        }

        /**
         * Generates and returns a valid {@code String} input with a series of process
         * definitions containing sequential referencing based on the specified {@code TestData}
         * with parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithParentheses(TestData data){
            StringBuilder builder = new StringBuilder();
            for(int i = data.actions.length - 1; i >= 0; i--){
                builder.append(data.processType + " " + data.actions[i].toUpperCase() + " = (" + data.actions[i] + " -> ");
                if(i == data.actions.length - 1){
                    builder.append(data.terminator);
                }
                else{
                    builder.append(data.actions[i + 1].toUpperCase());
                }
                builder.append(").");
                if(i != 0){
                    builder.append("\n");
                }
            }

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
         * Class representing the data required for a sequential reference test. This consists of a
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
    @DisplayName("self reference tests")
    public class SelfReferenceTests {

        /**
         * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
         * containing test cases for self reference process definitions without parentheses.
         *
         * @return {@code Stream} of {@code DynamicTest}s.
         */
        @TestFactory
        @DisplayName("non parenthesised tests")
        public Stream<DynamicTest> testsWithoutParentheses(){
            return generateTestDataStream(1).map(data -> generateTestCase(TestType.WITHOUT_PAREN, data));
        }

        /**
         * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
         * containing test cases for sef reference process definitions with parentheses.
         *
         * @return {@code Stream} of {@code DynamicTest}s.
         */
        @TestFactory
        @DisplayName("parenthesised tests")
        public Stream<DynamicTest> testsWithParentheses(){
            return generateTestDataStream(1).map(data -> generateTestCase(TestType.WITH_PAREN, data));
        }

        /**
         * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
         * containing test cases for self reference process definitions with nesting.
         *
         * @return {@code Stream} of {@code DynamicTest}s.
         */
        @TestFactory
        @DisplayName("nested tests")
        public Stream<DynamicTest> testsWithNesting(){
            return generateTestDataStream(2).map(data -> generateTestCase(TestType.NESTED, data));
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

        private Stream<TestData> generateTestDataStream(int start){
            return TestUtil.permute(ProcessType.values(), TestUtil.series(ACTIONS, start), TerminatorType.values()).stream()
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
                case NESTED:
                    return inputWithNesting(data);
            }

            throw new IllegalArgumentException("invalid test type: " + type);
        }

        /**
         * Generates and returns a valid self reference process definition {@code String} input
         * from the specified {@code TestData} without parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid process definition input.
         */
        private String inputWithoutParentheses(TestData data){
            StringBuilder builder = new StringBuilder();
            builder.append(data.processType + " " + data.identifier + " = ");
            for(String action : data.actions){
                builder.append(action + " -> ");
            }
            builder.append(data.identifier + ".");

            return builder.toString();
        }

        /**
         * Generates and returns a valid self reference process definition {@code String} input
         * from the specified {@code TestData} with parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid process definition input.
         */
        private String inputWithParentheses(TestData data){
            StringBuilder builder = new StringBuilder();
            builder.append(data.processType + " " + data.identifier + " = (");
            for(String action : data.actions){
                builder.append(action + " -> ");
            }
            builder.append(data.identifier + ").");

            return builder.toString();
        }

        /**
         * Generates and returns a valid self reference process definition {@code String} input
         * from the specified {@code TestData} with nesting.
         *
         * @param data The test data required to generate the input.
         * @return A valid process definition input.
         */
        private String inputWithNesting(TestData data){
            StringBuilder builder = new StringBuilder();
            builder.append(data.processType + " " + data.identifier + " = ");
            for(String action : data.actions){
                builder.append("(" + action + " -> ");
            }
            builder.append(data.identifier);
            for(int i = 0; i < data.actions.length; i++){
                builder.append(")");
            }
            builder.append(".");

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
                    return selfReferenceExpectedWithoutParentheses(data);
                case WITH_PAREN:
                    return selfReferenceExpectedWithParentheses(data);
                case NESTED:
                    return selfReferenceExpectedWithNesting(data);
            }

            throw new IllegalArgumentException("invalid test type: " + type);
        }

        /**
         * Class representing the data required for a self reference test. This consists of a
         * {@code ProcessType} and a series of actions.
         */
        public class TestData {

            // fields
            public final ProcessType processType;
            public final String identifier = "Test";
            public final String[] actions;

            /**
             * Constructs a new instance of a {@code TestData} object with the specified
             * {@code ProcessType} and array of action {@code String}s.
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