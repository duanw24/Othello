package othello.ai;
import othello.guiGame.Board;

import java.awt.*;

public class testClass {
    private int tokens = 0;
    public testClass() {

    }

  /*public static int getScore(Board board, boolean max, int depth,boolean black) {
    //spot value = 1
    //c value = -25
    //x value = -50
    //edge value = 5
    //corner value = 50
    Board copy = board.getCopy();
    blac+=getCorners(copy,true);
    blac+=getEdges(copy,true);
    System.out.println(blac + " points for black");
    return blac;
  }

   */

    public int pointscore(Board board) {
        int temp=0;
        int tokencount=0;
        //spot value = 1
        //c value = -25
        //x value = -50
        //edge value = 5
        //corner value = 50

        int[][] value = {
                {10000,-100,10,5,5,10,-100,10000},
                {-100,-100,-2,-2,-2,-2,-100,-100},
                {10,-2,-1,-1,-1,-1,-2,10},
                {5,-2,-1,-1,-1,-1,-2,5},
                {5,-2,-1,-1,-1,-1,-2,5},
                {10,-2,-1,-1,-1,-1,-2,10},
                {-100,-100,-2,-2,-2,-2,-100,-100},
                {10000,-100,10,5,5,10,-100,10000}
        };

        tokencount = 0;
        for(int i=0;i<board.getRows();i++) {
            for(int j=0;j<board.getColumns();j++) {
                if (board.getState(i, j)!=null) {
                    tokencount++;
                }
            }
        }

        if(tokencount < 58) {
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (board.getState(i, j) == Color.BLACK) {
                        temp += value[i][j];
                        System.out.println(value[i][j] + " value calculated for move" + i + ", " + j);
                    }
                }
            }
        } else {
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (board.getState(i, j) == Color.BLACK) {
                        temp += 100;
                    }
                }
            }
        }

        return temp;
    }

}