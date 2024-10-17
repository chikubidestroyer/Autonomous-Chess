
package view;



public class ChessBoard {
    public static void printBackground(double[] selected) {
     
        //String fname= args.length>0 ? args[0] : null;
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.PINK);
        
        StdDraw.setXscale(-4, +4);
        StdDraw.setYscale(-4, +4);
        StdDraw.setPenColor(StdDraw.BLACK);
        //Set the lines
        double y=0.0;
        //Row
        for(y=-4;y<=8;y++){
            StdDraw.line(-4,y,4,y);  
        }
        //Column
        StdDraw.line(-3,4,-3,-4);
        StdDraw.line(-2,4,-2,-4);
        StdDraw.line(-1,4,-1,-4);
        StdDraw.line(1,4,1,-4);
        StdDraw.line(2,4,2,-4);
        StdDraw.line(3,4,3,-4);
        StdDraw.line(0,4,0,-4);
        
        boolean drawBlack = false;
        for(double r = 3.5; r >= -3.5; r --){
            for(double c = -3.5; c <= 3.5; c ++){
                if(r == selected[0] && c == selected[1]){
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledSquare(c, r, 0.5);
                    StdDraw.setPenColor();
                }
                else if(drawBlack)
                    StdDraw.filledSquare(c, r, 0.5);
                drawBlack = !drawBlack;
            }
            drawBlack = !drawBlack;
        }
        
        
        StdDraw.show();
       
    }
    public static void printBoard(ChessPiece[][] board, double[] selected){
        ChessBoard.printBackground(selected);
        StdDraw.enableDoubleBuffering();
        for(int r = 0; r <= 7; r++){
            for(int c = 0; c <= 7; c++){
                if(board[r][c] != null){
                    double x_position = (double)board[r][c].col - 3.5;
                    double y_position = (double)(-1) * board[r][c].row + 3.5;
                    if(board[r][c].side == true && board[r][c].type.equals("Rook")){
                        StdDraw.picture(x_position, y_position, "CarWhite.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == true && board[r][c].type.equals("Knight")){
                        StdDraw.picture(x_position, y_position, "HorseWhite.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == true && board[r][c].type.equals("King")){
                        StdDraw.picture(x_position, y_position, "KingWhite.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == true && board[r][c].type.equals("Queen")){
                        StdDraw.picture(x_position, y_position, "QueenWhite.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == true && board[r][c].type.equals("Pawn")){
                        StdDraw.picture(x_position, y_position, "SoldierWhite.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == true && board[r][c].type.equals("Bishop")){
                        StdDraw.picture(x_position, y_position, "KnightWhite.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == false && board[r][c].type.equals("Rook")){
                        StdDraw.picture(x_position, y_position, "CarBlack.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == false && board[r][c].type.equals("Knight")){
                        StdDraw.picture(x_position, y_position, "HorseBlack.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == false && board[r][c].type.equals("King")){
                        StdDraw.picture(x_position, y_position, "KingBlack.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == false && board[r][c].type.equals("Queen")){
                        StdDraw.picture(x_position, y_position, "QueenBlack.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == false && board[r][c].type.equals("Pawn")){
                        StdDraw.picture(x_position, y_position, "SoldierBlack.png", 0.8, 0.8);
                        continue;
                    }
                    if(board[r][c].side == false && board[r][c].type.equals("Bishop")){
                        StdDraw.picture(x_position, y_position, "KnightBlack.png", 0.8, 0.8);
                        continue;
                    }
                }
                
            }
        }
        StdDraw.show();
    }
}
