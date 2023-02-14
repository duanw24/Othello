package othello.guiGame;

import java.awt.Color;

public class Board{
    private Color[][] theBoard;

    public Board(){
		this(8,8);
    }

    public Board(int rows, int columns){
		theBoard = new Color[rows][columns];
		for(int i=0; i<rows; i++)
			for(int j=0; j<columns; j++)
				theBoard[i][j] = null; //All dark = blank board
    }
	
	public Board(Color[][] board){
		theBoard = board;
	}
	
    //PreCondition:  (row, col) is legal
    //PostCondition:  Returns true if flip occurred, false otherwise
    public boolean placePiece(int row, int col, Color theColor){
		boolean flipped = false;
		if(!isOnBoard(row,col) || theBoard[row][col] != null){
			return flipped;
		}
		theBoard[row][col] = theColor;
		
		//Check for flips going down
		int r = row+1;
		while(isOnBoard(r,col) && theBoard[r][col]!=null && !theBoard[r][col].equals(theColor) ){
			r++;
		}
		if(isOnBoard(r,col) && theBoard[r][col]!=null){
			for(int cR = row; cR <= r; cR++){
				if(theBoard[cR][col] !=theColor){
					theBoard[cR][col] = theColor;
					flipped = true;
				}
			}
			
		}
		
		//Check for flips going up
		r = row-1;
		while(isOnBoard(r,col) && theBoard[r][col]!=null && !theBoard[r][col].equals(theColor) ){
			r--;
		}
		if(isOnBoard(r,col) && theBoard[r][col]!=null){
			for(int cR = r; cR <= row; cR++){
				if(theBoard[cR][col] != theColor){
					theBoard[cR][col] = theColor;
					flipped = true;
				}
			}
		}
		
		//Check for flips going right
		int c = col +1;
		while(isOnBoard(row,c) && theBoard[row][c]!=null && !theBoard[row][c].equals(theColor) ){
			c++;
		}
		if(isOnBoard(row,c) && theBoard[row][c]!=null){
			for(int cC = col; cC <= c; cC++){
				if(theBoard[row][cC] != theColor){
					theBoard[row][cC] = theColor;
					flipped = true;
				}
			}
		}
		
		//Check for flips going left
		c = col - 1;
		while(isOnBoard(row,c) && theBoard[row][c]!=null && !theBoard[row][c].equals(theColor) ){
			c--;
		}
		if(isOnBoard(row,c) && theBoard[row][c]!=null){
			for(int cC = c; cC <= col; cC++){
				if(theBoard[row][cC] != theColor){
					theBoard[row][cC] = theColor;
					flipped = true;
				}
			}
		}
		
		//Check for flips down and right (Diag)
		r = row+1;
		c = col+1;
		while(isOnBoard(r,c) && theBoard[r][c]!=null && !theBoard[r][c].equals(theColor) ){
			r++;
			c++;
		}
		if(isOnBoard(r,c) && theBoard[r][c]!=null){
			for(int cR = row, cC = col; cR <= r && cC <= c; cR++, cC++){
				if(theBoard[cR][cC] != theColor){
					theBoard[cR][cC] = theColor;
					flipped = true;
				}
			}
		}
		
		//Check for flips up and right (Diag)
		r = row-1;
		c = col+1;
		while(isOnBoard(r,c) && theBoard[r][c]!=null && !theBoard[r][c].equals(theColor) ){
			r--;
			c++;
		}
		if(isOnBoard(r,c) && theBoard[r][c]!=null){
			for(int cR = row, cC = col; cR >= r && cC <= c; cR--, cC++){
				if(theBoard[cR][cC] != theColor){
					theBoard[cR][cC] = theColor;
					flipped = true;
				}
			}
		}
		
		//Check for flips down and left (Diag)
		r = row+1;
		c = col-1;
		while(isOnBoard(r,c) && theBoard[r][c]!=null && !theBoard[r][c].equals(theColor) ){
			r++;
			c--;
		}
		if(isOnBoard(r,c) && theBoard[r][c]!=null){
			for(int cR = row, cC = col; cR <= r && cC >= c; cR++, cC--){
				if(theBoard[cR][cC] != theColor){
					theBoard[cR][cC] = theColor;
					flipped = true;
				}
			}
		}
		
		//Check for flips up and left (Diag)
		r = row-1;
		c = col-1;
		while(isOnBoard(r,c) && theBoard[r][c]!=null && !theBoard[r][c].equals(theColor) ){
			r--;
			c--;
		}
		if(isOnBoard(r,c) && theBoard[r][c]!=null){
			for(int cR = row, cC = col; cR >= r && cC >= c; cR--, cC--){
				if(theBoard[cR][cC] != theColor){
					theBoard[cR][cC] = theColor;
					flipped = true;
				}
			}
		}
		return flipped;
    }

	public boolean isDone(){
		for(int i=0; i<theBoard.length; i++)
			for(int j=0; j<theBoard[i].length; j++)
				if(theBoard[i][j] == null)
					return false;
		return true;
	}
	
	public int colorCount(Color theColor){
		int sum = 0;
		for(int i=0; i<theBoard.length; i++)
			for(int j=0; j<theBoard[i].length; j++)
				if(theBoard[i][j] != null && theBoard[i][j].equals(theColor) )
					sum++;
		return sum;
	}
	
    public int getRows(){
		return theBoard.length;
    }

    public int getColumns(){
		return theBoard[0].length;
    }

    public Color getState(int r, int c){
		return theBoard[r][c];
    }

    public void resetPuzzle(){
		for(int i=0; i<theBoard.length; i++)
			for(int j=0; j<theBoard[0].length; j++)
				theBoard[i][j] = null;
    }
	
	public boolean isOnBoard(int r, int c){
		return (r>=0 && r<theBoard.length && c>=0 && c<theBoard[0].length);
		
	}

	public boolean isLegal(int r, int c, Color clr){
		if(!isOnBoard(r,c)) return false;
		if(theBoard[r][c]!=null) return false;
		Board copy = getCopy();
		return copy.placePiece(r,c,clr);
		
	}

	public boolean hasMove(Color clr){
		for(int r=0; r<theBoard.length; r++)
			for(int c=0; c<theBoard[0].length; c++)
				if(isLegal(r,c,clr))
					return true;
		return false;
	}
	
	public Board getCopy(){
		Color[][] board_copy = new Color[theBoard.length][theBoard[0].length];
		for(int i=0; i<theBoard.length; i++)
			for(int j=0; j<theBoard.length; j++)
				board_copy[i][j] = theBoard[i][j];
		return new Board(board_copy);
	}
	
	public boolean equals(Object b){
		Board in = (Board) b;
		if(in.getRows() != this.getRows() || in.getColumns() != this.getColumns()) return false;
		for(int i=0; i < getRows(); i++){
			for(int j=0; j < getColumns(); j++){
				if(in.getState(i,j)==null && this.getState(i,j)!=null) return false;
				if(in.getState(i,j)!=null && this.getState(i,j)==null) return false;
				if(in.getState(i,j)!=null && this.getState(i,j)!=null){
					if(!in.getState(i,j).equals(this.getState(i,j))) return false;
				}
			}
		}
		return true;	
	}

}