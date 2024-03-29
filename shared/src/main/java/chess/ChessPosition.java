package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private final int row;
    private final int col;
    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {return row;}

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {return col;}

    @Override
    public String toString() {
        return "Pos{" + row + ", " + col + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that = (ChessPosition) o;
        return row == that.row && col == that.col;
    }

    /**
     * @return a new chess position that the current one shifted by the arguments row,
     */
    public ChessPosition newRelativeChessPosition(int row, int col){
        return new ChessPosition(this.getRow() + row, this.getColumn() + col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public boolean posOutOfBounds(){
        return (this.getRow() > 8 || this.getColumn() > 8 || this.getRow() < 1 || this.getColumn() < 1);
    }
}
