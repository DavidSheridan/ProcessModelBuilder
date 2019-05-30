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

    private static final String TEA_INPUT = "automata Tea = (takeTea -> STOP).";

    @Test
    @DisplayName(TEA_INPUT)
    public void teaExampleTest(){
        ExpectedType expected = expectedTeaExample();
        ExpectedType result = processor.process(TEA_INPUT);
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedTeaExample();

    private static final String TEA_TWO_INPUT = "automata TeaTwo = (teaButton -> takeTea -> STOP).";

    @Test
    @DisplayName(TEA_TWO_INPUT)
    public void teaTwoExampleTest(){
        ExpectedType expected = expectedTeaTwoExample();
        ExpectedType result = processor.process(TEA_TWO_INPUT);
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedTeaTwoExample();

    private static final String COFFEE_MACHINE_INPUT = "automata CoffeeMachine = (teaButton -> takeTea -> STOP | coffeeButton -> takeCoffee -> STOP).";

    @Test
    @DisplayName(COFFEE_MACHINE_INPUT)
    public void coffeeMachineExample(){
        ExpectedType expected = expectedCoffeeMachineExample();
        ExpectedType result = processor.process(COFFEE_MACHINE_INPUT);
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedCoffeeMachineExample();

    private static final String COFFEE_MACHINE_TWO_INPUT = "automata CoffeeMachineTwo = (coin -> (teaButton -> takeTea -> STOP | coffeeButton -> takeCoffee -> STOP)).";

    @Test
    @DisplayName(COFFEE_MACHINE_TWO_INPUT)
    public void coffeeMachineTwoExample(){
        ExpectedType expected = expectedCoffeeMachineTwoExample();
        ExpectedType result = processor.process(COFFEE_MACHINE_TWO_INPUT);
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedCoffeeMachineTwoExample();

    private static final String COFFEE_MACHINE_THREE_INPUT = "automata CoffeeMachineThree = (coin -> teaButton -> takeTea -> STOP | coin -> coffeeButton -> takeCoffee -> STOP).";

    @Test
    @DisplayName(COFFEE_MACHINE_THREE_INPUT)
    public void coffeeMachineThreeExample(){
        ExpectedType expected = expectedCoffeeMachineThreeExample();
        ExpectedType result = processor.process(COFFEE_MACHINE_THREE_INPUT);
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedCoffeeMachineThreeExample();

    private static final String TEA_THREE_INPUT = "automata TeaThree = (takeTea -> TeaThree).";

    @Test
    @DisplayName(TEA_THREE_INPUT)
    public void teaThreeExampleTest(){
        ExpectedType expected = expectedTeaThreeExample();
        ExpectedType result = processor.process(TEA_THREE_INPUT);
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedTeaThreeExample();

    private static final String P_INPUT = "automata P = (a -> Q), Q = (b -> P | c -> Q).";

    @Test
    @DisplayName(P_INPUT)
    public void localProcessExample(){
        ExpectedType expected = expectedLocalProcessExample();
        ExpectedType result = processor.process(P_INPUT);
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedLocalProcessExample();

    private static final String TRAFFIC_LIGHT_INPUT = "automata TrRed = (red -> TrRed | turnGreen -> TrGreen), TrGreen = (green -> TrGreen | turnRed -> TrRed).";

    @Test
    @DisplayName(TRAFFIC_LIGHT_INPUT)
    public void trafficLightExample(){
        ExpectedType expected = expectedTrafficLightExample();
        ExpectedType result = processor.process(TRAFFIC_LIGHT_INPUT);
        assertEquals(expected, result);
    }

    public abstract ExpectedType expectedTrafficLightExample();

}