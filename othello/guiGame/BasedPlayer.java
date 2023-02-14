package othello.guiGame;

import othello.ai.testClass;

import java.awt.*;
import java.util.ArrayList;

public class BasedPlayer extends Player {
  public BasedPlayer(Color c, String name){
    super(c, name);
  }

  public Point getMove(Board theBoard){
    ArrayList<Point> moves = getMoves(theBoard);

    int highest = Integer.MIN_VALUE;
    Point bestmove = moves.get((int)(Math.random()*moves.size()));
    for(Point p : moves) {
      Board copy = theBoard.getCopy();
      copy.placePiece((int)p.getX(),(int)p.getY(),getColor());
      int recent = new testClass().pointscore(copy);
      if (recent > highest){
        bestmove=p;
        highest=recent;
        //System.out.println("Bestmove score: " + highest);
      }
    }

    return bestmove;
    //return selectMove(moves, theBoard);
  }

  public ArrayList<Point> getMoves(Board b){
    ArrayList<Point> moves = new ArrayList<Point>();

    for(int r = 0; r < b.getRows(); r++){
      for(int c = 0; c < b.getColumns(); c++){
        moves.add(new Point(r,c));
      }
    }

    for(int i = moves.size()-1; i > -1; i--){
      if(!b.isLegal((int)(moves.get(i).getX()),(int)(moves.get(i).getY()),getColor())){
        moves.remove(i);
      }
    }
    return moves;
  }

  public Point selectMove(ArrayList<Point> moves, Board theBoard){
    int r = (int)(Math.random()*moves.size());
    return moves.get(r);

  }
}