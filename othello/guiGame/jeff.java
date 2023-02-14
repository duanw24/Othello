package othello.guiGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class jeff extends Player {

    private int [ ][ ] positionValue = {

            { 100, -20, 10, 5, 5, 10, -20, 100 },
            { -20, -100, -2, -2, -2, -2, -100, -20 },
            { 10, -2, -1, -1, -1, -1, -2, 5 },
            { 5, -2, -1, -1, -1, -1, -2, 5 },
            { 5, -2, -1, -1, -1, -1, -2, 5 },
            { 10, -2, -1, -1, -1, -1, -2, 5 },
            { -20, -100, -2, -2, -2, -2, -100, -20 },
            { 100, -20, 10, 5, 5, 10, -20, 100 }
    };
    private ArrayList<Point> previousMoves = new ArrayList<Point>();

    public jeff(Color c, String name){
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

        return selectMove(moves, theBoard);
    }

    public boolean check(Point mogus, ArrayList<Point> moves)
    {
        for(int i = 0; i < moves.size(); i++) {

            if(mogus.equals(moves.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void changeValue() {

        if(check(new Point(0,0), previousMoves)) {
            positionValue[0][1] = 110;
            positionValue[1][0] = 110;
            positionValue[0][2] = 20;
            positionValue[2][0] = 20;
            positionValue[1][1] = 20;

        }
        if(check(new Point(7,0), previousMoves)) {
            positionValue[7][1] = 110;
            positionValue[6][0] = 110;
            positionValue[5][0] = 20;
            positionValue[7][2] = 20;
            positionValue[6][1] = 20;
        }
        if(check(new Point(0,7), previousMoves)) {
            positionValue[0][6] = 110;
            positionValue[1][7] = 110;
            positionValue[0][5] = 20;
            positionValue[2][7] = 20;
            positionValue[1][6] = 20;
        }
        if(check(new Point(7,7), previousMoves)) {
            positionValue[6][7] = 110;
            positionValue[7][6] = 110;
            positionValue[7][5] = 20;
            positionValue[5][7] = 20;
            positionValue[6][6] = 20;
        }
    }

    public Point selectMove(ArrayList<Point> moves, Board theBoard){
        Point coord;
        changeValue();
        Collections.shuffle(moves);
        //credit:https://www.delftstack.com/howto/java/shuffle-an-array-in-java/#:~:text=Use%20the%20random()%20Method%20to%20Shuffle%20an%20Array%20in%20Java,-We%20can%20use&text=This%20method%20aims%20to%20start,the%20indexes%20of%20an%20array.
        for(int i = 0; i < moves.size(); i++) {
            coord = moves.get(i);
            if(positionValue[(int)coord.getX()][(int)coord.getY()]==110) {
                previousMoves.add(moves.get(i));
                return moves.get(i);
            }

        }
        for(int i = 0; i < moves.size(); i++) {
            coord = moves.get(i);
            if(positionValue[(int)coord.getX()][(int)coord.getY()]==100) {
                previousMoves.add(moves.get(i));
                return moves.get(i);
            }

        }
        for(int i = 0; i < moves.size(); i++) {
            coord = moves.get(i);
            if(positionValue[(int)coord.getX()][(int)coord.getY()]==20) {
                previousMoves.add(moves.get(i));
                return moves.get(i);
            }

        }

        //if edge is availible
        for(int i = 0; i < moves.size(); i++) {
            coord = moves.get(i);

            if(positionValue[(int)coord.getX()][(int)coord.getY()]==10) {
                previousMoves.add(moves.get(i));
                return moves.get(i);
            }
        }

        //if slightly less good edge is avalible
        for(int i = 0; i < moves.size(); i++) {
            coord = moves.get(i);

            if(positionValue[(int)coord.getX()][(int)coord.getY()]==5) {
                previousMoves.add(moves.get(i));
                return moves.get(i);
            }
        }


        //if mid
        for(int i = 0; i < moves.size(); i++) {
            coord = moves.get(i);

            if(positionValue[(int)coord.getX()][(int)coord.getY()]==-1) {
                previousMoves.add(moves.get(i));
                return moves.get(i);
            }
        }

        //if outside edge
        for(int i = 0; i < moves.size(); i++) {
            coord = moves.get(i);
            if(positionValue[(int)coord.getX()][(int)coord.getY()]==-2) {
                previousMoves.add(moves.get(i));
                return moves.get(i);
            }
        }

        //if 2nd worst spot
        for(int i = 0; i < moves.size(); i++) {
            coord = moves.get(i);
            if(positionValue[(int)coord.getX()][(int)coord.getY()]==-20) {
                previousMoves.add(moves.get(i));
                return moves.get(i);
            }
        }
        //if the worst spot
        for(int i = 0; i < moves.size(); i++) {
            coord = moves.get(i);
            if(positionValue[(int)coord.getX()][(int)coord.getY()]==-100) {
                return moves.get(i);
            }
        }

        //required
        return moves.get(0);
    }
}