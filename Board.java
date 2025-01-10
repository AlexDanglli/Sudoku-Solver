package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {
	
	private Cell[][] board = new Cell[9][9];
	private String level;
	
	public Board() {
		
		for(int x=0; x<9; x++) {
			for(int y=0; y<9; y++) {
				board[x][y] = new Cell();
				board[x][y].setBoxID(3*(x/3) + y/(3+1));
			}
		}

	}
	
	public void loadPuzzle(String level) throws FileNotFoundException  {
		
		this.level = level;
		String fileName = "easy.txt";
		if(level.contentEquals("medium"))
			fileName = "mediumPuzzle.txt";
		else if(level.contentEquals("hard"))
			fileName = "hardPuzzle.txt";
		
		Scanner input = new Scanner (new File(fileName));
		
		for(int x = 0; x < 9; x++)
			for(int y = 0 ; y < 9; y++)
			{
				int number = input.nextInt();
				if(number != 0)
					solve(x, y, number);
			}
						
		input.close();
		
	}
	
	public boolean isSolved() {
		
		boolean solved = true;
		
		for(int x = 0; x < board.length; x++)
		{
			for(int y = 0; y < board[0].length; y++)
			{
				if(board[x][y].getNumber() == 0)
				{
					solved = false;
				}
			}
		}
		
		return solved;
		
	}
	
	public void display() {
		
		System.out.println("_______________________");
		System.out.println(" ");
		
		for(int x = 0; x < board.length; x++)
		{
			System.out.print("|");
			for(int y = 0; y < board[0].length; y++)
			{
				System.out.print(board[x][y].getNumber() + " ");
				if(y % 3 == 2)
				{
					System.out.print("| ");
				}
			}
			if(x % 3 == 2)
			{
				System.out.println(" ");
				System.out.println("_______________________");
			}
			System.out.println(" ");
		}
		
		System.out.println(" ");
		
		
		
	}
	
	public void solve(int x, int y, int number) {
		
		board[x][y].setNumber(number);
		
		for(int a = 0; a < x; a++)
		{
			board[a][y].cantBe(number);
		}
		for(int a = x + 1; a < board.length; a++)
		{
			board[a][y].cantBe(number);
		}
		
		for(int a = 0; a < y; a++)
		{
			board[x][a].cantBe(number);
		}
		for(int a = y + 1; a < board[0].length; a++)
		{
			board[x][a].cantBe(number);
		}
		
		for(int a = 0; a < board.length; a++)
		{
			for(int b = 0; b < board[0].length; b++)
			{
				if(board[a][b].getBoxID() == board[x][y].getBoxID() && a != x && b != y)
				{
					board[a][b].cantBe(number);	
				}
			}
		}	
		
		
	}
	
	public void logicCycles() {
		while(isSolved() == false) {
			int changesMade = 0;
			do {
				changesMade = 0;
				changesMade += logic1();
				changesMade += logic2();
				changesMade += logic3();
				changesMade += logic4();
				if(errorFound())
					break;
				}while(changesMade != 0);
		}
		
	}
	
	public int logic1() {
		int changesMade = 0;
		
		for(int x = 0; x < board.length; x++)
		{
			for(int y = 0; y < board[0].length; y++)
			{
				if(board[x][y].getNumber() == 0 && board[x][y].numberOfPotentials() == 1)
				{
					int number = 0;
					
					for(int z = 0; z < 10; z++)
					{
						if(board[x][y].canBe(z))
						{
							number = z;
						}
					}
					
					solve(x, y, number);
					changesMade++;
				}
			}
		}
		
		
		return changesMade;
	}
	
	public int logic2() {
		int changesMade = 0;
		
		for(int x = 0; x < board.length; x++)
		{
			for(int z = 0; z < 10; z++)
			{
				int canBe = 0;
				int column = 0;
				
				for(int y = 0; y < board[x].length; y++)
				{	
					if(board[x][y].getNumber() == 0 && board[x][y].canBe(z))
					{
						canBe++;
						column = y;
					}
				}
				
				if(canBe == 1)
				{
					solve(x, column, z);
					changesMade++;
				}	
			}
		}
		
		for(int y = 0; y < board[0].length; y++)
		{
			for(int z = 0; z < 10; z++)
			{
				int canBe = 0;
				int row = 0;
				
				for(int x = 0; x < board.length; x++)
				{	
					if(board[x][y].getNumber() == 0 && board[x][y].canBe(z))
					{
						canBe++;
						row = x;
					}
				}
				
				if(canBe == 1)
				{
					solve(row, y, z);
					changesMade++;
				}	
			}
		}
		
		
		return changesMade;
	}
	
	public int logic3() {
		int changesMade = 0;
		
		for(int z = 0; z < 9; z++)
		{
			int canBe = 0;
			int column = 0;
			int row = 0;
			
			for(int x = 0; x < board.length; x++)
			{
				for(int y = 0; y < board[0].length; y++)
				{	
					for(int n = 0; n < 10; n++)
					{
						if(board[x][y].getBoxID() == z && board[x][y].getNumber() == 0 && board[x][y].canBe(n))
						{
							canBe++;
							row = x;
							column = y;
						}		
					}	
					
					if(canBe == 1)
					{
						solve(row, column, z);
						changesMade++;
					}
				}
			}
		}
		
		
		return changesMade;	
	}
	
	public int logic4() {
		int changesMade = 0;
		
		for (int row = 0; row < board.length; row++) 
	    {
	        for (int i = 0; i < board[row].length - 1; i++) 
	        {
	            if (board[row][i].getNumber() == 0 && board[row][i].numberOfPotentials() == 2) 
	            {
	                for (int j = i + 1; j < board[row].length; j++) 
	                {
	                    if (board[row][j].getNumber() == 0 && board[row][j].numberOfPotentials() == 2) 
	                    {
	                        if (board[row][i].getPotential() == board[row][j].getPotential()) 
	                        {
	                            for (int k = 0; k < board[row].length; k++) 
	                            {
	                                if (board[row][k].getNumber() == 0 && k != i && k != j) 
	                                {
	                                    if (board[row][k].canBe(board[row][i].getFirstPotential())) 
	                                    {
	                                        board[row][k].cantBe(board[row][i].getFirstPotential());
	                                        changesMade++;
	                                    }
	                                    if (board[row][k].canBe(board[row][i].getSecondPotential())) 
	                                    {
	                                        board[row][k].cantBe(board[row][i].getSecondPotential());
	                                        changesMade++;
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    } 
		
		 for (int col = 0; col < board[0].length; col++) 
		    {
		        for (int i = 0; i < board.length - 1; i++) 
		        {
		            if (board[i][col].getNumber() == 0 && board[i][col].numberOfPotentials() == 2) 
		            {
		                for (int j = i + 1; j < board.length; j++) 
		                {
		                    if (board[j][col].getNumber() == 0 && board[j][col].numberOfPotentials() == 2) 
		                    {
		                        if (board[i][col].getPotential() == board[j][col].getPotential()) 
		                        {
		                            for (int k = 0; k < board.length; k++) 
		                            {
		                                if (board[k][col].getNumber() == 0 && k != i && k != j) 
		                                {
		                                    if (board[k][col].canBe(board[i][col].getFirstPotential())) 
		                                    {
		                                        board[k][col].cantBe(board[i][col].getFirstPotential());
		                                        changesMade++;
		                                    }
		                                    if (board[k][col].canBe(board[i][col].getSecondPotential())) 
		                                    {
		                                        board[k][col].cantBe(board[i][col].getSecondPotential());
		                                        changesMade++;
		                                    }
		                                }
		                            }
		                        }
		                    }
		                }
		            }
		        }
		    }    
		
		return changesMade;	
	}
	
	public boolean errorFound() {
		
boolean errorFound = false;
		
		for(int x = 0; x < board.length; x++)
		{
			for(int y = 0; y < board[0].length; y++)
			{
				if(board[x][y].getFirstPotential() == -1)
				{
					errorFound = true;
				}	
			}
		}
		
		return errorFound;
		
		
	}
	
	
	
}
