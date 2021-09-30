
import java.awt.Color;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class TestLOA
{
		private static Board test = new Board();
		private static Player compTurn = new Player();
		private static int size, computerPlayer, userPlayer, userPlayer2, currentPlayer;
		private static String playerMove, moveSelection, ip;
		private static Scanner sc = new Scanner(System.in);
		private static boolean checkOne = false;
		
		public static int mode;
		
//--------------------------------------------------------------------------------------------------------------------------------------
	  public static void main(String[]  args)
	  {
		  size = Integer.parseInt(args[0]);
	      computerPlayer = 1;
	      userPlayer = 2;
	      currentPlayer = 2;	      
	    
	    if(args.length < 2)
	    {
	    	System.out.println("ERROR: too few arguments");
	    	System.exit(0);
	    }
	    
	    if(size < 4 || size > 16)
	    {
	    	System.out.println("ERROR: illegal size");
	    	System.exit(0);
	    }
	    else
	    {
	    	mode = Integer.parseInt(args[1]);
	        if(mode < 0 || mode > 3)
	        {
	        	System.out.println("ERROR: illegal mode");
	        	System.exit(0);
	        }
	        else
	        {
	        	if(mode == 0)
	        	{
	        		moveSelection = args[2];
	                testMode();
	        	}
	        	else if( mode == 1)
	        	{
	        		moveSelection = args[2];
	        		singleplayerMode();
	        		            
	        	}
	        	else if(mode == 2)
	        	{
	        			ip = args[2];
	        			int argsLen = args.length;
		        		multiplayerMode(ip, argsLen);
	        		
	        	}
	        	else if(mode == 3)
	        	{
	        		try {
						GUIMode();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        }
	    }
	  }
//----------------------------------------------------------------------------------------------------------------------------------------
	  public static void testMode() // method runs test mode
	  {
		  test.newBoard(size);
          playerMove = moveSelection;
          
          if(playerMove.equals("QUIT"))
          {
          	System.out.println("Player Quit");
          	System.exit(0);
          }
          else if(playerMove.length() > 4 || playerMove.length() < 4)
          {
          	System.out.println("ERROR: invalid move");
          	System.exit(0);
          }
          else
          {
          	System.out.println("\n" + playerMove + "\n");
          	test.moveConversion(playerMove, userPlayer);
          	
          	if(Board.hasWon(currentPlayer))
          	{
          		System.out.println("WINNER: " + checkPlayer());
          	}
          	else
          	{
          		System.out.println("\n" + "DRAW");
          	}
          }
	  }
//----------------------------------------------------------------------------------------------------------------------------------------
	  public static void singleplayerMode() // method runs single player mode
	  {
		checkOne = false;	        		       		
  		test.newBoard(size);        		
  		playerMove = "Default";
  		
  		while(Board.hasWon(currentPlayer) == false)
  		{
  			while(checkOne == false)
              {
  				currentPlayer = userPlayer;
  				
  				playerMove = sc.next();
  				
              	if(playerMove.equals("QUIT"))
                  {
                  	System.out.println("Player Quit");
                  	System.exit(0);
                  }
              	else if(playerMove.length() > 4 || playerMove.length() < 4)
                  {
                  	System.out.println("ERROR: invalid move");
                  }
              	else
              	{
              		checkOne = true;
              	}
              }
              System.out.println("\n" + playerMove + "\n");
          	test.moveConversion(playerMove, currentPlayer);
          	
          	currentPlayer = computerPlayer;
          	compTurn.checkIfValid(size);
          	
          	playerMove = sc.next();
  		}
      	
      	if(Board.hasWon(currentPlayer))
      	{
      		System.out.println("\nWINNER: " + currentPlayer);
      	}
      	else
      	{
      		System.out.println("\nDRAW");
      	}
	  }
//----------------------------------------------------------------------------------------------------------------------------------------
	  public static void multiplayerMode(String connectTo, int argsLen) // method runs multiplayer mode
	  {
		int c = 0;
		String bSize;
		if(argsLen < 3)
  		{
  			System.out.println("ERROR: Too Few Arguments");
  		}
  		else if (argsLen > 3)
  		{
  			c = Networking.connect(connectTo, true);
  		}
  		else
  		{
  			c = Networking.connect(connectTo);  			
  		}
		
		if (c == Networking.SERVER_MODE) {
			System.out.println("(Server mode)");
			userPlayer = Board.WHITE;
			userPlayer2 = Board.BLACK;
			
			System.out.println("Sending Board Size");
			bSize = Integer.toString(size);
			Networking.write(bSize);
			
			if(currentPlayer == userPlayer)
			{
				checkOne = false;	        		       		
		  		test.newBoard(size);        		
		  		
		  		while(Board.hasWon(currentPlayer) == false)
		  		{
		  			while(checkOne == false)
		              {
		  				
		  				playerMove = sc.next();
		  				
		              	if(playerMove.equals("QUIT"))
		                  {
		                  	System.out.println("Player Quit");
		                  	Networking.write("Player has quit.");
		                  	System.exit(0);
		                  }
		              	else if(playerMove.length() > 4 || playerMove.length() < 4)
		                  {
		                  	System.out.println("ERROR: invalid move");
		                  }
		              	else
		              	{
		              		checkOne = true;
		              	}
		              }
		            System.out.println("\n" + playerMove + "\n");
		          	test.moveConversion(playerMove, currentPlayer);
		          	Networking.write(playerMove);
		  		}
			}
			else
			{
				currentPlayer = userPlayer2;
				String player2Move = Networking.read();
				if(player2Move.equals("QUIT"))
				{
					System.out.println("Player 2 has quit");
				}
				else
				{
					test.moveConversion(player2Move, currentPlayer);
				}
			}
			
			System.out.println("Closing connection");
			Networking.close();
		} else if (c == Networking.CLIENT_MODE) {
			System.out.println("(Client mode)");
			userPlayer = Board.WHITE;
			userPlayer2 = Board.BLACK;
			
			System.out.println("Waiting for Board Size...");
			bSize = Networking.read();
			
			if(currentPlayer == userPlayer)
			{
				checkOne = false;	        		       		
		  		test.newBoard(size);        		
		  		
		  		while(Board.hasWon(currentPlayer) == false)
		  		{
		  			while(checkOne == false)
		              {
		  				
		  				playerMove = sc.next();
		  				
		              	if(playerMove.equals("QUIT"))
		                  {
		                  	System.out.println("Player Quit");
		                  	Networking.write("Player has quit.");
		                  	System.exit(0);
		                  }
		              	else if(playerMove.length() > 4 || playerMove.length() < 4)
		                  {
		                  	System.out.println("ERROR: invalid move");
		                  }
		              	else
		              	{
		              		checkOne = true;
		              	}
		              }
		            System.out.println("\n" + playerMove + "\n");
		          	test.moveConversion(playerMove, currentPlayer);
		          	Networking.write(playerMove);
		  		}
			}
			else
			{
				currentPlayer = userPlayer2;
				String player2Move = Networking.read();
				if(player2Move.equals("QUIT"))
				{
					System.out.println("Player 2 has quit");
				}
				else
				{
					test.moveConversion(player2Move, currentPlayer);
				}
			}
			
			System.out.println("Closing connection");
			Networking.close();
		} else {
			System.out.println("Connection error");
		}
	  }
//-----------------------------------------------------------------------------------------------------------
	  public static void GUISetup()
	  {
		StdDraw.init();
		StdDraw.setCanvasSize(1000, 1000);
		StdDraw.setXscale(0, size+1);
     	StdDraw.setYscale(0, size+1);
	    
	    test.newBoard(size);
	    drawGUI();    
	  }
//----------------------------------------------------------------------------------------------------------------------------------------
	  public static void GUIMode() throws InterruptedException // method runs graphical user interface mode
	  {
		double fX = 0.0, fY = 0.0, tX = 0.0, tY = 0.0;
		int p;
		String cP;    		       		       		
  		
		GUISetup();
		
		while(Board.hasWon(currentPlayer) == false)
  		{
			
			cP =  checkPlayer();
	  		StdDraw.setPenColor(Color.BLACK);
	  		StdDraw.text(0.5, (size+1)-0.25, "");
			StdDraw.text(0.5, (size+1)-0.25, cP);
			
	  		if(cP.equals("User Player"))
	  		{
	  			p = 2;
	  		}
	  		else
	  		{
	  			p = 1;
	  		}
	  		
	  		Thread.sleep(100);
	  		while (!StdDraw.mousePressed())
	  		{Thread.sleep(50);}
	  			fX = Math.round(StdDraw.mouseX());
		  	    fY = Math.round(StdDraw.mouseY());
		  	    
		  	  if((Math.round(fX) == size+1) && (Math.round(fY) == size+1) && StdDraw.mousePressed())
	  		  {
		  			 JOptionPane.showMessageDialog(null, "Game has been QUIT");
					 System.exit(0);
	  		  } 

		  	  Thread.sleep(150);
	  		  while(!StdDraw.mousePressed())

	  		      Thread.sleep(150);	 
  		    	  tX = Math.round(StdDraw.mouseX());
  				  tY = Math.round(StdDraw.mouseY());
  				  
  				  if(Board.isValidMove(p, (int) Math.abs(fY-size), (int) Math.abs(fX-1), (int) Math.abs(tY-size), (int) Math.abs(tX-1)))
  				  {
  					Board.makeMove(p, (int) Math.abs(fY-size), (int) Math.abs(fX-1), (int) Math.abs(tY-size), (int) Math.abs(tX-1));
  					drawGUI();
  					StdDraw.clear();
  					currentPlayer = nextPlayer(currentPlayer);
  					compTurn.checkIfValid(size);
  					drawGUI();
  					currentPlayer = nextPlayer(currentPlayer);
  				  }
  				  else if((Math.round(tX) == size+1) && (Math.round(tY) == size+1) && StdDraw.mousePressed())
		  		  {
			  			 JOptionPane.showMessageDialog(null, "Game has been QUIT");
						 System.exit(0);
		  		  }
  				  else
  				  {
  					JOptionPane.showMessageDialog(null, "ERROR: Invalid Move");
  					Thread.sleep(100);	 
  				  }
	  		}
		JOptionPane.showMessageDialog(null,  "Game Over\nWinner: " + checkPlayer());
		System.exit(0);
	  }
//----------------------------------------------------------------------------------------------------------------------------------------
	  public static void drawGUI() // method renders graphical user interface of the board
		{
		  StdDraw.setPenColor(Color.RED);
		  StdDraw.filledSquare(size+1, size+1, 0.5);
			for(int r = 0; r < size; r++)
			  {  
				  for(int c = 0; c < size; c++)
				  {
					  if((r+c) % 2 == 0)
					  {
						  StdDraw.setPenColor(Color.YELLOW);
						  StdDraw.filledSquare((c+1), (r+1), 0.5);
					  }
					  else
					  {
						  StdDraw.setPenColor(Color.GRAY);
						  StdDraw.filledSquare((c+1), (r+1), 0.5);
					  }
				  }
			  }
			for(int r = 0; r < size; r++)
			{
				for(int c = 0; c < size; c++)
				{
					if(Board.getPiece(r, c) == Board.BLACK)
					  {
						  StdDraw.setPenColor(Color.BLACK);
						  StdDraw.filledCircle(Math.abs(c+1), Math.abs(r-size), 0.45);
					  }
					  if(Board.getPiece(r, c) == Board.WHITE)
					  {
						  StdDraw.setPenColor(Color.WHITE);
						  StdDraw.filledCircle((c+1), Math.abs(r-size), 0.45);
					  }
				}
			}
		}
//----------------------------------------------------------------------------------------------------------------------------------------
	  public static String checkPlayer() // method checks current player
	  {
		  if(currentPlayer == 2)
		  {
			  StdDraw.setPenColor(Color.BLACK);
			  StdDraw.filledCircle(1.25, (size+1)-0.25, 0.15);
			  return "User Player";
		  }
		  else if(currentPlayer == 1)
		  {
			  StdDraw.setPenColor(Color.WHITE);
			  StdDraw.circle(1.25, (size+1)-0.25, 0.15);
			  return "Computer Ai";
		  }
		  else
		  {
			  return "ERROR";
		  }
	  }
//----------------------------------------------------------------------------------------------------------------------------------------
	  public static int nextPlayer(int cP) // method determines and sets next player 
	  {
		  int nextPlayer;
		  
		  if(cP == 2)
		  {
			  nextPlayer = 1;
		  }
		  else
		  {
			  nextPlayer = 2;
		  }
		  return nextPlayer;
	  }
//----------------------------------------------------------------------------------------------------------------------------------------
}
