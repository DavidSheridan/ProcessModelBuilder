package pmc.io;

import pmc.io.Lexer.*;
import pmc.lang.AST;

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
        return null;
    }

}