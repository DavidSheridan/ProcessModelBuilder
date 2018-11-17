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

public abstract class SequenceTests<ExpectedType> {

    /**
     * Enum representing the types of sequence tests there are.
     */
    public enum TestType {
        WITHOUT_PAREN,
        WITH_PAREN,
        NESTED
    }

    public static final String[] ACTIONS = new String[]{"a", "b", "c", "d", "e"};

    // field
    private TestProcessor<ExpectedType> processor;

    public SequenceTests(TestProcessor<ExpectedType> processor){
        this.processor = processor;
    }

    @TestFactory
    @DisplayName("non parenthesised tests")
    public Stream<DynamicTest> testsWithoutParentheses(){
        return generateTestDataStream(1).map(data -> generateTestCase(TestType.WITHOUT_PAREN, data));
    }

    @TestFactory
    @DisplayName("parenthesised tests")
    public Stream<DynamicTest> testsWithParentheses(){
        return generateTestDataStream(1).map(data -> generateTestCase(TestType.WITH_PAREN, data));
    }

    @TestFactory
    @DisplayName("nested tests")
    public Stream<DynamicTest> testsWithNesting(){
        return generateTestDataStream(2).map(data -> generateTestCase(TestType.NESTED, data));
    }

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
                .map(tuple -> new TestData((ProcessType)tuple[0], (String[])tuple[1], (TerminatorType)tuple[2]));
    }

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

    private String inputWithoutParentheses(TestData data){
        StringBuilder builder = new StringBuilder();
        builder.append(data.processType + " " + data.identifier + " = ");
        for(String action : data.actions){
            builder.append(action + " -> ");
        }
        builder.append(data.terminator + ".");

        return builder.toString();
    }

    private String inputWithParentheses(TestData data){
        StringBuilder builder = new StringBuilder();
        builder.append(data.processType + " " + data.identifier + " = (");
        for(String action : data.actions){
            builder.append(action + " -> ");
        }
        builder.append(data.terminator + ").");

        return builder.toString();
    }

    private String inputWithNesting(TestData data){
        StringBuilder builder = new StringBuilder();
        builder.append(data.processType + " " + data.identifier + " = ");
        for(String action : data.actions){
            builder.append("(" + action + " -> ");
        }
        builder.append(data.terminator);
        for(int i = 0; i < data.actions.length; i++){
            builder.append(")");
        }
        builder.append(data.terminator + ".");

        return builder.toString();
    }

    private ExpectedType expected(TestType type, TestData data){
        switch(type){
            case WITHOUT_PAREN:
                return expectedWithoutParentheses(data);
            case WITH_PAREN:
                return expectedWithParentheses(data);
            case NESTED:
                return expectedWithNesting(data);
        }

        throw new IllegalArgumentException("invalid test type: " + type);
    }

    public abstract ExpectedType expectedWithoutParentheses(TestData data);

    public abstract ExpectedType expectedWithParentheses(TestData data);

    public abstract ExpectedType expectedWithNesting(TestData data);

    public static class TestData {

        // fields
        public final ProcessType processType;
        public final String identifier = "Test";
        public final String[] actions;
        public final TerminatorType terminator;

        public TestData(ProcessType processType, String[] actions, TerminatorType terminator){
            this.processType = processType;
            this.actions = actions;
            this.terminator = terminator;
        }

    }

}