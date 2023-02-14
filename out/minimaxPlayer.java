package othello;

import lombok.Getter;
import lombok.Setter;
import othello.ai.minTest;
import othello.guiGame.Board;

import java.awt.*;
import java.util.ArrayList;
import static othello.ai.minTest.*;

public class minimaxPlayer extends Player {
    @Getter@Setter
    public static int tokencount=0;

    public minimaxPlayer(Color c, String name){
        super(c, name);
    }

    public Point getMove(Board theBoard){
        ArrayList<Point> moves = new ArrayList<Point>();

        for(int i = 0; i < theBoard.getRows(); i++){
            for(int j = 0; j < theBoard.getColumns(); j++){
                if(theBoard.isLegal(i,j,getColor())) {
                    moves.add(new Point(i,j));
                }
            }
        }

        tokencount = 0;
        for(int i=0;i<theBoard.getRows();i++) {
            for(int j=0;j<theBoard.getColumns();j++) {
                if (theBoard.getState(i, j)!=null) {
                    tokencount++;
                }
            }
        }

        Point bestmove = moves.get((int)(Math.random()* moves.size()));
        int temp = Integer.MIN_VALUE;
        for(Point p : moves) {
            nodes = 0;
            Board b = theBoard.getCopy();
            b.placePiece((int)p.getX(),(int)p.getY(),this.getColor());
            int val = new minTest().minimaxValue(b, 0,false,Integer.MIN_VALUE,Integer.MAX_VALUE);
            if(val > temp) {
                temp=val;
                bestmove = p;
            }
            System.out.println(temp + " is best move");
        }
        //System.out.println(nodes + " nodes considered");
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