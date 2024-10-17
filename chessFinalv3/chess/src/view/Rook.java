package view;
public class Rook extends ChessPiece {

  public Rook(int r, int c, boolean s, String t, int m) {
    super(r, c, s, t, m);
  }

  @Override
  public boolean isLegal(int r, int c, ChessPiece[][] board) {
    if ((c > -1 && c < 8) &&
        (r > -1 && r < 8) &&
        (c == col || r == row) &&
        !(col == c && row == r)) {
      // This is to determine if the rook moves top down left or right
      int dirR = 0;
      int dirC = 0;
      if (c == col) {
        if (r > row)
          dirR = 1;
        else
          dirR = -1;
      } else {
        if (c > col)
          dirC = 1;
        else
          dirC = -1;
      }
      // loops until we reach the disignation
      for (int tempCol = col + dirC, tempRow = row + dirR; c != tempCol
          || r != tempRow; tempCol += dirC, tempRow += dirR) {
        if (board[tempRow][tempCol] != null)
          return false; // if there are any pieces in between it is illegal
      }
      // is legal if there are no piece at designation or if it is an enemy piece
      if (board[r][c] == null || (board[r][c].side != side)) {
        //setNewPosition(r, c, board);
        return true;
      }
    }
    return false;
  }
}