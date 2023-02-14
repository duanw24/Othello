package othello.guiGame;

import lombok.Getter;
import lombok.Setter;
import othello.ai.minTest;

import java.awt.*;
import java.util.ArrayList;

public class minimaxPlayer extends Player {

    public minimaxPlayer(Color c, String name){
        super(c, name);
    }

    public Point getMove(Board theBoard){
        long t1 = System.currentTimeMillis();
        ArrayList<Point> moves = new ArrayList<>();

        for(int i = 0; i < theBoard.getRows(); i++){
            for(int j = 0; j < theBoard.getColumns(); j++){
                if(theBoard.isLegal(i,j,getColor())) {
                    moves.add(new Point(i,j));
                }
            }
        }

        Point bestmove = moves.get((int)(Math.random()* moves.size()));
        double temp = Integer.MIN_VALUE;
        for(Point p : moves) {
            Board b = theBoard.getCopy();
            b.placePiece((int)p.getX(),(int)p.getY(),this.getColor());
            double val = minTest.minimaxValue(b, 0,false,Integer.MIN_VALUE,Integer.MAX_VALUE);
            if(val > temp) {
                temp=val;
                bestmove = p;
            }
            //System.out.println(temp + " is best move");
        }
        //System.out.println(nodes + " nodes considered");
        System.out.println(System.currentTimeMillis()-t1 + " ms to find best move, "+minTest.nodes+" nodes considered");
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