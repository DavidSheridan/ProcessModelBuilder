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

    private enum TestType{
        WITHOUT_PAREN,
        WITH_PAREN
    }

    // field
    private TestProcessor<ExpectedType> processor;

    public TerminatorTests(TestProcessor<ExpectedType> processor){
        this.processor = processor;
    }

    @TestFactory
    @DisplayName("non parenthesised tests")
    public Stream<DynamicTest> testsWithoutParentheses(){
        return generateTestDataStream().map(data -> generateTestCase(TestType.WITHOUT_PAREN, data));
    }

    @TestFactory
    @DisplayName("parenthesised tests")
    public Stream<DynamicTest> testsWithParentheses(){
        return generateTestDataStream().map(data -> generateTestCase(TestType.WITH_PAREN, data));
    }

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

    private String inputWithoutParentheses(TestData data){
        return data.processType + " " + data.identifier + " = " + data.terminator + ".";
    }

    private String inputWithParentheses(TestData data){
        return data.processType + " " + data.identifier + " = (" + data.terminator + ").";
    }

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

    public abstract ExpectedType expectedWithoutParentheses(TestData data);

    public abstract ExpectedType expectedWithParentheses(TestData data);

    public static class TestData {

        // fields
        public final ProcessType processType;
        public final String identifier = "Test";
        public final TerminatorType terminator;

        public TestData(ProcessType processType, TerminatorType terminator){
            this.processType = processType;
            this.terminator = terminator;
        }

    }

}