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
import test.generator.TerminatorTests;
import test.processor.TestProcessor;

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

}