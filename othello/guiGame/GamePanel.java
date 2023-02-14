package othello.guiGame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements MouseListener{
    public static final int SQUARE_SIZE = 65;
    public static final int PIECE_OFFSET = 5;

    private Board theBoard;
    private int current_x, current_y;
    private int dot_r, dot_c;

	
    public GamePanel(int r, int c){
      	this(new Board(r,c));
	}
	
	public GamePanel(Board b){
		theBoard = b;
		addMouseListener(this);
		current_x = current_y = -1;
		dot_r = dot_c = -1;
	}

    public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i=0; i<theBoard.getColumns(); i++)  //Draw Boxes
			for(int j=0; j<theBoard.getRows(); j++){
				g.setColor(Color.GRAY);
				g.fillRect(i*SQUARE_SIZE, j*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
				
				//Draw piece
				if(theBoard.getState(j,i) != null){
					g.setColor(theBoard.getState(j,i));
					g.fillOval(i*SQUARE_SIZE+PIECE_OFFSET, j*SQUARE_SIZE+PIECE_OFFSET, 
							   SQUARE_SIZE-2*PIECE_OFFSET, SQUARE_SIZE-2*PIECE_OFFSET);
				}
			}
		
		g.setColor(Color.BLACK);
		for(int i=0; i< theBoard.getColumns()+1; i++) //Draw vertical lines
			g.drawLine(i*SQUARE_SIZE, 0, i*SQUARE_SIZE, theBoard.getRows()*SQUARE_SIZE);
		for(int j=0; j< theBoard.getRows()+1; j++) //Draw horizontal lines
			g.drawLine(0, j*SQUARE_SIZE, theBoard.getColumns()*SQUARE_SIZE, j*SQUARE_SIZE);

		if(dot_r!=-1 && dot_c!=-1){
			g.setColor(Color.RED);
			g.drawOval(dot_c*SQUARE_SIZE+PIECE_OFFSET, dot_r*SQUARE_SIZE+PIECE_OFFSET, 
							   SQUARE_SIZE-2*PIECE_OFFSET, SQUARE_SIZE-2*PIECE_OFFSET);
		}

	}

    public void setGame(Board b){
		theBoard = b;
		repaint();
    }
	
	public boolean placePiece(int row, int col, Color theColor){
		if(!theBoard.isLegal(row,col,theColor)){
			return false;
		}
		if(theBoard.getState(row,col) != null){
			return false;
		}
		theBoard.placePiece(row, col, theColor);
		repaint();
		return true;
	}

	//CODE BELOW IS ONLY FOR DISPLAYING A COLORED DOT - SEE REVERSI CLASS FOR "SHOW_MODE"
	public void drawPoint(int row, int col){
		if(!theBoard.isOnBoard(row,col)) return;
		dot_r = row;
		dot_c = col;		
		repaint();
	}


	//CODE BELOW THIS POINT IS ONLY PERTINENT FOR A MOUSE PLAYER, NOT FOR A othello.guiGame.GamePanel ITSELF
	public void mouseClicked(MouseEvent e){
		current_x = e.getY()/SQUARE_SIZE;
		current_y = e.getX()/SQUARE_SIZE;
		System.out.println(current_x+", "+current_y);
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void resetCurrent(){
		current_x = -1;
		current_y = -1;
	}
	public int getCurrentX(){
		return current_x;
	}
	public int getCurrentY(){
		return current_y;
	}

}