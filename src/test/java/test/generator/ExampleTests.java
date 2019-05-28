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
        ExpectedType result = processor.process("automata Tea = (takeTea -> STOP).");
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedTeaExample();

    @Test
    @DisplayName("automata TeaTwo = (teaButton -> takeTea -> STOP).")
    public void teaTwoExampleTest(){
        ExpectedType expected = expectedTeaTwoExample();
        ExpectedType result = processor.process("automata TeaTwo = (teaButton -> takeTea -> STOP).");
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedTeaTwoExample();

    @Test
    @DisplayName("automata CoffeeMachine = (teaButton -> takeTea -> STOP | coffeeButton -> takeCoffee -> STOP).")
    public void coffeeMachineExample(){
        ExpectedType expected = expectedCoffeeMachineExample();
        ExpectedType result = processor.process("automata CoffeeMachine = (teaButton -> takeTea -> STOP | coffeeButton -> takeCoffee -> STOP).");
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedCoffeeMachineExample();

    @Test
    @DisplayName("automata CoffeeMachineTwo = (coin -> (teaButton -> takeTea -> STOP | coffeeButton -> takeCoffee -> STOP)).")
    public void coffeeMachineTwoExample(){
        ExpectedType expected = expectedCoffeeMachineTwoExample();
        ExpectedType result = processor.process("automata CoffeeMachineTwo = (coin -> (teaButton -> takeTea -> STOP | coffeeButton -> takeCoffee -> STOP)).");
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedCoffeeMachineTwoExample();

    @Test
    @DisplayName("automata CoffeeMachineThree = (coin -> teaButton -> takeTea -> STOP | coin -> coffeeButton -> takeCoffee -> STOP).")
    public void coffeeMachineThreeExample(){
        ExpectedType expected = expectedCoffeeMachineThreeExample();
        ExpectedType result = processor.process("automata CoffeeMachineThree = (coin -> teaButton -> takeTea -> STOP | coin -> coffeeButton -> takeCoffee -> STOP).");
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedCoffeeMachineThreeExample();

    @Test
    @DisplayName("automata TeaThree = (takeTea -> TeaThree).")
    public void teaThreeExampleTest(){
        ExpectedType expected = expectedTeaThreeExample();
        ExpectedType result = processor.process("automata TeaThree = (takeTea -> TeaThree).");
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedTeaThreeExample();

}