package pmc.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import pmc.lang.AST;
import pmc.lang.action.ActionElementList;
import pmc.lang.action.element.StringActionElement;
import pmc.lang.definition.BlockDefinition;
import pmc.lang.definition.Definition;
import pmc.lang.definition.LocalProcessDefinition;
import pmc.lang.definition.ProcessDefinition;
import pmc.lang.process.*;
import pmc.lang.process.Process;
import pmc.lang.terminal.ProcessType;
import pmc.lang.terminal.TerminatorType;
import test.generator.*;
import test.processor.TestProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("parser tests")
public class ParserTests {

    @Nested
    @DisplayName("terminator tests")
    public class ParserTerminatorTests extends SimplifiedTerminatorTests<AST> {

        /**
         * Constructs a new instance of a {@code ParserTerminatorTests} object.
         */
        public ParserTerminatorTests(){
            super(TestProcessor.PARSER);
        }

        /**
         * Generates and returns the expected {@code AST} for the specified {@code TestData}.
         *
         * @param data The test data required to generate the expected {@ocde AST}.
         * @return The expected {@code AST}.
         */
        @Override
        public AST expected(TestData data){
            return new AST(new BlockDefinition(new ProcessDefinition.Builder(data.processType, data.identifier, new Terminator(data.terminator)).build()));
        }

    }

    @Nested
    @DisplayName("sequence tests")
    public class ParserSequenceTests extends SimplifiedSequenceTests<AST> {

        /**
         * Constructs a new instance of a {@code ParserSequenceTests} object.
         */
        public ParserSequenceTests(){
            super(TestProcessor.PARSER);
        }

        /**
         * Generates and returns the expected {@code AST} for the specified {@code TestData}
         * without parentheses.
         *
         * @param data The test data required to generate the expected {@ocde AST}.
         * @return The expected {@code AST}.
         */
        @Override
        public AST expected(TestData data){
            Process process = new Terminator(data.terminator);
            for(int i = data.actions.length - 1; i >= 0; i--){
                process = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement(data.actions[i]))), process);
            }

            return new AST(new BlockDefinition(new ProcessDefinition.Builder(data.processType, data.identifier, process).build()));
        }

    }

    @Nested
    @DisplayName("reference tests")
    public class ParserReferenceTests extends SimplifiedReferenceTests<AST> {

        public ParserReferenceTests(){
            super(TestProcessor.PARSER);
        }

        @Override
        public AST expected(SequentialReferenceTests.TestData data){
            List<Definition> definitions = new ArrayList<Definition>();
            for(int i = data.actions.length - 1; i >= 0; i--){
                Process process = (i == data.actions.length - 1) ? new Terminator(data.terminator) : new Reference(data.actions[i + 1].toUpperCase());
                process = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement(data.actions[i]))), process);

                definitions.add(new ProcessDefinition.Builder(data.processType, data.actions[i].toUpperCase(), process).build());
            }

            return new AST(new BlockDefinition(definitions));
        }

        @Override
        public AST expected(SelfReferenceTests.TestData data){
            Process process = new Reference(data.identifier);
            for(int i = data.actions.length - 1; i >= 0; i--){
                process = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement(data.actions[i]))), process);
            }

            return new AST(new BlockDefinition(new ProcessDefinition.Builder(data.processType, data.identifier, process).build()));
        }

    }

    @Nested
    @DisplayName("choice tests")
    public class ParserChoiceTests extends SimplifiedChoiceTests<AST> {

        public ParserChoiceTests(){
            super(TestProcessor.PARSER);
        }

        @Override
        public AST expected(TwoBranchChoiceTests.TestData data){
            Process sequence1 = new Terminator(data.terminator);
            for(int i = data.actions1.length - 1; i >= 0; i--){
                sequence1 = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement(data.actions1[i]))), sequence1);
            }
            Process sequence2 = new Terminator(data.terminator);
            for(int i = data.actions2.length - 1; i >= 0; i--){
                sequence2 = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement(data.actions2[i]))), sequence2);
            }
            Choice choice = new Choice(sequence1, sequence2);
            return new AST(new BlockDefinition(new ProcessDefinition.Builder(data.processType, data.identifier, choice).build()));
        }


        @Override
        public AST expected(MultiBranchChoiceTests.TestData data){
            Process choice = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement(data.actions[data.actions.length - 1]))), new Reference(data.identifier));
            for(int i = data.actions.length - 2; i >= 0; i--){
                choice = new Choice(new Sequence(new ActionElementList(Arrays.asList(new StringActionElement(data.actions[i]))), new Reference(data.identifier)), choice);
            }

            return new AST(new BlockDefinition(new ProcessDefinition.Builder(data.processType, data.identifier, choice).build()));
        }

    }

    @Nested
    @DisplayName("local process tests")
    public class ParserLocalProcessTests extends SimplifiedLocalProcessTests<AST> {

        public ParserLocalProcessTests(){
            super(TestProcessor.PARSER);
        }

        @Override
        public AST expected(SequentialLocalProcessTests.TestData data){
            ProcessDefinition.Builder builder = new ProcessDefinition.Builder();
            builder.setProcessType(data.processType);
            builder.setIdentifier(data.actions[0].toUpperCase());

            for(int i = 0; i < data.actions.length; i++){
                Sequence sequence = new Sequence(
                        new ActionElementList(Arrays.asList(new StringActionElement(data.actions[i]))),
                        i < data.actions.length - 1 ? new Reference(data.actions[i + 1].toUpperCase()) : new Terminator(data.terminator)
                );

                if(i == 0){
                    builder.setProcess(sequence);
                }
                else{
                    builder.addLocalProcess(new LocalProcessDefinition(data.actions[i].toUpperCase(), sequence));
                }
            }

            return new AST(new BlockDefinition(Arrays.asList(builder.build())));
        }

        @Override
        public AST expected(LoopedLocalProcessTests.TestData data){
            ProcessDefinition.Builder builder = new ProcessDefinition.Builder();
            builder.setProcessType(data.processType);
            builder.setIdentifier(data.actions[0].toUpperCase());

            for(int i = 0; i < data.actions.length; i++){
                Sequence sequence = new Sequence(
                        new ActionElementList(Arrays.asList(new StringActionElement(data.actions[i]))),
                        i < data.actions.length - 1 ? new Reference(data.actions[i + 1].toUpperCase()) : new Reference(data.actions[0].toUpperCase())
                );

                if(i == 0){
                    builder.setProcess(sequence);
                }
                else{
                    builder.addLocalProcess(new LocalProcessDefinition(data.actions[i].toUpperCase(), sequence));
                }
            }

            return new AST(new BlockDefinition(Arrays.asList(builder.build())));
        }
    }

    @Nested
    @DisplayName("example tests")
    public class ParserExampleTests extends ExampleTests<AST> {

        public ParserExampleTests(){
            super(TestProcessor.PARSER);
        }

        @Override
        public AST expectedTeaExample(){
            Sequence sequence = new Sequence(
                    new ActionElementList(Arrays.asList(new StringActionElement("takeTea"))),
                    new Terminator(TerminatorType.STOP)
            );

            return new AST(new BlockDefinition(new ProcessDefinition.Builder(ProcessType.AUTOMATA, "Tea", sequence).build()));
        }

        @Override
        public AST expectedTeaTwoExample(){
            Sequence sequence = new Sequence(
                    new ActionElementList(Arrays.asList(new StringActionElement("teaButton"))),
                    new Sequence(
                            new ActionElementList(Arrays.asList(new StringActionElement("takeTea"))),
                            new Terminator(TerminatorType.STOP)
                    )
            );

            return new AST(new BlockDefinition(new ProcessDefinition.Builder(ProcessType.AUTOMATA, "TeaTwo", sequence).build()));
        }

        @Override
        public AST expectedCoffeeMachineExample(){
            Choice choice = new Choice(
                    new Sequence(
                            new ActionElementList(Arrays.asList(new StringActionElement("teaButton"))),
                            new Sequence(
                                    new ActionElementList(Arrays.asList(new StringActionElement("takeTea"))),
                                    new Terminator(TerminatorType.STOP)
                            )
                    ),
                    new Sequence(
                            new ActionElementList(Arrays.asList(new StringActionElement("coffeeButton"))),
                            new Sequence(
                                    new ActionElementList(Arrays.asList(new StringActionElement("takeCoffee"))),
                                    new Terminator(TerminatorType.STOP)
                            )
                    )
            );

            return new AST(new BlockDefinition(new ProcessDefinition.Builder(ProcessType.AUTOMATA, "CoffeeMachine", choice).build()));
        }

        @Override
        public AST expectedCoffeeMachineTwoExample(){
            Sequence sequence = new Sequence(
                    new ActionElementList(Arrays.asList(new StringActionElement("coin"))),
                    new Choice(
                            new Sequence(
                                new ActionElementList(Arrays.asList(new StringActionElement("teaButton"))),
                                new Sequence(
                                            new ActionElementList(Arrays.asList(new StringActionElement("takeTea"))),
                                            new Terminator(TerminatorType.STOP)
                                )
                    ),
                            new Sequence(
                                    new ActionElementList(Arrays.asList(new StringActionElement("coffeeButton"))),
                                    new Sequence(
                                            new ActionElementList(Arrays.asList(new StringActionElement("takeCoffee"))),
                                            new Terminator(TerminatorType.STOP)
                                    )
                            )
                    )
            );

            return new AST(new BlockDefinition(new ProcessDefinition.Builder(ProcessType.AUTOMATA, "CoffeeMachineTwo", sequence).build()));
        }

        @Override
        public AST expectedCoffeeMachineThreeExample(){
            Choice choice = new Choice(
                    new Sequence(
                            new ActionElementList(Arrays.asList(new StringActionElement("coin"))),
                            new Sequence(
                                    new ActionElementList(Arrays.asList(new StringActionElement("teaButton"))),
                                    new Sequence(
                                            new ActionElementList(Arrays.asList(new StringActionElement("takeTea"))),
                                            new Terminator(TerminatorType.STOP)
                                    )
                            )
                    ),
                    new Sequence(
                            new ActionElementList(Arrays.asList(new StringActionElement("coin"))),
                            new Sequence(
                                    new ActionElementList(Arrays.asList(new StringActionElement("coffeeButton"))),
                                    new Sequence(
                                            new ActionElementList(Arrays.asList(new StringActionElement("takeCoffee"))),
                                            new Terminator(TerminatorType.STOP)
                                )
                            )
                    )
            );

            return new AST(new BlockDefinition(new ProcessDefinition.Builder(ProcessType.AUTOMATA, "CoffeeMachineThree", choice).build()));
        }

        @Override
        public AST expectedTeaThreeExample(){
            Sequence sequence = new Sequence(
                    new ActionElementList(Arrays.asList(new StringActionElement("takeTea"))),
                    new Reference("TeaThree")
            );

            return new AST(new BlockDefinition(new ProcessDefinition.Builder(ProcessType.AUTOMATA, "TeaThree", sequence).build()));
        }

    }

}