package headles;

import java.awt.*;

public class Board {
    private int rows=8;
    private int columns=8;
    private int[][] theBoard = new int[rows][columns];
    public Board() {
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                theBoard[i][j] = 0;
            }
        }
        theBoard[3][3] = 1;
        theBoard[4][4] = 1;
        theBoard[3][4] = 2;
        theBoard[4][3] = 2;
    }
    public Board copy() {
        Board newBoard = new Board();
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                newBoard.theBoard[i][j] = theBoard[i][j];
            }
        }
        return newBoard;
    }
    public int[][] getBoard() {
        return theBoard;
    }
    public int getBoard(int row, int column) {
        return theBoard[row][column];
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
    public boolean placePiece(int r, int c, int clr) {
        boolean flipped = false;
        if (r<0 || r>=rows || c<0 || c>=columns) return flipped;
        if (theBoard[r][c]!=0) return flipped;
        theBoard[r][c] = clr;

        //Check for flips going down
        return false;
    }

    public boolean isLegal(int r, int c, int clr){
        if(r<0 || r>=rows || c<0 || c>=columns) return false;
        if(theBoard[r][c]!=0) return false;
        Board copy = copy();
        return copy.placePiece(r,c,clr);

    }
}
