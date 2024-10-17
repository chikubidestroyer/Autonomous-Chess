package view;
public class Bishop extends ChessPiece{
  
  public Bishop(int r, int c, boolean s, String t, int m) {
    super(r, c, s, t, m);
  }

  @Override
  public boolean isLegal(int r, int c, ChessPiece[][] board){
    if(Math.abs(r - row) == Math.abs(c - col) && 
      (c > -1 && c < 8) && (r > -1 && r < 8) && 
      (col != c && row != r)
    ){
      int minCol = Math.min(col, c);
      int minRow = Math.min(row, r);
      int dirC = 0;
      int dirR = 0;
      
      if(col > minCol) dirC = -1;
      else dirC = 1;
      if(row > minRow) dirR = -1;
      else dirR = 1;
      
      for(int i = row + dirR, j = col + dirC; i != r && j != c; i = i + dirR, j = j + dirC){
        if(board[i][j] != null) return false;
      }
      if(board[r][c] == null || board[r][c].side != this.side){
        //setNewPosition(r, c, board);
        return true;
      }
    }
    return false;
  }
}