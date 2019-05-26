package pmc.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pmc.io.Lexer.*;
import pmc.lang.terminal.ProcessType;
import pmc.lang.terminal.Terminal;
import pmc.lang.terminal.TerminalSymbol;
import pmc.lang.terminal.TerminatorType;
import test.generator.ExampleTests;
import test.generator.ReferenceTests;
import test.generator.SequenceTests;
import test.generator.TerminatorTests;
import test.processor.TestProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("lexer tests")
public class LexerTests {

    /**
     * Generates tests to assert that all {@code Terminal} inputs are converted to
     * {@code TerminalToken}s by the lexer.
     */
    @Nested
    @DisplayName("terminal tests")
    public class LexerTerminalTests {

        /**
         * Generates tests to assert that all {@code ProcessType} inputs are converted to
         * {@code TerminalToken}s by the lexer.
         */
        @ParameterizedTest
        @EnumSource(ProcessType.class)
        @DisplayName("process type terminals")
        public void shouldScanProcessTypeTerminalsCorrectly(ProcessType terminal){
            runTest(terminal);
        }

        /**
         * Generates tests to assert that all {@code TerminalSymbol} inputs are converted to
         * {@code TerminalToken}s by the lexer.
         */
        @ParameterizedTest
        @EnumSource(TerminalSymbol.class)
        @DisplayName("terminal symbol terminals")
        public void shouldScanTerminalSymbolTerminalsCorrectly(TerminalSymbol terminal){
            runTest(terminal);
        }

        /**
         * Generates tests to assert that all {@code TerminatorType} inputs are converted to
         * {@code TerminalToken}s by the lexer.
         */
        @ParameterizedTest
        @EnumSource(TerminatorType.class)
        @DisplayName("terminator type terminals")
        public void shouldScanTerminatorTypeTerminalsCorrectly(TerminatorType terminal){
            runTest(terminal);
        }

        /**
         * Helper method that runs the assertion checking whether or not the lexer correctly
         * converts a {@code Terminal} input into a {@code List} of {@code TerminalToken}s.
         * @param terminal
         */
        private void runTest(Terminal terminal){
            assertEquals(Arrays.asList(new Lexer.TerminalToken(terminal)), new Lexer(terminal.getValue()).scan());
        }

    }

    @Nested
    @DisplayName("terminator tests")
    public class LexerTerminatorTests extends TerminatorTests<List<Token>> {

        /**
         * Constructs a new instance of a {@code LexerTerminatorTests} object.
         */
        public LexerTerminatorTests(){
            super(TestProcessor.LEXER);
        }

        /**
         * Generates and returns the expected {@code List} of {@code Token}s for the specified {@code TestData}
         * without parentheses.
         *
         * @param data The test data required to generate the expected {@ocde List} of {@code Token}s.
         * @return The expected {@ocde List} of {@code Token}s.
         */
        public List<Lexer.Token> expectedWithoutParentheses(TestData data){
            return Arrays.asList(
                    new TerminalToken(data.processType),
                    new UpperCaseIdentifierToken(data.identifier),
                    new TerminalToken(TerminalSymbol.ASSIGN),
                    new TerminalToken(data.terminator),
                    new TerminalToken(TerminalSymbol.DOT)
            );
        }

        /**
         * Generates and returns the expected {@code List} of {@code Token}s for the specified {@code TestData}
         * with parentheses.
         *
         * @param data The test data required to generate the expected {@ocde List} of {@code Token}s.
         * @return The expected {@ocde List} of {@code Token}s.
         */
        public List<Lexer.Token> expectedWithParentheses(TestData data){
            return Arrays.asList(
                    new TerminalToken(data.processType),
                    new UpperCaseIdentifierToken(data.identifier),
                    new TerminalToken(TerminalSymbol.ASSIGN),
                    new TerminalToken(TerminalSymbol.OPEN_PAREN),
                    new TerminalToken(data.terminator),
                    new TerminalToken(TerminalSymbol.CLOSE_PAREN),
                    new TerminalToken(TerminalSymbol.DOT)
            );
        }

    }

    @Nested
    @DisplayName("sequence tests")
    public class LexerSequenceTests extends SequenceTests<List<Token>> {

        /**
         * Constructs a new instance of a {@code LexerSequenceTests} object.
         */
        public LexerSequenceTests(){
            super(TestProcessor.LEXER);
        }

        /**
         * Generates and returns the expected {@code List} of {@code Token}s for the specified {@code TestData}
         * without parentheses.
         *
         * @param data The test data required to generate the expected {@ocde List} of {@code Token}s.
         * @return The expected {@ocde List} of {@code Token}s.
         */
        public List<Token> expectedWithoutParentheses(TestData data){
            List<Token> tokens = new ArrayList<Token>();
            tokens.add(new TerminalToken(data.processType));
            tokens.add(new UpperCaseIdentifierToken(data.identifier));
            tokens.add(new TerminalToken(TerminalSymbol.ASSIGN));
            for(String action : data.actions){
                tokens.add(new LowerCaseIdentifierToken(action));
                tokens.add(new TerminalToken(TerminalSymbol.SEQUENCE));
            }
            tokens.add(new TerminalToken(data.terminator));
            tokens.add(new TerminalToken(TerminalSymbol.DOT));

            return tokens;
        }

        /**
         * Generates and returns the expected {@code List} of {@code Token}s for the specified {@code TestData}
         * with parentheses.
         *
         * @param data The test data required to generate the expected {@ocde List} of {@code Token}s.
         * @return The expected {@ocde List} of {@code Token}s.
         */
        public List<Token> expectedWithParentheses(TestData data){
            List<Token> tokens = new ArrayList<Token>();
            tokens.add(new TerminalToken(data.processType));
            tokens.add(new UpperCaseIdentifierToken(data.identifier));
            tokens.add(new TerminalToken(TerminalSymbol.ASSIGN));
            tokens.add(new TerminalToken(TerminalSymbol.OPEN_PAREN));
            for(String action : data.actions){
                tokens.add(new LowerCaseIdentifierToken(action));
                tokens.add(new TerminalToken(TerminalSymbol.SEQUENCE));
            }
            tokens.add(new TerminalToken(data.terminator));
            tokens.add(new TerminalToken(TerminalSymbol.CLOSE_PAREN));
            tokens.add(new TerminalToken(TerminalSymbol.DOT));

            return tokens;
        }

        /**
         * Generates and returns the expected {@code List} of {@code Token}s for the specified {@code TestData}
         * with nested sequences.
         *
         * @param data The test data required to generate the expected {@ocde List} of {@code Token}s.
         * @return The expected {@ocde List} of {@code Token}s.
         */
        public List<Token> expectedWithNesting(TestData data){
            List<Token> tokens = new ArrayList<Token>();
            tokens.add(new TerminalToken(data.processType));
            tokens.add(new UpperCaseIdentifierToken(data.identifier));
            tokens.add(new TerminalToken(TerminalSymbol.ASSIGN));
            for(String action : data.actions){
                tokens.add(new TerminalToken(TerminalSymbol.OPEN_PAREN));
                tokens.add(new LowerCaseIdentifierToken(action));
                tokens.add(new TerminalToken(TerminalSymbol.SEQUENCE));
            }
            tokens.add(new TerminalToken(data.terminator));
            for(int i = 0; i < data.actions.length; i++){
                tokens.add(new TerminalToken(TerminalSymbol.CLOSE_PAREN));
            }
            tokens.add(new TerminalToken(TerminalSymbol.DOT));

            return tokens;
        }

    }

    @Nested
    @DisplayName("reference tests")
    public class LexerReferenceTests extends ReferenceTests<List<Token>> {

        public LexerReferenceTests(){
            super(TestProcessor.LEXER);
        }

        @Override
        public List<Token> sequentialExpectedWithoutParentheses(SequentialReferenceTests.TestData data){
            List<Token> tokens = new ArrayList<Token>();
            for(int i = data.actions.length - 1; i >= 0; i--){
                tokens.add(new TerminalToken(data.processType));
                tokens.add(new UpperCaseIdentifierToken(data.actions[i].toUpperCase()));
                tokens.add(new TerminalToken(TerminalSymbol.ASSIGN));
                tokens.add(new LowerCaseIdentifierToken(data.actions[i]));
                tokens.add(new TerminalToken(TerminalSymbol.SEQUENCE));
                if(i == data.actions.length - 1){
                    tokens.add(new TerminalToken(data.terminator));
                }
                else{
                    tokens.add(new UpperCaseIdentifierToken(data.actions[i + 1].toUpperCase()));
                }
                tokens.add(new TerminalToken(TerminalSymbol.DOT));
            }

            return tokens;
        }

        @Override
        public List<Token> sequentialExpectedWithParentheses(SequentialReferenceTests.TestData data){
            List<Token> tokens = new ArrayList<Token>();
            for(int i = data.actions.length - 1; i >= 0; i--){
                tokens.add(new TerminalToken(data.processType));
                tokens.add(new UpperCaseIdentifierToken(data.actions[i].toUpperCase()));
                tokens.add(new TerminalToken(TerminalSymbol.ASSIGN));
                tokens.add(new TerminalToken(TerminalSymbol.OPEN_PAREN));
                tokens.add(new LowerCaseIdentifierToken(data.actions[i]));
                tokens.add(new TerminalToken(TerminalSymbol.SEQUENCE));
                if(i == data.actions.length - 1){
                    tokens.add(new TerminalToken(data.terminator));
                }
                else{
                    tokens.add(new UpperCaseIdentifierToken(data.actions[i + 1].toUpperCase()));
                }
                tokens.add(new TerminalToken(TerminalSymbol.CLOSE_PAREN));
                tokens.add(new TerminalToken(TerminalSymbol.DOT));
            }

            return tokens;
        }

        @Override
        public List<Token> selfReferenceExpectedWithoutParentheses(SelfReferenceTests.TestData data){
            List<Token> tokens = new ArrayList<Token>();
            tokens.add(new TerminalToken(data.processType));
            tokens.add(new UpperCaseIdentifierToken(data.identifier));
            tokens.add(new TerminalToken(TerminalSymbol.ASSIGN));
            for(String action : data.actions){
                tokens.add(new LowerCaseIdentifierToken(action));
                tokens.add(new TerminalToken(TerminalSymbol.SEQUENCE));
            }
            tokens.add(new UpperCaseIdentifierToken(data.identifier));
            tokens.add(new TerminalToken(TerminalSymbol.DOT));

            return tokens;
        }

        @Override
        public List<Token> selfReferenceExpectedWithParentheses(SelfReferenceTests.TestData data){
            List<Token> tokens = new ArrayList<Token>();
            tokens.add(new TerminalToken(data.processType));
            tokens.add(new UpperCaseIdentifierToken(data.identifier));
            tokens.add(new TerminalToken(TerminalSymbol.ASSIGN));
            tokens.add(new TerminalToken(TerminalSymbol.OPEN_PAREN));
            for(String action : data.actions){
                tokens.add(new LowerCaseIdentifierToken(action));
                tokens.add(new TerminalToken(TerminalSymbol.SEQUENCE));
            }
            tokens.add(new UpperCaseIdentifierToken(data.identifier));
            tokens.add(new TerminalToken(TerminalSymbol.CLOSE_PAREN));
            tokens.add(new TerminalToken(TerminalSymbol.DOT));

            return tokens;
        }

        @Override
        public List<Token> selfReferenceExpectedWithNesting(SelfReferenceTests.TestData data){
            List<Token> tokens = new ArrayList<Token>();
            tokens.add(new TerminalToken(data.processType));
            tokens.add(new UpperCaseIdentifierToken(data.identifier));
            tokens.add(new TerminalToken(TerminalSymbol.ASSIGN));
            for(String action : data.actions){
                tokens.add(new TerminalToken(TerminalSymbol.OPEN_PAREN));
                tokens.add(new LowerCaseIdentifierToken(action));
                tokens.add(new TerminalToken(TerminalSymbol.SEQUENCE));
            }
            tokens.add(new UpperCaseIdentifierToken(data.identifier));
            for(int i = 0; i < data.actions.length; i++){
                tokens.add(new TerminalToken(TerminalSymbol.CLOSE_PAREN));
            }
            tokens.add(new TerminalToken(TerminalSymbol.DOT));

            return tokens;
        }
    }

    @Nested
    @DisplayName("example tests")
    public class LexerExampleTests extends ExampleTests<List<Token>> {

        public LexerExampleTests(){
            super(TestProcessor.LEXER);
        }

        @Override
        public List<Token> expectedTeaExample(){
            return Arrays.asList(
                    new TerminalToken(ProcessType.AUTOMATA),
                    new UpperCaseIdentifierToken("Tea"),
                    new TerminalToken(TerminalSymbol.ASSIGN),
                    new TerminalToken(TerminalSymbol.OPEN_PAREN),
                    new LowerCaseIdentifierToken("takeTea"),
                    new TerminalToken(TerminalSymbol.SEQUENCE),
                    new TerminalToken(TerminatorType.STOP),
                    new TerminalToken(TerminalSymbol.CLOSE_PAREN),
                    new TerminalToken(TerminalSymbol.DOT)
            );
        }

        @Override
        public List<Token> expectedTeaTwoExample(){
            return Arrays.asList(
                    new TerminalToken(ProcessType.AUTOMATA),
                    new UpperCaseIdentifierToken("TeaTwo"),
                    new TerminalToken(TerminalSymbol.ASSIGN),
                    new TerminalToken(TerminalSymbol.OPEN_PAREN),
                    new LowerCaseIdentifierToken("teaButton"),
                    new TerminalToken(TerminalSymbol.SEQUENCE),
                    new LowerCaseIdentifierToken("takeTea"),
                    new TerminalToken(TerminalSymbol.SEQUENCE),
                    new TerminalToken(TerminatorType.STOP),
                    new TerminalToken(TerminalSymbol.CLOSE_PAREN),
                    new TerminalToken(TerminalSymbol.DOT)
            );
        }

        @Override
        public List<Token> expectedTeaThreeExample(){
            return Arrays.asList(
                    new TerminalToken(ProcessType.AUTOMATA),
                    new UpperCaseIdentifierToken("TeaThree"),
                    new TerminalToken(TerminalSymbol.ASSIGN),
                    new TerminalToken(TerminalSymbol.OPEN_PAREN),
                    new LowerCaseIdentifierToken("takeTea"),
                    new TerminalToken(TerminalSymbol.SEQUENCE),
                    new UpperCaseIdentifierToken("TeaThree"),
                    new TerminalToken(TerminalSymbol.CLOSE_PAREN),
                    new TerminalToken(TerminalSymbol.DOT)
            );
        }

    }

}