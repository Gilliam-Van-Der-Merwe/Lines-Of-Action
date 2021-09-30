
import java.util.Scanner;

  /*
 * This class implements the board. It stores an array that contains the content
 * of the board, and several functions that perform operations such as moving a
 * piece or counting the number of markers in a row, column, or diagonal.
 */
public class Board {

	// The size of the board
	private static int size;

	// The pieces
	public static final int INVALID = -1;
	public static final int EMPTY = 0;
	public static final int WHITE = 1;
	public static final int BLACK = 2;
	public static final int USER = BLACK;
	public static final int COMPUTER = WHITE;

	// The board
	private static int[][] board;
	private static String alphabet = "ABCDEFGHIJKLMNOP";
	// Convention: board[0][0] is the south west square
//----------------------------------------------------------------------------------------------------------------------------------------
	/*
	 * Create and set up a new board
	 */
	public void newBoard(int newSize) {
     // Initialization of new board.
     board = new int[newSize][newSize];
	 size = newSize;
	 setSize(newSize);
	 
		 for(int r = 0; r < newSize; r++)
		 {
			 for(int c = 0; c < newSize; c++)
			 {
				 board[r][c] = EMPTY;
			 }
		 }
		 for(int i = 1; i < newSize-1; i++)
		 {
			 	board[0][i] = BLACK;
		 }
		 for(int j = 1; j < newSize-1; j++)
		 {
			 board[newSize-1][j] = BLACK;
		 }
		 for(int k = 1; k < newSize-1; k++)
		 {
			 board[k][0] = WHITE;
		 }
		 for(int l = 1; l < newSize-1; l++)
		 {
			 board[l][newSize-1] = WHITE;
		 }
		 
		 if(!(TestLOA.mode == 3))
		 {
			 boardDisplay();  
		 }
	}
//--------------------------------------------------------------------------------------------------------------------------------------	
	public static void backLayerBoardDisplay() // method to test value movement
	{
		for(int r = 0; r < size; r++)
        {
          for(int c = 0; c < size; c++)
          {
            System.out.print(" " + getPiece(r, c));
          }
          System.out.println();
        }
		System.out.println();
	}
//--------------------------------------------------------------------------------------------------------------------------------------	
	public void boardDisplay()
	{
		// Display of Board.
		String temp, temp2;
	    System.out.print(" ");
	    for(int i = 0; i < size; i++)
		{
	    	 temp = alphabet.substring(i, i+1);
			 System.out.print(" " + temp + " ");
		}
		System.out.println();
		for(int r = 0; r < size; r++)
	    {
			temp2 = alphabet.substring(size-r-1, size-r);
			System.out.print(temp2);
			 for(int c = 0; c < size; c++)
			 {
				 if(board[r][c] == EMPTY)
			 	{
					System.out.print(" . ");
				}
				else if(board[r][c] == BLACK)
				{
					System.out.print(" B ");
				}
				else if(board[r][c] == WHITE)
				{
					System.out.print(" W ");
				}
			 }
			System.out.println();
	    }
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	/*
	 * Function that returns the piece currently on the board at the specified
	 * row and column.
	 */
	public static int getPiece(int row, int col) {
		if ((row < 0) || (row >= size)) {
			return INVALID;
		}
		if ((col < 0) || (col >= size)) {
			return INVALID;
		}
		return board[row][col];
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	/*
	 * Make a move. Check that the move is valid. If not, return false. If
	 * valid, modify the board that the piece has moved from (fromRow, fromCol)
	 * to (toRow, toCol).
	 */
	public void moveConversion(String movement, int pNum) // Convert move into coordinates
	{
		int[] moveConvert = new int[4];
		
		if(movement.equals("QUIT"))
        {
        	System.out.println("Player Quit");
        	System.exit(0);
        }
		
		for(int i = 0; i <= 3; i++)
		{
			for(int k = 0; k < alphabet.length(); k++)
			{
				if(movement.charAt(i) == alphabet.charAt(k))
				{
					if(i == 0 || i == 2)
					{
						moveConvert[i] = (size-1) - k;
					}
					else
					{
						moveConvert[i] = k;
					}					
				}
			}
		}
		if(isValidMove(pNum, moveConvert[0],moveConvert[1],moveConvert[2],moveConvert[3]))
		{
			makeMove(pNum, moveConvert[0],moveConvert[1],moveConvert[2],moveConvert[3]);
			boardDisplay();
		}
		else
		{
			System.out.println("ERROR: invalid move");
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Move");
			String move = sc.next();
			redoCheck(move, pNum);
			sc.close();
		}
	}
//--------------------------------------------------------------------------------------------------------------------------------------	
	public void redoCheck(String move, int pNum) // double check move conversion after invalid move
	{
		moveConversion(move, pNum);
	}
//--------------------------------------------------------------------------------------------------------------------------------------	
	
	public static boolean makeMove(int player, int fromRow, int fromCol, int toRow, int toCol) { // method alters marker locations making a move
		if (!isValidMove(player, fromRow, fromCol, toRow, toCol)) {
			return false;
		}
		else if(board[fromRow][fromCol] == player && board[toRow][toCol] == player)
		{
			return false;
		}
		// int opponent = (player == WHITE) ? BLACK : WHITE;
		board[fromRow][fromCol] = EMPTY;
		board[toRow][toCol] = player;
		return true;
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	/*
	 * Return the size of the board.
	 */
	public static int getSize() {
		System.out.println(size);
		return size;
	}
//--------------------------------------------------------------------------------------------------------------------------------------	
	public static void setSize(int temp) // set the size of the board
	{
		size = temp;
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	/*
	 * Check if the given move is valid.  This entails checking that:
	 *
	 * - the player is valid
	 *
	 * - (fromRow, fromCol) is a valid coordinate
	 *
	 * - (toRow, toCol) is a valid coordinate
	 *
	 * - the from square contains a marker that belongs to the player
	 *
	 * - check that we are moving a "legal" number of squares
	 */
	
	public static boolean isValidMove(int player, int fromRow, int fromCol, int toRow, int toCol) {
		// Check if the player is valid
		if ((player != WHITE) && (player != BLACK)) {
			return false;
		}
		
		// Check if the from and to squares are valid
		if ((fromRow < 0) || (fromRow >= size) || (toRow < 0) || (toRow >= size)) {
			return false;
		}
		if ((fromCol < 0) || (fromCol >= size) || (toCol < 0) || (toCol >= size)) {
			return false;
		}

		// Check that the starting square contains a piece of the player
		if (board[fromRow][fromCol] != player) {
			return false;
		}

		// Who is the opponent?
		int opponent = WHITE;
		if (player == WHITE) {
			opponent = BLACK;
		}

		// How much are we moving?
		int deltaR = toRow - fromRow;
		int deltaC = toCol - fromCol;

		// Check we can move in this direction and calculate the distance
		int distance = 0, count = 0;
		if (deltaC == 0) {
			// North or south
			distance = Math.abs(deltaR);
			count = colCount(fromCol);
		} else if (deltaR == 0) {
			// West or east
			distance = Math.abs(deltaC);
			count = rowCount(fromRow);
		} else if ((deltaC > 0) == (deltaR > 0)) {
			// Northeast or southwest
			if (deltaC != deltaR) {
				return false;
			}
			distance = Math.abs(deltaC);
			count = diagNortheastCount(fromRow, fromCol);
		} else {
			// Northwest or southeast
			if (deltaC != -deltaR) {
				return false;
			}
			distance = Math.abs(deltaC);
			count = diagNorthwestCount(fromRow, fromCol);
		}

		// Check we are moving the correct distance
		if (distance != count) {
			return false;
		}

		// Calculate the one-step size
		int stepR = (deltaR > 0) ? 1 : ((deltaR < 0) ? -1 : 0);
		int stepC = (deltaC > 0) ? 1 : ((deltaC < 0) ? -1 : 0);

		// Now check each square
		for (int r = fromRow, c = fromCol; (r != toRow) || (c != toCol); r += stepR, c += stepC) {
			if ((r == toRow) && (c == toCol)) {
				if (board[r][c] == player) {
					return false;
				}				
			} else if (board[r][c] == opponent) {
				return false;
			}
		}
		return true;
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	public static int ValidSquareMovementCount(int fromRow, int fromCol) // check valid movement by calling count methods
	{
		int validMoveCount = 0;
		validMoveCount = rowCount(fromRow);
		validMoveCount = colCount(fromCol);
		validMoveCount =  diagNortheastCount(fromRow, fromCol);
		validMoveCount =  diagNorthwestCount(fromRow, fromCol);
		return validMoveCount;
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	public static int SquareMoveCount(int fromRow, int fromCol, int toRow, int toCol) // method to calculate movement made
	{
		int moveCount = 0, posR = fromRow, posC = fromCol;
		if(posR > toRow && posC < toCol)
		{
			while(posR > toRow && posC < toCol)
			{
				posR--;
				posC++;
				moveCount++;
			}
		}
		else if(posR < toRow && posC > toCol)
		{
			while(posR < toRow && posC > toCol)
			{
				posR++;
				posC--;
				moveCount++;
			}
		}
		else if(posR > toRow && posC > toCol)
		{
			while(posR > toRow && posC > toCol)
			{
				posR--;
				posC--;
				moveCount++;
			}
		}
		return moveCount;
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	/*
	 * Count the number of markers (non-empty squares) in the specified row.
	 */
	
	private static int rowCount(int row) { 
		if ((row < 0) || (row >= size)) {
			return INVALID;
		}
		int count = 0;
		for (int col = 0; col < size; col++) {
			if (board[row][col] != EMPTY) {
				count += 1;
			}
		}
		return count;
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	/*
	 * Count the number of markers (non-empty squares) in the specified column.
	 */
	
	private static int colCount(int col) {
		if ((col < 0) || (col >= size)) {
			return INVALID;
		}
		int count = 0;
		for (int row = 0; row < size; row++) {
			if (board[col][row] != EMPTY) {
				count += 1;
			}
		}
		return count;
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	/*
	 * Count the number of markers (non-empty squares) in the diagonal that runs
	 * from the north-east corner to the south-west corner of the board, and
	 * that passes through the specified row and column.
	 */
	
	private static int diagNortheastCount(int row, int col) {
		if ((row < 0) || (row >= size)) {
			return -1;
		}
		if ((col < 0) || (col >= size)) {
			return -1;
		}
		int count = 0;
		for (int r = row, c = col; (r < size) && (c < size); r++, c++) {
			if (board[r][c] != EMPTY) {
				count += 1;
			}
		}
		for (int r = row - 1, c = col - 1; (r >= 0) && (c >= 0); r--, c--) {
			if (board[r][c] != EMPTY) {
				count += 1;
			}
		}
		return count;
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	/*
	 * Count the number of markers (non-empty squares) in the diagonal that runs
	 * from the north-west corner to the south-east corner of the board, and
	 * that passes through the specified row and column.
	 */
	
	private static int diagNorthwestCount(int row, int col) {
		if ((row < 0) || (row >= size)) {
			return -1;
		}
		if ((col < 0) || (col >= size)) {
			return -1;
		}
		int count = 0;
		for (int r = row, c = col; (r < size) && (c >= 0); r++, c--) {
			if (board[r][c] != EMPTY) {
				count += 1;
			}
		}
		for (int r = row - 1, c = col + 1; (r >= 0) && (c < size); r--, c++) {
			if (board[r][c] != EMPTY) {
				count += 1;
			}
		}
		return count;
	}
//--------------------------------------------------------------------------------------------------------------------------------------
	public static boolean hasWon(int player) { // method checks whether current player has won
		return Util.isConnected(board, player);
	}
//--------------------------------------------------------------------------------------------------------------------------------------
}
