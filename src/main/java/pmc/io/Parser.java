package pmc.io;

import pmc.io.Lexer.*;
import pmc.lang.AST;
import pmc.lang.action.Action;
import pmc.lang.action.ActionElementList;
import pmc.lang.action.element.ActionElement;
import pmc.lang.action.element.StringActionElement;
import pmc.lang.definition.BlockDefinition;
import pmc.lang.definition.Definition;
import pmc.lang.definition.LocalProcessDefinition;
import pmc.lang.definition.ProcessDefinition;
import pmc.lang.process.*;
import pmc.lang.process.Process;
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
        ProcessDefinition.Builder builder = new ProcessDefinition.Builder();

        builder.setProcessType((ProcessType)match(ProcessType.values()));
        builder.setIdentifier(parseIdentifier());
        match(TerminalSymbol.ASSIGN);
        builder.setProcess(parseChoice());

        while(hasNext(TerminalSymbol.COMMA)){
            match(TerminalSymbol.COMMA);
            String identifier = parseIdentifier();
            match(TerminalSymbol.ASSIGN);
            Process process = parseChoice();

            builder.addLocalProcess(new LocalProcessDefinition(identifier, process));
        }

        match(TerminalSymbol.DOT);

        return builder.build();
    }

    private String parseIdentifier(){
        Token token = next();
        if(token instanceof UpperCaseIdentifierToken){
            return ((UpperCaseIdentifierToken)token).getValue();
        }

        throw new SyntaxError("expecting to parse an identifier but received: " + token, token.getPosition());
    }

    private Process parseChoice(){
        Process firstBranch = parseProcess();
        if(hasNext(TerminalSymbol.CHOICE)){
            match(TerminalSymbol.CHOICE);
            Process secondBranch = parseChoice();
            return new Choice(firstBranch, secondBranch);
        }

        return firstBranch;
    }

    private Process parseProcess(){
        if(peek() instanceof LowerCaseIdentifierToken){
            return parseSequence();
        }
        else if(hasNext(TerminatorType.values())){
            return parseTerminator();
        }
        else if(peek() instanceof UpperCaseIdentifierToken){
            return parseReference();
        }
        else if(hasNext(TerminalSymbol.OPEN_PAREN)){
            match(TerminalSymbol.OPEN_PAREN);
            Process process = parseChoice();
            match(TerminalSymbol.CLOSE_PAREN);

            return process;
        }

        throw new SyntaxError("expecting to parse a process but received: " + peek(), peek().getPosition());
    }

    private Sequence parseSequence(){
        Action action = parseAction();
        match(TerminalSymbol.SEQUENCE);
        Process next = parseProcess();


        return new Sequence(action, next);
    }

    private Terminator parseTerminator(){
        if(hasNext(TerminatorType.values())){
            return new Terminator((TerminatorType)match(TerminatorType.values()));
        }

        throw new SyntaxError("expecting to parse a terminator but received: " + peek(), peek().getPosition());
    }

    private Reference parseReference(){
        Token token = next();
        if(!(token instanceof UpperCaseIdentifierToken)){
            throw new SyntaxError("expecting to parse a reference but received: " + token, token.getPosition());
        }

        return new Reference(((UpperCaseIdentifierToken)token).getValue());
    }

    private Action parseAction(){
        List<ActionElement> elements = new ArrayList<ActionElement>();

        while(index < tokens.size()){
            Token token = peek();
            if(token instanceof LowerCaseIdentifierToken){
                elements.add(new StringActionElement(((LowerCaseIdentifierToken)token).getValue()));
            }
            else{
                break;
            }

            index++;
        }

        return new ActionElementList(elements);
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