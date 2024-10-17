package view;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Driver {
  private static ChessPiece[][] board;
  private static int turn;
  private static Scanner scan;
  private static double[] selected = {100, 100};

  public Driver() {
    board = new ChessPiece[8][8];
    for(int i = 0; i < 8; i++){
      board[1][i] = new Pawn(1, i, false, "Pawn", 0);
      board[6][i] = new Pawn(6, i, true, "Pawn", 0);
    }
    for(int i = 2; i < 8; i += 3){
      board[0][i] = new Bishop(0, i, false, "Bishop", 0);
      board[7][i] = new Bishop(7, i, true, "Bishop", 0);
    }
    for(int i = 1; i < 8; i += 5){
      board[0][i] = new Knight(0, i, false, "Knight", 0);
      board[7][i] = new Knight(7, i, true, "Knight", 0);
    }
    for(int i = 0; i<8; i+=7){
      board[0][i] = new Rook(0, i, false, "Rook", 0);
      board[7][i] = new Rook(7, i, true, "Rook", 0);
    }
    board[0][3] = new Queen(0, 3, false, "Queen", 0);
    board[7][3] = new Queen(7, 3, true, "Queen", 0);

    board[0][4] = new King(0, 4, false, "King", 0);
    board[7][4] = new King(7, 4, true, "King", 0);

    turn = 1;
    scan = new Scanner(System.in);
  }

  public static void main(String args[]) throws InterruptedException, IOException {
    
    startGame();
  }

  public static void startGame() throws InterruptedException, IOException{
    //Scanner scan = new Scanner(System.in);
    Driver drive = new Driver();
    System.out.println("the game has started");
    StdDraw.setTit("The game has started");
    boolean fail = false;
    boolean error = false;
    while(!gameEnd()){
      if(!fail){
        ChessBoard.printBoard(board, selected);
      }
      String whichPlayer = "";
      if(turn % 2 == 0) whichPlayer = "black";
      else whichPlayer = "white";

      if(newGameEnd(whichPlayer)) break; // new end condition based on escape rules
      if(!error) StdDraw.setTit("It is " + whichPlayer + "'s turn, make your move");
      error = true;
      System.out.println("turn " + turn + " it is " + whichPlayer + "'s turn");
        System.out.println("enter piece you want to move");
        int[] coorOfPiece = getCoordinate();
        if(hasPiece(coorOfPiece)){
          StdDraw.setTit("It is " + whichPlayer + "'s turn, make your move");
          TimeUnit.MILLISECONDS.sleep(500);
          ChessBoard.printBoard(board, selected);
          System.out.println("Where do you want to move it?");
          int[] coorOfDesig = getCoordinate();
          TimeUnit.MILLISECONDS.sleep(500);
          ChessPiece piece = board[coorOfPiece[0]][coorOfPiece[1]];
          if(isWithinBorder(coorOfDesig) && piece.isLegal(coorOfDesig[0], coorOfDesig[1], board)){
            var shadow = new ChessPiece[8][8];
              for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                  shadow[i][j] = board[i][j];
                }
              }
            if(piece.isKingNotBeingChecked(coorOfDesig[0], coorOfDesig[1], shadow)){
              
              int checkCount = piece.numCheck(coorOfDesig[0], coorOfDesig[1], board, shadow);
              double disobeyChance = checkCount/5.0 > 1 ? 1 : checkCount/5.0; // limits chance to 100% when targetted by 5 enemy
              if(Math.random()>disobeyChance){
                piece.setNewPosition(coorOfDesig[0], coorOfDesig[1], board);
                ChessBoard.printBoard(board, selected);
                error = false;
              }
              else{
                piece.disobeyCount++;
                
                System.out.println("The piece refused to execute your orders");
                if(piece.disobeyCount == 3){
                  board[piece.row][piece.col] = null;
                  System.out.println("And has escaped due to your poor leadership");
                  StdDraw.setTit("Order Disobeyed: the " + piece.type + " has fled");
                }
                else if(!piece.isSafe(piece.row, piece.col, board)){
                  betrayal(piece);
                  System.out.println("And has defected to the oponent's side");
                  StdDraw.setTit("Order Disobeyed: the " + piece.type + " has defected");
                }
                else StdDraw.setTit("Order Disobeyed: due to low morale");
                
              }
              fail = false;
              turn++;
            }
            else{
              StdDraw.setTit("Order Disobeyed: King was not safe");
              System.out.println("Your king was not safe");
              selected[0] = 100;
              selected[1] = 100;
              ChessBoard.printBoard(board, selected);
              fail = true;
            }
          }
          else{
            System.out.println("That move was invalid");
            StdDraw.setTit("Invalid move");
            selected[0] = 100;
            selected[1] = 100;
            ChessBoard.printBoard(board, selected);
            fail = true;
          }
        }
        else{
          System.out.println("That was not valid, try again");
          StdDraw.setTit("Invalid move");
          selected[0] = 100;
          selected[1] = 100;
          fail = true;
        }
    }
    StdDraw.setTit("Game Over: refer to terminal to restart");
    System.out.println("game over");
    System.out.println("Do you want to play again? (y for yes and anything else as no)");
    String response = scan.next();
    switch(response){
    	case "y":
    		StdDraw.clear();
    		startGame();
    		break;
    }
  }
  public static int[] getCoordinate(){
    boolean selects = false;
    int[] result = new int[2];
    if(selected[0] == 100 || selected[1] == 100){
      while(!selects){
        if(StdDraw.isMousePressed()){
          //cast mouse position to int in order to locate which cell is clicked
          int col = (int)(StdDraw.mouseX() + 4);
          int row = (int)(4 - StdDraw.mouseY());
          
          //case when mouse goes out of board
          if((row >= 8 || col >= 8) || (row < 0 || col < 0)) continue;
          
          //locate the center of the cell that was clicked
          double x = col - 3.5;
          double y = 3.5 - row;
          //only highlight selected if it is different from the previous selected
          selected[0] = y;
          selected[1] = x;
          selects = true;
          result[0] = row;
          result[1] = col;
          return result;
        }
      }
    }
    else{
      while(!selects){
        if(StdDraw.isMousePressed()){
          //cast mouse position to int in order to locate which cell is clicked
          int col = (int)(StdDraw.mouseX() + 4);
          int row = (int)(4 - StdDraw.mouseY());
          
          //case when mouse goes out of board
          if((row >= 8 || col >= 8) || (row < 0 || col < 0)) continue;
        
          //locate the center of the cell that was clicked
          double x = col - 3.5;
          double y = 3.5 - row;
          //only highlight selected if it is different from the previous selected
          if(selected[0] != y || selected[1] != x){
            
            selects = true;
            result[0] = row;
            result[1] = col;
            selected[0] = 100;
            selected[1] = 100;
            return result;
          }
        }
      }
    }
    return result;
  }

  public static boolean isWithinBorder(int[] coor){
    return (coor[0] >= 0 && coor[0] <= 7) && (coor[1] >= 0 && coor[1] <= 7);
  }

  public static boolean hasPiece(int[] coordinates){
    ChessPiece temp = board[coordinates[0]][coordinates[1]];
    boolean tempSide = turn % 2 != 0;
    return temp != null && (temp.side == tempSide);
  }
  
  // function that determines if game is won, little difficult
  public static boolean gameEnd(){
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(board[i][j] == null) continue;
        boolean turnTemp = turn % 2 != 0;
        if(board[i][j].side != turnTemp) continue;
        ChessPiece piece = board[i][j];
        for(int a = 0; a < 8; a++){
          for(int b = 0; b < 8; b++){
            if(piece.isKingNotBeingChecked(a, b, board) && piece.isLegal(a, b, board)){
              return false;
            }
          }
        }
      }
    }
    // let's add the name of the winner here, black or white
    String win = "";
    if(turn % 2 != 0) win = "Black";
    else win = "White";
    System.out.println("The Winner is: " + win);
    return true;
  }

  public static boolean newGameEnd(String player){ // end game condition with new rules
    boolean side = player.equals("white") ? true : false;
    int kingC = 0;
    int kingR = 0;
    OUTER: for(int i = 0; i < 8; i++)
      for(int j = 0; j < 8; j++)
        if(board[i][j] != null &&
           (board[i][j].type.equals("King") && board[i][j].side != side)
        ){
          kingR = i;
          kingC = j;
          break OUTER;
        }
    if(board[kingR][kingC].isSafe(kingR, kingC, board)) return false;
    return true;
  }
  
  public static void betrayal(ChessPiece p){
    p.side = !p.side;
    p.disobeyCount = 0;
  }
}