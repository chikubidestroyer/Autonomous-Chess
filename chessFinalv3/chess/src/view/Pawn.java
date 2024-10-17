package view;
import java.util.*;

public class Pawn extends ChessPiece {

  public Pawn(int r, int c, boolean s, String t, int m) {
    super(r, c, s, t, m);
  }

  @Override
  public boolean isLegal(int r, int c, ChessPiece[][] board) {
    int dir; // white pawns and black pawns move in different directions
    if (this.side)
      dir = -1;
    else
      dir = 1;
    int mapEnd = (int) (3.5 + 3.5 * dir);
    if ((r != mapEnd + dir) && (c > -1 && c < 8)) { // check that the designation is not out of the map
      if ((r == this.row + 2 * dir) &&
          (c == this.col) &&
          (moves == 0) &&
          (board[r][c] == null) &&
          (board[r - dir][c] == null)) {
        // validate the 2-step move with pawn's first move
        return true;
      }
      // The other scenario, where the pawn moves only one step
      if (this.row + dir == r) {
        if ((board[r][c] == null) && (c == col)) {
          return true;
        } else if ((c == this.col + 1 || c == this.col - 1)) { // condition when pawn captures enemy piece
          if ((board[r][c] != null) && (board[r][c].side != this.side)) {
            return true;
          }
          if ( // En Passant conditions
          (history.get(history.size() - 1) == board[row][c]) &&
              (board[row][c].moves == 1) &&
              (board[row][c].type.equals("Pawn"))) {
            //board[row][c] = null;
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public void setNewPosition(int r, int c, ChessPiece[][] board) {
    super.setNewPosition(r, c, board);
    if (r == 0 || r == 7) {
      changePiece(board);
    }
    if ( // En Passant conditions
          (history.get(history.size() - 1) == board[r-1][c]) &&
              (board[r-1][c].moves == 1) &&
              (board[r-1][c].type.equals("Pawn"))) {
                board[r-1][c]=null;
              }
  }

  public void changePiece(ChessPiece[][] board) {
    try (Scanner scan = new Scanner(System.in)) {
      boolean repeatQuestion = true;
      while (repeatQuestion) {
        System.out.println("Which piece do you want to change the pawn to?");
        System.out.println("Q for queen, B for bishop, K for knight, R for rook\nThis is case sensitive");
        String s = scan.next();
        ChessPiece temp;
        switch (s) {
          case "Q":
            repeatQuestion = false;
            temp = new Queen(row, col, side, "Queen", moves);
            board[row][col] = temp;
            break;
          case "B":
            repeatQuestion = false;
            temp = new Bishop(row, col, side, "Bishop", moves);
            board[row][col] = temp;
            break;
          case "K":
            repeatQuestion = false;
            temp = new Knight(row, col, side, "Knight", moves);
            board[row][col] = temp;
            break;
          case "R":
            repeatQuestion = false;
            temp = new Rook(row, col, side, "Rook", moves);
            board[row][col] = temp;
            break;
          default:
            System.out.println("Please follow instructions");
        }
      }
    }
  }
}