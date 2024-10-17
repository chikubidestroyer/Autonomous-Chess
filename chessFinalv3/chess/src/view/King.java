package view;
public class King extends ChessPiece{
  private boolean hasCastle;
  private boolean castleLeft;
  
  public King(int r, int c, boolean s, String t, int m){
    super(r, c, s, t, m);
  }
  @Override
  public boolean isLegal(int r, int c, ChessPiece[][] board) {
    if (
      (((row == 0 && r == 0) || (row == 7 && r == 7)) && (col == 4 && c == 6)) || 
      (((row == 0 && r == 0) || (row == 7 && r == 7)) && (col == 4 && c == 2))
      )
      {
        if (
          board[row][col+1] == null &&
          board[row][col+2] == null &&
          board[row][col+3] != null &&
          board[row][col+3].side == this.side &&
          board[row][col+3].moves == 0 &&
          this.moves == 0
        ){
          hasCastle = true;
          castleLeft = true;
          return true;
        }
        else if (
          board[row][col-1] == null &&
          board[row][col-2] == null &&
          board[row][col-3] == null &&
          board[row][col-4] != null &&
          board[row][col-4].side == this.side &&
          board[row][col-4].moves == 0 &&
          this.moves == 0
        ) {
          hasCastle = true;
          return true;
        }
      }
    else if ((c > -1 && c < 8) &&
        (r > -1 && r < 8) &&
        (c == col || r == row) &&
        !(col == c && row == r) &&
        (Math.abs(col - c) == 1 || Math.abs(row - r) == 1)
    ) {
      if(board[r][c] == null || (board[r][c].side != side)){
          return true;
      }
    }
    else if((Math.abs(r-row) == 1 && Math.abs(c - col) == 1) &&
    	    (c > -1 && c < 8) && (r > -1 && r < 8) && 
    	    (col != c && row != r)
    	    ){
    	      if(board[r][c] == null || board[r][c].side != this.side){
    	        return true;
    	      }
    	    }
    	    return false;
  }
  
  @Override
  public void setNewPosition(int r, int c, ChessPiece[][] board){
    super.setNewPosition(r, c, board);
    if(hasCastle){
      if(castleLeft){
        board[row][col+1]=null;
        board[row][col-1]= new Rook(row, col-1, this.side, "Rook", 0);
      }
      else{
        board[row][col-2]=null;
        board[row][col+1]= new Rook(row, col+1, this.side, "Rook", 0);
      }
      hasCastle = false;
    }
  }
  
}