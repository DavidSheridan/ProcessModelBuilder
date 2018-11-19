package test.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.processor.TestProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class ExampleTests<ExpectedType> {

    // field
    private TestProcessor<ExpectedType> processor;

    public ExampleTests(TestProcessor<ExpectedType> processor){
        this.processor = processor;
    }

    @Test
    @DisplayName("automata Tea = (takeTea -> STOP).")
    public void teaExampleTest(){
        ExpectedType expected = expectedTeaExample();
        ExpectedType result = processor.process("automata Tea = takeTea -> STOP).");
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedTeaExample();

    @Test
    @DisplayName("automata TeaTwo = (teaButton -> takeTea -> STOP).")
    public void teaTwoExampleTest(){
        ExpectedType expected = expectedTeaTwoExample();
        ExpectedType result = processor.process("automata Tea = teaButton -> takeTea -> STOP).");
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedTeaTwoExample();

}