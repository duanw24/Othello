package othello.ai;
import othello.guiGame.Board;
import othello.guiGame.minimaxPlayer;

import java.awt.*;
import java.util.ArrayList;

public class minTest {
    public minTest(){}

    public static int nodes = 0;


    public static double minimaxValue(Board board,int depth,boolean isMax, int alpha, int beta) {
        //eval leaf nodes
        nodes++;
        if ((depth == 4) || board.isDone()) {
            return heuristic(board);
        }

        ArrayList<Point> moves = new ArrayList<>();

        if (isMax) {
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (board.isLegal(i, j, Color.BLACK)) {
                        moves.add(new Point(i, j));
                    }
                }
            }

            if(moves.size() == 0) {
                return minimaxValue(board, depth + 1, false, alpha, beta);
            }

            int bestVal = Integer.MIN_VALUE;

            for (Point p : moves) {
                Board copy = board.getCopy();
                copy.placePiece((int) p.getX(), (int) p.getY(), Color.BLACK);
                int value = (int) minimaxValue(copy, depth + 1, false, alpha, beta);
                bestVal = Math.max(bestVal, value);
                alpha = Math.max(alpha, bestVal);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestVal;
        } else {
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (board.isLegal(i, j, Color.WHITE)) {
                        moves.add(new Point(i, j));
                    }
                }
            }

            if(moves.size() == 0) {
                return minimaxValue(board, depth + 1, true, alpha, beta);
            }

            int bestVal = Integer.MAX_VALUE;

            for (Point p : moves) {
                Board copy = board.getCopy();
                copy.placePiece((int)p.getX(), (int)p.getY(), Color.WHITE);
                int value = (int) minimaxValue(copy, depth + 1, true, alpha, beta);
                bestVal = Math.min(bestVal, value);
                beta = Math.min(beta, bestVal);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestVal;
        }
    }

  public static double heuristic(Board board) {
        /*
      int temp=0;
      int tokencount;

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

      if(tokencount < 59) {
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
*/
        int bFront = 0;
        int wFront = 0;
        int bScore=0;
        int white = 0;
        int wMoves = 0;
        int black = 0;
        int bMoves = 0;
        int[] X1 = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] Y1 = {0, 1, 1, 1, 0, -1, -1, -1};

        int[][] weights = {
                {100, -20, 10, 5, 5, 10, -20, 100},
                {-20, -50, -2, -2, -2, -2, -50, -20},
                {10, -2, -1, -1, -1, -1, -2, 10},
                {5, -2, -1, -1, -1, -1, -2, 5},
                {5, -2, -1, -1, -1, -1, -2, 5},
                {10, -2, -1, -1, -1, -1, -2, 10},
                {-20, -50, -2, -2, -2, -2, -50, -20},
                {100, -20, 10, 5, 5, 10, -20, 100}
        };

      for(int i=0;i<board.getRows();i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                if(board.isLegal(i,j,Color.WHITE)) {
                    wMoves++;
                }
                if(board.isLegal(i,j,Color.BLACK)) {
                    bMoves++;
                }
                if (board.getState(i, j) == Color.WHITE) {
                    bScore-=weights[i][j];
                    white++;
                }
                if (board.getState(i, j) == Color.BLACK) {
                    bScore+=weights[i][j];
                    black++;
                }
                if(board.getState(i,j) != null) {
                    for (int k = 0; k < 8; k++) {
                        int x = i + X1[k];
                        int y = j + Y1[k];
                        if (x >= 0 && x < 8 && y >= 0 && y < 8 && board.getState(x, y) == null) {
                            if (board.getState(i, j) == Color.WHITE)
                                wFront++;
                            else bFront++;
                            break;
                        }
                    }
                }
            }
        }

        double parity;
        double front;
        double corner;
        double mobility;
        double closeness;
        if(white>black) {
            parity=100*(double)black/(white+black);}
        else if(black>white){
            parity=-100*(double)white/(white+black);}
        else parity=0;

        if(black>white)
            mobility= 100*((double)bMoves)/(wMoves+bMoves);
        else if(white>black)
            mobility= -100*((double)wMoves)/(wMoves+bMoves);
        else mobility=0;

        if(bFront > wFront)
          front = -(100.0 * bFront)/(bFront + wFront);
        else if(bFront < wFront)
          front = (100.0 * bFront)/(bFront + wFront);
        else front = 0;

        white=0;
        black=0;
      if(board.getState(0,0)==Color.BLACK) black++;
      else if(board.getState(0,0)==Color.WHITE) white++;
      if(board.getState(0,7)==Color.BLACK) black++;
      else if(board.getState(0,7)==Color.WHITE) white++;
      if(board.getState(7,0)==Color.BLACK) black++;
      else if(board.getState(7,0)==Color.WHITE) white++;
      if(board.getState(7,7)==Color.BLACK) black++;
      else if(board.getState(7,7)==Color.WHITE) white++;
      corner = 25 * (black - white);

      white=0;
      black=0;
      if(board.getState(0,0) == null)   {
          if(board.getState(0,1) == Color.BLACK) black++;
          else if(board.getState(0,1) == Color.WHITE) white++;
          if(board.getState(1,1) == Color.BLACK) black++;
          else if(board.getState(1,1) == Color.WHITE) white++;
          if(board.getState(1,0) == Color.BLACK) black++;
          else if(board.getState(1,0) == Color.WHITE) white++;
      }
      if(board.getState(0,7) == null)   {
          if(board.getState(0,6) == Color.BLACK) black++;
          else if(board.getState(0,6) == Color.WHITE) white++;
          if(board.getState(6,6) == Color.BLACK) black++;
          else if(board.getState(6,6) == Color.WHITE) white++;
          if(board.getState(7,1) == Color.BLACK) black++;
          else if(board.getState(7,1) == Color.WHITE) white++;
      }
      if(board.getState(7,0) == null)   {
          if(board.getState(7,1) == Color.BLACK) black++;
          else if(board.getState(7,1) == Color.WHITE) white++;
          if(board.getState(6,1) == Color.BLACK) black++;
          else if(board.getState(6,1) == Color.WHITE) white++;
          if(board.getState(6,0) == Color.BLACK) black++;
          else if(board.getState(6,0) == Color.WHITE) white++;
      }
      if(board.getState(7,7) == null)   {
          if(board.getState(6,6) == Color.BLACK) black++;
          else if(board.getState(6,6) == Color.WHITE) white++;
          if(board.getState(7,6) == Color.BLACK) black++;
          else if(board.getState(7,6) == Color.WHITE) white++;
          if(board.getState(6,7) == Color.BLACK) black++;
          else if(board.getState(6,7) == Color.WHITE) white++;
      }
      closeness = -12.5 * (black-white);
      return (10 * parity) + (801.724 * corner) + (382.026 * closeness) + (78.922 * mobility) + (74.396 * front) + (10 * bScore);

  }


/*
    public int getCorners(Board b){
        int cornerscore = 0;
        ArrayList<Point> corners = new ArrayList<>();
        corners.add(new Point(0,0));
        corners.add(new Point(0,7));
        corners.add(new Point(7,0));
        corners.add(new Point(7,7));
        for(Point p : corners) {
                if (b.getState((int) p.getX(), (int) p.getY()) == Color.WHITE) {
                    cornerscore += 2000;
                }
            }

        ArrayList<Point> xcoords = new ArrayList<>();
        xcoords.add(new Point(1,1));
        xcoords.add(new Point(6,1));
        xcoords.add(new Point(1,6));
        xcoords.add(new Point(6,6));
        for(Point p : xcoords) {
                if (b.getState((int) p.getX(), (int) p.getY()) == Color.WHITE) {
                    cornerscore -= 100;
                }
        }

        ArrayList<Point> ccoords = new ArrayList<>();
        ccoords.add(new Point(0,1));
        ccoords.add(new Point(1,0));
        ccoords.add(new Point(0,6));
        ccoords.add(new Point(1,7));
        ccoords.add(new Point(6,0));
        ccoords.add(new Point(7,1));
        ccoords.add(new Point(6,7));
        ccoords.add(new Point(7,6));
        for(Point p : ccoords) {
                if (b.getState((int) p.getX(), (int) p.getY()) == Color.WHITE) {
                    cornerscore -= 100;
                }
        }
        return cornerscore;
    }

    public int getEdges(Board b){
        int edgescore = 0;
        ArrayList<Point> edges = new ArrayList<>();
        for(int i=1;i<7;i++) {
            edges.add(new Point(i,0));
            edges.add(new Point(0,i));
            edges.add(new Point(i,7));
            edges.add(new Point(7,i));
        }
        for(Point p : edges) {
                if (b.getState((int) p.getX(), (int) p.getY()) == Color.WHITE) {
                    edgescore+=500;
                }
        }
        return edgescore;
    }

 */

}