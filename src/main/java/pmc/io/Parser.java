package pmc.io;

import pmc.io.Lexer.*;
import pmc.lang.AST;
import pmc.lang.definition.BlockDefinition;
import pmc.lang.definition.Definition;
import pmc.lang.definition.ProcessDefinition;
import pmc.lang.process.Process;
import pmc.lang.process.Terminator;
import pmc.lang.terminal.ProcessType;
import pmc.lang.terminal.Terminal;
import pmc.lang.terminal.TerminalSymbol;
import pmc.lang.terminal.TerminatorType;
import pmc.util.SyntaxError;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    // fields
    private List<Token> tokens;
    private int index;

    public Parser(List<Token> tokens){
        this.tokens = new ArrayList<Token>(tokens);
        this.index = 0;
    }

    public AST scan(){
        List<Definition> definitions = new ArrayList<Definition>();

        while(index < tokens.size()){
            definitions.add(parseProcessDefinition());
        }

        return new AST(new BlockDefinition(definitions));
    }

    private ProcessDefinition parseProcessDefinition(){
        ProcessType processType = (ProcessType)match(ProcessType.values());
        String identifier = parseIdentifier();
        match(TerminalSymbol.ASSIGN);
        Process process = parseProcess();
        match(TerminalSymbol.DOT);

        return new ProcessDefinition(processType, identifier, process);
    }

    private String parseIdentifier(){
        Token token = next();
        if(token instanceof UpperCaseIdentifierToken){
            return ((UpperCaseIdentifierToken)token).getValue();
        }

        throw new SyntaxError("expecting to parse an identifier but received: " + token, token.getPosition());
    }

    private Process parseProcess(){
        if(hasNext(TerminatorType.values())){
            return parseTerminator();
        }
        else if(hasNext(TerminalSymbol.OPEN_PAREN)){
            match(TerminalSymbol.OPEN_PAREN);
            Process process = parseProcess();
            match(TerminalSymbol.CLOSE_PAREN);

            return process;
        }

        throw new SyntaxError("expecting to parse a process but received: " + peek(), peek().getPosition());
    }

    private Terminator parseTerminator(){
        if(hasNext(TerminatorType.values())){
            return new Terminator((TerminatorType)match(TerminatorType.values()));
        }

        throw new SyntaxError("expecting to parse a terminator but received: " + peek(), peek().getPosition());
    }

    private Terminal match(Terminal...values){
        Token token = next();

        if(!(token instanceof TerminalToken)){
            throw new SyntaxError("expecting to parse a terminal but received: " + token, token.getPosition());
        }

        Terminal terminal = ((TerminalToken)token).getValue();

        for(Terminal value : values){
            if(terminal == value){
                return value;
            }
        }

        throw new SyntaxError("received invalid terminal: " + terminal, token.getPosition());
    }

    private Token peek(){
        if(index < tokens.size()){
            return tokens.get(index);
        }
        else{
            throw new SyntaxError("end of file", tokens.get(tokens.size() - 1).getPosition());
        }
    }

    private Token next(){
        Token token = peek();
        index++;

        return token;
    }

    private boolean hasNext(Terminal...values){
        Token token = peek();

        if(!(token instanceof TerminalToken)){
            return false;
        }

        Terminal terminal = ((TerminalToken)token).getValue();

        for(Terminal value : values){
            if(terminal == value){
                return true;
            }
        }

        return false;
    }

}