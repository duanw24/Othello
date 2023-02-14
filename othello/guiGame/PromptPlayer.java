package othello.guiGame;

import javax.swing.*;
import java.awt.*;

public class PromptPlayer extends Player{
	public PromptPlayer(Color c, String name){
		super(c, name);
	}
	
	public Point getMove(Board theBoard){
				
		int r = Integer.parseInt(JOptionPane.showInputDialog(null, "Row (0-"+(theBoard.getRows()-1)+")?",
								 getName()+"\'s Move.",JOptionPane.INFORMATION_MESSAGE));
		int c = Integer.parseInt(JOptionPane.showInputDialog(null, "Column (0-"+(theBoard.getColumns()-1)+")?",
								 getName()+"\'s Move.",JOptionPane.INFORMATION_MESSAGE));
		return new Point(r,c);
	}
	
}
