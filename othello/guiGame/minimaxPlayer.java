package othello.guiGame;

import lombok.Getter;
import lombok.Setter;
import othello.ai.minTest;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class minimaxPlayer extends Player {
    private BufferedWriter bfr;

    public minimaxPlayer(Color c, String name){
        super(c, name);
        AIEngine.getInstance().init(this,2);
        try {
            bfr=new BufferedWriter(new FileWriter("stats.txt"));
        } catch(Exception e) {}

    }

    public Point getMove(Board theBoard){
        AIEngine.getInstance().nodes = 0;
        long t1 = System.currentTimeMillis();
        Point bestmove = AIEngine.getInstance().getOptimalMove(theBoard);

        System.out.println(System.currentTimeMillis()-t1 + " ms to find best move, "+AIEngine.getInstance().nodes+" nodes considered");
        return bestmove;
    }

    public static ArrayList<Point> getLegalMoves(Board b, Color c) {
        ArrayList<Point> moves = new ArrayList<>();
        int rows = b.getRows();
        int columns = b.getColumns();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (b.isLegal(i, j, c)) {
                    moves.add(new Point(i, j));
                }
            }
        }

        return moves;
    }

    public static class AIEngine {
        private Player player;
        private Color mainColor;
        private Color oppColor;
        private int maxDepth;
        @Getter@Setter
        private int nodes = 0;

        private static AIEngine instance = new AIEngine();

        private AIEngine() {}

        public static AIEngine getInstance() {return instance;}

        public void init(Player p, int maxDepth) {
            this.mainColor=p.getColor();
            this.oppColor=p.getColor()==Color.BLACK?Color.WHITE:Color.BLACK;
            this.player=p;
            this.maxDepth=maxDepth;
        }

        public Point getOptimalMove(Board theBoard) {
            ArrayList<Point> moves = getLegalMoves(theBoard, mainColor);
            Point bestMove = moves.get(0);
            //Double.MAX_VALUE>0 also took me ages to figure out :(
            double bestValue = Double.NEGATIVE_INFINITY;

            for (Point move : moves) {
                Board copyBoard = theBoard.getCopy();
                copyBoard.placePiece((int) move.getX(), (int) move.getY(), mainColor);
                double value = minimaxSearch(copyBoard, 0, false, Double.NEGATIVE_INFINITY, Double.MAX_VALUE);
                if (value >= bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            }
            System.out.println("Best value: " + bestValue);

            return bestMove;
        }


        //isMax=black,!isMax=white
        public double minimaxSearch(Board board,int depth,boolean isMax, double alpha, double beta) {
            nodes++;
            if ((depth == maxDepth) || board.isDone()) {
                return heuristic(board);
            }

            ArrayList<Point> moves;

            if (isMax) {
                moves = getLegalMoves(board, mainColor);

                if(moves.isEmpty()) {
                    return minimaxSearch(board, depth + 1, false, alpha, beta);
                }

                double bestVal = Double.NEGATIVE_INFINITY;

                for (Point p : moves) {
                    Board copy = board.getCopy();
                    copy.placePiece((int) p.getX(), (int) p.getY(), mainColor);
                    double value = minimaxSearch(copy, depth + 1, false, alpha, beta);
                    bestVal = Math.max(bestVal, value);
                    alpha = Math.max(alpha, bestVal);
                    if (beta <= alpha) {
                        break;
                    }
                }
                return bestVal;
            } else {
                //bruh oppcolor was maincolor for a few days and it took me 5+ hours to find it :(
                moves = getLegalMoves(board, oppColor);

                if(moves.isEmpty()) {
                    return minimaxSearch(board, depth + 1, true, alpha, beta);
                }

                double bestVal = Double.MAX_VALUE;

                for (Point p : moves) {
                    Board copy = board.getCopy();
                    copy.placePiece((int)p.getX(), (int)p.getY(), oppColor);
                    double value = minimaxSearch(copy, depth + 1, true, alpha, beta);
                    bestVal = Math.min(bestVal, value);
                    beta = Math.min(beta, bestVal);
                    if (beta <= alpha) {
                        break;
                    }
                }
                return bestVal;
            }
        }

        public double heuristic(Board board) {
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
                    if(board.isLegal(i,j,oppColor)) {
                        wMoves++;
                    }
                    if(board.isLegal(i,j,mainColor)) {
                        bMoves++;
                    }
                    if (board.getState(i, j) == oppColor) {
                        bScore-=weights[i][j];
                        white++;
                    }
                    if (board.getState(i, j) == mainColor) {
                        bScore+=weights[i][j];
                        black++;
                    }
                    if(board.getState(i,j) != null) {
                        for (int k = 0; k < 8; k++) {
                            int x = i + X1[k];
                            int y = j + Y1[k];
                            if (x >= 0 && x < 8 && y >= 0 && y < 8 && board.getState(x, y) == null) {
                                if (board.getState(i, j) == oppColor)
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
            if(board.getState(0,0)==mainColor) black++;
            else if(board.getState(0,0)==oppColor) white++;
            if(board.getState(0,7)==mainColor) black++;
            else if(board.getState(0,7)==oppColor) white++;
            if(board.getState(7,0)==mainColor) black++;
            else if(board.getState(7,0)==oppColor) white++;
            if(board.getState(7,7)==mainColor) black++;
            else if(board.getState(7,7)==oppColor) white++;
            corner = 25 * (black - white);

            white=0;
            black=0;
            if(board.getState(0,0) == null)   {
                if(board.getState(0,1) == mainColor) black++;
                else if(board.getState(0,1) == oppColor) white++;
                if(board.getState(1,1) == mainColor) black++;
                else if(board.getState(1,1) == oppColor) white++;
                if(board.getState(1,0) == mainColor) black++;
                else if(board.getState(1,0) == oppColor) white++;
            }
            if(board.getState(0,7) == null)   {
                if(board.getState(0,6) == mainColor) black++;
                else if(board.getState(0,6) == oppColor) white++;
                if(board.getState(6,6) == mainColor) black++;
                else if(board.getState(6,6) == oppColor) white++;
                if(board.getState(7,1) == mainColor) black++;
                else if(board.getState(7,1) == oppColor) white++;
            }
            if(board.getState(7,0) == null)   {
                if(board.getState(7,1) == mainColor) black++;
                else if(board.getState(7,1) == oppColor) white++;
                if(board.getState(6,1) == mainColor) black++;
                else if(board.getState(6,1) == oppColor) white++;
                if(board.getState(6,0) == mainColor) black++;
                else if(board.getState(6,0) == oppColor) white++;
            }
            if(board.getState(7,7) == null)   {
                if(board.getState(6,6) == mainColor) black++;
                else if(board.getState(6,6) == oppColor) white++;
                if(board.getState(7,6) == mainColor) black++;
                else if(board.getState(7,6) == oppColor) white++;
                if(board.getState(6,7) == mainColor) black++;
                else if(board.getState(6,7) == oppColor) white++;
            }
            closeness = -12.5 * (black-white);
            return (10 * parity) + (801.724 * corner) + (382.026 * closeness) + (78.922 * mobility) + (74.396 * front) + (10 * bScore);
        }


    }
}