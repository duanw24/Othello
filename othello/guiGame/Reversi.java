package othello.guiGame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Point;
import java.util.Random;

public class Reversi extends JFrame implements ActionListener{
 private static int NUMBER_OF_GAMES = 10;
 private static boolean test = true;
 private static final int EXTRA_SPACE = 100;
 private static final int ROWS = 8, COLUMNS = 8;
 private static final Color COLOR_ONE = Color.BLACK;
 private static final Color COLOR_TWO = Color.WHITE;
 
 
 private JButton newGame, quit, move, play;
 private JCheckBox showModeBox;
 private JLabel playerOneName, playerTwoName;
 private GamePanel center;
 
 private Player playerOne;
 private Player playerTwo;
 private boolean player;
 private boolean first_player;
 private int player_one_record;
 private int player_two_record;
 private boolean game_done;
 private boolean playingMultipleGames;
 
 private Board theBoard;

  public Reversi(){
  super();

  newGame = new JButton("New");
  newGame.addActionListener(this);
  quit = new JButton("Quit");
  quit.addActionListener(this);
  showModeBox = new JCheckBox("Show Moves");
  move = new JButton("Move");
  move.addActionListener(this);
  play = new JButton("Play "+NUMBER_OF_GAMES+" Games");
  play.addActionListener(this);
  
  playerOneName = new JLabel("");
  playerTwoName = new JLabel("");
    
  JPanel bottom = new JPanel();
  bottom.add(playerOneName);
  bottom.add(move);
  bottom.add(play);
  bottom.add(playerTwoName);
  
  JPanel top = new JPanel();
  top.add(newGame);
  top.add(quit);
  top.add(showModeBox);
  
  center = new GamePanel(0,0);
  this.getContentPane().add(center, BorderLayout.CENTER);
  this.getContentPane().add(bottom, BorderLayout.SOUTH);
  this.getContentPane().add(top, BorderLayout.NORTH);
  first_player = true;
  game_done = false;
  playingMultipleGames = false;

  this.createGame();

  this.setTitle("REVERSI");
  this.setResizable(false);
  this.setLocation(100,100);
  this.setVisible(true);
  
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
    }

  private void createGame(){
  Color[][] setUp = new Color[ROWS][COLUMNS];
  //Generate starting position in= the center
  setUp[ROWS/2 - 1][COLUMNS/2 - 1] = COLOR_ONE;
  setUp[ROWS/2][COLUMNS/2 -1] = COLOR_TWO;
  setUp[ROWS/2 - 1][COLUMNS/2] = COLOR_TWO;
  setUp[ROWS/2][COLUMNS/2] = COLOR_ONE;
  theBoard = new Board(setUp);

  //Instantiate new player objects in lines 86 and 87
  playerOne = new minimaxPlayer(COLOR_ONE, "BLACK");
  playerTwo = new jeff(COLOR_TWO, "WHITE");

  if(first_player) {
   player = true;
  }
  else {
   player = false;
  }
  
  if(first_player){
   move.setText("Move: "+playerOne.getName());  
  }
  else{
   move.setText("Move: "+playerTwo.getName());
  }
  playerOneName.setText(playerOne.getName()+": "+theBoard.colorCount(playerOne.getColor())+"\t\t\t\t\t");
  playerTwoName.setText("\t\t\t\t\t"+playerTwo.getName()+": "+theBoard.colorCount(playerTwo.getColor()));
  
  first_player = !first_player;
  
  center.setGame(theBoard);
 
  this.setResizable(true);
  this.setSize(GamePanel.SQUARE_SIZE*COLUMNS, GamePanel.SQUARE_SIZE*ROWS+EXTRA_SPACE);
  this.setResizable(false);

 }

    public void actionPerformed(ActionEvent e){
  if(e.getSource() == newGame)
   this.createGame();
  if(e.getSource() == quit)
   System.exit(0);
  if(e.getSource() == move){
   Board board_copy = theBoard.getCopy();
   if(player){
    if(theBoard.hasMove(COLOR_ONE) == false){
     if(!playingMultipleGames){
      JOptionPane.showMessageDialog(null, playerOne.getName()+" has no legal move.\nPassing move to "+playerTwo.getName()+".");
     }
    }
    else{
     Point move = playerOne.getMove(theBoard);
     if(!theBoard.equals(board_copy)){
      JOptionPane.showMessageDialog(null, playerOne.getName()+" cheated by changing the board.\nGAME OVER.\n\n"
              +playerTwo.getName()+" WINS!");
      player_two_record++;
      game_done = true;
      this.createGame();
      return;     
     }
     if(showModeBox.isSelected()){
      center.drawPoint((int) move.getX(),(int) move.getY());
      JOptionPane.showMessageDialog(null, "Proceed with move.");
     }
     boolean m = center.placePiece((int) move.getX(),(int) move.getY(), COLOR_ONE);
     if(!m){
      JOptionPane.showMessageDialog(null, playerOne.getName()+" made an illegal move at "+move+".\nGAME OVER.\n\n"
              +playerTwo.getName()+" WINS!");
      player_two_record++;
      game_done = true;
      this.createGame();
      return;
     }
    }
   }
   else{
    if(theBoard.hasMove(COLOR_TWO) == false){
     if(!playingMultipleGames){
      JOptionPane.showMessageDialog(null, playerTwo.getName()+" has no legal move.\nPassing move to "+playerOne.getName()+".");
     }
    }
    else{
     Point move = playerTwo.getMove(theBoard);
     if(!theBoard.equals(board_copy)){
      JOptionPane.showMessageDialog(null, playerTwo.getName()+" cheated by changing the board.\nGAME OVER.\n\n"
              +playerOne.getName()+" WINS!");
      player_one_record++;
      game_done = true;
      this.createGame();
      return;     
     }
     if(showModeBox.isSelected()){
      center.drawPoint((int) move.getX(),(int) move.getY());
      JOptionPane.showMessageDialog(null, "Proceed with move.");
     }
     boolean m = center.placePiece((int) move.getX(),(int) move.getY(), COLOR_TWO);
     if(!m){
      JOptionPane.showMessageDialog(null, playerTwo.getName()+" made an illegal move at "+move+".\nGAME OVER.\n\n"+
              playerOne.getName()+" WINS!");
      player_one_record++;
      game_done = true;
      this.createGame();
      return;
     }
    }
   }
   player = !player;
   if(player){
    move.setText("Move: "+playerOne.getName());
   }
   else {
    move.setText("Move: "+playerTwo.getName());
   }
   playerOneName.setText(playerOne.getName()+": "+theBoard.colorCount(playerOne.getColor())+"\t\t\t\t\t");
   playerTwoName.setText("\t\t\t\t\t"+playerTwo.getName()+": "+theBoard.colorCount(playerTwo.getColor()));

   String first_to_move = "";
   if(first_player){
    first_to_move = playerTwo.getName();
   }
   else{
    first_to_move = playerOne.getName();

   }
   //Check for both Colors out of play
   boolean stuck = false;
   if(theBoard.hasMove(COLOR_ONE)==false && theBoard.hasMove(COLOR_TWO)==false)
    stuck = true;
   if(theBoard.isDone()) //not really needed - hasMove should catch this as well
    stuck = true;
   if(stuck){
    int one = theBoard.colorCount(COLOR_ONE);
    int two = theBoard.colorCount(COLOR_TWO);
    if(one > two){
     if(!test){JOptionPane.showMessageDialog(null, first_to_move+" went first.\n\n"+playerOne.getName()+" Wins: "+one+" to "+two);}
     //System.out.println( first_to_move+" went first.\n\n"+playerOne.getName()+" Wins: "+one+" to "+two);
     player_one_record++;
     game_done = true;
    }
    else if(two > one){
     if(!test) {
      JOptionPane.showMessageDialog(null, first_to_move + " went first.\n\n" + playerTwo.getName() + " Wins: " + two + " to " + one);
     }
      //System.out.println( first_to_move+" went first.\n\n"+playerTwo.getName()+" Wins: "+one+" to "+two);
     player_two_record++;
     game_done = true;
    }
    else{
     if(!test) {
      JOptionPane.showMessageDialog(null, first_to_move + " went first.\n\n" + "It is a tie: " + two + " to " + one);
     }
     //System.out.println("was a tie\n\n"+" Wins: "+one+" to "+two);
     game_done = true;
    }
    this.createGame();
   }
  }
  if(e.getSource() == play){
   playingMultipleGames = true;
   player_one_record = 0;
   player_two_record = 0;
   for(int i=0; i< NUMBER_OF_GAMES; i++){
    this.createGame();
    game_done = false;
    while(!game_done){
     move.doClick();
    }
   }
   JOptionPane.showMessageDialog(null,playerOne.getName()+" wins "+player_one_record+" games.\n"+
            playerTwo.getName()+" wins "+player_two_record+" games.");
   playingMultipleGames = false;
  }

    }
 

 
 public static void main(String[] args){
  Reversi game = new Reversi();
    }

}