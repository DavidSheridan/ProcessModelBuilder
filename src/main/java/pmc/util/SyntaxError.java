package pmc.util;

public class SyntaxError extends RuntimeException {

    // field
    private Position position;

    public SyntaxError(String message, Position position){
        super(message);
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }

}