package pmc.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import pmc.lang.AST;
import pmc.lang.action.ActionElementList;
import pmc.lang.action.element.StringActionElement;
import pmc.lang.definition.BlockDefinition;
import pmc.lang.definition.Definition;
import pmc.lang.definition.ProcessDefinition;
import pmc.lang.process.Process;
import pmc.lang.process.Reference;
import pmc.lang.process.Sequence;
import pmc.lang.process.Terminator;
import pmc.lang.terminal.ProcessType;
import pmc.lang.terminal.TerminatorType;
import test.generator.ExampleTests;
import test.generator.ReferenceTests;
import test.generator.SequenceTests;
import test.generator.TerminatorTests;
import test.processor.TestProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("parser tests")
public class ParserTests {

    @Nested
    @DisplayName("terminator tests")
    public class ParserTerminatorTests extends TerminatorTests<AST> {

        /**
         * Constructs a new instance of a {@code ParserTerminatorTests} object.
         */
        public ParserTerminatorTests(){
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
        public AST expectedWithoutParentheses(TestData data){
            return new AST(new BlockDefinition(new ProcessDefinition(data.processType, data.identifier, new Terminator(data.terminator))));
        }

        /**
         * Generates and returns the expected {@code AST} for the specified {@code TestData}
         * with parentheses.
         *
         * @param data The test data required to generate the expected {@ocde AST}.
         * @return The expected {@ocde AST}.
         */
        @Override
        public AST expectedWithParentheses(TestData data){
            return expectedWithoutParentheses(data);
        }

    }

    @Nested
    @DisplayName("sequence tests")
    public class ParserSequenceTests extends SequenceTests<AST> {

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
        public AST expectedWithoutParentheses(TestData data){
            Process process = new Terminator(data.terminator);
            for(int i = data.actions.length - 1; i >= 0; i--){
                process = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement(data.actions[i]))), process);
            }

            return new AST(new BlockDefinition(new ProcessDefinition(data.processType, data.identifier, process)));
        }

        /**
         * Generates and returns the expected {@code AST} for the specified {@code TestData}
         * with parentheses.
         *
         * @param data The test data required to generate the expected {@ocde AST}.
         * @return The expected {@code AST}.
         */
        @Override
        public AST expectedWithParentheses(TestData data){
            return expectedWithoutParentheses(data);
        }

        /**
         * Generates and returns the expected {@code AST} for the specified {@code TestData}
         * with nested sequences.
         *
         * @param data The test data required to generate the expected {@ocde AST}.
         * @return The expected {@code AST}.
         */
        @Override
        public AST expectedWithNesting(TestData data){
            return expectedWithoutParentheses(data);
        }

    }

    @Nested
    @DisplayName("reference tests")
    public class ParserReferenceTests extends ReferenceTests<AST> {

        public ParserReferenceTests(){
            super(TestProcessor.PARSER);
        }

        @Override
        public AST sequentialExpectedWithoutParentheses(SequentialReferenceTests.TestData data){
            List<Definition> definitions = new ArrayList<Definition>();
            for(int i = data.actions.length - 1; i >= 0; i--){
                Process process = (i == data.actions.length - 1) ? new Terminator(data.terminator) : new Reference(data.actions[i + 1].toUpperCase());
                process = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement(data.actions[i]))), process);

                definitions.add(new ProcessDefinition(data.processType, data.actions[i].toUpperCase(), process));
            }

            return new AST(new BlockDefinition(definitions));
        }

        @Override
        public AST sequentialExpectedWithParentheses(SequentialReferenceTests.TestData data){
            return sequentialExpectedWithoutParentheses(data);
        }

        @Override
        public AST selfReferenceExpectedWithoutParentheses(SelfReferenceTests.TestData data){
            Process process = new Reference(data.identifier);
            for(int i = data.actions.length - 1; i >= 0; i--){
                process = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement(data.actions[i]))), process);
            }

            return new AST(new BlockDefinition(new ProcessDefinition(data.processType, data.identifier, process)));
        }

        @Override
        public AST selfReferenceExpectedWithParentheses(SelfReferenceTests.TestData data){
            return selfReferenceExpectedWithoutParentheses(data);
        }

        @Override
        public AST selfReferenceExpectedWithNesting(SelfReferenceTests.TestData data){
            return selfReferenceExpectedWithoutParentheses(data);
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
            Sequence sequence = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement("takeTea"))), new Terminator(TerminatorType.STOP));
            return new AST(new BlockDefinition(new ProcessDefinition(ProcessType.AUTOMATA, "Tea", sequence)));
        }

        @Override
        public AST expectedTeaTwoExample(){
            Sequence sequence = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement("takeTea"))), new Terminator(TerminatorType.STOP));
            sequence = new Sequence(new ActionElementList(Arrays.asList(new StringActionElement("teaButton"))), sequence);
            return new AST(new BlockDefinition(new ProcessDefinition(ProcessType.AUTOMATA, "TeaTwo", sequence)));
        }

    }

}