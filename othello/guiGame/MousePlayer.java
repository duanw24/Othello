package othello.guiGame;

import java.awt.*;

public class MousePlayer extends Player {
	private GamePanel thePanel;
	public static final int TIME_TOLERANCE = 100;

	public MousePlayer(Color c, String n, GamePanel gp){
		super(c,n);
		thePanel = gp;
	}
	
	public Point getMove(Board theBoard){
		int count = 0;
		int x = thePanel.getCurrentX();
		int y = thePanel.getCurrentY();
		while(x==-1 || y==-1){System.out.println(x+" : "+y);
			x = thePanel.getCurrentX();
			y = thePanel.getCurrentY();
			count++;
			if(count > TIME_TOLERANCE) return new Point(-1,-1);
		}
		Point p = new Point(x,y);
		thePanel.resetCurrent();

		return p;
	}
}