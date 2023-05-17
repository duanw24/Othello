package othello.ai;
import othello.guiGame.Board;
import othello.guiGame.minimaxPlayer;

import java.awt.*;

public class Tests {
    public static void main(String[] args) {
        Board b = new Board();

        System.out.println(minTest.heuristic(b));
        minimaxPlayer.AIEngine.getInstance().init(new minimaxPlayer(Color.BLACK,"1"),2);
        System.out.println(minimaxPlayer.AIEngine.getInstance().heuristic(b));
    }
}
