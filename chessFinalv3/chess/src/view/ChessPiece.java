package view;
import java.util.ArrayList;
public abstract class ChessPiece {
  public int row;
  public int col;
  public int moves = 0; //counts the number of moves the piece has made
  public boolean side; //decides if the piece is white or black, use boolean b/c convenient (true = white, false = black)
  public String type; //type of chess piece (pawn or bishop etc.)
  public static ArrayList<ChessPiece> history = new ArrayList<ChessPiece>(); // keeps track of the pieces that moved
  //Important for pawn features, en passant and two space moves
  public int disobeyCount = 0; //counts number of times this piece has disobeyed

  public ChessPiece(int r, int c, boolean s, String t, int m) {
    row = r;
    col = c;
    side = s;
    type = t;
    moves = m;
  }

  // this function determines if a move is legal
  public abstract boolean isLegal(int r, int c, ChessPiece[][] board); 

  //sets the new position of the piece if it was determined to be a legal move
  public void setNewPosition(int r, int c, ChessPiece[][] board){
    board[row][col] = null;
    board[r][c] = this;
    history.add(this);
    this.moves++;
    row = r;
    col = c;
  }

  public String toString(){
    return this.type;
  }

  public boolean isPieceChecked(int r, int c, ChessPiece[][] board, ChessPiece p){
    return p.isLegal(r, c, board);
  }

  public int numCheck(int r, int c, ChessPiece[][] board, ChessPiece[][] shadowBoard){
    shadowBoard[row][col] = null;
    shadowBoard[r][c] = this;
    int count = 0;
    for(int i = 0; i < 8; i++)
      for(int j = 0; j < 8; j++)
        if(shadowBoard[i][j] != null && shadowBoard[i][j].side != side && isPieceChecked(r, c, shadowBoard, shadowBoard[i][j])) count++;
    return count;    
  }

  public boolean isKingNotBeingChecked(int r, int c, ChessPiece[][] board){
    ChessPiece[][] tempBoard = new ChessPiece[8][8];
    int kingR = 0;
    int kingC = 0;
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        tempBoard[i][j] = board[i][j];
        if(tempBoard[i][j] != null &&
           (tempBoard[i][j].type.equals("King") && tempBoard[i][j].side == side)
        ){
          kingR = i;
          kingC = j;
        }
      }
    }
    tempBoard[row][col] = null;
    tempBoard[r][c] = this;
    int tempRow = row;
    int tempCol = col;
    this.row = r;
    this.col = c;
    if(this.type.equals("King")){
      kingR = r;
      kingC = c;
    }
    if(tempBoard[kingR][kingC].isSafe(kingR, kingC, tempBoard)) {
    	row = tempRow;
    	col = tempCol;
    	return true;
    }
    row = tempRow;
    col = tempCol;
    return false;
  }

  public boolean isSafe(int r, int c, ChessPiece[][] board){
    for(int i = 0; i < 8; i++)
      for(int j = 0; j < 8; j++)
        if(board[i][j] != null)
          if(board[i][j].side != this.side && board[i][j].isLegal(r, c, board)) return false;
    return true;
  }
}