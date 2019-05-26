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

public abstract class ChoiceTests<ExpectedType> {

    /**
     * Enum representing the types of reference tests there are.
     */
    public enum TestType{
        WITHOUT_PAREN,
        WITH_PAREN,
        NESTED
    }

    private static final String[] TWO_BRANCH_ACTIONS_1 = new String[]{"a", "b", "c"};
    private static final String[] TWO_BRANCH_ACTIONS_2 = new String[]{"x", "y", "z"};
    private static final String[] MULTI_BRANCH_ACTIONS = new String[]{"a", "b", "c", "d", "e"};

    // fields
    private TestProcessor<ExpectedType> processor;

    /**
     * Constructs a new instance of a {@code Choice} object with the specified
     * {@code TestProcessor}.
     *
     * @param processor The {@code TestProcessor} to be used in tests.
     */
    public ChoiceTests(TestProcessor<ExpectedType> processor){
        this.processor = processor;
    }

    /**
     * Generates and returns the {@code ExpectedType} for the specified two branch {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return
     */
    public abstract ExpectedType twoBranchExpectedWithoutParentheses(TwoBranchChoiceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified two branch {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return
     */
    public abstract ExpectedType twoBranchExpectedWithParentheses(TwoBranchChoiceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified two branch {@code TestData}
     * with nesting.
     *
     * @param data The test data required to generate the expected result.
     * @return
     */
    public abstract ExpectedType twoBranchExpectedWithNesting(TwoBranchChoiceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified multi branch {@code TestData}
     * without parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return
     */
    public abstract ExpectedType multiBranchExpectedWithoutParentheses(MultiBranchChoiceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified multi branch {@code TestData}
     * with parentheses.
     *
     * @param data The test data required to generate the expected result.
     * @return
     */
    public abstract ExpectedType multiBranchExpectedWithParentheses(MultiBranchChoiceTests.TestData data);

    /**
     * Generates and returns the {@code ExpectedType} for the specified multi branch {@code TestData}
     * with nesting.
     *
     * @param data The test data required to generate the expected result.
     * @return
     */
    public abstract ExpectedType multiBranchExpectedWithNesting(MultiBranchChoiceTests.TestData data);

    @Nested
    @DisplayName("two branch tests")
    public class TwoBranchChoiceTests {

        /**
         * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
         * containing test cases for two branch choice process definitions without parentheses.
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
         * containing test cases for two branch choice process definitions with parentheses.
         *
         * @return {@code Stream} of {@code DynamicTest}s.
         */
        @TestFactory
        @DisplayName("parenthesised tests")
        public Stream<DynamicTest> testsWithParentheses(){
            return generateTestDataStream().map(data -> generateTestCase(TestType.WITHOUT_PAREN, data));
        }

        /**
         * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
         * containing test cases for two branch choice process definitions with nesting.
         *
         * @return {@code Stream} of {@code DynamicTest}s.
         */
        @TestFactory
        @DisplayName("nested tests")
        public Stream<DynamicTest> testsWithNesting(){
            return generateTestDataStream().map(data -> generateTestCase(TestType.WITHOUT_PAREN, data));
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
            return TestUtil.permute(ProcessType.values(), TestUtil.series(TWO_BRANCH_ACTIONS_1), TestUtil.series(TWO_BRANCH_ACTIONS_2)).stream()
                    .map(tuple -> new TestData((ProcessType)tuple[0], (String[])tuple[1], (String[])tuple[2], TerminatorType.STOP));
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
         * Generates and returns a valid choice {@code String} input based on
         * the specified {@code TestData} without parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithoutParentheses(TestData data){
            StringBuilder builder = new StringBuilder();
            builder.append(data.processType + " Test = ");
            for(String action : data.actions1){
                builder.append(action + " -> ");
            }
            builder.append(data.terminator);
            builder.append(" | ");
            for(String action : data.actions2){
                builder.append(action + " -> ");
            }
            builder.append(data.terminator);
            builder.append(".");

            return builder.toString();
        }

        /**
         * Generates and returns a valid choice {@code String} input based on
         * the specified {@code TestData} with parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithParentheses(TestData data){
            StringBuilder builder = new StringBuilder();
            builder.append(data.processType + " Test = (");
            for(String action : data.actions1){
                builder.append(action + " -> ");
            }
            builder.append(data.terminator);
            builder.append(" | ");
            for(String action : data.actions2){
                builder.append(action + " -> ");
            }
            builder.append(data.terminator);
            builder.append(").");

            return builder.toString();
        }

        /**
         * Generates and returns a valid choice {@code String} input based on
         * the specified {@code TestData} with nesting.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithNesting(TestData data){
            StringBuilder builder = new StringBuilder();
            builder.append(data.processType + " Test = ");
            for(String action : data.actions1){
                builder.append("(" + action + " -> ");
            }
            builder.append(data.terminator);
            for(int i = 0; i < data.actions1.length; i++){
                builder.append(")");
            }
            builder.append(" | ");
            for(String action : data.actions2){
                builder.append("(" + action + " -> ");
            }
            builder.append(data.terminator);
            for(int i = 0; i < data.actions2.length; i++){
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
        public ExpectedType expected(TestType type, TestData data){
            switch(type){
                case WITHOUT_PAREN:
                    return twoBranchExpectedWithoutParentheses(data);
                case WITH_PAREN:
                    return twoBranchExpectedWithParentheses(data);
                case NESTED:
                    return twoBranchExpectedWithNesting(data);
            }

            throw new IllegalArgumentException("invalid test type: " + type);
        }

        /**
         * Class representing the data required for a two branch choice test. This consists of a
         * {@code ProcessType}, two series of actions and {@code TerminatorType}.
         */
        public class TestData {

            // fields
            public final ProcessType processType;
            public final String[] actions1;
            public final String[] actions2;
            public final TerminatorType terminator;

            /**
             * Constructs a new instance of a {@code TestData} object with the specified
             * {@code ProcessType}, two arrays of action {@code String}s and {@code TerminatorType}.
             *
             * @param processType - The process type.
             * @param actions1 - The action strings for the first branch.
             * @param actions2 - The action strings for the second branch.
             * @param terminator - The terminator type.
             */
            public TestData(ProcessType processType, String[] actions1, String[] actions2, TerminatorType terminator){
                this.processType = processType;
                this.actions1 = actions1;
                this.actions2 = actions2;
                this.terminator = terminator;
            }
        }
    }

    @Nested
    @DisplayName("multi branch tests")
    public class MultiBranchChoiceTests {

        /**
         * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
         * containing test cases for multi branch choice process definitions without parentheses.
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
         * containing test cases for multi branch choice process definitions with parentheses.
         *
         * @return {@code Stream} of {@code DynamicTest}s.
         */
        @TestFactory
        @DisplayName("parenthesised tests")
        public Stream<DynamicTest> testsWithParentheses(){
            return generateTestDataStream().map(data -> generateTestCase(TestType.WITHOUT_PAREN, data));
        }

        /**
         * Dynamically defines and returns a {@code Stream} of {@code DynamicTest}s
         * containing test cases for multi branch choice process definitions with nesting.
         *
         * @return {@code Stream} of {@code DynamicTest}s.
         */
        @TestFactory
        @DisplayName("nested tests")
        public Stream<DynamicTest> testsWithNesting(){
            return generateTestDataStream().map(data -> generateTestCase(TestType.WITHOUT_PAREN, data));
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
        private DynamicTest generateTestCase(TestType type, MultiBranchChoiceTests.TestData data){
            String input = input(type, data);
            return DynamicTest.dynamicTest(input, () -> {
                ExpectedType expected = expected(type, data);
                ExpectedType result = processor.process(input);
                assertEquals(expected, result);
            });
        }

        private Stream<MultiBranchChoiceTests.TestData> generateTestDataStream(){
            return TestUtil.permute(ProcessType.values(), TestUtil.series(MULTI_BRANCH_ACTIONS, 3)).stream()
                    .map(tuple -> new MultiBranchChoiceTests.TestData((ProcessType)tuple[0], (String[])tuple[1]));
        }

        /**
         * Generates and returns the a valid process definition {@code String} input based
         * on the specified {@code TestType} and {@code TestData}.
         *
         * @param type The type of input to be generated.
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String input(TestType type, MultiBranchChoiceTests.TestData data){
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
         * Generates and returns a valid choice {@code String} input based on
         * the specified {@code TestData} without parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithoutParentheses(MultiBranchChoiceTests.TestData data){
            StringBuilder builder = new StringBuilder();
            builder.append(data.processType + " " + data.identifier + " = ");
            for(int i = 0; i < data.actions.length; i++){
                builder.append(data.actions[i] + " -> " + data.identifier);
                if(i < data.actions.length -1){
                    builder.append(" | ");
                }
            }
            builder.append(".");

            return builder.toString();
        }

        /**
         * Generates and returns a valid choice {@code String} input based on
         * the specified {@code TestData} with parentheses.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithParentheses(MultiBranchChoiceTests.TestData data){
            StringBuilder builder = new StringBuilder();
            builder.append(data.processType + " " + data.identifier + " = (");
            for(int i = 0; i < data.actions.length; i++){
                builder.append(data.actions[i] + " -> " + data.identifier);
                if(i < data.actions.length -1){
                    builder.append(" | ");
                }
            }
            builder.append(").");

            return builder.toString();
        }

        /**
         * Generates and returns a valid choice {@code String} input based on
         * the specified {@code TestData} with nesting.
         *
         * @param data The test data required to generate the input.
         * @return A valid input.
         */
        private String inputWithNesting(MultiBranchChoiceTests.TestData data){
            StringBuilder builder = new StringBuilder();
            builder.append(data.processType + " " + data.identifier + " = ");
            for(int i = 0; i < data.actions.length; i++){
                builder.append( "(" + data.actions[i] + " -> " + data.identifier + ")");
                if(i < data.actions.length -1){
                    builder.append(" | ");
                }
            }
            builder.append(").");

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
        public ExpectedType expected(TestType type, MultiBranchChoiceTests.TestData data){
            switch(type){
                case WITHOUT_PAREN:
                    return multiBranchExpectedWithoutParentheses(data);
                case WITH_PAREN:
                    return multiBranchExpectedWithParentheses(data);
                case NESTED:
                    return multiBranchExpectedWithNesting(data);
            }

            throw new IllegalArgumentException("invalid test type: " + type);
        }

        /**
         * Class representing the data required for a two branch choice test. This consists of a
         * {@code ProcessType}, {@code String} identifier and series of actions.
         */
        public class TestData {

            // fields
            public final ProcessType processType;
            public final String identifier = "Test";
            public final String[] actions;

            /**
             * Constructs a new instance of a {@code TestData} object with the specified
             * {@code ProcessType} and array of action {@code String}s.
             * @param processType
             * @param actions
             */
            public TestData(ProcessType processType, String[] actions){
                this.processType = processType;
                this.actions = actions;
            }

        }

    }

}