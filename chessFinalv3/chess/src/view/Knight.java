package view;
public class Knight extends ChessPiece{

  public Knight(int r, int c, boolean s, String t, int m) {
    super(r, c, s, t, m);
  }

  @Override
  public boolean isLegal(int r, int c, ChessPiece[][] board){
    if(
      (r != row && c != col) &&
      (r > -1 && r < 8) &&
      (c > -1 && c < 8)
    ){
      if(
        (Math.abs(row - r) <= 2) &&
        (Math.abs(col - c) <= 2) &&
        (Math.abs(row - r) != Math.abs(col - c))
      ){
        if(board[r][c] == null || board[r][c].side != this.side){
          //setNewPosition(r, c, board);
          return true;
        }
      }
    }
    return false;
  }
}