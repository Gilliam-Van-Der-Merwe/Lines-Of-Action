
public class Player
{
	//--------------------------------------------------------------------------------------------------------------------------------------
	private static Board utility = new Board(); // instance of Board class
	private static int limitSize;
	//--------------------------------------------------------------------------------------------------------------------------------------
	public static int randomComputerRow() // Generate random row value
	{
		int rCR = (int) (Math.random() * limitSize) + 0;
		return rCR;
	}
	//--------------------------------------------------------------------------------------------------------------------------------------
	public static int randomComputerCol() // Generate random column value 
	{
		int rCC = (int) (Math.random() * limitSize) + 0;
		return rCC;
	}
	//--------------------------------------------------------------------------------------------------------------------------------------
	public void checkIfValid(int boardSize) // check validity of computer move and make valid move
	{
		boolean valid = false;
		limitSize = boardSize;
		int fromRow, fromCol, toRow, toCol;
		
		while(valid == false)
		{
			fromRow = randomComputerRow();
			fromCol = randomComputerCol();
			toRow = randomComputerRow();
			toCol = randomComputerCol();
			
			if(Board.getPiece(fromRow, fromCol) == 1)
			{
				valid = Board.isValidMove(1, fromRow, fromCol, toRow, toCol);
				if(valid == true)
				{
					Board.makeMove(1, fromRow, fromCol, toRow, toCol);
					Board.ValidSquareMovementCount(fromRow, fromCol);
				}
			}		
		}
	}
	//--------------------------------------------------------------------------------------------------------------------------------------
	public static int moveC(int fromRow, int fromCol, int toRow, int toCol ) // return move count made
	{
		return Board.SquareMoveCount(fromRow, fromCol, toRow, toCol);
	}
	//--------------------------------------------------------------------------------------------------------------------------------------
}
