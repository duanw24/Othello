package othello;

import othello.guiGame.Board;

import java.awt.Color;
import java.awt.Point;
import java.lang.Math;
import java.util.ArrayList;

public class RandomPlayer extends Player{
  public int movess = 0;
  
  public RandomPlayer(Color c, String name){
    super(c, name);
  }
  
  public Point getMove(Board theBoard){
    ArrayList<Point> moves = new ArrayList<Point>();
    
    for(int r = 0; r < theBoard.getRows(); r++){
      for(int c = 0; c < theBoard.getColumns(); c++){
        moves.add(new Point(r,c));
      }
    }
    
    for(int i = moves.size()-1; i > -1; i--){
      if(!theBoard.isLegal((int)(moves.get(i).getX()),(int)(moves.get(i).getY()),getColor())){
        moves.remove(i);
      }
    }

    /*
    movess = 0;
    for(int i=0;i<theBoard.getRows();i++) {
      for(int j=0;j<theBoard.getColumns();j++) {
        if (theBoard.getState(i, j)!=null) {
          movess++;
        }
      }
    }
    System.out.println(movess + " tokens on board detected");
    */
    return selectMove(moves, theBoard);
  }
  
  public Point selectMove(ArrayList<Point> moves, Board theBoard){
    int r = (int)(Math.random()*moves.size());
    return moves.get(r);
  }
}