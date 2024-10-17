package view;
public class Queen extends ChessPiece{
  
  public Queen(int r, int c, boolean s, String t, int m){
    super(r, c, s, t, m);
  }

  @Override
  public boolean isLegal(int r, int c, ChessPiece[][] board){
    if((c > -1 && c < 8) && (r > -1 && r < 8)){
      //copy and pasted from bishop code, because queen can move like the bishop
    	if(Math.abs(r - row) == Math.abs(c - col) && 
    	(col != c && row != r)
    	){
        //System.out.println("1");
    		int minCol = Math.min(col, c);
    		int minRow = Math.min(row, r);
    		int dirC = 0;
    		int dirR = 0;
    		      
    		if(col != minCol) dirC = -1;
    		else dirC = 1;
    		if(row != minRow) dirR = -1;
    		else dirR = 1;
    		      
    		for(int i = row + dirR, j = col + dirC; i != r && j != c; i = i + dirR, j = j + dirC){
          //System.out.println(board[i][j]);
    			if(board[i][j] != null) return false;
    		}
    		if(board[r][c] == null || board[r][c].side != this.side){
    		        //setNewPosition(r, c, board);
    			return true;
    		}
    	}
      //This is the test cases for when queen moves horizontally or vertically, 
      //you could just copy and paste this part into the rook's class
      //however rook has some special move scenarios like castling, that you have to implement on your own
      else if(
        //makes sure that the given coordinate moves vertically or horizontally and not sideways
        (c == col || r == row) &&
        !(col == c && row == r)
      ){
        //This is to determine if the rook moves top down left or right
        int dirR = 0;
        int dirC = 0;
        if(c == col){
          if(r > row) dirR = 1;
          else dirR = -1;
        }
        else{
          if(c > col) dirC = 1;
          else dirC = -1;
        }
        //loops until we reach the disignation
        for(int tempCol = col + dirC, tempRow = row + dirR; 
          c != tempCol || r != tempRow; 
          tempCol += dirC, tempRow += dirR
        ){
          if(board[tempRow][tempCol] != null) return false; //if there are any pieces in between it is illegal
        }
        //is legal if there are no piece at designation or if it is an enemy piece
        if(board[r][c] == null || (board[r][c].side != side)){
          //setNewPosition(r, c, board);
          return true;
        }
      }
    }
    return false;
  }
}