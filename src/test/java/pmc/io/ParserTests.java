package pmc.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import pmc.lang.AST;
import pmc.lang.definition.BlockDefinition;
import pmc.lang.definition.ProcessDefinition;
import pmc.lang.process.Terminator;
import test.generator.TerminatorTests;
import test.processor.TestProcessor;

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
        public AST expectedWithParentheses(TestData data){
            return expectedWithoutParentheses(data);
        }

    }
}
