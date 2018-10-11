package pmc.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pmc.lang.ProcessType;
import pmc.lang.Terminal;
import pmc.lang.TerminalSymbol;
import pmc.lang.TerminatorType;

import java.util.Arrays;

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

}