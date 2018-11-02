package test.processor;

public interface TestProcessor<ExpectedType> {

    ExpectedType process(String input);

}