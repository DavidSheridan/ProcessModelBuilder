package pmc.util;

/**
 * The {@code Position} class represents a position in an input stream, spanning from
 * the line and column where the position starts and the line and column where the
 * position ends.
 */
public class Position {

    // fields
    public final int LINE_START;
    public final int COLUMN_START;
    public final int LINE_END;
    public final int COLUMN_END;

    /**
     * Constructs a new instance of a {@code Position} object with the specified start and end
     * points of the position.
     *
     * @param lineStart The line where the position starts.
     * @param columnStart The column where the position starts.
     * @param lineEnd The line where the position ends.
     * @param columnEnd The column where position ends.
     */
    public Position(int lineStart, int columnStart, int lineEnd, int columnEnd){
        LINE_START = lineStart;
        COLUMN_START = columnStart;
        LINE_END = lineEnd;
        COLUMN_END = columnEnd;
    }

}